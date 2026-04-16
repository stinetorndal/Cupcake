package app.controllers;

import app.entities.Cupcake;
import app.entities.Customer;
import app.entities.User;
import app.entities.Order;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import app.persistence.OrderMapper;
import app.app.exceptions.DatabaseException;
import app.services.AdminService;
import app.services.UserService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;
import java.util.List;

public class AdminController {

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        //app.post("/admin/soeg", ctx -> handleSearch (ctx, connectionPool));
        app.get("/admin/alle-kunder", ctx -> showAllCustomers(ctx, connectionPool));
        app.get("/admin/alle-ordrer", ctx -> showAllOrders(ctx, connectionPool));

        // I addRoutes metoden
        app.get("/admin/kunde-detaljer", ctx -> showCustomerDetails(ctx, connectionPool));
        app.post("/admin/update-balance", ctx -> handleUpdateBalance(ctx, connectionPool));
        app.post("/admin/soeg", ctx -> handleSearch (ctx, connectionPool));
    }

    //searchCustomerByEmail

    private void showAllOrders(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Order> allOrders = AdminService.getAllOrders(connectionPool);

            ctx.attribute("valgtVisning", "alle-ordrer");
            ctx.attribute("alleOrdrer", allOrders);

            // VIGTIGT: Vi sender tomme værdier til de andre sektioner
            ctx.attribute("brugerListe", new ArrayList<Customer>());
            ctx.attribute("foundUser", null);

            ctx.render("admin.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Fejl ved hentning af ordrer: " + e.getMessage());
            ctx.render("admin.html");
        }
    }

    private void showAllCustomers(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Customer> allCustomers = AdminService.getAllCustomers(connectionPool);

            ctx.attribute("valgtVisning", "alle-kunder");
            ctx.attribute("brugerListe", allCustomers);

            // En tom liste her
            ctx.attribute("alleOrdrer", new ArrayList<Order>());
            ctx.attribute("foundUser", null);

            ctx.render("admin.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Fejl ved hentning af kunder: " + e.getMessage());
            ctx.render("admin.html");
        }
    }

    private void showCustomerDetails(Context ctx, ConnectionPool connectionPool) {
        // Hent ID fra URL (?id=5)
        int customerId = Integer.parseInt(ctx.queryParam("id"));

        try {
            // Du skal have en metode i din Mapper/Service der henter én kunde på ID
            Customer selectedCustomer = AdminService.getCustomerById(customerId, connectionPool);

            ctx.attribute("selectedCustomer", selectedCustomer);
            ctx.attribute("valgtVisning", "profil"); // Standard visning

            // Vi sender tomme lister med for at undgå Thymeleaf fejl i sektionerne nederst
            ctx.attribute("kurvListe", new ArrayList<>());
            ctx.attribute("ordreHistorik", new ArrayList<>());

            ctx.render("kunde.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("admin.html");
        }
    }

    private void handleUpdateBalance(Context ctx, ConnectionPool connectionPool) {
        int customerId = Integer.parseInt(ctx.formParam("customerId"));
        double amount = Double.parseDouble(ctx.formParam("amount"));

        try {
            // 1. Opdater i databasen via din eksisterende service
            AdminService.updateBalance(customerId, amount, connectionPool);

            // 2. For at vise de nye tal, sender vi dem tilbage til kunde-detaljer
            // Dette kaldes et "Redirect-after-post" pattern
            ctx.redirect("/admin/kunde-detaljer?id=" + customerId);

        } catch (DatabaseException e) {
            ctx.attribute("message", "Kunne ikke opdatere saldo: " + e.getMessage());
            ctx.render("kunde.html");
        }
    }

    private void handleSearch(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email"); // Henter email fra input-feltet

        try {
            Customer foundUser = AdminService.getCustomerByEmail(email, connectionPool);

            ctx.attribute("foundUser", foundUser);
            ctx.attribute("valgtVisning", "soeg");

            // Tomme attributter så Thymeleaf ikke fejler
            ctx.attribute("brugerListe", new ArrayList<Customer>());
            ctx.attribute("alleOrdrer", new ArrayList<Order>());

            ctx.render("admin.html");

        } catch (DatabaseException e) {
            ctx.attribute("message", "Ingen kunde fundet med email: " + email);
            ctx.attribute("foundUser", null);
            ctx.attribute("valgtVisning", "soeg");
            ctx.render("admin.html");
        }
    }
}
