package com.example.computerandfishstore.computer.utils;

public record ValidationResult<T>(boolean valid, String message, T result) {

	public ValidationResult(boolean valid) {
		this(valid, "");
	}

	public ValidationResult(boolean valid, String message) {
		this(valid, message, null);
	}

	public ValidationResult(T result) {
		this(true, "", result);
	}

}
