package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getProductsByCostBefore(Long maxCost);
    List<Product> getProductsByCostAfter(Long minCost);
    List<Product> getProductsByCostBetween(Long minCost, Long maxCost);
}
