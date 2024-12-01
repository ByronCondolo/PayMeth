package ec.edu.uce.jpa;

import jakarta.persistence.EntityManager;

public class ClientService {

    private EntityManager em;
    public ClientService(EntityManager em) {
        this.em = em;
    }

    public Client createStudent(String name, int id) {
        Client student = new Client();
        student.setId(id);
        student.setName(name);
        em.persist(student);//actualiza todos los datos
        return student;
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
