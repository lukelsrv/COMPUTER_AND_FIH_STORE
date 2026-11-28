package com.example.computerandfishstore;

import com.example.computerandfishstore.classes.Buildable;
import com.example.computerandfishstore.classes.Buildable.Builder;
import com.example.computerandfishstore.classes.Buildable.ComponentType;
import com.example.computerandfishstore.classes.Cart;
import com.example.computerandfishstore.computer.parts.Rig.RigBuilder;
import com.example.computerandfishstore.fish.Aquarium;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for MainMenu.
 */
public class MainMenuController {

	public static final Cart CART = new Cart();

	//creates Button variables for the buttons on the main menu FXML sheet
	@FXML
	private Button shopButton;

	@FXML
	private Button buildComputerButton;

	@FXML
	private Button buildAquariumButton;

	@FXML
	private Button checkoutButton;

	//creates a variable for the text area
	@FXML
	private TextArea TotalAndContentsTextArea;

	/**
	 * Initializes this controller by displaying cart on the menu and adding handlers to buttons.
	 */
	@FXML
	public void initialize() {
		displayCart();

		shopButton.setOnAction(event -> toShop());

		//The controls for the Build Aquarium button
		buildAquariumButton.setOnMouseClicked(event -> toBuilder(new Aquarium.AquariumBuilder()));

		//The controls for the Build Aquarium button
		buildComputerButton.setOnMouseClicked(event -> toBuilder(new RigBuilder()));

		//The controls for the Build Aquarium button
		checkoutButton.setOnMouseClicked(event -> toCheckout());

	}

	/**
	 * Helper method to open the ShopMenu.
	 */
	private void toShop() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShopMenu.fxml"));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Stage stage = (Stage) buildAquariumButton.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}

	/**
	 * Helper method to open the BuilderMenu.
	 * @param builder The builder to use in the BuilderMenuController.
	 */
	private <B extends Buildable, C extends ComponentType<B>> void toBuilder(Builder<B, C> builder) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BuilderMenu.fxml"));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Stage stage = (Stage) buildAquariumButton.getScene().getWindow();
		BuilderMenuController<B, C> builderMenuController = loader.getController();
		builderMenuController.init(builder);
		stage.setScene(new Scene(root));
		stage.show();
	}

	private void toCheckout() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Checkout.fxml"));
		Parent root;
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Stage stage = (Stage) checkoutButton.getScene().getWindow();
		stage.setScene(new Scene(root));
		stage.show();
	}

	/**
	 * Helper method to display/update the cart on the main menu.
	 */
	public void displayCart() {
		TotalAndContentsTextArea.setText(CART.getDisplay());
	}

}
