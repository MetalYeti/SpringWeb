package ru.geekbrains.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Cart {
    private final Set<Product> products;

    private ProductRepository repository;

    public Cart() {
        products = new HashSet<>();
    }

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    public void removeProduct(long id) {
        Product product = repository.findById(id);
        if (products.contains(product)) {
            products.remove(product);
        } else {
            System.out.println("No such product in the Cart!");
        }
    }

    public void addProduct(long id) {
        this.products.add(repository.findById(id));
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                '}';
    }
}
