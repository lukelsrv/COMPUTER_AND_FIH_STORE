package com.example.computerandfishstore.computer.components;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for HeatSinks for CPU cooling.
 */
public class HeatSink extends CPUCooler {

	private static final Image IMAGE = getImage("HEATSINK.png");

	/**
	 * Constructs a new HeatSink.
	 * @param name The name of this HeatSink.
	 * @param price The price of this HeatSink.
	 * @param requiredPower The required power of this HeatSink.
	 */
	public HeatSink(String name, double price, int requiredPower) {
		super(name, price, requiredPower);
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of("Type: Heat Sink");
	}

}
