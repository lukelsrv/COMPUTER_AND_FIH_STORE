package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.StoreItem;
import javafx.scene.image.Image;

import java.util.List;

public class Fish extends StoreItem {

	private final double designatedGallons;
	private final boolean large;
	private final boolean carnivorous;
	private final boolean saltwater;

	public Fish(
		String name,
		double price,
		boolean large,
		boolean carnivorous,
		boolean saltwater,
		double designatedGallons
	) {
		super(name, price);
		this.large = large;
		this.carnivorous = carnivorous;
		this.saltwater = saltwater;
		this.designatedGallons = designatedGallons;
	}

	public String getSize() {
		return large ? "Large" : "Small";
	}

	public String getDiet(){
		//carnivorous fish are incompatible with small fish and cannot be in the same tank
		return carnivorous ? "Carnivorous" : "Herbivore";
	}

	public String getWaterType(){
		return saltwater ? "Saltwater" : "Freshwater";
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

	public double getDesignatedGallons() {
		return this.designatedGallons;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public List<String> getProperties() {
		return List.of(
			"Size: " + getSize(),
			"Diet: " + getDiet(),
			"Water Type: " + getWaterType()
		);
	}

	@Override
	public String toString() {
		return String.format("%-20s%-20s%s%n%-20s%s%n%-20s%s%n%n",
				super.toString(),
				"Size: ", this.getSize(),
				"Diet: ", this.getDiet(),
				"Water type: ", this.getWaterType());
	}

}
