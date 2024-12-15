package ec.edu.uce.paymeth;
import ec.edu.uce.Records.*;
import ec.edu.uce.interfaces.IPay;
import ec.edu.uce.interfaces.QualifierPayment;
import ec.edu.uce.jpa.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
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
    @QualifierPayment("invoiceService")
    InvoiceService invoiceService;


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
    @Path("/findClient/{id}")
    public Response searchClienrt(@PathParam("id") int id) {
        Client client = clientService.findByID(id);
        ClientRecord clientRecord= impresiones.createClientRecord(client);
        return Response.ok(clientRecord).build();
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
    public String paypal(@QueryParam("Client_ID") int clientID,
                         @QueryParam("Products_ids") List<Integer> Products_ids) {
        Invoice invoice = new Invoice();
        Client client = clientService.findByID(clientID);
        if (client == null) {
            client = clientService.findByID(95);
        }
        invoice.setClient(client);
        invoice.setDate(new Date());
        ClientRecord newClientRec = impresiones.createClientRecord(invoice.getClient());
        List<Product> products = new ArrayList<>();
        double ammount=0;
        if (Products_ids.isEmpty()) {
            products.add(productService.findByID(802));
            products.add(productService.findByID(852));
            ammount += productService.findByID(802).getPrice();
            ammount += productService.findByID(852).getPrice();
        }else {
            for (Integer id : Products_ids) {
                products.add(productService.findByID(id));
                ammount += productService.findByID(id).getPrice();
            }
        }
        invoice.setTotal_purchase_value(ammount);
        List<ProductRecord> productList = impresiones.createProductRecord(products);
        invoiceRec.setClient_Record(newClientRec);
        invoiceRec.setProduct_RecordList(productList);
        invoiceRec.setAmount_to_pay(ammount);
        invoiceRec.setDate(invoice.getDate());
        invoiceRec.setId(invoice.getId());//ingresar el id despues de persistir en caso de tener un id generado automaticamente
        String msg = paypalPay.sendPayNotify(invoiceRec);
        invoice.setMethod_pay(invoiceRec.getMethod_pay());
        invoiceService.createInvoice(invoice);
        return msg;
        }

    @GET
    @Produces("text/plain")
    @Path("/card")
    public String card(@QueryParam("Client_ID") int clientID,
                         @QueryParam("Products_ids") List<Integer> Products_ids) {
        Invoice invoice = new Invoice();
        Client client = clientService.findByID(clientID);
        if (client == null) {
            client = clientService.findByID(95);
        }
        invoice.setClient(client);
        invoice.setDate(new Date());
        ClientRecord newClientRec = impresiones.createClientRecord(invoice.getClient());
        List<Product> products = new ArrayList<>();
        double ammount = 0;
        if (Products_ids.isEmpty()) {
            products.add(productService.findByID(802));
            products.add(productService.findByID(852));
            ammount += productService.findByID(802).getPrice();
            ammount += productService.findByID(852).getPrice();
        }else {
            for (Integer id : Products_ids) {
                products.add(productService.findByID(id));
                ammount += productService.findByID(id).getPrice();
            }
        }
        invoice.setTotal_purchase_value(ammount);
        List<ProductRecord> productList = impresiones.createProductRecord(products);
        invoiceRec.setClient_Record(newClientRec);
        invoiceRec.setProduct_RecordList(productList);
        invoiceRec.setAmount_to_pay(ammount);
        invoiceRec.setDate(invoice.getDate());
        invoiceRec.setId(invoice.getId());//ingresar el id despues de persistir en caso de tener un id generado automaticamente
        String msg = cardPay.sendPayNotify(invoiceRec);
        invoice.setMethod_pay(invoiceRec.getMethod_pay());
        invoiceService.createInvoice(invoice);
        return msg;
    }
    @GET
    @Produces("text/plain")
    @Path("/transfer")
    public String transfer(@QueryParam("Client_ID") int clientID,
                         @QueryParam("Products_ids") List<Integer> Products_ids) {
        Invoice invoice = new Invoice();
        Client client = clientService.findByID(clientID);
        if (client == null) {
            client = clientService.findByID(95);
        }
        invoice.setClient(client);
        invoice.setDate(new Date());
        ClientRecord newClientRec = impresiones.createClientRecord(invoice.getClient());
        List<Product> products = new ArrayList<>();
        double ammount = 0;
        if (Products_ids.isEmpty()){
            products.add(productService.findByID(802));
            products.add(productService.findByID(852));
            ammount += productService.findByID(802).getPrice();
            ammount += productService.findByID(852).getPrice();
        }else {
            for (Integer id : Products_ids) {
                products.add(productService.findByID(id));
                ammount += productService.findByID(id).getPrice();
            }
        }
        invoice.setTotal_purchase_value(ammount);
        List<ProductRecord> productList = impresiones.createProductRecord(products);
        invoiceRec.setClient_Record(newClientRec);
        invoiceRec.setProduct_RecordList(productList);
        invoiceRec.setAmount_to_pay(ammount);
        invoiceRec.setDate(invoice.getDate());
        invoiceRec.setId(invoice.getId());//ingresar el id despues de persistir en caso de tener un id generado automaticamente
        String msg = transferPay.sendPayNotify(invoiceRec);
        invoice.setMethod_pay(invoiceRec.getMethod_pay());
        invoiceService.createInvoice(invoice);
        return msg;
    }

    }

