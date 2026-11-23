package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.ComponentType;
import com.example.computerandfishstore.computer.classes.ComputerComponent;

public class GPU extends ComputerComponent {

	private static final ComponentType COMPONENT_TYPE = ComponentType.GPU;

	private final int capacityGB;

	public GPU(String name, double price, int requiredPower, int capacityGB) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.capacityGB = capacityGB;
	}

	public int getCapacityGB() {
		return capacityGB;
	}

}
