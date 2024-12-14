package ec.edu.uce.Records;
import ec.edu.uce.interfaces.QualifierPayment;
import ec.edu.uce.jpa.Account;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@Singleton
@QualifierPayment("invoiceRec")
public class InvoiceRecords {
    private ClientRecord client_Record;
    private List<ProductRecord> product_RecordList;

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

    public InvoiceRecords() {
    }
    public  String getdata(){
        StringBuilder data = new StringBuilder();
        double amount_to_pay = 0;
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
            amount_to_pay += p.getPrice();
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

        return String.format("Client: %s," +
                "\nCi: %d" +
                "\nEmail: %s" +
                "\nPhone: %s" +
                "\nAccounts: %s"+
                "\nProducts: %s" +
                "\nAmount: %.2f" ,
                client_Record.getName(),
                client_Record.getCi(),
                client_Record.getEmail(),
                client_Record.getPhone(),
                data2,
                data,
                amount_to_pay);
    }
}
