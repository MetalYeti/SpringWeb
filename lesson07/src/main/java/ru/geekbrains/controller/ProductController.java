package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.validation.Valid;

@RequestMapping("/products")
@Controller
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listPage(@RequestParam(name = "minCost",required = false) Long minCost, @RequestParam(name = "maxCost",required = false) Long maxCost, Model model) {
        if (minCost == null && maxCost == null) {
            model.addAttribute("products", productRepository.findAll());
        } else {
            if (minCost == null) {
                model.addAttribute("products", productRepository.getProductsByCostBefore(maxCost));
            } else if (maxCost == null) {
                model.addAttribute("products", productRepository.getProductsByCostAfter(minCost));
            } else {
                model.addAttribute("products", productRepository.getProductsByCostBetween(minCost, maxCost));
            }
        }
        return "products";
    }

    @GetMapping("/{id}")
    public String form(@PathVariable("id") long id, Model model) {
        model.addAttribute("product", productRepository.findById(id));
        return "product_form";
    }

    @GetMapping("/delete/{id}")
    public String remove(@PathVariable("id") long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("product", new Product("", 0));
        return "product_form";
    }

    @PostMapping
    public String save(@Valid Product product, BindingResult binding) {
        if (binding.hasErrors()) {
            return "product_form";
        }
        productRepository.save(product);
        return "redirect:/products";
    }
}
