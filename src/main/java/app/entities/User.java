package app.entities;

public class User {
    private long user_id;
    private String username;
    private String password;

    public User(long user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }
}
