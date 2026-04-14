package app.controllers;

import app.app.exceptions.DatabaseException;
import app.entities.Customer;
import app.persistence.ConnectionPool;
import app.services.PaymentService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class OrderController {

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.get("/betaling", ctx -> ctx.render("betaling.html"));
        //Når kunde klikker på betaling, "pay" ligger i html:
        app.post("/pay", ctx -> handlePayment(ctx, connectionPool));
        app.get("/ordrebekraeftelse.html", ctx -> ctx.render ("ordrebekraeftelse.html"));
    }

    public void handlePayment(Context ctx, ConnectionPool connectionPool) {
        Customer customer = ctx.sessionAttribute("currentUser");

        //Hent kurv og beregn total, dummyværdi indtil kurv er klar
        double total = 30.0;
        try {
            //Her forsøger jeg at betale
            PaymentService.Payment(customer, total, connectionPool);
            //Hvis det går godt:
            ctx.sessionAttribute("message", "Betaling gennemført");
            ctx.redirect("/ordrebekraeftelse");
        } catch (DatabaseException e) {
            //I tilfælde af fejl:
            ctx.attribute("message", e.getMessage());
            ctx.render("betaling.html");
        }
    }
}