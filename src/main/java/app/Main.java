import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.*;
import app.entities.ShoppingCart;
import app.persistence.ConnectionPool;
import app.persistence.DBconfig;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public static void main(String[] args) {

    ConnectionPool connectionPool = ConnectionPool.getInstance(
            DBconfig.USER,
            DBconfig.PASSWORD,
            DBconfig.URL,
            DBconfig.DB
    );

    // Initializing Javalin and Jetty webserver
    Javalin app = Javalin.create(config -> {
        config.staticFiles.add("/public");
        config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
        config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
    }).start(7070);

    UserController userController = new UserController();
    ShoppingCartController shoppingCartController = new ShoppingCartController();
    OrderController orderController = new OrderController();
    CupcakeController cupcakeController = new CupcakeController();
    AdminController adminController = new AdminController();

    // Routing

    app.get("/", ctx -> ctx.render("index.html"));
    userController.addRoutes(app, connectionPool);
    shoppingCartController.addRoutes(app, connectionPool);
    orderController.addRoutes(app, connectionPool);
    cupcakeController.addRoutes(app, connectionPool);
    adminController.addRoutes(app, connectionPool );

}