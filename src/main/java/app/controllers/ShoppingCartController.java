package app.controllers;

import app.app.exceptions.DatabaseException;
import app.entities.OrderLine;
import app.entities.ShoppingCart;
import app.persistence.ConnectionPool;
import app.services.ShoppingCartService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartController {

    private ShoppingCartService shoppingCartService = new ShoppingCartService();

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/tilfoej-til-kurv", ctx -> addToCart(ctx, connectionPool));
        app.get("/kurv", ctx -> showCart(ctx));
    }

    public void addToCart(Context ctx, ConnectionPool connectionPool) {
        int toppingId = getCustomerTopping(ctx);
        int bottomId = getCustomerBottom(ctx);
        int quantity = getQuantity(ctx);

        List<OrderLine> orderLines = new ArrayList<>();
        ShoppingCart shoppingCart = ctx.sessionAttribute("shoppingcart");
        if(shoppingCart == null) {
            shoppingCart = new ShoppingCart(orderLines);
        }

        try {
            OrderLine orderLine = shoppingCartService.createOrderLineObject(toppingId, bottomId, quantity, connectionPool);
            shoppingCart.getOrderLines().add(orderLine);
            ctx.sessionAttribute("shoppingcart", shoppingCart);
            ctx.redirect("/bygdinegencupcake");

        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("bygdinegencupcake.html");
        }
    }

    public void showCart(Context ctx) {
        ShoppingCart shoppingCart = ctx.sessionAttribute("shoppingcart");
        if (shoppingCart == null){
            shoppingCart = new ShoppingCart(new ArrayList<>());
        }
        ctx.attribute("shoppingcart", shoppingCart);
        ctx.render("kurv.html");
    }

    //Henter kundes topping-valg fra html
    public int getCustomerTopping(Context ctx) {
        return Integer.parseInt(ctx.formParam("top"));
    }

    //Henter kundens bottom-valg fra html
    public int getCustomerBottom(Context ctx) {
        return Integer.parseInt(ctx.formParam("bund"));
    }

    public int getQuantity(Context ctx) {
        return Integer.parseInt(ctx.formParam("quantity"));
    }
}
