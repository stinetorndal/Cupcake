package app.controllers;

import app.entities.Post;
import app.app.exceptions.DatabaseException; // Rettet (fjernet det ekstra app.)
import app.persistence.ConnectionPool;
import app.persistence.PostMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;
import java.util.Map;
import java.util.List;

public class PostController {

    public void addRoutes(Javalin app, ConnectionPool connectionPool) {
        // Vis siden med alle opslag
        app.get("/posts", ctx -> visAllePosts(ctx, connectionPool));

        // Modtag data fra formularen og gem det
        app.post("/gem-post", ctx -> opretPost(ctx, connectionPool));
    }

    private void visAllePosts(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Post> allePosts = PostMapper.getAllPosts(connectionPool);
            ctx.render("posts.html", Map.of("allePosts", allePosts));
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }

    private void opretPost(Context ctx, ConnectionPool connectionPool) {
        String titel = ctx.formParam("titel");
        String indhold = ctx.formParam("indhold");

        try {
            if (indhold.length() > 500) {
                ctx.attribute("errorMessage", "Dit opslag er for langt. Max 500 tegn");
                return;
            }
            PostMapper.createPost(titel, indhold, connectionPool);
            ctx.redirect("/posts"); // Gå tilbage til listen efter succes
        } catch (DatabaseException e)   {
            ctx.attribute("message", e.getMessage());
            ctx.render("posts.html");
        }
    }
}
