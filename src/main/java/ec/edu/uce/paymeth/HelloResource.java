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
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Product")
    public Response product(@QueryParam("name") String name, @QueryParam("quantity") int quantity, @QueryParam("price") double price) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");
        EntityManager em = emf.createEntityManager();
        ProductService productService = new ProductService(em);

        try {
            // Crear producto
            if (name!=null)
            {
                em.getTransaction().begin();
                Product product = productService.createProduct(name, quantity, price);
                em.getTransaction().commit();
            }
            // Buscar todos los productos
            List<Product> products = productService.findAll();

            // Crear una lista para almacenar los ProductRecord
            List<ProductRecord> productList = new ArrayList<>();
            // Iterar sobre los productos y agregar a la lista
            for (Product p : products) {
                // Crear una nueva instancia de ProductRecord
                ProductRecord newProductRec = new ProductRecord();
                newProductRec.setProductID(p.getId());
                newProductRec.setProductName(p.getName());
                newProductRec.setQuantity(p.getQuantity());
                newProductRec.setPrice(p.getPrice());
                // Agregar el nuevo ProductRecord a la lista
                productList.add(newProductRec);
            }

            // Retornar la lista de ProductRecord como respuesta en formato JSON
            return Response.ok(productList).build();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir si hay algún error
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud").build();
        } finally {
            // Cerrar el EntityManager y EntityManagerFactory para liberar los recursos
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Client")
    public Response emailNotification(@QueryParam("ci") int ci, @QueryParam("name") String name,@QueryParam("email") String email, @QueryParam("phone") String phone, @QueryParam("bank_account") String bank_account) {
        //entity manager factoy
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");
        EntityManager em = emf.createEntityManager();

        //create students services
        ClientService clientService = new ClientService(em);
        try {
            // Crear producto
            if (name!=null)
            {
                em.getTransaction().begin();
                Client client = clientService.createClient(ci, name, email, phone, bank_account);
                em.getTransaction().commit();
            }
            // Buscar todos los productos
            List<Client> clients = clientService.findAll();

            // Crear una lista para almacenar los ProductRecord
            List<ClientRecord> clientList = new ArrayList<>();
            // Iterar sobre los productos y agregar a la lista
            for (Client c : clients) {
                // Crear una nueva instancia de ProductRecord
                ClientRecord newClientRec = new ClientRecord();
                newClientRec.setCi(c.getCi());
                newClientRec.setName(c.getName());
                newClientRec.setEmail(c.getEmail());
                newClientRec.setPhone(c.getPhone());
                newClientRec.setBank_account(c.getBank_account());
                // Agregar el nuevo ProductRecord a la lista
                clientList.add(newClientRec);
            }
            // Retornar la lista de ProductRecord como respuesta en formato JSON
            return Response.ok(clientList).build();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir si hay algún error
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud").build();
        } finally {
            // Cerrar el EntityManager y EntityManagerFactory para liberar los recursos
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }

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

