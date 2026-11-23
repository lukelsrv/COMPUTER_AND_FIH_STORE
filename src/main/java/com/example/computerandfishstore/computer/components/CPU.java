package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.BrandType;
import com.example.computerandfishstore.computer.classes.ComponentType;
import com.example.computerandfishstore.computer.classes.ComputerComponent;

public class CPU extends ComputerComponent {

	private static final ComponentType COMPONENT_TYPE = ComponentType.CPU;

	private final BrandType brandType;
	private final int cores;
	private final int processors;

	public CPU(String name, double price, int requiredPower, BrandType brandType, int cores, int processors) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.brandType = brandType;
		this.cores = cores;
		this.processors = processors;
	}

	public BrandType getBrandType() {
		return brandType;
	}

	public int getCores() {
		return cores;
	}

	public int getProcessors() {
		return processors;
	}

	public boolean supports(BrandType brandType) {
		return this.brandType == brandType;
	}

}
