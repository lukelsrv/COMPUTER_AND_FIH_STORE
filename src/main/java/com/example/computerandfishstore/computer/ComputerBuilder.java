package com.example.computerandfishstore.computer;

import com.example.computerandfishstore.MainMenu;
import com.example.computerandfishstore.MainMenuController;
import com.example.computerandfishstore.classes.Cart;
import com.example.computerandfishstore.classes.Inventory;
import com.example.computerandfishstore.classes.Buildable.Component;
import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.classes.RigComponent;
import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.parts.Rig;
import com.example.computerandfishstore.computer.parts.Rig.RigBuilder;
import com.example.computerandfishstore.computer.utils.Validation;
import com.example.computerandfishstore.computer.utils.ValidationResult;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ComputerBuilder {

	@FXML
	private GridPane itemGrid;

	@FXML
	private Button nextButton;

	@FXML
	private Button backButton;

	@FXML
	private TextArea PriceAndChosenComponentsTextArea;

	@FXML
	private TextArea OthertextArea;

	@FXML
	private TextArea ComputerErrors;

	private RigBuilder rigBuilder;

	private RadioButton currentItemSelector;
	private RigComponent currentItemSelected;

	@FXML
	public void initialize() {
		rigBuilder = new RigBuilder();
		updateCart();
		updateInventory();

		nextButton.setOnAction(event -> {
			if (!rigBuilder.canNext())
				return;
			if (rigBuilder.isEnd()) {
				ValidationResult<Rig> result = rigBuilder.build();
				if (!result.valid()) {
					displayError(result.message());
				} else {
					Rig rig = result.result();
					Cart.add(rig);
					toMainMenu();
				}
			} else {
				rigBuilder.next();
				updateInventory();
			}
		});
		backButton.setOnAction(event -> {
			if (rigBuilder.isStart()) {
				toMainMenu();
			} else {
				rigBuilder.prev();
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
		nextButton.setVisible(rigBuilder.canNext());
		if (rigBuilder.isEnd()) {
			nextButton.setText("Finish");
		} else {
			nextButton.setText("Next");
		}
		if (rigBuilder.isStart()) {
			backButton.setText("Menu");
		} else {
			backButton.setText("Back");
		}
	}

	private void updateInventory() {
		itemGrid.getChildren().clear();
		updateButtons();
		RigComponentType componentType = rigBuilder.getCurrentType();
		Class<? extends RigComponent> componentClass = componentType.getComponentClass();
		List<? extends RigComponent> items = Inventory.getInventory(componentClass);
		RigComponent selected = null;
		if (componentType.isSingle()) {
			selected = (RigComponent) rigBuilder.getSingle(componentType);
		}
		int count = 0;
		for (RigComponent item : items) {
			if (!rigBuilder.filter(item))
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

		for (RigComponentType type : rigBuilder.getTypes()) {
			text += "\n- " + type + ": ";
			List<Component<Rig>> components = rigBuilder.getData(type);
			if (components.isEmpty()) {
				text += "None";
				continue;
			}
			if (type.isSingle()) {
				RigComponent component = (RigComponent) components.get(0);
				text += component.getName();
			} else {
				for (Component<Rig> component : components) {
					RigComponent rigComponent = (RigComponent) component;
					text += "\n\t- " + rigComponent.getName();
				}
			}
		}

		PriceAndChosenComponentsTextArea.setText(text);
		updateDetails();
		updateButtons();
	}

	private void updateDetails() {
		String text = "Additional Details:\n- Total Cost: $"
			+ String.format("%.2f", rigBuilder.getSum(rigComponent -> ((ComputerPart) rigComponent).getPrice()))
			+ "\n- Required Power: " + rigBuilder.getSum(rigComponent -> ((RigComponent) rigComponent).getRequiredPower());
		OthertextArea.setText(text);
	}

	private void displayError(String error) {
		ComputerErrors.setText(error);
	}

	private VBox createItemCard(RigComponent component, boolean selected) {
		VBox card = new VBox(10);
		card.setAlignment(Pos.TOP_CENTER);
		card.setPrefWidth(220);
		card.setStyle("-fx-background-color: #FFDEB0;");

		Label nameLabel = new Label(component.getName());
		nameLabel.setUnderline(true);
		nameLabel.setStyle("-fx-font-size: 12px;");

		ImageView itemImage = new ImageView(component.getImage());
		itemImage.setFitWidth(166);
		itemImage.setFitHeight(94);
		itemImage.setPreserveRatio(true);
		itemImage.setPickOnBounds(true);

		Label priceLabel = new Label("$" + String.format("%,.2f", component.getPrice()));
		priceLabel.setStyle("-fx-font-size: 12px;");

		VBox header = new VBox();
		header.setAlignment(Pos.TOP_CENTER);
		header.getChildren().addAll(nameLabel, itemImage, priceLabel);

		VBox propertyContainer = new VBox();
		propertyContainer.setAlignment(Pos.TOP_CENTER);

		for (String property : component.getProperties()) {
			propertyContainer.getChildren().add(new Label(property));
		}

		HBox buttons;
		if (component.getComponentType().isSingle()) {
			RadioButton selectButton = new RadioButton("Select");
			if (selected)
				selectButton.fire();
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

	private void selectComponent(RadioButton button, RigComponent component) {
		if (currentItemSelected != null && currentItemSelector.isSelected()) {
			currentItemSelector.fire();
			rigBuilder.remove(currentItemSelected);
		}
		rigBuilder.add(component, false);
		currentItemSelected = component;
		currentItemSelector = button;
		updateCart();
	}

	private void addComponent(RigComponent component) {
		Validation validation = rigBuilder.add(component, false);
		if (!validation.valid()) {
			displayError(validation.message());
		} else {
			updateCart();
		}
	}

	private void removeComponent(RigComponent component) {
		rigBuilder.remove(component);
		updateCart();
	}

}
