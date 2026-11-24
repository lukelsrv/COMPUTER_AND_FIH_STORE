package com.example.computerandfishstore.computer.classes;

/**
 * Typed class for Computer Parts.
 */
public class ComputerPart {

	private final String name;
	private final double price;

	/**
	 * Constructs a new ComputerPart.
	 * @param name The name of this ComputerPart.
	 * @param price The price of this ComputerPart.
	 */
	public ComputerPart(String name, double price) {
		this.name = name;
		this.price = price;
	}

	/**
	 * @return The name of this ComputerPart.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The price of this ComputerPart.
	 */
	public double getPrice() {
		return price;
	}

}
