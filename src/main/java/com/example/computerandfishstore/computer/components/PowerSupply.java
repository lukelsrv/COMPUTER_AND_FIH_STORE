package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.ComponentType;
import com.example.computerandfishstore.computer.classes.ComputerComponent;

public class PowerSupply extends ComputerComponent {

	private static final ComponentType COMPONENT_TYPE = ComponentType.POWER_SUPPLY;

	private final int suppliedPower;

	public PowerSupply(String name, double price, int suppliedPower) {
		super(name, price, COMPONENT_TYPE, 0);
		this.suppliedPower = suppliedPower;
	}

	public int getSuppliedPower() {
		return suppliedPower;
	}

}
