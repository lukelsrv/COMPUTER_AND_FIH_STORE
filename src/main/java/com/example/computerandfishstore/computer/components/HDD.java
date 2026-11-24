package com.example.computerandfishstore.computer.components;

/**
 * Typed class for HDD Storage.
 */
public class HDD extends Storage {

	/**
	 * Constructs a new HDD.
	 * @param name The name of this HDD.
	 * @param price The price of this HDD.
	 * @param requiredPower The required power of this HDD.
	 * @param capacityGB The capacity of storage in GB of this HDD.
	 * @param internal Whether this HDD is for internal usage.
	 */
	public HDD(String name, double price, int requiredPower, int capacityGB, boolean internal) {
		super(name, price, requiredPower, capacityGB, internal);
	}

}
