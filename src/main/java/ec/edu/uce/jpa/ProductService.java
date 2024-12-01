package ec.edu.uce.jpa;

import jakarta.persistence.EntityManager;

public class ProductService {
    private EntityManager em;
    public ProductService(EntityManager em) {
        this.em = em;
    }

    public Product createProduct(String name, String quantity, int price) {
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        em.persist(product);//actualiza todos los datos
        return product;
    }

    public Product findByName(String name) {

        return em.find(Product.class, name);
    }


    public void updateProduct(Product product) {
        em.getTransaction().begin();
        em.merge(product);
        em.getTransaction().commit();
    }

    public void delete(String name) {
        Product product = findByName(name);
        em.getTransaction().begin();
        em.remove(product);
        em.getTransaction().commit();
    }


}
