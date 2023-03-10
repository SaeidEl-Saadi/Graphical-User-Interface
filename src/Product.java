//Base class for all products the store will sell
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Product {
    private double price;
    private int stockQuantity;
    private int soldQuantity;
    private int inCart; //ADDED ATTRIBUTE

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
        inCart = 0;

    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public double getPrice() {
        return price;
    }

    public int getInCart() { return inCart; }

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public double sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount) {
            stockQuantity -= amount;
            soldQuantity += amount;
            inCart = 0;
            return price * amount;
        }
        return 0.0;
    }

    public void buying() {
        inCart++;
    }

    public void remove() {
        inCart--;
    }




}