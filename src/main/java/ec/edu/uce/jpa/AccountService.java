package ec.edu.uce.jpa;

import jakarta.persistence.EntityManager;

public class AccountService {
    private EntityManager em;
    public AccountService(EntityManager em) {
        this.em = em;
    }

    public void createClient(Account card) {
        em.getTransaction().begin();
        em.persist(card);//actualiza todos los dato
        em.getTransaction().commit();
    }

}
