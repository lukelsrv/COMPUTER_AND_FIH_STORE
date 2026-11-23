package com.example.computerandfishstore.computer.utils;

public record Validation(boolean valid, String message) {

	public Validation(boolean valid) {
		this(valid, "");
	}

}
