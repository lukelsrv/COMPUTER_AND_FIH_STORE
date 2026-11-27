package com.example.computerandfishstore.fish;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for Fish.
 */
public class Fish extends AquariumComponent {

	private static final AquariumComponentType COMPONENT_TYPE = AquariumComponentType.FISH;

	private final double designatedGallons;
	private final boolean large;
	private final boolean carnivorous;
	private final boolean saltwater;
	private final Image image;

	/**
	 * Constructs a new Fish.
	 * @param name The name of this Fish.
	 * @param price The price of this Fish.
	 * @param large Whether this Fish is large.
	 * @param carnivorous Whether this Fish is carnivorous.
	 * @param saltwater Whether this fish requires saltwater.
	 * @param designatedGallons The gallons needed for this Fish.
	 * @param image The image of this Fish.
	 */
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

	/**
	 * @return Whether this Fish is large.
	 */
	public boolean isLarge() {
		return large;
	}

	/**
	 * @return Whether this Fish is carnivorous.
	 */
	public boolean isCarnivorous() {
		return carnivorous;
	}

	/**
	 * @return Whether this Fish requires saltwater.
	 */
	public boolean isSaltwater() {
		return saltwater;
	}

	/**
	 * @return The gallons needed for this Fish.
	 */
	public double getDesignatedGallons() {
		return this.designatedGallons;
	}

	@Override
	public Image getImage() {
		return image;
	}

	/**
	 * @return The displayed size of this Fish.
	 */
	public String getSize() {
		return large ? "Large" : "Small";
	}

	/**
	 * @return The displayed diet of this Fish.
	 */
	public String getDiet(){
		return carnivorous ? "Carnivorous" : "Herbivore";
	}

	/**
	 * @return The displayed water type of this Fish.
	 */
	public String getWaterType(){
		return saltwater ? "Saltwater" : "Freshwater";
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
