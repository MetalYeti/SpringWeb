package ru.geekbrains.components;

import ru.geekbrains.dto.CustomerDto;
import ru.geekbrains.dto.ProductDto;

public class LineItem {

    private ProductDto product;
    private CustomerDto customer;
    private int productCount;

    public LineItem(ProductDto product, CustomerDto customer, int productCount) {
        this.product = product;
        this.customer = customer;
        this.productCount = productCount;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "product=" + product +
                ", customer=" + customer +
                ", productCount=" + productCount +
                '}';
    }
}
