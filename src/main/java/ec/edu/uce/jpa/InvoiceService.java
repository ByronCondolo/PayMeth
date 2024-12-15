package ec.edu.uce.jpa;

import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
@QualifierPayment("invoiceService")
public class InvoiceService {
    private EntityManager em ;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");

    public InvoiceService() {
        this.em = emf.createEntityManager();
    }

    public void createInvoice(Invoice invoice) {
        em.getTransaction().begin();
        em.persist(invoice);//actualiza todos los datos
        em.getTransaction().commit();
    }

    public Invoice findByID(int id) {

        return em.find(Invoice.class, id);
    }


    public List<Invoice> findAllInvoicesWithClients(Client client) {
        TypedQuery<Invoice> query = em.createQuery(
                "SELECT i FROM Invoice i JOIN FETCH i.client c WHERE c = :client",
                Invoice.class
        );
        query.setParameter("client", client);
        return query.getResultList();
    }




    public void updateProduct(Invoice invoice) {
        em.getTransaction().begin();
        em.merge(invoice);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Invoice invoice = findByID(id);
        em.getTransaction().begin();
        em.remove(invoice);
        em.getTransaction().commit();
    }



}
