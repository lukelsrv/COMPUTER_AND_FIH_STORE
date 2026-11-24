package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.BrandType;
import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.classes.RigComponent;

import java.util.Set;

/**
 * Typed class for Motherboards.
 */
public class Motherboard extends RigComponent {

	private static final RigComponentType COMPONENT_TYPE = RigComponentType.MOTHERBOARD;

	private final int ramDDR;
	private final Set<BrandType> supportedBrands;

	/**
	 * Constructs a new Motherboard.
	 * @param name The name of this motherboard.
	 * @param price The price of this motherboard.
	 * @param requiredPower The required power of this motherboard.
	 * @param ramDDR The ram DDR type of this motherboard.
	 * @param supportedBrands The brand types supported of this motherboard.
	 */
	public Motherboard(String name, double price, int requiredPower, int ramDDR, Set<BrandType> supportedBrands) {
		super(name, price, COMPONENT_TYPE, requiredPower);
		this.ramDDR = ramDDR;
		this.supportedBrands = supportedBrands;
	}

	/**
	 * @return The ram DDR type of this motherboard.
	 */
	public int getRamDDR() {
		return ramDDR;
	}

	/**
	 * @return The brand types supported of this motherboard.
	 */
	public Set<BrandType> getSupportedBrands() {
		return supportedBrands;
	}

}
