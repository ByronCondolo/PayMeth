package ec.edu.uce.interfaces;

import ec.edu.uce.Records.InvoiceRecords;
import ec.edu.uce.Records.Record;

public interface IPay {

    String sendPayNotify(InvoiceRecords invoiceRecords);

}
