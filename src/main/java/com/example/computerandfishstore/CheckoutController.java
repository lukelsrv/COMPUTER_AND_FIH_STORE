package com.example.computerandfishstore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the Checkout Menu.
 */
public class CheckoutController {
	@FXML
	private Button ConfirmCheckout;

	@FXML
	private Button BackButton;

	@FXML
	private TextArea CartTextArea;

	/**
	 * Initializes the controller and displays the cart of items added by the user along with the total by calling the displayCart method
	 * Assigns actioin events to the checkout and back buttons which call the appropriate methods.
	 */
	public void initialize() {
		displayCart();

		BackButton.setOnAction(event -> backToMainMenu());

		ConfirmCheckout.setOnAction(event ->
			checkoutToMainMenu());
	}

	/**
	 * Helper method to go back to the main menu
	 */
	private void backToMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) BackButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {}
	}

	/**
	 * Helper method to check out and return to the main menu
	 * also clears the cart to demonstrate that the user has checked out and has the option to start another cart
	 */
	private void checkoutToMainMenu() {
		try {
			MainMenuController.CART.clearCart();
			FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) ConfirmCheckout.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {}
	}

	public void displayCart() {
		CartTextArea.setText(MainMenuController.CART.getDisplay());
	}
}
