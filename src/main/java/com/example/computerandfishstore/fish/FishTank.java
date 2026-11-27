package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.StoreItem;
import javafx.scene.image.Image;

import java.util.List;

public class FishTank extends AquariumComponent {

	private static final AquariumComponentType COMPONENT_TYPE = AquariumComponentType.TANK;

	private final double gallons;
	private final Image image;

	public FishTank(
		String name,
		double price,
		double gallons,
		String image
	) {
		super(name, price, COMPONENT_TYPE);
		this.gallons = gallons;
		this.image = getImage(image);
	}

	public double getGallons(){
		return this.gallons;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public List<String> getProperties() {
		return List.of("Size: " + gallons + " gallons");
	}

	@Override
	public String toString() {
		return String.format("%s%-20s%.2f%s%n%n",
				super.toString(),
				"Tank Size: ", gallons, " gallons");
	}

}
