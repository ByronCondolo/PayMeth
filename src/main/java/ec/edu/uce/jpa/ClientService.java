package ec.edu.uce.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ClientService {

    private EntityManager em;
    public ClientService(EntityManager em) {
        this.em = em;
    }

    public Client createClient(int ci,String name, String email, String phone, String bank_account) {
        Client client = new Client();
        client.setCi(ci);
        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        client.setBank_account(bank_account);
        em.persist(client);//actualiza todos los datos
        return client;
    }

    public Client findByID(int ci) {

        return em.find(Client.class, ci);
    }


    public void updateStudent(Client student) {
        em.getTransaction().begin();
        em.merge(student);
        em.getTransaction().commit();
    }

    public void delete(int ci) {
        Client student = findByID(ci);
        em.getTransaction().begin();
        em.remove(student);
        em.getTransaction().commit();
    }
    public List<Client> findAll() {
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }
}
