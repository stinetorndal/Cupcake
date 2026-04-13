package app.services;

public class Validator {

    public static String validateEmail (String email) {
        // isBlank er bedre end isEmpty. Fjerner usynlige tegn før check. Det gør isEmpty ikke
        if (email == null || email.isBlank()) {
            return "Email skal udfyldes";
        }
        if (!hasAtSymbol (email)) {
            return "Email skal indeholde @";
        }
        if (!hasDotAfterAt (email)) {
            return "Email skal have et domæne (f.eks. .dk)";
        }
        return null;
    }

    // ---- HJÆLPEMETODER ---
    private static boolean hasAtSymbol (String email) {
        return email.contains("@");
    }
    private static boolean hasDotAfterAt (String email) {
        String [] splitEmail = email.split("@");
        //Bør splitte i to dele (navn + domæne+suffix), ellers er der fejl
        //Checker første del her:
        if (splitEmail.length < 2) {
            return  false;
        }
        //Anden del, der indeholder domæne og suffix
        String domain = splitEmail[2];
        return domain.contains(".");
    }

    public static String validatePassword (String password) {
        if (password == null || password.length() < 8) {
            return "Adgangskode skal være mindst 8 tegn";
        }
        return null;
    }

}
