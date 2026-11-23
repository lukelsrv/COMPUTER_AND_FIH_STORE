package com.example.computerandfishstore;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AquariumBuilderController {

    //creating variables for the navigation panes
    @FXML
    private Pane TankPane;

    @FXML
    private Pane WaterTypePane;

    @FXML
    private Pane FishSelectionPane;

    @FXML
    private TextArea AquariumContentsTextArea;

    @FXML
    private TextArea AquariumTotalAndTankCapacityTextArea;

    @FXML
    private TextArea ErrorsTextArea;

    //creating variables for the next and back buttons
    @FXML
    private Button BackButton;

    @FXML
    private Button NextButton;

    //creating variables for the tank selection buttons
    @FXML
    private RadioButton FishbowlButton;

    @FXML
    private RadioButton SmallTankButton;

    @FXML
    private RadioButton MediumTankButton;

    @FXML
    private RadioButton LargeTankButton;

    @FXML
    private RadioButton XLTankButton;

    @FXML
    private RadioButton MegaTankButton;

    //creating variables for the water type selection buttons
    @FXML
    private RadioButton FreshwaterButton;

    @FXML
    private RadioButton SaltwaterButton;

    //controls for Golden Arowana
    @FXML
    private Button goldenArowanaMinusButton;

    @FXML
    private Button goldenArowanaPlusButton;

    //controls for Burmese Greenfish
    @FXML
    private Button burmeseGreenfishMinusButton;

    @FXML
    private Button burmeseGreenfishPlusButton;

    //controls for blue sea bass
    @FXML
    private Button blueSeaBassMinusButton;

    @FXML
    private Button blueSeaBassPlusButton;

    //controls for orange sand shark
    @FXML
    private Button orangeSandSharkMinusButton;

    @FXML
    private Button orangeSandSharkPlusButton;

    //controls for orange sand shark
    @FXML
    private Button purpleMiniStingrayMinusButton;

    @FXML
    private Button purpleMiniStingrayPlusButton;

    //controls for goldfish
    @FXML
    private Button goldfishMinusButton;

    @FXML
    private Button goldfishPlusButton;

    //controls for brazilian silver pacu
    @FXML
    private Button brazilianSilverPacuMinusButton;

    @FXML
    private Button brazilianSilverPacuPlusButton;

    //controls for european teal eel
    @FXML
    private Button europeanTealEelMinusButton;

    @FXML
    private Button europeanTealEelPlusButton;

    //controls for european teal eel
    @FXML
    private Button pinkNebulaBetaMinusButton;

    @FXML
    private Button pinkNebulaBetaPlusButton;

    //the strings that will be displayed in the side boxes on the right
    //one to display saltwater or freshwater
    //one to display the error to the user
    private String userWaterTypeString = "";
    private String error = "";

    //The variables that are defined by the user's choice
    private FishTank userTank;
    private Fish userfish;
    private int userWaterType;
    private int chosenFishIndex;

    //The variables that are related to tank's gallons/capacity
    private double tankOccupancy = 0;
    private double remainingGallons = 0;

    //The variables that are related to the monetary cost of the tank/fish
    private double aquariumTotal = 0;
    private double fishTotal = 0;

    //The inventory for the aquarium that the chosen fish will be added into
    private List<Fish> aquariumInventory = new ArrayList<>();

    private Fish[] fishInventory = {(new Fish(5000.00, 0, true, "Golden Arowana", true, false, 100)),
            (new Fish(15.00, 0, false, "Burmese Greenfish", false, false, 2)),
            (new Fish(18.00, 0, false, "Blue Sea Bass", true, true, 10)),
            (new Fish(2500.00, 0, true, "Orange Sandshark", true, true, 500)),
            (new Fish(150.00, 0, false, "Purple Mini Stingray", true, true, 30)),
            (new Fish(5.00, 0, false, "Goldfish", false, false, 1)),
            (new Fish(200.00, 0, true, "Brazilian Silver Pacu", false, false, 50)),
            (new Fish(120.00, 0, true, "European Teal Eel", true, false, 100)),
            (new Fish(20.00, 0, false, "Pink Nebula Beta", false, false, 1))};

    private FishTank[] tankInventory = {(new FishTank(10.00, 0, 2, "Fishbowl")),
            (new FishTank(30.00, 0, 10, "Small Tank")),
            (new FishTank(60.00, 0, 50, "Medium Tank")),
            (new FishTank(200.00, 0, 100, "large Tank")),
            (new FishTank(800.00, 0, 500, "Extra Large Tank")),
            (new FishTank(3200.00, 0, 2000, "Mega Tank"))};

    /**
     * Initializes the various screens and their respective visibilities, also initializes the controls for the various buttons on the page.
     * Is the default status of the stage upon opening.
     */
    @FXML
    public void initialize() {
        TankPane.setVisible(true);
        WaterTypePane.setVisible(false);
        FishSelectionPane.setVisible(false);

        printAquarium(aquariumInventory);

        printTotalAndCapacity();


        //setting the actions for the next and back buttons
        //it's the same principle for the below buttons
        NextButton.setOnMouseClicked(event -> {
            //method is called
            nextButtonPressed();
            //the contents in the text areas on the right are updated
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        BackButton.setOnMouseClicked(event -> {
            backButtonPressed();
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //setting the actions for the tank selection buttons
        //it's the same principle for the other tank selection buttons
        FishbowlButton.setOnMouseClicked(event -> {
            //calls the method for the appropriate for the choice made by the user
            fishBowlChosen();
            //updates the text in the text areas on the right
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        SmallTankButton.setOnMouseClicked(event -> {
            smallTankChosen();
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        MediumTankButton.setOnMouseClicked(event -> {
            mediumTankChosen();
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        LargeTankButton.setOnMouseClicked(event -> {
            largeTankChosen();
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        XLTankButton.setOnMouseClicked(event -> {
            xlTankChosen();
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        MegaTankButton.setOnMouseClicked(event -> {
            megaTankChosen();
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //setting the actions for the water selection buttons
        //same principle for the freshwater button
        SaltwaterButton.setOnMouseClicked(event -> {
            //calls the appropriate method for the user's choice
            saltwaterChosen();
            //updates the text in the text area on the right
            printTotalAndCapacity();
        });

        FreshwaterButton.setOnMouseClicked(event -> {
            freshwaterChosen();
            printTotalAndCapacity();
        });

        //golden arowana buttons
        //it is the same principle for the other fishes' add and subtract buttons
        goldenArowanaPlusButton.setOnMouseClicked(event -> {
            //Since the fish inventory defined above is unchanging, each fish has a unique index
            //the variable below is how the fish is selected from the fish inventory and used as an argument in the addFishToInventory method
            chosenFishIndex = 0;
            //the method is called to add the fish to the user's aquarium
            addFishToInventory(chosenFishIndex);
            //the text in the text areas on the right are updated
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        goldenArowanaMinusButton.setOnMouseClicked(event -> {
            //Since the fish inventory defined above is unchanging, each fish has a unique index
            //the variable below is how the fish is selected from the fish inventory and used as an argument in the subtractFishFromInventory
            chosenFishIndex = 0;
            //the method is called to remove a fish or decrement the quantity variable
            subtractFishFromInventory(chosenFishIndex);
            //the text in the text areas on the right are updated
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //burmeseGreenfish buttons
        burmeseGreenfishPlusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 1;
            addFishToInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        burmeseGreenfishMinusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 1;
            subtractFishFromInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //blue sea bass buttons
        blueSeaBassPlusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 2;
            addFishToInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        blueSeaBassMinusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 2;
            subtractFishFromInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //orange sand shark buttons
        orangeSandSharkPlusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 3;
            addFishToInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        orangeSandSharkMinusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 3;
            subtractFishFromInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //purple mini stingray buttons
        purpleMiniStingrayPlusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 4;
            addFishToInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        purpleMiniStingrayMinusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 4;
            subtractFishFromInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //goldfish buttons
        goldfishPlusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 5;
            addFishToInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        goldfishMinusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 5;
            subtractFishFromInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //brazilian silver pacu buttons
        brazilianSilverPacuPlusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 6;
            addFishToInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        brazilianSilverPacuMinusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 6;
            subtractFishFromInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //european teal eel buttons
        europeanTealEelPlusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 7;
            addFishToInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        europeanTealEelMinusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 7;
            subtractFishFromInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        //european teal eel buttons
        pinkNebulaBetaPlusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 8;
            addFishToInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });

        pinkNebulaBetaMinusButton.setOnMouseClicked(event -> {
            chosenFishIndex = 8;
            subtractFishFromInventory(chosenFishIndex);
            printAquarium(aquariumInventory);
            printTotalAndCapacity();
        });
    }

    /**
     * Method that orients the visibility so that only the water selection interface is visible
     */
    @FXML
    private void goToWaterSelection() {
        //if this method is called, then only the water selection pane is visible
        TankPane.setVisible(false);
        WaterTypePane.setVisible(true);
        FishSelectionPane.setVisible(false);

        //clears the aquarium of all fish if the user chooses to select a different water type from the one initially chosen
        for (Fish fish : fishInventory) {
            fish.setCartQuantity(0);
        }
        //also clears the inventory
        aquariumInventory.clear();

        //resets the amount of space taken up in the user's fish tank to zero
        tankOccupancy = 0;
        //also resets the remaining gallons and the total to their original values
        remainingGallons = userTank.getGallons();
        aquariumTotal = userTank.getPrice();
        //removes the error text in the text area on the right
        ErrorsTextArea.setText("");
    }

    /**
     * Method that orients the visibility so that only the fish selection interface is visible
     */
    @FXML
    private void goToFishSelection() {
        TankPane.setVisible(false);
        WaterTypePane.setVisible(false);
        FishSelectionPane.setVisible(true);

        ErrorsTextArea.setText("");
    }

    /**
     * Method that orients the visibility so that only the tank selection interface is visible
     */
    @FXML
    private void goToTankSelection() {
        //if the method is called, then only the tank pane is visible
        FishSelectionPane.setVisible(false);
        WaterTypePane.setVisible(false);
        TankPane.setVisible(true);

        //deselects all water types if the user chooses to choose a different tank size
        userWaterType = 0;
        //resets the total and other values back to zero so the user can start over
        aquariumTotal = 0;
        remainingGallons = 0;
        //clears away the text in the text areas on the right
        userWaterTypeString = "";
        ErrorsTextArea.setText("");
    }

    /**
     * Method that runs if the back button was pressed
     */
    @FXML
    private void backButtonPressed() {
        //if the fish selection pane is the current one being shown, navigate back to the water selection
        if (FishSelectionPane.isVisible()) {
            goToWaterSelection();
        }

        //if the water selection pane is the one being shown, navigate back to the tank selection
        else if(WaterTypePane.isVisible()) {
            goToTankSelection();
        }

        else if(TankPane.isVisible()){
            //This block returns the user to the main menu
            //in a try block in case an exception is thrown by an issue reading in the file
            try {
                System.out.println("Exit the screen and go back to the main menu");
                //Loads the FXML file for the aquarium builder scene and assigns it to the variable "root"
                Parent root = FXMLLoader.load(getClass().getResource("CFStoreMainMenu.fxml"));
                //Creates a new stage object for it to use
                Stage stage = (Stage) BackButton.getScene().getWindow();
                //creates a new scene using the variable assigned to the FXMLLoader
                stage.setScene(new Scene(root));
                //displays the scene
                stage.show();
            //the exception is caught here
            } catch(IOException e){
                System.out.print("IOException: Issue Reading File");
            }
        }

        //all the buttons are then deselected
        FishbowlButton.setSelected(false);
        SmallTankButton.setSelected(false);
        MediumTankButton.setSelected(false);
        LargeTankButton.setSelected(false);
        XLTankButton.setSelected(false);
        MegaTankButton.setSelected(false);

        SaltwaterButton.setSelected(false);
        FreshwaterButton.setSelected(false);
    }

    @FXML
    /**
     * Method that runs if the next button is pressed
     */
    private void nextButtonPressed(){

        //if the fish selection pane is the one being shown, IDKKKKKK yet...
        if(FishSelectionPane.isVisible()){
            if(checkFishInAquarium()) {
                System.out.println("Exit the screen and go back to the main menu");
                //This block returns the user to the main menu
                //in a try block in case an exception is thrown by an issue reading in the file
                try {
                    System.out.println("Exit the screen and go back to the main menu");
                    //Loads the FXML file for the aquarium builder scene and assigns it to the variable "root"
                    Parent root = FXMLLoader.load(getClass().getResource("CFStoreMainMenu.fxml"));
                    //Creates a new stage object for it to use
                    Stage stage = (Stage) NextButton.getScene().getWindow();
                    //creates a new scene using the variable assigned to the FXMLLoader
                    stage.setScene(new Scene(root));
                    //displays the scene
                    stage.show();
                    //the exception is caught here
                } catch(IOException e){
                    System.out.print("IOException: Issue Reading File");
                }
            }
            //ensures that the user adds at least one fish by calling the CheckFishInAquarium method above
            else{
                error = "please add at least least one fish before exiting";
                //prints an error message in the text area on the right, this is used in the code quite frequently
                printError(error);
            }
        }

        //if the water selection pane is the one being shown, then navigate forward to the fish selection
        else if(WaterTypePane.isVisible()){
            if(checkWaterSelected()){
                goToFishSelection();
            }
            //ensures that the user makes at least one selection by calling the checkWaterSelected method above
            else{
                error = "Please select a water type";
                printError(error);
            }
        }

        //if the tank pane is the one being shown, then navigate forward to the tank selection pane
        else if(TankPane.isVisible()){
            if(checkTankSelected()){
                goToWaterSelection();
            }
            //ensures that the user makes at least one selection by calling the checkTankSelected method above
            else{
                error = "Please select a tank";
                printError(error);
            }
        }

        //resets the tank's selection to false
        FishbowlButton.setSelected(false);
        SmallTankButton.setSelected(false);
        MediumTankButton.setSelected(false);
        LargeTankButton.setSelected(false);
        XLTankButton.setSelected(false);
        MegaTankButton.setSelected(false);

        SaltwaterButton.setSelected(false);
        FreshwaterButton.setSelected(false);
    }

    //methods for choosing a tank
    private void fishBowlChosen(){
        //all the setSelected(true/false) statements are to make sure only one radio button is selected at a time
        FishbowlButton.setSelected(true);
        SmallTankButton.setSelected(false);
        MediumTankButton.setSelected(false);
        LargeTankButton.setSelected(false);
        XLTankButton.setSelected(false);
        MegaTankButton.setSelected(false);

        //Sets the userTank to the first object in the tank's inventory, which is the fishbowl
        //The other tank chosen methods operate the same way
        userTank = tankInventory[0];
        //sets gallons of the tank as the capacity limit for the user
        remainingGallons = userTank.getGallons();
        //sets the total as the tank's price
        aquariumTotal = userTank.getPrice();
        //clears the error text on the right
        ErrorsTextArea.setText("");
    }

    private void smallTankChosen(){
        FishbowlButton.setSelected(false);
        SmallTankButton.setSelected(true);
        MediumTankButton.setSelected(false);
        LargeTankButton.setSelected(false);
        XLTankButton.setSelected(false);
        MegaTankButton.setSelected(false);

        userTank = tankInventory[1];
        remainingGallons = userTank.getGallons();
        aquariumTotal = userTank.getPrice();
        ErrorsTextArea.setText("");
    }

    private void mediumTankChosen(){
        FishbowlButton.setSelected(false);
        SmallTankButton.setSelected(false);
        MediumTankButton.setSelected(true);
        LargeTankButton.setSelected(false);
        XLTankButton.setSelected(false);
        MegaTankButton.setSelected(false);


        userTank = tankInventory[2];
        remainingGallons = userTank.getGallons();
        aquariumTotal = userTank.getPrice();
        ErrorsTextArea.setText("");
    }

    private void largeTankChosen(){
        FishbowlButton.setSelected(false);
        SmallTankButton.setSelected(false);
        MediumTankButton.setSelected(false);
        LargeTankButton.setSelected(true);
        XLTankButton.setSelected(false);
        MegaTankButton.setSelected(false);

        userTank = tankInventory[3];
        remainingGallons = userTank.getGallons();
        aquariumTotal = userTank.getPrice();
        ErrorsTextArea.setText("");
    }

    private void xlTankChosen(){
        FishbowlButton.setSelected(false);
        SmallTankButton.setSelected(false);
        MediumTankButton.setSelected(false);
        LargeTankButton.setSelected(false);
        XLTankButton.setSelected(true);
        MegaTankButton.setSelected(false);

        userTank = tankInventory[4];
        remainingGallons = userTank.getGallons();
        aquariumTotal = userTank.getPrice();
        ErrorsTextArea.setText("");
    }

    private void megaTankChosen(){
        FishbowlButton.setSelected(false);
        SmallTankButton.setSelected(false);
        MediumTankButton.setSelected(false);
        LargeTankButton.setSelected(false);
        XLTankButton.setSelected(false);
        MegaTankButton.setSelected(true);

        userTank = tankInventory[5];
        remainingGallons = userTank.getGallons();
        aquariumTotal = userTank.getPrice();
        ErrorsTextArea.setText("");
    }

    //methods for choosing salt or freshwater
    private void saltwaterChosen(){
        //the true and false is to make sure that both radio buttons cant be selected at once
        SaltwaterButton.setSelected(true);
        FreshwaterButton.setSelected(false);

        //integer value for saltwater
        userWaterType = 2;

        ErrorsTextArea.setText("");
    }

    private void freshwaterChosen(){
        SaltwaterButton.setSelected(false);
        FreshwaterButton.setSelected(true);

        //integer value for freshwater
        userWaterType = 1;

        ErrorsTextArea.setText("");
    }

    /**
     * Method for adding a fish to an inventory, calls a similar method that performs more checks on the manner in which the fish should be added
     * @param chosenFishIndex
     */
    private void addFishToInventory(int chosenFishIndex) {
        //selects the fish by indexing the fish inventory
        userfish = fishInventory[chosenFishIndex];

        //uses the defined variables in the more in depth method
        fishTotal = addFishToAquarium(
                userfish, aquariumInventory, userWaterType, tankOccupancy, userTank);

        //if the addFishToAquarium methods returns a value indicating that a fish was viable and actually added
        if (fishTotal > 0) {
            //then update the aquarium total
            aquariumTotal += fishTotal;
            //increase the occupied space
            tankOccupancy += userfish.getDesignatedGallons();
            //decrease the remaining capacity
            remainingGallons -= userfish.getDesignatedGallons();
            ErrorsTextArea.setText("");
        }
    }

    /**
     * Removes fish from the aquarium
     * @param chosenFishIndex
     */
    private void subtractFishFromInventory(int chosenFishIndex) {
        Fish chosenFish = fishInventory[chosenFishIndex];

        //if the aquarium already contains the selected fish
        if (aquariumInventory.contains(chosenFish)) {
            int chosenFishCartQuantity = chosenFish.getCartQuantity();

            //if the quantity of that fish is more than one
            if (chosenFishCartQuantity > 1) {
                //then decrement the cart quantity
                chosenFish.setCartQuantity(chosenFishCartQuantity - 1);
            }
            else {
                //if the quantity is less than 1, then no need to decrement
                //just set the cart quantity to zero and remove the fish
                chosenFish.setCartQuantity(0);
                aquariumInventory.remove(chosenFish);
            }

            //increase the gallons remaining since the tank has more space now
            remainingGallons += chosenFish.getDesignatedGallons();
            //decrease the occupied space
            tankOccupancy -= chosenFish.getDesignatedGallons();
            //decrement the price
            aquariumTotal -= chosenFish.getPrice();
        }
    }

    /**
     * Method for adding fish to the aquarium
     * @param aquariumInventory
     * @return double fish total to be added to the entire aquarium total
     */
    public double addFishToAquarium(Fish userFish, List<Fish> aquariumInventory, int userWaterType, double tankOccupancy, FishTank userTank){
        //fish total is the price of the fish that was added, and the value the method returns
        //the method returning zero would indicate that no fish were added because of an incompatibility
        double fishTotal = 0;
        //the boolean that determines what the method returns
        boolean fishIsValid = true;

        //loop through the current aquarium and compare every fish in there to the fish being added
        for (Fish aquariumFish : aquariumInventory) {
            //more error checks are performed in this methid
            if (!(checkFishCompatibility(userFish, aquariumFish))) {
                //if it returns invalid then set it to invalid
                fishIsValid = false;
                //break the loop so it doesn't keep running and printing multiple error messages
                break;
            }
        }
        //below are non fish related compatability checks such as water type and tank gallons
        //if the fish don't match in water types
        if(userFish.checkSaltwater() && userWaterType == 1){
            //the boolean is false and a message is displayed
            fishIsValid = false;
            error = "Saltwater fish can not survive in freshwater";
            printError(error);
        }
        if(!(userFish.checkSaltwater()) && userWaterType == 2){
            fishIsValid = false;
            error = "Freshwater fish can not survive in Saltwater";
            printError(error);
        }
        //if the fish exceeds the remaining capacity of the tank
        if(userFish.getDesignatedGallons() + tankOccupancy > userTank.getGallons()){
            //the boolean is false and a message is displayed
            fishIsValid = false;
            error = "Selection exceeds tank capacity";
            printError(error);
        }

        //if it remains valid after compatibility checks, then add the fish to the aquarium
        if (fishIsValid) {
            //the fishtotal with return the price of the fish that was added, allowing the total cost of the aquarium to be incremented
            fishTotal += userFish.getPrice();

            int userFishCartQuantity = userFish.getCartQuantity();

            //determines if a new line of text appears in the text area on the right, or if the quantity is simply incremented
            if (!(aquariumInventory.contains(userFish))) {
                aquariumInventory.add(userFish);
                userFish.setCartQuantity(userFishCartQuantity += 1);
            } else {
                userFish.setCartQuantity(userFishCartQuantity += 1);
            }
        }
        return fishTotal;
    }

    /**
     * Checks the compatability of the fish being added with the fish already in the aquarium
     * @param fish1
     * @param fish2
     * @return
     */
    public boolean checkFishCompatibility(Fish fish1, Fish fish2){
        boolean fishIsValid = true;
        boolean smallValid = true;
        boolean largeValid = true;
        boolean betaValid = true;

        //if both fish are beta fish
        if(Objects.equals(fish1.getName(), "Pink Nebula Beta") &&
                Objects.equals(fish2.getName(), "Pink Nebula Beta")){
            //this boolean is set to false
            betaValid = false;
        }

        //if fish is small and not carnivorous
        if(!(fish1.checkLarge()) && !(fish1.checkCarnivorous())){
            if(fish2.checkCarnivorous()){
                //set to false
                smallValid = false;
            }
        }

        if(!(fish2.checkLarge()) && !(fish2.checkCarnivorous())){
            if(fish1.checkCarnivorous()){
                smallValid = false;
            }
        }

        //if fish is small and carnivorous
        if(!(fish1.checkLarge()) && fish1.checkCarnivorous()){
            if(fish2.checkCarnivorous() && fish2.checkLarge()){
                //set to false
                smallValid = false;
            }
        }

        if(!(fish2.checkLarge()) && fish2.checkCarnivorous()){
            if(fish1.checkCarnivorous() && fish1.checkLarge()){
                smallValid = false;
            }
        }

        //if fish is large and not carnivorous
        if((fish1.checkLarge()) && !(fish1.checkCarnivorous())) {
            if (fish2.checkCarnivorous() && fish2.checkLarge()) {
                //set to false
                largeValid = false;
            }
        }

        if((fish2.checkLarge()) && !(fish2.checkCarnivorous())) {
            if (fish1.checkCarnivorous() && fish1.checkLarge()) {
                largeValid = false;
            }
        }

        if(!(smallValid)){
            //sets the main validity variable to false and displays an error message in the text area on the right
            //the other blocks below operate the same way
            fishIsValid = false;
            System.out.println("----------------------------------------");
            System.out.println("Carnivorous fish are not compatible with small fish");
            error = "Carnivorous fish are not compatible with small fish";
            printError(error);

        }

        if(!(largeValid)){
            fishIsValid = false;
            System.out.println("-----------------------------------------");
            error = "Large carnivorous fish are not compatible with large herbivore fish";
            printError(error);

        }

        if(!(betaValid)){
            fishIsValid = false;
            System.out.println("-----------------------------------------");
            System.out.println("There cannot be more than one Beta Fish in an aquarium");
            error = "There cannot be more than one Beta Fish in an aquarium";
            printError(error);
        }

        return fishIsValid;
    }

    /**
     * checks if at least one tank is selected
     * @return boolean
     */
    public boolean checkTankSelected(){
        if(FishbowlButton.isSelected() == false &&
        SmallTankButton.isSelected() == false &&
        MediumTankButton.isSelected() == false &&
        LargeTankButton.isSelected() == false &&
        XLTankButton.isSelected() == false &&
        MegaTankButton.isSelected() == false){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * checks if at least one water type is selected
     * @return boolean
     */
    public boolean checkWaterSelected(){
        if(FreshwaterButton.isSelected() == false &&
                SaltwaterButton.isSelected() == false){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * checks if at least one fish was added to the aquarium
     * @return
     */
    public boolean checkFishInAquarium(){
        if(aquariumInventory.size() == 0){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Prints the current fish contents of the aquarium in the text area on the right
     * @param inventory
     */
    public void printAquarium(List<Fish> inventory) {
        String aquariumPrinted = "Here is what is currently in your aquarium:\n";

        for (int index = 0; index < inventory.size(); index += 1) {
            aquariumPrinted += String.format("%d%s %s %s%d%n",
                    index + 1, ".",
                    inventory.get(index).getName(),
                    "x",
                    inventory.get(index).getCartQuantity());
        }
        AquariumContentsTextArea.setText(aquariumPrinted);
    }

    /**
     * Prints the monetary total the remaining tank capacity in the text area on the right
     */
    public void printTotalAndCapacity(){
        //determines whether to print salt or fresh water based on the integer value defined by the water type selection methods
        if(userWaterType == 1){
            userWaterTypeString = "Freshwater";
        }
        else if(userWaterType == 2){
            userWaterTypeString = "Saltwater";
        }
        String totalAndCapacityPrinted = String.format("%s%s%.2f%n%s%.2f%s%n%s%s",
                "Total: ", "$", aquariumTotal,
                "Remaining Capacity: ", remainingGallons, " Gallons",
                "Water Type: ", userWaterTypeString);
        AquariumTotalAndTankCapacityTextArea.setText(totalAndCapacityPrinted);
    }

    /**
     * prints the error messages in the text area on the right
     * @param error
     */
    public void printError(String error){
        ErrorsTextArea.setText(error);
    }

    public double getAquariumTotal(){
        return this.aquariumTotal;
    }
}
