package com.example.computerandfishstore.classes;

import com.example.computerandfishstore.MainMenu;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for an item to be listed in the Store.
 */
public abstract class StoreItem {

	/**
	 * Helper method for getting the image object from the provided file name.
	 * @param fileName The name of the image file to get.
	 * @return The Image.
	 */
	protected static Image getImage(String fileName) {
		return new Image(MainMenu.class.getResource("Images/" + fileName).toExternalForm());
	}

	private final String name;
	private final double price;

	/**
	 * Constructs a new StoreItem.
	 * @param name The name of the StoreItem.
	 * @param price The price of the StoreItem.
	 */
	public StoreItem(String name, double price) {
		this.name = name;
		this.price = price;
	}

	/**
	 * @return The name of the StoreItem.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The price of the StoreItem.
	 */
	public double getPrice(){
		return this.price;
	}

	/**
	 * @return The image of the StoreItem. Used in Menus.
	 */
	public abstract Image getImage();

	/**
	 * @return The displayed properties of the StoreItem. Used in Menus.
	 */
	public abstract List<String> getProperties();

	@Override
	public String toString() {
		return String.format("%-20s%.2f%n",
						"Price: ", price);
	}

}
