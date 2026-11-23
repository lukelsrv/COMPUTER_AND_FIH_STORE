package com.example.computerandfishstore.computer.parts;

import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.classes.MouseType;

public class Mouse extends ComputerPart {

	private final MouseType mouseType;

	public Mouse(String name, double price, MouseType mouseType) {
		super(name, price);
		this.mouseType = mouseType;
	}

	public MouseType getMouseType() {
		return mouseType;
	}

}
