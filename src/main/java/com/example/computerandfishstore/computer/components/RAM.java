package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.classes.RigComponent;

/**
 * Typed class for RAM.
 */
public class RAM extends RigComponent {

	private static final RigComponentType COMPONENT_TYPE = RigComponentType.RAM;

	private final int ramDDR;
	private final int speed;
	private final int capacityGB;

	/**
	 * Constructs a new RAM.
	 * @param name The name of this RAM.
	 * @param price The price of this RAM.
	 * @param requiredPower The required power of this RAM.
	 * @param ramDDR The ram DDR type of this RAM.
	 * @param speed The speed of this RAM.
	 * @param capacityGB The capacity of RAM in GB of this RAM.
	 */
	public RAM(String name, double price, int requiredPower, int ramDDR, int speed, int capacityGB) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.ramDDR = ramDDR;
		this.speed = speed;
		this.capacityGB = capacityGB;
	}

	/**
	 * @return The ram DDR type of this RAM.
	 */
	public int getRamDDR() {
		return ramDDR;
	}

	/**
	 * @return The speed of this RAM.
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @return The capacity of RAM in GB of this RAM.
	 */
	public int getCapacityGB() {
		return capacityGB;
	}

}
