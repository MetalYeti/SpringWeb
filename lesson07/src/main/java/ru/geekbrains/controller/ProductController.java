package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;

@RequestMapping("/products")
@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listPage(
            @RequestParam(name = "minCost",required = false) Long minCost,
            @RequestParam(name = "maxCost",required = false) Long maxCost,
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "size",required = false) Integer size,
            @RequestParam(name = "sortField", required = false) String sort,
            Model model) {
        Integer pageValue = page == null ? 0 : page - 1;
        Integer sizeValue = size == null ? 5 : size;
        String sortValue = sort == null ? "id" : sort;
        model.addAttribute("products", productService.getProductsByFilter(minCost, maxCost, pageValue, sizeValue, sortValue));
        return "products";
    }

    @GetMapping("/{id}")
    public String form(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product_form";
    }

    @GetMapping("/delete/{id}")
    public String remove(@PathVariable("id") long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("product", new ProductDto("", 0));
        return "product_form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("product") ProductDto product, BindingResult binding) {
        if (binding.hasErrors()) {
            return "product_form";
        }
        productService.save(product);
        return "redirect:/products";
    }
}
