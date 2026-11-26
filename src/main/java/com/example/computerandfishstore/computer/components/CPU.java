package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.BrandType;
import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.classes.RigComponent;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for CPUs.
 */
public class CPU extends RigComponent {

	private static final RigComponentType COMPONENT_TYPE = RigComponentType.CPU;
	private static final Image IMAGE = getImage("CPU.png");

	private final BrandType brandType;
	private final int cores;
	private final int processors;

	/**
	 * Constructs a new CPU.
	 * @param name The name of this CPU.
	 * @param price The price of this CPU.
	 * @param requiredPower The required power of this CPU.
	 * @param brandType The brand type of this CPU.
	 * @param cores The number of cores of this CPU.
	 * @param processors The number of processor of this CPU.
	 */
	public CPU(String name, double price, int requiredPower, BrandType brandType, int cores, int processors) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.brandType = brandType;
		this.cores = cores;
		this.processors = processors;
	}

	/**
	 * @return The brand type of this CPU.
	 */
	public BrandType getBrandType() {
		return brandType;
	}

	/**
	 * @return The number of cores of this CPU.
	 */
	public int getCores() {
		return cores;
	}

	/**
	 * @return The number of processor of this CPU.
	 */
	public int getProcessors() {
		return processors;
	}

	/**
	 * @param brandType The brand type to check against.
	 * @return {@code true} if this CPU supports the brand type, otherwise {@code false}.
	 */
	public boolean supports(BrandType brandType) {
		return this.brandType == brandType;
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of(
			"Brand Type: " + brandType,
			"Cores: " + cores,
			"Processors: " + processors
		);
	}

}
