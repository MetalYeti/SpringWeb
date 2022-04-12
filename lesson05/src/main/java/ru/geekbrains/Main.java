package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductDao;

import javax.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();

        ProductDao pd = new ProductDao(entityManagerFactory);

        System.out.println(pd.findAll());
        System.out.println(pd.findById(1));
        System.out.println(pd.save(new Product("Product 2", 550)));
        System.out.println(pd.save(new Product("Product 3", 250)));
        System.out.println(pd.save(new Product("Product 4", 1550)));
        System.out.println(pd.findAll());
        pd.delete(3);
        System.out.println(pd.findAll());

        entityManagerFactory.close();
    }
}
