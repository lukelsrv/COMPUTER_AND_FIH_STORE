package com.example.computerandfishstore;

import com.example.computerandfishstore.classes.Cart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class CheckoutController {
	@FXML
	private Button ConfirmCheckout;

	@FXML
	private Button BackButton;

	@FXML
	private TextArea CartTextArea;

	@FXML
	public void initialize() {
		displayCart();

		BackButton.setOnAction(event -> backToMainMenu());

		ConfirmCheckout.setOnAction(event ->
			checkoutToMainMenu());
	}

	private void backToMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) BackButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {}
	}

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
