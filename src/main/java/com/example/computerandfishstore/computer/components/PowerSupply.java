package com.example.computerandfishstore.computer.components;

import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.classes.RigComponent;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for PowerSupplys.
 */
public class PowerSupply extends RigComponent {

	private static final RigComponentType COMPONENT_TYPE = RigComponentType.POWER_SUPPLY;
	private static final Image IMAGE = getImage("POWERSUPPLY.png");

	private final int suppliedPower;

	/**
	 * Constructs a new PowerSupply.
	 * @param name The name of this PowerSupply.
	 * @param price The price of this PowerSupply.
	 * @param suppliedPower The amount of power supplied by this PowerSupply.
	 */
	public PowerSupply(String name, double price, int suppliedPower) {
		super(name, price, COMPONENT_TYPE, 0);
		this.suppliedPower = suppliedPower;
	}

	/**
	 * @return The amount of power supplied by this PowerSupply.
	 */
	public int getSuppliedPower() {
		return suppliedPower;
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of("Supplies: " + suppliedPower + "W");
	}

}
