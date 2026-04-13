package app.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPassword {
    @Test
    void shouldRejectBlankEmailField () {
        // Arrange
        // Act
        // Forventet værdi
        String error = Validator.validateEmail("");
        // Assert (forventet værdi, faktisk værdi)
        assertEquals("Email skal udfyldes", error);
    }

    @Test
    // Tester om man kan logge ind med email-adresse udfyldt
    void shouldAcceptWrittenEmailField () {
        String error = Validator.validateEmail("jan-olsker.dk");
        assertEquals("Email skal indeholde @", error);
    }

    @Test
    // Tester om password følger regler
    void isPasswordCorrect () {
        String error = Validator.validatePassword("1234567");
        assertEquals("Adgangskode skal indeholde mindst 8 tegn", error);
    }

    //Planlagte tests:
    //check for specialtegn
    //check om bruger findes
}
