package com.example.computerandfishstore.computer.parts;

import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.classes.KeyboardSize;
import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for Keyboards.
 */
public class Keyboard extends ComputerPart {

	private static final Image IMAGE = getImage("KEYBOARD.png");

	private final KeyboardSize size;
	private final boolean rgb;

	/**
	 * Constructs a new Keyboard.
	 * @param name The name of this Keyboard.
	 * @param price The price of this Keyboard.
	 * @param size The keyboard size of this Keyboard.
	 * @param rgb Whether this Keyboard has RGB.
	 */
	public Keyboard(String name, double price, KeyboardSize size, boolean rgb) {
		super(name, price);
		this.size = size;
		this.rgb = rgb;
	}

	/**
	 * @return The keyboard size of this Keyboard.
	 */
	public KeyboardSize getSize() {
		return size;
	}

	/**
	 * @return {@code true} if this Keyboard has RGB, otherwise {@code false}.
	 */
	public boolean hasRGB() {
		return rgb;
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of(
			"Size: " + size,
			"RGB: " + (rgb ? "Yes" : "No")
		);
	}

}
