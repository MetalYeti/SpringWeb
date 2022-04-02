package ru.geekbrains.persist;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ProductRepository {
    private final Map<Long, Product> productMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.create(new Product( "Product 1", 1500));
        this.create(new Product( "Product 2", 750));
        this.create(new Product( "Product 3", 200));
        this.create(new Product( "Product 4", 800));
        this.create(new Product( "Product 5", 20));
    }

    private final AtomicLong identity = new AtomicLong(0);

    public List<Product> findAll() {
        return new ArrayList<>(productMap.values());
    }

    public Product findById(long id) {
        return productMap.get(id);
    }

    public void create(Product product) {
        long id = identity.incrementAndGet();
        product.setId(id);
        productMap.put(id, product);
    }

    public void update(Product product) {
        productMap.put(product.getId(), product);
    }

    public void delete(long id) {
        productMap.remove(id);
    }
}
