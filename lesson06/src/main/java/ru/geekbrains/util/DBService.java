package ru.geekbrains.util;

import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.Product;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Service
public class DBService {

    EntityManagerFactory emf;

    public DBService() {
        this.emf = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
    }

    public EntityManagerFactory getManagerFactory() {
        return emf;
    }

    public EntityManager getManager() {
        return emf.createEntityManager();
    }

    public void close(){
        emf.close();
    }

    @PreDestroy
    public void destroy(){
        close();
    }
}
