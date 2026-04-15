package app.controllers;

import app.app.exceptions.DatabaseException;
import app.entities.Customer;
import app.entities.ShoppingCart;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.services.PaymentService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.Connection;

public class OrderController {

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {

           //Når kunde klikker på betaling, "pay" ligger i html:
        app.post("/pay", ctx -> handlePayment(ctx, connectionPool));
        app.get("/ordrebekraeftelse.html", ctx -> ctx.render ("ordrebekraeftelse.html"));
    }


    public void handlePayment (Context ctx, ConnectionPool connectionPool) {
        Customer customer = ctx.sessionAttribute("currentUser");
        ShoppingCart shoppingCart = ctx.sessionAttribute("shoppingcart");
        if (shoppingCart == null || shoppingCart.getOrderLines().isEmpty()) {
            ctx.attribute("message", "Din kurv er tom");
            ctx.render("kurv.html");
        }
            double total = shoppingCart.getTotalPrice();

            try {
                // Betaling
                PaymentService.Payment(customer, total, connectionPool);
                // Gem ordre
                OrderMapper.saveOrderInDB(customer.getUserId(), connectionPool);
                //Send tal til ordrebekraeftelse.html
                ctx.attribute("total", total);
                ctx.attribute("moms", PaymentService.calculateVAT(total));
                ctx.render("ordrebekraeftelse.html");

            } catch (DatabaseException e) {
                ctx.attribute("message", e.getMessage());
                ctx.render("kurv.html");
            }

        }
    }