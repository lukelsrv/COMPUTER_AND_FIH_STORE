package com.example.computerandfishstore;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CFStoreMainMenuController {

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

    private static int aquariumCounter = 0;
    private static double mainTotal = 0;

    private AquariumBuilderController aquarium;
    //keeps track of how many aquariums the

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

    /**
     * Method to display the main total
     * @return
     */
    public String displayMainTotal(){
        String mainTotalString = String.format("%s%n%n%s%s%.2f%n%n",
                                        "Your Cart:",
                                        "Total: ", "$",
                                        mainTotal);
        return mainTotalString;
    }

    /**
     * method to display the cart, is called in the aquarium builder controller
     */
    public void displayCart(){

        String aquariumCounterString = "";
        if(aquariumCounter > 0){
            aquariumCounterString = String.format("%s%s%d",
                    "Custom Aquarium", " x", aquariumCounter);
        }

        String cartString = String.format("%s%s",
                    displayMainTotal(), aquariumCounterString);

        TotalAndContentsTextArea.setText(cartString);
    }

    /**
     * method to increment the main shopping total, by taking the totals from the other methods and adding to them
     * @param aquarium
     * @param aquariumTotal
     */
    public void incrementTotal(AquariumBuilderController aquarium, double aquariumTotal){
        mainTotal += aquariumTotal;
        //mainTotal += computerTotal
        //mainTotal += prices of individual stuff the user wants to buy
    }

    /**
     * Increments the counters related to custom created products by the user
     */
    public void incrementCustomBuildCounter(){
        aquariumCounter += 1;
        //computer counter += 1
    }
}
