package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.classes.RigComponent;

/**
 * Typed class for CPU Cooles.
 */
public class CPUCooler extends RigComponent {

	private static final RigComponentType COMPONENT_TYPE = RigComponentType.CPU_COOLER;

	/**
	 * Constructs a new CPUCooler.
	 * @param name The name of this CPUCooler.
	 * @param price The price of this CPUCooler.
	 * @param requiredPower The required power of this CPUCooler.
	 */
	public CPUCooler(String name, double price, int requiredPower) {
		super(name, price, COMPONENT_TYPE, requiredPower);
	}

}
