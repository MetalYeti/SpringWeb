package ru.geekbrains.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.geekbrains.persist.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class UserDto {

    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*?[0-9])(?=.*?[A-Z]).{6,}$", message = "Password too simple")
    private String password;

    @JsonIgnore
    private String passwordToMatch;

    private Set<Role> roles;

    public UserDto(Long id, String name, String email, String password, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserDto(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordToMatch() {
        return passwordToMatch;
    }

    public void setPasswordToMatch(String passwordToMatch) {
        this.passwordToMatch = passwordToMatch;
    }
}
