package ec.edu.uce.jpa;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int id;
    @Column(name = "invoice_Date")
    private Date date;
    @Column(name = "invoice_method_pay")
    private String method_pay;
    @Column(name = "invoice_total_purchase_value")
    private double total_purchase_value;
    public Invoice() {
    }

    public double getTotal_purchase_value() {
        return total_purchase_value;
    }

    public void setTotal_purchase_value(double total_purchase_value) {
        this.total_purchase_value = total_purchase_value;
    }

    @ManyToOne
    @JoinColumn(name = "Client_Id")
    private Client client;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMethod_pay() {
        return method_pay;
    }

    public void setMethod_pay(String method_pay) {
        this.method_pay = method_pay;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
