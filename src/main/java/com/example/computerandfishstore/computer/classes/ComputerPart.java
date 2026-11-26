package com.example.computerandfishstore.computer.classes;

import com.example.computerandfishstore.StoreItem;
import com.example.computerandfishstore.computer.Buildable.Component;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for Computer Parts.
 */
public abstract class ComputerPart {

	protected static Image getImage(String fileName) {
		return new Image(StoreItem.class.getResource("Images/" + fileName).toExternalForm());
	}

	private final String name;
	private final double price;

	/**
	 * Constructs a new ComputerPart.
	 * @param name The name of this ComputerPart.
	 * @param price The price of this ComputerPart.
	 */
	public ComputerPart(String name, double price) {
		this.name = name;
		this.price = price;
	}

	/**
	 * @return The name of this ComputerPart.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The price of this ComputerPart.
	 */
	public double getPrice() {
		return price;
	}

	public abstract Image getImage();

	public abstract List<String> getProperties();

}
