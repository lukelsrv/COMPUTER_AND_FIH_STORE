package com.example.computerandfishstore.classes;

/**
 * Helper class for getting data and ensuring it validity, otherwise contains reasoning for failure.
 * @param valid Whether validation was successful.
 * @param message The reasoning for failure.
 * @param result The resulting data object.
 * @param <T> The type of data expected for result.
 */
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
