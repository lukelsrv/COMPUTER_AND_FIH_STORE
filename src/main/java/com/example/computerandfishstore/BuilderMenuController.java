package com.example.computerandfishstore;

import com.example.computerandfishstore.classes.Buildable;
import com.example.computerandfishstore.classes.Buildable.Builder;
import com.example.computerandfishstore.classes.Buildable.Component;
import com.example.computerandfishstore.classes.Buildable.ComponentType;
import com.example.computerandfishstore.classes.Cart;
import com.example.computerandfishstore.classes.Inventory;
import com.example.computerandfishstore.classes.StoreItem;
import com.example.computerandfishstore.utils.Validation;
import com.example.computerandfishstore.utils.ValidationResult;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BuilderMenuController<B extends Buildable, C extends ComponentType<B>> {

	@FXML
	public Text titleLabel;

	@FXML
	private GridPane itemGrid;

	@FXML
	private Button nextButton;

	@FXML
	private Button backButton;

	@FXML
	private TextArea componentsTextArea;

	@FXML
	private TextArea detailsTextArea;

	@FXML
	private TextArea errorsTextArea;

	private Builder<B, C> builder;
	private RadioButton currentItemSelector;
	private Component<B> currentItemSelected;

	public void init(Builder<B, C> builder) {
		this.builder = builder;
		titleLabel.setText(builder.getTitle());
		updateCart();
		updateInventory();

		nextButton.setOnAction(event -> {
			if (!builder.canNext())
				return;
			if (builder.isEnd()) {
				ValidationResult<B> result = builder.build();
				if (!result.valid()) {
					displayError(result.message());
				} else {
					B b = result.result();
					MainMenuController.CART.add((StoreItem) b);
					toMainMenu();
				}
			} else {
				builder.next();
				updateInventory();
			}
		});
		backButton.setOnAction(event -> {
			if (builder.isStart()) {
				toMainMenu();
			} else {
				builder.prev();
				updateInventory();
			}
		});
	}

	private void toMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) nextButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {}
	}

	private void updateButtons() {
		nextButton.setVisible(builder.canNext());
		if (builder.isEnd()) {
			nextButton.setText("Finish");
		} else {
			nextButton.setText("Next");
		}
		if (builder.isStart()) {
			backButton.setText("Menu");
		} else {
			backButton.setText("Back");
		}
	}

	private void updateInventory() {
		itemGrid.getChildren().clear();
		updateButtons();
		C componentType = builder.getCurrentType();
		Class<? extends Component<B>> componentClass = componentType.getComponentClass();
		List<? extends Component<B>> items = Inventory.getInventory(componentClass);
		Component<B> selected = null;
		if (componentType.isSingle()) {
			selected = builder.getSingle(componentType);
		}
		int count = 0;
		for (Component<B> item : items) {
			if (!builder.filter(item))
				continue;
			VBox card = createItemCard(item, item == selected);
			int column = count % 2;
			int row = count / 2;
			itemGrid.add(card, column, row);
			count++;
		}
	}

	private void updateCart() {
		String text = "Current Build:\n";

		for (C type : builder.getTypes()) {
			text += "\n- " + type + ": ";
			List<Component<B>> components = builder.getData(type);
			if (components.isEmpty()) {
				text += "None";
				continue;
			}
			if (type.isSingle()) {
				StoreItem component = (StoreItem) components.get(0);
				text += component.getName();
			} else {
				for (Component<B> component : components) {
					StoreItem rigComponent = (StoreItem) component;
					text += "\n\t- " + rigComponent.getName();
				}
			}
		}

		componentsTextArea.setText(text);
		updateDetails();
		updateButtons();
	}

	private void updateDetails() {
		String text = "Additional Details:\n- Total Cost: $"
			+ String.format("%,.2f", builder.getSum(rigComponent -> ((StoreItem) rigComponent).getPrice()));
		for (String detail : builder.getDetails())
			text += "\n- " + detail;
		detailsTextArea.setText(text);
	}

	private void displayError(String error) {
		errorsTextArea.setText(error);
	}

	private VBox createItemCard(Component<B> component, boolean selected) {
		StoreItem storeItem = (StoreItem) component;
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

		HBox buttons;
		if (component.getComponentType().isSingle()) {
			RadioButton selectButton = new RadioButton("Select");
			if (selected) {
				selectButton.fire();
				currentItemSelector = selectButton;
				currentItemSelected = component;
			}
			selectButton.setOnAction(event -> {
				selectComponent(selectButton, component);
			});
			buttons = new HBox(selectButton);
			buttons.setPadding(new Insets(30, 0, 10, 80));
		} else {
			Button minusButton = new Button("-");
			minusButton.setPrefSize(26, 25);
			minusButton.setOnAction(event -> {
				removeComponent(component);
			});

			Button plusButton = new Button("+");
			plusButton.setPrefSize(26, 25);
			plusButton.setOnAction(event -> {
				addComponent(component);
			});
			buttons = new HBox(20, minusButton, plusButton);
			buttons.setPadding(new Insets(30, 0, 10, 75));
		}

		card.getChildren().addAll(header, propertyContainer, buttons);
		return card;
	}

	private void selectComponent(RadioButton button, Component<B> component) {
		if (currentItemSelected != null && currentItemSelector.isSelected()) {
			currentItemSelector.fire();
			builder.remove(currentItemSelected);
		}
		builder.add(component, false);
		currentItemSelected = component;
		currentItemSelector = button;
		updateCart();
	}

	private void addComponent(Component<B> component) {
		Validation validation = builder.add(component, false);
		if (!validation.valid()) {
			displayError(validation.message());
		} else {
			updateCart();
		}
	}

	private void removeComponent(Component<B> component) {
		builder.remove(component);
		updateCart();
	}

}
