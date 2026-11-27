package com.example.computerandfishstore.computer.classes;

import com.example.computerandfishstore.MainMenu;
import com.example.computerandfishstore.classes.StoreItem;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for Computer Parts.
 */
public abstract class ComputerPart extends StoreItem {

	/**
	 * Constructs a new ComputerPart.
	 * @param name The name of this ComputerPart.
	 * @param price The price of this ComputerPart.
	 */
	public ComputerPart(String name, double price) {
		super(name, price);
	}



}
