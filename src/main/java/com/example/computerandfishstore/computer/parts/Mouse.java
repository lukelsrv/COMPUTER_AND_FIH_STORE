package com.example.computerandfishstore.computer.parts;

import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.classes.MouseType;

/**
 * Typed class for Mouses.
 */
public class Mouse extends ComputerPart {

	private final MouseType mouseType;

	/**
	 * Constructs a new Mouse.
	 * @param name The name of this Mouse.
	 * @param price The price of this Mouse.
	 * @param mouseType The mouse type of this Mouse.
	 */
	public Mouse(String name, double price, MouseType mouseType) {
		super(name, price);
		this.mouseType = mouseType;
	}

	/**
	 * @return The mouse type of this Mouse.
	 */
	public MouseType getMouseType() {
		return mouseType;
	}

}
