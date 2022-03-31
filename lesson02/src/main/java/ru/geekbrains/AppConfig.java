package ru.geekbrains;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.geekbrains.data.Cart;
import ru.geekbrains.data.ProductRepository;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    public Cart cart() {
        return new Cart();
    }

    @Bean
    public ProductRepository products() {
        return new ProductRepository();
    }

}
