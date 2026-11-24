package com.example.computerandfishstore.computer.components;

/**
 * Typed class for SSD Storage.
 */
public class SSD extends Storage {

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

}
