package ru.geekbrains.persist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> getProductsByCostBefore(Long maxCost, Pageable pageable);
    Page<Product> getProductsByCostAfter(Long minCost, Pageable pageable);
    Page<Product> getProductsByCostBetween(Long minCost, Long maxCost, Pageable pageable);
}
