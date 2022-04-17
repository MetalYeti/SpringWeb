package ru.geekbrains.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.Product;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

@Service
public class CustomerProductInfo {
    DBService dbService;

    @Autowired
    public CustomerProductInfo(DBService dbService) {
        this.dbService = dbService;
    }

    public List<Customer> getCustomersByProductId(Long id) {
        return emOperation(em -> em.find(Product.class, id).getCustomers());
    }

    public List<Product> getProductsByCustomerId(Long id) {
        return emOperation(em -> em.find(Customer.class, id).getProducts());
    }

    private <R> R emOperation(Function<EntityManager, R> func) {
        EntityManager em = dbService.getManager();
        try {
            em.getTransaction().begin();
            return func.apply(em);
        } catch (Exception ex) {
            em.getTransaction().rollback();
            return null;
        } finally {
            em.getTransaction().commit();
        }
    }
}
