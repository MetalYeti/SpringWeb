package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> getProductsByFilter(Long minCost, Long maxCost, Integer page, Integer size, String sort) {
        Page<Product> result;
        if (minCost == null && maxCost == null) {
            result = productRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
        } else {
            if (minCost == null) {
                result = productRepository.getProductsByCostBefore(maxCost, PageRequest.of(page, size, Sort.by(sort)));
            } else if (maxCost == null) {
                result = productRepository.getProductsByCostAfter(minCost, PageRequest.of(page, size, Sort.by(sort)));
            } else {
                result = productRepository.getProductsByCostBetween(minCost, maxCost, PageRequest.of(page, size, Sort.by(sort)));
            }
        }

        return result.map(ProductServiceImpl::getMapper);
    }

    @Override
    public Optional<ProductDto> findById(long id) {
        return productRepository.findById(id).map(ProductServiceImpl::getMapper);
    }

    @Override
    public ProductDto save(ProductDto product) {
        return getMapper(productRepository.save(new Product(product.getId(), product.getTitle(), product.getCost())));
    }

    @Override
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    private static ProductDto getMapper(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getCost());
    }
}
