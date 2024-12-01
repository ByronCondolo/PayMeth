package ec.edu.uce.jpa;

import jakarta.persistence.EntityManager;

public class MessageService {
    private EntityManager em;
    public MessageService(EntityManager em) {
        this.em = em;
    }

    public void create(Messege messege){
        em.getTransaction().begin();
        em.persist(messege);
        em.getTransaction().commit();
    }

    public Messege read(int id){
        return em.find(Messege.class, id);
    }
    public Messege findByID(int id) {

        return em.find(Messege.class, id);
    }

    public void updateStudent(Messege messege) {
        em.getTransaction().begin();
        em.merge(messege);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        em.getTransaction().begin();
        Messege messege = em.find(Messege.class, id);
        em.remove(messege);
        em.getTransaction().commit();
    }

}
