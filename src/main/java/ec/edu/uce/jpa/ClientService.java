package ec.edu.uce.jpa;

import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
@QualifierPayment("clientService")
public class ClientService {

    private EntityManager em ;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");

    public ClientService() {

        this.em = emf.createEntityManager();
    }

    public void createClientWhitAccount(Client client,Account account) {
        em.getTransaction().begin();
        em.persist(account);
        em.persist(client);//actualiza todos los datos
        em.getTransaction().commit();
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
        // Utilizamos un JOIN entre Client y Account
        TypedQuery<Client> query = em.createQuery(
                "SELECT c FROM Client c JOIN FETCH c.accounts", Client.class
        );
        return query.getResultList();
    }

}
