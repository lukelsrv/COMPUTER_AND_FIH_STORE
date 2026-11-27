package com.example.computerandfishstore.fish;

import javafx.scene.image.Image;

import java.util.List;

public class TankWater extends AquariumComponent {

	private final boolean saltwater;
	private final Image image;

	public TankWater(String name, boolean saltwater) {
		super(name, 0, AquariumComponentType.TANK_WATER);
		this.saltwater = saltwater;
		image = saltwater ? getImage("SALTWATER.png") : getImage("FRESHWATER.png");
	}

	public boolean isSaltwater() {
		return saltwater;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public List<String> getProperties() {
		return List.of();
	}

}
