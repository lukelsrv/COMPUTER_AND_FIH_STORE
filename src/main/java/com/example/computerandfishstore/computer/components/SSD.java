package com.example.computerandfishstore.computer.components;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for SSD Storage.
 */
public class SSD extends Storage {

	private static final Image IMAGE = getImage("SSD.png");

	/**
	 * Constructs a new SSD.
	 * @param name The name of this SSD.
	 * @param price The price of this SSD.
	 * @param requiredPower The required power of this SSD.
	 * @param capacityGB The capacity of storage in GB of this SSD.
	 * @param internal Whether this SSD is for internal usage.
	 */
	public SSD(String name, double price, int requiredPower, int capacityGB, boolean internal) {
		super(name, price, requiredPower, capacityGB, internal);
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of(
			"Type: SSD",
			"Internal: " + (isInternal() ? "Yes" : "No"),
			"Capacity: " + getCapacityGB() + "GB"
		);
	}

}
