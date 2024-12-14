package ec.edu.uce.Records;
import ec.edu.uce.interfaces.QualifierPayment;
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
            data.append("\n")
            .append(p.getProductID())
            .append("\t")
            .append(p.getProductName())
            .append("\t")
            .append(p.getPrice())
            .append("\t")
            .append(p.getQuantity())
            .append("\n");
            amount_to_pay += p.getPrice();
        }

        return String.format("Client: %s," +
                "\nCi: %d" +
                "\nEmail: %s" +
                "\nPhone: %s" +
                "\nProducts: %s" +
                "\nAmount: %.2f" ,
                client_Record.getName(),
                client_Record.getCi(),
                client_Record.getEmail(),
                client_Record.getPhone(),
                data,
                amount_to_pay);


    }
}
