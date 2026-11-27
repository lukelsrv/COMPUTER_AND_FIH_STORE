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

/**
 * Controller class for the BuilderMenu.
 */
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

	/**
	 * Initializes this controller by updating menu aspects and adding handlers to buttons.
	 * @param builder The builder used with the menu to display components and build the final item.
	 */
	public void init(Builder<B, C> builder) {
		this.builder = builder;
		titleLabel.setText(builder.getTitle());
		updateCart();
		updateInventory();

		nextButton.setOnAction(event -> {
			if (!builder.canNext()) // Safety check, button shouldn't be visible if it can't go next.
				return;
			if (builder.isEnd()) { // Builder is at last component, finalize, add to main cart, back to main menu.
				ValidationResult<B> result = builder.build();
				if (!result.valid()) {
					displayError(result.message());
				} else {
					B b = result.result();
					MainMenuController.CART.add((StoreItem) b);
					toMainMenu();
				}
			} else { // Builder not done, move to next component to customize.
				builder.next();
				updateInventory();
			}
		});
		backButton.setOnAction(event -> {
			if (builder.isStart()) { // Builder at first component, go to main menu.
				toMainMenu();
			} else { // Otherwise, go back and edit components.
				builder.prev();
				updateInventory();
			}
		});
	}

	/**
	 * Helper method to go back to main menu.
	 */
	private void toMainMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(MainMenu.class.getResource("MainMenu.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) nextButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {}
	}

	/**
	 * Helper method to update the next and back buttons.
	 */
	private void updateButtons() {
		nextButton.setVisible(builder.canNext()); // Don't allows going to next component if current is not satisfied.
		if (builder.isEnd()) { // Builder is at last component
			nextButton.setText("Finish");
		} else {
			nextButton.setText("Next");
		}
		if (builder.isStart()) { // Builder is at first component
			backButton.setText("Menu");
		} else {
			backButton.setText("Back");
		}
	}

	/**
	 * Helper method to update the items listed in the Menu.
	 */
	private void updateInventory() {
		// Remove previous items.
		itemGrid.getChildren().clear();
		updateButtons();
		// Retrieve items to display.
		C componentType = builder.getCurrentType();
		Class<? extends Component<B>> componentClass = componentType.getComponentClass();
		List<? extends Component<B>> items = Inventory.getInventory(componentClass);
		Component<B> selected = null;
		if (componentType.isSingle()) { // If this component already has an associated item.
			selected = builder.getSingle(componentType);
		}
		int count = 0;
		for (Component<B> item : items) {
			if (!builder.filter(item)) // Filter out items that can't be displayed due to incompatibility.
				continue;
			VBox card = createItemCard(item, item == selected);
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

	/**
	 * Helper method to update the details of the build.
	 */
	private void updateDetails() {
		String text = "Additional Details:\n- Total Cost: $"
			+ String.format("%,.2f", builder.getSum(rigComponent -> ((StoreItem) rigComponent).getPrice()));
		for (String detail : builder.getDetails())
			text += "\n- " + detail;
		detailsTextArea.setText(text);
	}

	/**
	 * Helper method to display the provided error in the menu.
	 * @param error The error to display.
	 */
	private void displayError(String error) {
		errorsTextArea.setText(error);
	}

	/**
	 * Helper method to create JavaFX objects to display the necessary information on the Menu.
	 * @param component The component to display the data of.
	 * @param selected Whether this component has already been selected.
	 * @return The JavaFX container to display in the Menu.
	 */
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
		if (component.getComponentType().isSingle()) { // Single type, have RadioButtons instead of minus and plus buttons.
			RadioButton selectButton = new RadioButton("Select");
			if (selected) { // This item is already selected, set necessary data
				selectButton.fire();
				currentItemSelector = selectButton;
				currentItemSelected = component;
			}
			selectButton.setOnAction(event -> {
				selectComponent(selectButton, component);
			});
			buttons = new HBox(selectButton);
			buttons.setPadding(new Insets(30, 0, 10, 80));
		} else { // Multiple type, have minus and plus buttons.
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

	/**
	 * Helper method for selecting a component. Used for single type components.
	 * @param button The button that was clicked.
	 * @param component The associated component being selected.
	 */
	private void selectComponent(RadioButton button, Component<B> component) {
		// Something else was selected, we can only have 1, deselect the other component
		if (currentItemSelected != null && currentItemSelector.isSelected()) {
			currentItemSelector.fire();
			builder.remove(currentItemSelected);
		}
		builder.add(component, false);
		currentItemSelected = component;
		currentItemSelector = button;
		updateCart();
	}

	/**
	 * Helper method for adding a component. Used for multiple type components.
	 * @param component The component to add.
	 */
	private void addComponent(Component<B> component) {
		Validation validation = builder.add(component, false);
		if (!validation.valid()) {
			displayError(validation.message());
		} else {
			updateCart();
		}
	}

	/**
	 * Helper method for removing a component. Used for multiple type components.
	 * @param component The component to remove.
	 */
	private void removeComponent(Component<B> component) {
		builder.remove(component);
		updateCart();
	}

}
