package com.example.computerandfishstore;

public class FishTank extends StoreItem {
    private String tankClass;
    private double gallons;

    public FishTank(double price, int cartQuantity,
                    double gallons, String tankClass){
        super(price, cartQuantity);
        this.gallons = gallons;
        this.tankClass = tankClass;
    }

    public double getGallons(){
        return this.gallons;
    }

    public String getTankClass(){
        return this.tankClass;
    }

    @Override
    public String toString() {
        return String.format("%s%-20s%.2f%s%n%-20s%s%n%n",
                super.toString(),
                "Tank Size: ", gallons, " gallons",
                "Tank Class: ", tankClass);
    }
}
