package com.example.computerandfishstore;

import com.example.computerandfishstore.computer.Buildable.Component;
import com.example.computerandfishstore.computer.Inventory;
import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.classes.RigComponent;
import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.parts.Rig;
import com.example.computerandfishstore.computer.parts.Rig.RigBuilder;
import com.example.computerandfishstore.computer.utils.Validation;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
			rigBuilder.next();
			updateInventory();
		});
		backButton.setOnAction(event -> {
			rigBuilder.prev();
			updateInventory();
		});
	}

	private void checkNext() {
		nextButton.setVisible(rigBuilder.canNext());
	}

	private void updateInventory() {
		itemGrid.getChildren().clear();
		checkNext();
		RigComponentType componentType = rigBuilder.getCurrentType();
		Class<? extends RigComponent> componentClass = componentType.getComponentClass();
		List<? extends RigComponent> items = Inventory.getInventory(componentClass);
		int count = 0;
		for (RigComponent item : items) {
			if (!rigBuilder.filter(item))
				continue;
			VBox card = createItemCard(item);
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
		checkNext();
	}

	private void updateDetails() {
		String text = "Additional Details:\n- Total Cost: $"
			+ String.format("%.2f", rigBuilder.getSum(rigComponent -> ((ComputerPart) rigComponent).getPrice()))
			+ "\n- Required Power: " + rigBuilder.getSum(rigComponent -> ((RigComponent) rigComponent).getRequiredPower());
		OthertextArea.setText(text);
	}

	private VBox createItemCard(RigComponent component) {
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

		VBox header = new VBox();
		header.setAlignment(Pos.TOP_CENTER);
		header.getChildren().addAll(nameLabel, itemImage);

		VBox propertyContainer = new VBox();
		propertyContainer.setAlignment(Pos.TOP_CENTER);

		for (String property : component.getProperties()) {
			propertyContainer.getChildren().add(new Label(property));
		}

		HBox buttons;
		if (component.getComponentType().isSingle()) {
			RadioButton selectButton = new RadioButton("Select");
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
			// Print error
		} else {
			updateCart();
		}
	}

	private void removeComponent(RigComponent component) {
		rigBuilder.remove(component);
		updateCart();
	}

}
