package ec.edu.uce.paymeth;

import ec.edu.uce.Records.AccountRecord;
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
    @QualifierPayment("accountRec")
    AccountRecord accountRecord;

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
    public Response emailNotification(@QueryParam("ci") int ci, @QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("phone") String phone, @QueryParam("bank_account") String bank_account) {
        // Crear EntityManager y EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");
        EntityManager em = emf.createEntityManager();

        // Crear el servicio de cliente
        ClientService clientService = new ClientService(em);
        try {
            // Crear un nuevo cliente y asignar datos de ejemplo
            Client client = new Client();
            client.setCi(ci != 0 ? ci: 25656846);
            client.setName(name != null ? name : "Jose"); // Si no se pasa nombre, usamos "alex"
            client.setEmail(email != null ? email : "example@uce.com"); // Si no se pasa email, usamos uno por defecto
            client.setPhone(phone != null ? phone : "11565484"); // Si no se pasa teléfono, usamos uno por defecto

            // Crear una cuenta asociada al cliente
            Account account = new Account();
            account.setNumber(bank_account != null ? bank_account : "156518"); // Si no se pasa cuenta, usamos una por defecto
            account.setType("bank");

            // Añadir la cuenta a la lista de cuentas del cliente
            List<Account> accounts = new ArrayList<>();
            accounts.add(account);
            client.setAccounts(accounts);

            // Crear el cliente y sus cuentas en la base de datos
            clientService.createClientWhitAccount(client, account);

            // Obtener todos los clientes con cuentas asociadas
            List<Client> clients = clientService.findAll();

            // Crear una lista para almacenar los ClientRecord con los datos de Account
            List<ClientRecord> clientList = new ArrayList<>();

            // Iterar sobre los clientes
            for (Client c : clients) {
                // Crear una nueva instancia de ClientRecord
                ClientRecord newClientRec = new ClientRecord();
                newClientRec.setCi(c.getCi());
                newClientRec.setName(c.getName());
                newClientRec.setEmail(c.getEmail());
                newClientRec.setPhone(c.getPhone());

                // Si el cliente tiene cuentas, agregarlas a la lista (suponiendo que las cuentas son parte de la entidad Client)
                List<Account> accounts1 = c.getAccounts(); // Aquí estamos accediendo a las cuentas del cliente
                List<AccountRecord> accountList = new ArrayList<>();

                // Iterar sobre las cuentas del cliente y convertirlas en AccountRecord
                for (Account a : accounts1) {
                    AccountRecord accountRec = new AccountRecord();
                    accountRec.setId(a.getId());
                    accountRec.setNumber(a.getNumber());
                    accountRec.setType(a.getType());

                    // Agregar la cuenta a la lista
                    accountList.add(accountRec);
                }

                // Establecer la lista de cuentas en el ClientRecord
                newClientRec.setAccounts(accounts1);

                // Agregar el nuevo ClientRecord a la lista
                clientList.add(newClientRec);
            }

            // Retornar la lista de ClientRecord como respuesta en formato JSON
            return Response.ok(clientList).build();

        } catch (Exception e) {
            // Manejo de errores y rollback en caso de fallo
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

