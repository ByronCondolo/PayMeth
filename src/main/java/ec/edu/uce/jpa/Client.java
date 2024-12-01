package ec.edu.uce.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//POJO plain old java object
@Entity
@Table(name = "persistence_user")
public class Client {
    @Id
   private int id;
    @Column(name = "Client_name")
   private String name;
    //constructor por defecto y getters and set minimo
    public Client(){

    }

    public Client(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
