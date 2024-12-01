package ec.edu.uce.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "persistence_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID_Product")
    private int id;
    @Column(name = "Name_Product")
    private String name;
    @Column(name = "Quantity_Product")
    private int quantity;
    @Column(name = "Price_Product")
    private double price;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
