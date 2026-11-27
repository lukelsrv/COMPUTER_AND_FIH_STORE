package com.example.computerandfishstore.classes;

import com.example.computerandfishstore.MainMenu;
import javafx.scene.image.Image;

import java.util.List;

public abstract class StoreItem {

	protected static Image getImage(String fileName) {
		return new Image(MainMenu.class.getResource("Images/" + fileName).toExternalForm());
	}

	private final String name;
	private final double price;

	public StoreItem(String name, double price){
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getPrice(){
		return this.price;
	}

	public abstract Image getImage();

	public abstract List<String> getProperties();

	@Override
	public String toString() {
		return String.format("%-20s%.2f%n",
						"Price: ", price);
	}

}
