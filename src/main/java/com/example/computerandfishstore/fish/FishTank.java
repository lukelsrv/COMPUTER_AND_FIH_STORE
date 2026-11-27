package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.StoreItem;
import javafx.scene.image.Image;

import java.util.List;

public class FishTank extends StoreItem {

	private final double gallons;

	public FishTank(
		String name,
		double price,
		double gallons
	) {
		super(name, price);
		this.gallons = gallons;
	}

	public double getGallons(){
		return this.gallons;
	}

	@Override
	public Image getImage() {
		return null;
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
