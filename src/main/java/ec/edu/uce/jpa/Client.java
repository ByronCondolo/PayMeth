package ec.edu.uce.jpa;

import jakarta.persistence.*;

import java.util.List;

//POJO plain old java object
@Entity
@Table(name = "persistence_user")
public class Client {
    @Id
    @Column(name = "Client_ci")
    private int ci;
    @Column(name = "Client_name")
    private String name;
    @Column(name = "Client_email")
    private String email;
    @Column(name = "Client_phone")
    private String phone;

    @OneToMany
    @JoinColumn(name = "Client_Id")
    List<Account> accounts;

    //constructor por defecto y getters and set minimo
    public Client(){

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
