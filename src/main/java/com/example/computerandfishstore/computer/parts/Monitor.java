package com.example.computerandfishstore.computer.parts;

import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.classes.Resolution;

/**
 * Typed class for Monitors.
 */
public class Monitor extends ComputerPart {

	private final int screenWidth;
	private final int screenLength;
	private final Resolution resolution;

	/**
	 * Constructs a new Monitor.
	 * @param name The name of this Monitor.
	 * @param price The price of this Monitor.
	 * @param screenWidth The screen width of this Monitor.
	 * @param screenLength The screen length of this Monitor.
	 * @param resolution The resolution this Monitor displays.
	 */
	public Monitor(String name, double price, int screenWidth, int screenLength, Resolution resolution) {
		super(name, price);
		this.screenWidth = screenWidth;
		this.screenLength = screenLength;
		this.resolution = resolution;
	}

	/**
	 * @return The screen width of this Monitor.
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @return The screen length of this Monitor.
	 */
	public int getScreenLength() {
		return screenLength;
	}

	/**
	 * @return The resolution this Monitor displays.
	 */
	public Resolution getResolution() {
		return resolution;
	}

}
