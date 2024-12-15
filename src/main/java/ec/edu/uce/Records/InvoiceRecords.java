package ec.edu.uce.Records;
import ec.edu.uce.interfaces.QualifierPayment;
import ec.edu.uce.jpa.Account;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Date;
import java.util.List;

@ApplicationScoped
@Singleton
@QualifierPayment("invoiceRec")
public class InvoiceRecords {
    private int id;
    private Date date;
    private String method_pay;
    private ClientRecord client_Record;
    private List<ProductRecord> product_RecordList;
    private double amount_to_pay;

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

    public double getAmount_to_pay() {
        return amount_to_pay;
    }

    public ClientRecord getClient_Record() {
        return client_Record;
    }

    public void setClient_Record(ClientRecord client_Record) {
        this.client_Record = client_Record;
    }

    public List<ProductRecord> getProduct_RecordList() {
        return product_RecordList;
    }

    public void setProduct_RecordList(List<ProductRecord> product_RecordList) {
        this.product_RecordList = product_RecordList;
    }

    public void setAmount_to_pay(double amount_to_pay) {
        this.amount_to_pay = amount_to_pay;
    }

    public InvoiceRecords() {
    }
    public  String getdata(){
        StringBuilder data = new StringBuilder();
        for (ProductRecord p : product_RecordList) {
            data.append("\n------------------------------")
            .append("\n Product id: ")
            .append(p.getProductID())
            .append("\n Product name: ")
            .append(p.getProductName())
            .append("\n Product price: ")
            .append(p.getPrice())
            .append("\n Quantity: ")
            .append(p.getQuantity())
            .append("\n------------------------------");
        }
        StringBuilder data2 = new StringBuilder();
        for (Account a : client_Record.getAccounts())
        {
            data.append("\n------------------------------")
                    .append("\n Account id: ")
                    .append(a.getId())
                    .append("\n Account number: ")
                    .append(a.getNumber())
                    .append("\n Account type: ")
                    .append(a.getType())
                    .append("\n------------------------------");
        }

        return String.format("\nId Invoice: %d," +
                "\nDate: %s," +
                "\nMethod: %s," +
                "\nClient: %s," +
                "\nCi: %d" +
                "\nEmail: %s" +
                "\nPhone: %s" +
                "\nAccounts: %s"+
                "\nProducts: %s" +
                "\nAmount: %.2f" ,
                id,
                date,
                method_pay,
                client_Record.getName(),
                client_Record.getCi(),
                client_Record.getEmail(),
                client_Record.getPhone(),
                data2,
                data,
                amount_to_pay);
    }
}
