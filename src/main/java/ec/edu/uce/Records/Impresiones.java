package ec.edu.uce.Records;

import ec.edu.uce.interfaces.QualifierPayment;
import ec.edu.uce.jpa.Account;
import ec.edu.uce.jpa.Client;
import ec.edu.uce.jpa.Product;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Singleton
@QualifierPayment("impresiones")
public class Impresiones {

    @Inject
    @QualifierPayment("clientRec")
    ClientRecord newClientRec;


    Impresiones(){

    }
    public ClientRecord createClientRecord(Client client){
        ClientRecord newClientRec = new ClientRecord();
        newClientRec.setCi(client.getCi());
        newClientRec.setName(client.getName());
        newClientRec.setEmail(client.getEmail());
        newClientRec.setPhone(client.getPhone());

        List<AccountRecord> accountList = new ArrayList<>();
        for (Account a : client.getAccounts()) {
            AccountRecord accountRec = new AccountRecord();
            accountRec.setId(a.getId());
            accountRec.setNumber(a.getNumber());
            accountRec.setType(a.getType());
            accountList.add(accountRec);
        }
        newClientRec.setAccounts(client.getAccounts());
        return newClientRec;
    }

    public List<ProductRecord> createProductRecord(List<Product> products){

        List<ProductRecord> productRecords = new ArrayList<>();
        for (Product p : products) {
            ProductRecord newProductRec = new ProductRecord();
            newProductRec.setProductID(p.getId());
            newProductRec.setProductName(p.getName());
            newProductRec.setQuantity(p.getQuantity());
            newProductRec.setPrice(p.getPrice());
            // Agregar el nuevo ProductRecord a la lista
            productRecords.add(newProductRec);
        }
        return productRecords;
    }

}
