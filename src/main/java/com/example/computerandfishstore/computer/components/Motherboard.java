package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.BrandType;
import com.example.computerandfishstore.computer.classes.ComponentType;
import com.example.computerandfishstore.computer.classes.ComputerComponent;

import java.util.Set;

public class Motherboard extends ComputerComponent {

	private static final ComponentType COMPONENT_TYPE = ComponentType.MOTHERBOARD;

	private final int ramDDR;
	private final Set<BrandType> supportedBrands;
	private final int ramSlots;

	public Motherboard(String name, double price, int requiredPower, int ramDDR, Set<BrandType> supportedBrands, int ramSlots) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.ramDDR = ramDDR;
		this.supportedBrands = supportedBrands;
		this.ramSlots = ramSlots;
	}

	public int getRamDDR() {
		return ramDDR;
	}

	public Set<BrandType> getSupportedBrands() {
		return supportedBrands;
	}

	public int getRamSlots() {
		return ramSlots;
	}

}
