package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.ComponentType;
import com.example.computerandfishstore.computer.classes.ComputerComponent;

public class CPUCooler extends ComputerComponent {

	private static final ComponentType COMPONENT_TYPE = ComponentType.CPU_COOLER;

	public CPUCooler(String name, double price, int requiredPower) {
		super(name, price, COMPONENT_TYPE, requiredPower);
	}

}
