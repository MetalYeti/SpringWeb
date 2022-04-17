package ru.geekbrains.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.geekbrains.util.DBService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

@Repository
public class CustomerDao {
    private final DBService dbService;

    @Autowired
    public CustomerDao(DBService dbService) {
        this.dbService = dbService;
    }

    public List<Customer> findAll() {
        return emNoTransactionOperation(em -> em.createQuery("from Customer").getResultList());
    }

    public Customer findById(long id) {
        return emNoTransactionOperation(em -> em.find(Customer.class, id));
    }

    public Customer save(Customer customer) {
        EntityManager em = dbService.getManager();
        em.getTransaction().begin();
        try {
            if (customer.getId() == null) {
                em.persist(customer);
            } else {
                em.merge(customer);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return customer;
    }

    public void delete(long id) {
        EntityManager em = dbService.getManager();
        em.getTransaction().begin();
        try {
            Customer deleteCandidate = em.find(Customer.class, id);
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
