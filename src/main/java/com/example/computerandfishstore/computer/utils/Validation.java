package com.example.computerandfishstore.computer.utils;

/**
 * Helper class for ensuring a check passed, otherwise contains the reasoning for failure.
 * @param valid Whether validation was successful.
 * @param message The reasoning for failure.
 */
public record Validation(boolean valid, String message) {

	public Validation(boolean valid) {
		this(valid, "");
	}

}
