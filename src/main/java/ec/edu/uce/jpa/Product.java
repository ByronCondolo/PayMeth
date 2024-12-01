package ec.edu.uce.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "persistence_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_Product")
    private int id;
    @Column(name = "Name_Product")
    private String name;
    @Column(name = "Quantity_Product")
    private String quantity;
    @Column(name = "Price_Product")
    private int price;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
