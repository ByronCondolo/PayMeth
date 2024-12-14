package ec.edu.uce.Records;
import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
@Singleton
@QualifierPayment("invoiceRec")
public class InvoiceRecords {
    private ClientRecord record;
    private List<ProductRecord> productRecordList;

    public ClientRecord getRecord() {
        return record;
    }

    public void setRecord(ClientRecord record) {
        this.record = record;
    }

    public List<ProductRecord> getProductRecordList() {
        return productRecordList;
    }

    public void setProductRecordList(List<ProductRecord> productRecordList) {
        this.productRecordList = productRecordList;
    }

    public InvoiceRecords() {
    }
}
