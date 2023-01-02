//Class representing an electronic store
//Has an array of products that represent the items the store can sell

import java.util.ArrayList;
import java.util.Scanner;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts, sales;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue, avg;
    private ArrayList<Product> productsInCart;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        productsInCart = new ArrayList<Product>();
    }

    public String getName() {
        return name;
    }
    public Product[] getStock() { return stock; }
    public int getCurProducts() { return curProducts; }
    public ArrayList<Product> getProductsInCart() { return productsInCart; }
    public double getRevenue() { return revenue; }
    public int getSales() { return sales; }
    public double getAvg() { return avg; }


    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }

    public Product[] computePopular() {
        Product[] popular;

        if (curProducts == 0) {
            Product[] tempPopular = {};
            return tempPopular;
        }
        else if (curProducts > 0 && curProducts < 3) {
            popular = new Product[curProducts];
        }
        else {
            popular = new Product[3];
        }

        Product[] popularTemp = new Product[curProducts];

        int count = 0;
        for (Product p: stock) {
            if (p != null) {
                popularTemp[count] = p;
                count++;
            }
        }

        for (int i = 0; i < popularTemp.length; i++) {
            for (int k = 1; k < popularTemp.length; k++) {
                if (popularTemp[k].getSoldQuantity() >= popularTemp[k - 1].getSoldQuantity()) {
                    Product temp = popularTemp[k];
                    popularTemp[k] = popularTemp[k - 1];
                    popularTemp[k - 1] = temp;
                }
            }
        }

        count = 0;
        for (Product p: popularTemp) {
            if (count < 3) {
                popular[count] = p;
                count++;
            }
        }

        return popular;

    }

    public void addToCart() {
        productsInCart.clear();
        for (Product p: stock) {
            if (p != null) {
                if (p.getInCart() > 0) {
                    productsInCart.add(p);
                }
            }
        }
    }

    public void removeFromCart(Product p) {
        if (p.getInCart() == 0) {
            productsInCart.remove(p);
        }
    }

    public Product getCartIndex(int index) {
        return productsInCart.get(index);
    }

    public void computeSales() {
        for (Product p: productsInCart) {
            revenue += p.sellUnits(p.getInCart());
        }
        sales++;
        productsInCart.clear();
        avg = revenue / sales;
    }

    public double computeTotal() {
        double total = 0.0;
        for (Product p: productsInCart) {
            total += p.getPrice() * p.getInCart();
        }
        return total;
    }



} 