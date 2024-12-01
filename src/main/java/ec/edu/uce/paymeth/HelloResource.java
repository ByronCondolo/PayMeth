package ec.edu.uce.paymeth;

import ec.edu.uce.Records.ClientRecord;
import ec.edu.uce.Records.ProductRecord;
import ec.edu.uce.interfaces.IPay;
import ec.edu.uce.interfaces.QualifierPayment;
import ec.edu.uce.Records.Record;
import ec.edu.uce.jpa.*;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


@Path("/app")
public class HelloResource {

    @Inject
    @QualifierPayment("paymentRec")
    Record paymentRec;

    @Inject
    @QualifierPayment("productRec")
    ProductRecord productRec;

    @Inject
    @QualifierPayment("clientRec")
    ClientRecord clientRec;

    @Inject
    @QualifierPayment("card")
    IPay cardPay;

    @Inject
    @QualifierPayment("paypal")
    IPay paypalPay;

    @Inject
    @QualifierPayment("transfer")
    IPay transferPay;

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }


    @GET
    @Produces
    @Path("/Product")
    public String product(@QueryParam("name") String name, @QueryParam("quantity") int quantity, @QueryParam("price") double price) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");
        EntityManager em = emf.createEntityManager();
        ProductService productService = new ProductService(em);
        Product product;
        //create
        em.getTransaction().begin();
        product = productService.createProduct(name,quantity,price);
        em.getTransaction().commit();

        //read
        //product= productService.findByID(1);

        productRec.setProductID(product.getId());
        productRec.setProductName(product.getName());
        productRec.setPrice(product.getPrice());
        productRec.setQuantity(product.getQuantity());

        return productRec.getdata();
    }



    @GET
    @Produces("text/plain")
    @Path("/Client")
    public String emailNotification(@QueryParam("ci") int ci, @QueryParam("name") String name,@QueryParam("email") String email, @QueryParam("phone") String phone, @QueryParam("bank_account") String bank_account) {
        //entity manager factoy
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");
        EntityManager em = emf.createEntityManager();

        //create students services
        ClientService clientService = new ClientService(em);
        Client client;

        //create
        em.getTransaction().begin();
        client = clientService.createClient(ci, name, email, phone, bank_account);
        em.getTransaction().commit();


        //read
        //client= clientService.findByID(1672839302);

        //update
        //student.setName("nuevoUsuario");
        //studentService.updateStudent(student);

        //studentService.delete(1);

        clientRec.setCi(client.getCi());
        clientRec.setName(client.getName());
        clientRec.setEmail(client.getEmail());
        clientRec.setPhone(client.getPhone());
        clientRec.setBank_account(client.getBank_account());

        return clientRec.getdata();
    }



    @GET
    @Produces("text/plain")
    @Path("/paypal")
    public String SMSNotification() {
        return paypalPay.sendPayNotify(paymentRec, "paypal pay");

    }

    @GET
    @Produces("text/plain")
    @Path("/transfer")
    public String pushNotification() {
        return transferPay.sendPayNotify(paymentRec,"transfer pay");

    }



}

