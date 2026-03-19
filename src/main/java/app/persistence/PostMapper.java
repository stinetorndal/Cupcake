package app.persistence;

import app.entities.Post;
import app.app.exceptions.DatabaseException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostMapper {

    // Denne metode henter alle posts fra din Postgres-database
    public static List<Post> getAllPosts(ConnectionPool connectionPool) throws DatabaseException {
        List<Post> postList = new ArrayList<>();
        String sql = "SELECT * FROM posts";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("post_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                postList.add(new Post(id, title, content));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl i databasen: " + e.getMessage());
        }
        return postList;
    }

    // Denne metode gemmer et nyt post i databasen
    public static void createPost(String title, String content, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO posts (title, content) VALUES (?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke gemme dit opslag.");
        }
    }
}