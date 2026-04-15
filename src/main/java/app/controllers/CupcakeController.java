package app.controllers;

import app.app.exceptions.DatabaseException;
import app.entities.Bottom;
import app.entities.Topping;
import app.persistence.ConnectionPool;
import app.services.CupcakeService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class CupcakeController {

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/bygdinegencupcake", ctx -> showCupcakeComponents(ctx, connectionPool));
    }

    public void showCupcakeComponents(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Topping> allToppings = CupcakeService.getToppings(connectionPool);
            List<Bottom> allBottoms = CupcakeService.getBottoms(connectionPool);

            ctx.attribute("toppings", allToppings);
            ctx.attribute("bottoms", allBottoms);
            ctx.render("bygdinegencupcake.html");

        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("bygdinegencupcake.html");
        }
    }
}
