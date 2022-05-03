package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.UserDto;
import ru.geekbrains.persist.Role;
import ru.geekbrains.service.RoleService;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/users")
@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @GetMapping
    public String listPage(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size,
            @RequestParam(name = "sortField", required = false) String sort,
            Model model) {
        Integer pageValue = page == null ? 0 : page - 1;
        Integer sizeValue = size == null ? 5 : size;
        String sortValue = sort == null || sort.isEmpty() ? "id" : sort;
        model.addAttribute("users", userService.getUsersByFilter(pageValue, sizeValue, sortValue));
        return "users";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @GetMapping("/{id}")
    public String form(@PathVariable("id") long id, Model model) {
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("user", userService.findById(id).get());
        return "user_form";
    }

    @Secured("ROLE_SUPERADMIN")
    @GetMapping("/delete/{id}")
    public String remove(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @Secured("ROLE_SUPERADMIN")
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERADMIN"})
    @PostMapping
    public String save(@Valid @ModelAttribute("user") UserDto user, BindingResult binding, Model model, Authentication auth) {
        if (binding.hasErrors()) {
            model.addAttribute("roles", roleService.getRoles());
            return "user_form";
        }
        if (!user.getPassword().equals(user.getPasswordToMatch())) {
            model.addAttribute("roles", roleService.getRoles());
            binding.rejectValue("password", "", "Password not match");
            return "user_form";
        }
        if (!auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).contains("SUPERADMIN")) {
            user.setRoles(getRoles(user.getId()));
        }
        userService.save(user);

        return "redirect:/users";
    }

    private Set<Role> getRoles(Long id) {
        return userService.findById(id).get().getRoles();
    }
}
