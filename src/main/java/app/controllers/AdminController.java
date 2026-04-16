package app.controllers;

import app.entities.Customer;
import app.entities.Order;
import app.persistence.ConnectionPool;
import app.app.exceptions.DatabaseException;
import app.services.AdminService;
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

        private void showAllOrders(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Order> allOrders = AdminService.getAllOrders(connectionPool);

            ctx.attribute("valgtVisning", "alle-ordrer");
            ctx.attribute("alleOrdrer", allOrders);

            // Vi sender tomme værdier til de andre sektioner pga Thymeleaf
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

            ctx.attribute("alleOrdrer", new ArrayList<Order>());
            ctx.attribute("foundUser", null);

            ctx.render("admin.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Fejl ved hentning af kunder: " + e.getMessage());
            ctx.render("admin.html");
        }
    }

    private void showCustomerDetails(Context ctx, ConnectionPool connectionPool) {

        int customerId = Integer.parseInt(ctx.queryParam("id"));

        try {
            Customer selectedCustomer = AdminService.getCustomerById(customerId, connectionPool);

            ctx.attribute("selectedCustomer", selectedCustomer);
            ctx.attribute("valgtVisning", "profil");

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

            AdminService.updateBalance(customerId, amount, connectionPool);

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
