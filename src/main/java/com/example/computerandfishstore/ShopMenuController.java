package com.example.computerandfishstore;

import com.example.computerandfishstore.classes.Cart;
import com.example.computerandfishstore.classes.Inventory;
import com.example.computerandfishstore.classes.StoreItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller class for the ShopMenu.
 */
public class ShopMenuController {

	@FXML
	public ScrollPane itemScroll;

	@FXML
	private GridPane itemGrid;

	@FXML
	private Button finishButton;

	@FXML
	private Button cancelButton;

	@FXML
	private TextArea cartTextArea;

	@FXML
	private TextArea detailsTextArea;

	private Cart cart;

	/**
	 * Initializes this controller by updating menu aspects and adding handlers to buttons.
	 */
	@FXML
	public void initialize() {
		cart = new Cart();
		updateCart();
		updateInventory();

		finishButton.setOnAction(event -> {
			MainMenuController.CART.add(cart);
			toMainMenu();
		});
		cancelButton.setOnAction(event -> toMainMenu());
	}

	/**
	 * Helper method to go back to main menu.
	 */
	private void toMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) finishButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {}
	}

	/**
	 * Helper method to update the items listed in the Menu.
	 */
	private void updateInventory() {
		// Remove previous items.
		itemGrid.getChildren().clear();
		int count = 0;
		// Retrieve items to display.
		for (StoreItem item : Inventory.getInventory()) {
			VBox card = createItemCard(item);
			int column = count % 2;
			int row = count / 2;
			itemGrid.add(card, column, row);
			count++;
		}
	}

	/**
	 * Helper method to update the items in the build.
	 */
	private void updateCart() {
		cartTextArea.setText(cart.getItemsDisplay());
		updateDetails();
	}

	/**
	 * Helper method to update the details of the cart.
	 */
	private void updateDetails() {
		String text = "Additional Details:\n- Total Cost: $" + String.format("%,.2f", cart.getTotalCost());
		detailsTextArea.setText(text);
	}

	/**
	 * Helper method to create JavaFX objects to display the necessary information on the Menu.
	 * @param storeItem The store item to display the data of.
	 * @return The JavaFX container to display in the Menu.
	 */
	private VBox createItemCard(StoreItem storeItem) {
		VBox card = new VBox(10);
		card.setAlignment(Pos.TOP_CENTER);
		card.setPrefWidth(220);
		card.setStyle("-fx-background-color: #FFDEB0;");

		Label nameLabel = new Label(storeItem.getName());
		nameLabel.setUnderline(true);
		nameLabel.setStyle("-fx-font-size: 12px;");

		ImageView itemImage = new ImageView(storeItem.getImage());
		itemImage.setFitWidth(166);
		itemImage.setFitHeight(94);
		itemImage.setPreserveRatio(true);
		itemImage.setPickOnBounds(true);

		double price = storeItem.getPrice();
		String priceText = price <= 0 ? "Free" : "$" + String.format("%,.2f", price);

		Label priceLabel = new Label(priceText);
		priceLabel.setStyle("-fx-font-size: 12px;");

		VBox header = new VBox();
		header.setAlignment(Pos.TOP_CENTER);
		header.getChildren().addAll(nameLabel, itemImage, priceLabel);

		VBox propertyContainer = new VBox();
		propertyContainer.setAlignment(Pos.TOP_CENTER);

		for (String property : storeItem.getProperties()) {
			propertyContainer.getChildren().add(new Label(property));
		}

		Button minusButton = new Button("-");
		minusButton.setPrefSize(26, 25);
		minusButton.setOnAction(event -> {
			cart.remove(storeItem);
			updateCart();
		});

		Button plusButton = new Button("+");
		plusButton.setPrefSize(26, 25);
		plusButton.setOnAction(event -> {
			cart.add(storeItem);
			updateCart();
		});
		HBox buttons = new HBox(20, minusButton, plusButton);
		buttons.setPadding(new Insets(30, 0, 10, 75));

		card.getChildren().addAll(header, propertyContainer, buttons);
		return card;
	}

}
