package ec.edu.uce.Records;

import ec.edu.uce.interfaces.QualifierPayment;
import ec.edu.uce.jpa.Account;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.List;

@ApplicationScoped
@Singleton
@QualifierPayment("clientRec")
public class ClientRecord {
    private int ci;
    private String name;
    private String email;
    private String phone;
    private List<Account> accounts;

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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
