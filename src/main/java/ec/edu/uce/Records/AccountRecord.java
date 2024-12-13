package ec.edu.uce.Records;

import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Column;

@ApplicationScoped
@Singleton
@QualifierPayment("accountRec")
public class AccountRecord {
    private int id;
    private String number;
    private String type;

    public AccountRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
