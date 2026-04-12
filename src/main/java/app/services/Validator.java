package app.services;

public class Validator {

    public static String validateUser (String email, String password) {
        // isBlank er bedre end isEmpty. Fjerner usynlige tegn før check. Det gør isEmpty ikke
        if (email == null || email.isBlank()) {
            return "Email skal udfyldes";
        }
        if (password == null || password.length() < 8) {
            return "Adgangskode skal være mindst 8 tegn";
        }
        return null;
    }
}
