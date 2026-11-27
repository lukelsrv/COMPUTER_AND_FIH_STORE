package com.example.computerandfishstore.computer.components;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for LiquidCooler for CPU cooling.
 */
public class LiquidCooler extends CPUCooler {

	private static final Image IMAGE = getImage("LIQUIDCOOLER.png");

	/**
	 * Constructs a new LiquidCooler.
	 * @param name The name of this LiquidCooler.
	 * @param price The price of this LiquidCooler.
	 * @param requiredPower The required power of this LiquidCooler.
	 */
	public LiquidCooler(String name, double price, int requiredPower) {
		super(name, price, requiredPower);
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of("Type: Liquid Cooling");
	}

}
