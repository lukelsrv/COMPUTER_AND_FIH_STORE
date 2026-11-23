package com.example.computerandfishstore;

public class StoreItem {
    private double price;
    private int cartQuantity;

    public StoreItem(double price, int cartQuantity){
        this.price = price;
        this.cartQuantity = cartQuantity;
    }

    public StoreItem(){
    }

    public double getPrice(){
        return this.price;
    }

    public int getCartQuantity(){
        return this.cartQuantity;
    }

    public void setCartQuantity(int cartQuantity){
        this.cartQuantity = cartQuantity;
    }

    @Override
    public String toString() {
        return String.format("%-20s%.2f%n",
                        "Price: ", price);
    }
}
