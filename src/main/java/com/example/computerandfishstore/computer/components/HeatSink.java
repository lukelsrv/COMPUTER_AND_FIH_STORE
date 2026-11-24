package com.example.computerandfishstore.computer.components;

/**
 * Typed class for HeatSinks for CPU cooling.
 */
public class HeatSink extends CPUCooler {

	/**
	 * Constructs a new HeatSink.
	 * @param name The name of this HeatSink.
	 * @param price The price of this HeatSink.
	 * @param requiredPower The required power of this HeatSink.
	 */
	public HeatSink(String name, double price, int requiredPower) {
		super(name, price, requiredPower);
	}

}
