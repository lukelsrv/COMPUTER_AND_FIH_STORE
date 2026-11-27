package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.StoreItem;
import javafx.scene.image.Image;

import java.util.List;

public class Fish extends AquariumComponent {

	private static final AquariumComponentType COMPONENT_TYPE = AquariumComponentType.FISH;

	private final double designatedGallons;
	private final boolean large;
	private final boolean carnivorous;
	private final boolean saltwater;
	private final Image image;

	public Fish(
		String name,
		double price,
		boolean large,
		boolean carnivorous,
		boolean saltwater,
		double designatedGallons,
		String image
	) {
		super(name, price, COMPONENT_TYPE);
		this.large = large;
		this.carnivorous = carnivorous;
		this.saltwater = saltwater;
		this.designatedGallons = designatedGallons;
		this.image = getImage(image);
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

	public boolean isLarge() {
		return large;
	}

	public boolean isCarnivorous() {
		return carnivorous;
	}

	public boolean isSaltwater() {
		return saltwater;
	}

	public double getDesignatedGallons() {
		return this.designatedGallons;
	}

	@Override
	public Image getImage() {
		return image;
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
