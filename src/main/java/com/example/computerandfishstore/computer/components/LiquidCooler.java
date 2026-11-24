package com.example.computerandfishstore.computer.components;

/**
 * Typed class for LiquidCooler for CPU cooling.
 */
public class LiquidCooler extends CPUCooler {

	/**
	 * Constructs a new LiquidCooler.
	 * @param name The name of this LiquidCooler.
	 * @param price The price of this LiquidCooler.
	 * @param requiredPower The required power of this LiquidCooler.
	 */
	public LiquidCooler(String name, double price, int requiredPower) {
		super(name, price, requiredPower);
	}

}
