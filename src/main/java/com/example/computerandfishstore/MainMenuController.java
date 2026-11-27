package com.example.computerandfishstore;

import com.example.computerandfishstore.classes.Cart;
import com.example.computerandfishstore.fish.AquariumBuilderController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

	//creates Button variables for the buttons on the main menu FXML sheet
	@FXML
	private Button ShopIndividuallyButton;

	@FXML
	private Button BuildComputerButton;

	@FXML
	private Button BuildAquariumButton;

	@FXML
	private Button CheckoutButton;

	//creates a variable for the text area
	@FXML
	private TextArea TotalAndContentsTextArea;

	private AquariumBuilderController aquarium;

	@FXML
	/**
	 * Method for initializing the main menu to its default state
	 * Contains the controls for the buttons
	 */
	public void initialize() {

		displayCart();

		//The controls for the Build Aquarium button
		BuildAquariumButton.setOnMouseClicked(event -> {
			//enclosed in a try block in case there is an issue with the goToAquariumBuilder method
			//specifically an issue reading in the FXML file
			try {
				//calls method
				goToAquariumBuilder();
			//if an issue arises the exception is caught and a message is shown
			} catch(IOException e) {
				System.out.print("IOException: Issue Reading File");
			}
		});

		//The controls for the Build Aquarium button
		BuildComputerButton.setOnMouseClicked(event -> {
			//enclosed in a try block in case there is an issue with the goToAquariumBuilder method
			//specifically an issue reading in the FXML file
			goToComputerBuilder();
		});

	}

	/**
	 * Method for switching to the aquarium building interface
	 * @throws IOException
	 */
	private void goToAquariumBuilder() throws IOException {
		//Loads the FXML file for the aquarium builder scene and assigns it to the variable loader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AquariumBuilderController.fxml"));
		//loads the FXML file and assigns it to a variable called root
		Parent root = loader.load();
		//Creates a new stage object for it to use
		Stage stage = (Stage) BuildAquariumButton.getScene().getWindow();
		//creates a new instance of the aquarium controller each time the aquarium builder is loaded
		aquarium = loader.getController();
		//creates a new scene using the variable assigned to the FXMLLoader
		stage.setScene(new Scene(root));
		//displays the scene
		stage.show();
	}

	private void goToComputerBuilder() {
		//Loads the FXML file for the computer builder scene and assigns it to the variable loader
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ComputerBuilder.fxml"));
		//loads the FXML file and assigns it to a variable called root
		Parent root = null;
		try {
			root = loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		//Creates a new stage object for it to use
		Stage stage = (Stage) BuildComputerButton.getScene().getWindow();
		//creates a new scene using the variable assigned to the FXMLLoader
		stage.setScene(new Scene(root));
		//displays the scene
		stage.show();
	}

	/**
	 * method to display the cart, is called in the aquarium builder controller
	 */
	public void displayCart() {
		TotalAndContentsTextArea.setText(Cart.getDisplay());
	}

}
