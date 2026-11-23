package com.example.computerandfishstore.computer.parts;

import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.classes.Resolution;

public class Monitor extends ComputerPart {

	private final int screenWidth;
	private final int screenLength;
	private final Resolution resolution;

	public Monitor(String name, double price, int screenWidth, int screenLength, Resolution resolution) {
		super(name, price);
		this.screenWidth = screenWidth;
		this.screenLength = screenLength;
		this.resolution = resolution;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenLength() {
		return screenLength;
	}

	public Resolution getResolution() {
		return resolution;
	}

}
