package ec.edu.uce.jpa;

import jakarta.persistence.EntityManager;

public class ClientService {

    private EntityManager em;
    public ClientService(EntityManager em) {
        this.em = em;
    }

    public Client createClient(String name, int id) {
        Client client = new Client();
        client.setId(id);
        client.setName(name);
        em.persist(client);//actualiza todos los datos
        return client;
    }

    public Client findByID(int id) {

        return em.find(Client.class, id);
    }


    public void updateStudent(Client student) {
        em.getTransaction().begin();
        em.merge(student);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Client student = findByID(id);
        em.getTransaction().begin();
        em.remove(student);
        em.getTransaction().commit();
    }

}
