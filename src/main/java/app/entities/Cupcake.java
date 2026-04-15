package app.entities;

public class Cupcake {

    private Topping topping;
    private Bottom bottom;

    public Cupcake(Topping topping, Bottom bottom) {
        this.topping = topping;
        this.bottom = bottom;
    }

    public double getUnitPrice() {
        return topping.getPrice() + bottom.getPrice();
    }

    public String getFullName() {
        return "Bund: " + bottom.getName() + ", top: " + topping.getName();
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }

    public Bottom getBottom() {
        return bottom;
    }

    public void setBottom(Bottom bottom) {
        this.bottom = bottom;
    }
}
