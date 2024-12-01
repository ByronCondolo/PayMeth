package ec.edu.uce.classes;

import ec.edu.uce.interfaces.IPay;
import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

@QualifierPayment("transfer")
public class Transfer implements IPay {
    @Override
    public String sendPayNotify(Record record, String message) {
        record.setMessage(message);
        return record.getdata();
    }

}
