package ec.edu.uce.Records;

import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Singleton
@QualifierPayment("productRec")
public class ProductRecord {
    private int productID;
    private String productName;
    private int quantity;
    private double price;
    public ProductRecord() {

    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    //public  String getdata(){
      //  return String.format("ID: %s\nName: %s\nQuantity: %d\nPrice: %.2f",
       //         this.productID, this.productName, (int)this.quantity, this.price);
    //}



}
