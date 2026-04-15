package app.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CupcakeTest {

    @Test
    void getUnitPrice() {
        //Arrange – sæt testen op (opret de objekter du skal bruge)
        Topping t1 = new Topping(1, "jordbær", 10.0);
        Bottom b1 = new Bottom(1, "chokolade", 15.0);
        Cupcake cupcake = new Cupcake(t1, b1);

        //Act – kald den metode du vil teste
        double totalPrice = cupcake.getUnitPrice();

        //Assert – tjek at resultatet er det forventede
        assertEquals(25.0, totalPrice);
    }

    @Test
    void getFullName() {
        //Arrange – sæt testen op (opret de objekter du skal bruge)
        Topping t2 = new Topping(2, "Chokolade", 10.0);
        Bottom b2 = new Bottom(3, "Pistacie", 15.0);
        Cupcake cupcake2 = new Cupcake(t2, b2);

        //Act – kald den metode du vil teste
        String fullName = cupcake2.getFullName();

        //Assert – tjek at resultatet er det forventede
        assertEquals("Bund: Pistacie, top: Chokolade", fullName);
    }
}