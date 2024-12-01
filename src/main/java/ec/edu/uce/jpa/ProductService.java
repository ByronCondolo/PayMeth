package ec.edu.uce.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductService {
    private EntityManager em;
    public ProductService(EntityManager em) {
        this.em = em;
    }

    public Product createProduct(String name, int quantity, double price) {
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        em.persist(product);//actualiza todos los datos
        return product;
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
