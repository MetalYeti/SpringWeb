package ru.geekbrains.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.geekbrains.util.DBService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

@Repository
public class ProductDao {
    private final DBService dbService;

    @Autowired
    public ProductDao(DBService dbService) {
        this.dbService = dbService;
    }

    public List<Product> findAll() {
        return emNoTransactionOperation(em -> em.createQuery("from Product").getResultList());
    }

    public Product findById(long id) {
        return emNoTransactionOperation(em -> em.find(Product.class, id));
    }

    public Product save(Product product) {
        EntityManager em = dbService.getManager();
        em.getTransaction().begin();
        try {
            if (product.getId() == null) {
                em.persist(product);
            } else {
                em.merge(product);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return product;
    }

    public void delete(long id) {
        EntityManager em = dbService.getManager();
        em.getTransaction().begin();
        try {
            Product deleteCandidate = em.find(Product.class, id);
            if (deleteCandidate != null) {
                em.remove(deleteCandidate);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private <R> R emNoTransactionOperation(Function<EntityManager, R> func) {
        EntityManager em = dbService.getManager();
        try {
            return func.apply(em);
        } finally {
            em.close();
        }
    }
}
