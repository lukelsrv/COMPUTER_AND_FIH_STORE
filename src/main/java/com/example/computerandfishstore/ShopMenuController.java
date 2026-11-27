package com.example.computerandfishstore;

import com.example.computerandfishstore.classes.Buildable.Component;
import com.example.computerandfishstore.classes.Cart;
import com.example.computerandfishstore.classes.Inventory;
import com.example.computerandfishstore.classes.StoreItem;
import com.example.computerandfishstore.utils.Validation;
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
import java.util.List;

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

	private void toMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) finishButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {}
	}

	private void updateInventory() {
		itemGrid.getChildren().clear();
		int count = 0;
		for (StoreItem item : Inventory.getInventory()) {
			VBox card = createItemCard(item);
			int column = count % 2;
			int row = count / 2;
			itemGrid.add(card, column, row);
			count++;
		}
	}

	private void updateCart() {
		cartTextArea.setText(cart.getItemsDisplay());
		updateDetails();
	}

	private void updateDetails() {
		String text = "Additional Details:\n- Total Cost: $" + String.format("%,.2f", cart.getTotalCost());
		detailsTextArea.setText(text);
	}

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
