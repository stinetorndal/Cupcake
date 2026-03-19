package app.entities;

public class Post {
    private int postId; // Database ID
    private String title;
    private String content;

    // Bruges når jeg henter FRA databasen (med ID)
    public Post(int postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    // Bruges når jeg opretter en NY (uden ID endnu)
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Gettere - Thymeleaf SKAL bruge disse for at kunne skrive titlen på skærmen
    public int getPostId() { return postId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}