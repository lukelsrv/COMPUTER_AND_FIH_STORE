package com.example.computerandfishstore;

public class Fish extends StoreItem {
    private String name;
    private double designatedGallons;
    private boolean large;
    private boolean carnivorous;
    private boolean saltwater;

    public Fish(double price, int cartQuantity,
                boolean large, String name, boolean carnivorous, boolean saltwater, double designatedGallons){
        super(price, cartQuantity);
        this.large = large;
        this.name = name;
        this.carnivorous = carnivorous;
        this.saltwater = saltwater;
        this.designatedGallons = designatedGallons;
    }

    public Fish(){
    }

    public String getSize(){
        if(this.large){
            return "Large";
        }
        else{
            return "Small";
        }
    }

    public String getDiet(){
        //carnivorous fish are incompatible with small fish and cannot be in the same tank
        if(this.carnivorous){
            return "Carnivorous";
        }
        else{
            return "Herbivore";
        }
    }

    public String getWaterType(){
        if(this.saltwater){
            return "Saltwater";
        }
        else{
            return "Freshwater";
        }
    }

    public boolean checkLarge(){
        return this.large;
    }

    public boolean checkCarnivorous(){
        return this.carnivorous;
    }

    public boolean checkSaltwater(){
        return this.saltwater;
    }

    public String getName(){
        return this.name;
    }

    public double getDesignatedGallons() { return this.designatedGallons;}

    @Override
    public String toString() {
        return String.format("%-20s%s%n%-20s%-20s%s%n%-20s%s%n%-20s%s%n%n",
                "Name: ", name,
                super.toString(),
                "Size: ", this.getSize(),
                "Diet: ", this.getDiet(),
                "Water type: ", this.getWaterType());
    }
}
