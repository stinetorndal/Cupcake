package app.controllers;
import app.app.exceptions.DatabaseException;
import app.entities.Customer;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;



public class UserController {

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.get("/registrerbruger", ctx -> ctx.render("registrerbruger.html"));
        app.post("/new", ctx -> registerNewCustomer(ctx, connectionPool));
        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.post("/logout", ctx -> logout(ctx));
    }

    public void logout(Context ctx) {
        ctx.consumeSessionAttribute("currentUser"); //sletter bruger fra hukommelse
        ctx.redirect("/login");
    }
    public void login(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password = ctx.formParam("password");
        try {
            // Vi kalder UserMapper for at se om brugeren findes
            User user = UserMapper.login(username, password, connectionPool);

            // Hvis det lykkes, gemmer vi brugeren i en session (så de forbliver logget ind)
            ctx.sessionAttribute("currentUser", user);

            // NU sender vi dem til opslagstavlen i stedet for registrering!
            ctx.redirect("/posts");

        } catch (DatabaseException e) {
            // Hvis login fejler (forkert password osv.)
            ctx.attribute("message", "Forkert brugernavn eller adgangskode");
            ctx.render("login.html");
        }
    }

    public void registerNewCustomer(Context ctx, ConnectionPool connectionPool) {
        Customer customer = new Customer(ctx.formParam("firstName"), ctx.formParam("lastName"), ctx.formParam("email"), ctx.formParam("password"));
        //String email = ctx.formParam("username");
        //String password = ctx.formParam("password");


        try{
            //Mapperlag kaldes, som så skriver til DB
            UserMapper.createUser(customer, connectionPool);
            //Laver en primitiv popup med brugeroprettelse
            ctx.attribute("showAlert", true);
            ctx.attribute("msg", "Brugeren: " + customer.getEmail() + " er nu oprettet");
            //slut med popup og tilbage til forside
            ctx.render("index.html");

            //Hvis success - bruger sendes til forside
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("registrerbruger.html");
        }

    }

}