package ec.edu.uce.classes;

import ec.edu.uce.Records.InvoiceRecords;
import ec.edu.uce.interfaces.IPay;
import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@QualifierPayment("card")
public class Card implements IPay {
    @Override
    public String sendPayNotify(InvoiceRecords invoiceRecords) {

        invoiceRecords.setMethod_pay(getClass().getSimpleName());
        return invoiceRecords.getdata();
    }

}
