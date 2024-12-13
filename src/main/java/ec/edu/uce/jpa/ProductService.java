package ec.edu.uce.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductService {
    private EntityManager em;
    public ProductService(EntityManager em) {
        this.em = em;
    }

    public void createProduct(Product product) {
        em.getTransaction().begin();
        em.persist(product);//actualiza todos los datos
        em.getTransaction().commit();
    }

    public Product findByID(int id) {

        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        TypedQuery<Product> query = em.createQuery("select p from Product p", Product.class);
        return query.getResultList();
    }


    public void updateProduct(Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        Product product = findByID(id);
        em.getTransaction().begin();
        em.remove(product);
        em.getTransaction().commit();
    }


}
