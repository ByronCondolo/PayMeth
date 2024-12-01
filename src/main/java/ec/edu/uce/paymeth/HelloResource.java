package ec.edu.uce.paymeth;

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


@Path("/app")
public class HelloResource {

    @Inject
    @QualifierPayment("paymentRec")
    Record paymentRec;

    @Inject
    @QualifierPayment("productRec")
    ProductRecord productRec;

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
        //create
        //em.getTransaction().begin();
        //productService.createProduct(name,quantity,price);
        //em.getTransaction().commit();

        //read
        Product product= productService.findByID(1);

        productRec.setProductID(product.getId());
        productRec.setProductName(product.getName());
        productRec.setPrice(product.getPrice());
        productRec.setQuantity(product.getQuantity());

        return productRec.getdata();
    }



    @GET
    @Produces("text/plain")
    @Path("/card")
    public String emailNotification() {
        //entity manager factoy
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");
        EntityManager em = emf.createEntityManager();

        //create students services
        ClientService clientService = new ClientService(em);

        //create
        //em.getTransaction().begin();
        //clientService.createClient("Nombre del Cliente",5);
        //em.getTransaction().commit();

        //read
        Client client= clientService.findByID(4);



        //update
        //student.setName("nuevoUsuario");
        //studentService.updateStudent(student);

        //studentService.delete(1);

        MessageService messageService = new MessageService(em);
        Messege messege = new Messege();
        messege.setMessage("esto es una prueba");
        messageService.create(messege);

        paymentRec.setFrom(client.getName());
        paymentRec.setTo(String.valueOf(client.getId()));
        paymentRec.setMessage(client.getName());

        return cardPay.sendPayNotify(paymentRec, "card pay");
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

