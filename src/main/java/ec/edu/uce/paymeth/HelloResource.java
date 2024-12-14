package ec.edu.uce.paymeth;
import ec.edu.uce.Records.*;
import ec.edu.uce.Records.Record;
import ec.edu.uce.interfaces.IPay;
import ec.edu.uce.interfaces.QualifierPayment;
import ec.edu.uce.jpa.*;
import jakarta.inject.Inject;
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
    @QualifierPayment("productService")
    ProductService productService;

    @Inject
    @QualifierPayment("clientService")
    ClientService clientService;

    @Inject
    @QualifierPayment("paymentRec")
    Record paymentRec;

    @Inject
    @QualifierPayment("productRec")
    ProductRecord productRec;

    @Inject
    @QualifierPayment("impresiones")
    Impresiones impresiones;

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
            Product product = new Product();
            product.setName(name != null ? name : "Jamon");
            product.setQuantity(quantity !=0 ? quantity : 12);
            product.setPrice(price != 0 ? price : 12.23);
            productService.createProduct(product);

            // Buscar todos los productos
            List<Product> products = productService.findAll();

            // Crear una lista para almacenar los ProductRecord
            List<ProductRecord> productList = impresiones.createProductRecord(products);
            // Retornar la lista de ProductRecord como respuesta en formato JSON
            return Response.ok(productList).build();
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

            Client client = new Client();
            client.setCi(ci != null ? ci : 95);
            client.setName(name != null ? name : "Maria");
            client.setEmail(email != null ? email : "example3@uce.com");
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
                ClientRecord newClientRec = impresiones.createClientRecord(c);
                clientList.add(newClientRec);
            }
            return Response.ok(clientList).build();
    }

    @GET
    @Produces("text/plain")
    @Path("/paypal")
    public String paypal(@QueryParam("Client_ID") int clientID,@QueryParam("Products_ids") List<Integer> Products_ids) {
        if ((clientID ==0 || Products_ids == null) || (Products_ids==null && clientID==0)) {
            return "http://localhost:8080/PayMeth-1.0-SNAPSHOT/api/app/paypal?Client_ID=25656846&Products_ids=752&Products_ids=652";
        }else {
            Client client= clientService.findByID(clientID);
            ClientRecord newClientRec = impresiones.createClientRecord(client);
            List<Product> products = new ArrayList<>();
            for (Integer id : Products_ids) {
                products.add(productService.findByID(id));
            }
            List<ProductRecord> productList = impresiones.createProductRecord(products);

            invoiceRec.setClient_Record(newClientRec);
            invoiceRec.setProduct_RecordList(productList);


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

