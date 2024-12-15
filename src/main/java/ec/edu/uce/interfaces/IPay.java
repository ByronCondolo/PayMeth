package ec.edu.uce.interfaces;

import ec.edu.uce.Records.InvoiceRecords;

public interface IPay {

    String sendPayNotify(InvoiceRecords invoiceRecords);

}
