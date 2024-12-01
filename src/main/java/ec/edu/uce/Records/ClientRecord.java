package ec.edu.uce.Records;

import ec.edu.uce.interfaces.QualifierPayment;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

@ApplicationScoped
@Singleton
@QualifierPayment("clientRec")
public class ClientRecord {
    private int ci;
    private String name;
    private String email;
    private String phone;
    private String bank_account;

    public ClientRecord() {
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }



}
