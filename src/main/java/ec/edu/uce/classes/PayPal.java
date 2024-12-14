package ec.edu.uce.classes;

import ec.edu.uce.Records.InvoiceRecords;
import ec.edu.uce.Records.Record;
import ec.edu.uce.interfaces.IPay;
import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@QualifierPayment("paypal")
public class PayPal implements IPay {

    @Override
    public String sendPayNotify(InvoiceRecords invoiceRecords) {
        return invoiceRecords.getdata();
    }
}
