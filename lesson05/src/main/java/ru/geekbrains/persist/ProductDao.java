package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Function;

public class ProductDao {
    private final EntityManagerFactory entityManagerFactory;

    public ProductDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Product> findAll() {
        return emNoTransactionOperation(em -> em.createQuery("from Product").getResultList());
    }

    public Product findById(long id) {
        return emNoTransactionOperation(em -> em.find(Product.class, id));
    }

    public Product save(Product product) {
        EntityManager em = entityManagerFactory.createEntityManager();
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
        EntityManager em = entityManagerFactory.createEntityManager();
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
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return func.apply(em);
        } finally {
            em.close();
        }
    }
}
