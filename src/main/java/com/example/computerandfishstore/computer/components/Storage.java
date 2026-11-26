package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.classes.RigComponent;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for Storage.
 */
public class Storage extends RigComponent {

	private static final RigComponentType COMPONENT_TYPE = RigComponentType.STORAGE;
	private static final Image IMAGE = getImage("STORAGE.png");

	private final int capacityGB;
	private final boolean internal;

	/**
	 * Constructs a new Storage.
	 * @param name The name of this Storage.
	 * @param price The price of this Storage.
	 * @param requiredPower The required power of this Storage.
	 * @param capacityGB The capacity of storage in GB of this Storage.
	 * @param internal Whether this Storage is for internal usage.
	 */
	public Storage(String name, double price, int requiredPower, int capacityGB, boolean internal) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.capacityGB = capacityGB;
		this.internal = internal;
	}

	/**
	 * @return The capacity of storage in GB of this Storage.
	 */
	public int getCapacityGB() {
		return capacityGB;
	}

	/**
	 * @return {@code true} if this Storage is for internal usage, otherwise {@code false}.
	 */
	public boolean isInternal() {
		return internal;
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of(
			"Internal: " + (internal ? "Yes" : "No"),
			"Capacity: " + capacityGB + "GB"
		);
	}

}
