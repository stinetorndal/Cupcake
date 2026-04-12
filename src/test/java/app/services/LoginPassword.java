package app.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPassword {
    @Test
    void shouldRejectBlankEmailField () {
        // Arrange
        // Act
        // Forventet værdi
        String error = Validator.validateUser ("", "12345678");
        // Assert (forventet værdi, faktisk værdi)
        assertEquals("Email skal udfyldes", error);
    }

    //Planlagte tests:
    //check for specialtegn
    //check om bruger findes
}
