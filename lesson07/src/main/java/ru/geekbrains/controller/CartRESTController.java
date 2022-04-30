package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.components.CartService;
import ru.geekbrains.components.LineItem;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartRESTController {

    private final CartService cartService;

    @Autowired
    public CartRESTController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/customer/{id}")
    @ResponseBody
    public List<LineItem> getProducts(@PathVariable("id") long id) {
        return cartService.findProductsByCustomerId(id);
    }

    @GetMapping("/product/{id}")
    @ResponseBody
    public List<LineItem> getCustomers(@PathVariable("id") long id) {
        return cartService.findCustomersByProductId(id);
    }

    @PostMapping
    @ResponseBody
    public LineItem addProduct(@RequestParam("customerId") long customerId, @RequestParam("productId") long productId, @RequestParam(name = "count", required = false) Integer count) {
        return cartService.addProductToCustomer(customerId, productId, count == null ? 1 : count);
    }

    @DeleteMapping("/customer/{cId}/product/{pId}")
    public void remove(@PathVariable("cId") long customerId, @PathVariable("pId") long productId) {
        cartService.removeProductFromCustomer(customerId, productId);
    }
}
