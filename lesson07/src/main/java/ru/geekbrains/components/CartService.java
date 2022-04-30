package ru.geekbrains.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.dto.CustomerDto;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomerRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final List<LineItem> lineItems;


    @Autowired
    public CartService(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.lineItems = new ArrayList<>();
    }

    public LineItem addProductToCustomer(Long customerId, Long productId, int count) {
        if (!lineItems.isEmpty()) {
            List<LineItem> list = findProductsByCustomerId(customerId).stream().filter(item -> item.getProduct().getId().equals(productId)).collect(Collectors.toList());
            if (!list.isEmpty()) {
                list.stream().findFirst().ifPresent(item -> item.setProductCount(item.getProductCount() + count));
                logger.info("Modified {}", list.get(0));
                return list.get(0);
            }
        }
        LineItem result = new LineItem(productMapper(productRepository.getById(productId)), customerMapper(customerRepository.getById(customerId)), count);
        lineItems.add(result);
        logger.info("Added {}", result);
        return result;
    }

    public void removeProductFromCustomer(Long customerId, Long productId) {
        if (!lineItems.isEmpty()) {
            List<LineItem> list = findProductsByCustomerId(customerId).stream().filter(item -> item.getProduct().getId().equals(productId)).collect(Collectors.toList());
            if (!list.isEmpty()) {
                lineItems.remove(list.get(0));
            }
        }
    }

    public List<LineItem> findCustomersByProductId(Long productId) {
        logger.info("ProductID {}", productId);
        return lineItems.stream().filter(item -> Objects.equals(item.getProduct().getId(), productId)).collect(Collectors.toList());
    }

    public List<LineItem> findProductsByCustomerId(Long customerId) {
        return lineItems.stream().filter(item -> Objects.equals(item.getCustomer().getId(), customerId)).collect(Collectors.toList());
    }

    private static ProductDto productMapper(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getCost());
    }

    private static CustomerDto customerMapper(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName());
    }

}
