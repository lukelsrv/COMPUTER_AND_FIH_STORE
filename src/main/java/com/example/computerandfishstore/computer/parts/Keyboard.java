package com.example.computerandfishstore.computer.parts;

import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.classes.KeyboardSize;

public class Keyboard extends ComputerPart {

	private final KeyboardSize size;
	private final boolean rgb;

	public Keyboard(String name, double price, KeyboardSize size, boolean rgb) {
		super(name, price);
		this.size = size;
		this.rgb = rgb;
	}

	public KeyboardSize getSize() {
		return size;
	}

	public boolean hasRGB() {
		return rgb;
	}

}
