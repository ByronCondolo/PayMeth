package ec.edu.uce.paymeth;
import ec.edu.uce.Records.*;
import ec.edu.uce.Records.Record;
import ec.edu.uce.interfaces.IPay;
import ec.edu.uce.interfaces.QualifierPayment;
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
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("paymeth");
    private EntityManager em = emf.createEntityManager();



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
    @QualifierPayment("invoiceRec")
    InvoiceRecords invoiceRec;

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
    public Response product(@QueryParam("name") String name,
                            @QueryParam("quantity") int quantity,
                            @QueryParam("price") double price) {
        ProductService productService = new ProductService(em);
        try {
            Product product = new Product();
            product.setName(name != null ? name : "Jamon");
            product.setQuantity(quantity !=0 ? quantity : 12);
            product.setPrice(price != 0 ? price : 12.23);
            productService.createProduct(product);

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
    public Response saveNewClient(
            @QueryParam("ci") Integer ci,
            @QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("phone") String phone,
            @QueryParam("bank_account") String bank_account,
            @QueryParam("bank_type") String bank_type) {

        ClientService clientService = new ClientService(em);
        try {
            Client client = new Client();
            client.setCi(ci != null ? ci : 25653445);
            client.setName(name != null ? name : "Jose");
            client.setEmail(email != null ? email : "example@uce.com");
            client.setPhone(phone != null ? phone : "11565484");

            Account account = new Account();
            account.setNumber(bank_account != null ? bank_account : "156518");
            account.setType(bank_type !=null ? bank_type:"Pichincha");

            List<Account> accounts = new ArrayList<>();
            accounts.add(account);
            client.setAccounts(accounts);

            clientService.createClientWhitAccount(client, account);

            List<Client> clients = clientService.findAll();
            List<ClientRecord> clientList = new ArrayList<>();

            for (Client c : clients) {
                ClientRecord newClientRec = new ClientRecord();
                newClientRec.setCi(c.getCi());
                newClientRec.setName(c.getName());
                newClientRec.setEmail(c.getEmail());
                newClientRec.setPhone(c.getPhone());

                List<AccountRecord> accountList = new ArrayList<>();
                for (Account a : c.getAccounts()) {
                    AccountRecord accountRec = new AccountRecord();
                    accountRec.setId(a.getId());
                    accountRec.setNumber(a.getNumber());
                    accountRec.setType(a.getType());
                    accountList.add(accountRec);
                }

                newClientRec.setAccounts(c.getAccounts());
                clientList.add(newClientRec);
            }

            return Response.ok(clientList).build();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar la solicitud").build();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }




    @GET
    @Produces("text/plain")
    @Path("/paypal")
    public String paypal(@QueryParam("Client_ID") int clientID,@QueryParam("Products_ids") List<Integer> Products_ids) {
        ClientService clientService=new ClientService(em);
        ProductService productService=new ProductService(em);
        if ((clientID ==0 || Products_ids == null) || (Products_ids==null && clientID==0)) {

            return null;
        }else {
            Client client= clientService.findByID(clientID);
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


            List<Product> products = new ArrayList<>();
            for (Integer id : Products_ids) {
                products.add(productService.findByID(id));
            }
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

            invoiceRec.setClient_Record(newClientRec);
            invoiceRec.setProduct_RecordList(productRecords);
            return paypalPay.sendPayNotify(invoiceRec);
        }
    }

    @GET
    @Produces("text/plain")
    @Path("/transfer")
    public String pushNotification() {


        return null;
    }



}

