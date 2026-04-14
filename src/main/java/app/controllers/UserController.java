package app.controllers;
import app.app.exceptions.DatabaseException;
import app.entities.Customer;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import app.services.UserService;
import io.javalin.http.Context;
import io.javalin.Javalin;



public class UserController {

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {

        app.get("/opretbruger", ctx -> ctx.render("opretbruger.html"));
        app.post("/register", ctx -> register(ctx, connectionPool));

        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.post("/logout", ctx -> logout(ctx));
    }
    public void register (Context ctx, ConnectionPool connectionPool) {
        User newUser = new User(
                ctx.formParam("firstname"),
                ctx.formParam("lastname"),
                ctx.formParam("email"),
                ctx.formParam("password")
        );
        try {
            UserService.createCustomer(newUser, connectionPool);

            ctx.attribute("message", "Velkommen til familien. Du kan nu logge ind");
            ctx.render("login.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("opretbruger.html");
        }
    }

    public void login(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        try {
            // Vi kalder UserMapper for at se om brugeren findes
            User user = UserMapper.login(email, password, connectionPool);
            // Bruger gemmes i session
            ctx.sessionAttribute("currentUser", user);

            if (user.getRole().equals("admin")) {
                ctx.render("admin.html");
            }else {
                ctx.render("bygdinegencupcake.html");
            }

        } catch (DatabaseException e) {
            // Hvis login fejler (forkert password osv.)
            ctx.attribute("message", "Forkert brugernavn eller adgangskode");
            ctx.render("login.html");
        }
    }

        public void logout(Context ctx) {
        ctx.consumeSessionAttribute("currentUser"); //sletter bruger fra hukommelse
        ctx.redirect("/login");
    }

    }

