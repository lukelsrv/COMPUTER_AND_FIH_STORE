package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.classes.RigComponent;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for GPUs.
 */
public class GPU extends RigComponent {

	private static final RigComponentType COMPONENT_TYPE = RigComponentType.GPU;
	private static final Image IMAGE = getImage("GPU.png");

	private final int capacityGB;

	/**
	 * Construct a new GPU.
	 * @param name The name of this GPU.
	 * @param price The price of this GPU.
	 * @param requiredPower The required power of this GPU.
	 * @param capacityGB The capacity of internal RAM in GB of this GPU.
	 */
	public GPU(String name, double price, int requiredPower, int capacityGB) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.capacityGB = capacityGB;
	}

	/**
	 * @return The capacity of internal RAM in GB of this GPU.
	 */
	public int getCapacityGB() {
		return capacityGB;
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of("Internal RAM: " + capacityGB + "GB");
	}

}
