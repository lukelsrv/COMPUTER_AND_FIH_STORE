package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.ComponentType;
import com.example.computerandfishstore.computer.classes.ComputerComponent;

public class Storage extends ComputerComponent {

	private static final ComponentType COMPONENT_TYPE = ComponentType.STORAGE;

	private final int capacityGB;
	private final boolean internal;

	public Storage(String name, double price, int requiredPower, int capacityGB, boolean internal) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.capacityGB = capacityGB;
		this.internal = internal;
	}

	public int getCapacityGB() {
		return capacityGB;
	}

	public boolean isInternal() {
		return internal;
	}

}
