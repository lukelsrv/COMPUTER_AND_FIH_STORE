package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.ComponentType;
import com.example.computerandfishstore.computer.classes.ComputerComponent;

public class RAM extends ComputerComponent {

	private static final ComponentType COMPONENT_TYPE = ComponentType.RAM;

	private final int ramDDR;
	private final int speed;
	private final int capacityGB;

	public RAM(String name, double price, int requiredPower, int ramDDR, int speed, int capacityGB) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.ramDDR = ramDDR;
		this.speed = speed;
		this.capacityGB = capacityGB;
	}

	public int getRamDDR() {
		return ramDDR;
	}

	public int getSpeed() {
		return speed;
	}

	public int getCapacityGB() {
		return capacityGB;
	}

}
