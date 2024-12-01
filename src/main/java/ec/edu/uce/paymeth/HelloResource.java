package ec.edu.uce.paymeth;

import ec.edu.uce.interfaces.IPay;
import ec.edu.uce.interfaces.QualifierPayment;
import ec.edu.uce.classes.Record;
import ec.edu.uce.jpa.MessageService;
import ec.edu.uce.jpa.Messege;
import ec.edu.uce.jpa.ClientService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


@Path("/app")
public class HelloResource {

    @Inject
    Record record;

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
    @Produces("text/plain")
    @Path("/card")
    public String emailNotification() {
        //entity manager factoy
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cliente");
        EntityManager em = emf.createEntityManager();

        //create students services
        ClientService studentService = new ClientService(em);

        //create
        em.getTransaction().begin();
        studentService.createStudent("Nombre del Cliente",4);
        em.getTransaction().commit();

        //read
        //Student student= studentService.findByID(1);



        //update
        //student.setName("nuevoUsuario");
        //studentService.updateStudent(student);

        //studentService.delete(1);

        MessageService messageService = new MessageService(em);
        Messege messege = new Messege();
        messege.setMessage("esto es una prueba");
        messageService.create(messege);


        //record.setTo(String.valueOf(student.getId()));
        //record.setMessage(student.getName());


        return cardPay.sendPayNotify(record, "card pay");
    }

    @GET
    @Produces("text/plain")
    @Path("/paypal")
    public String SMSNotification() {
        return paypalPay.sendPayNotify(record, "paypal pay");

    }

    @GET
    @Produces("text/plain")
    @Path("/transfer")
    public String pushNotification() {
        return transferPay.sendPayNotify(record,"transfer pay");

    }

}

