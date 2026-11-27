package com.example.computerandfishstore.fish;

import javafx.scene.image.Image;

import java.util.List;

/**
 * Typed class for Tank Water.
 */
public class TankWater extends AquariumComponent {

	private final boolean saltwater;
	private final Image image;

	/**
	 * Constructs a new TankWater.
	 * @param name The name of this TankWater.
	 * @param saltwater Whether this TankWater is saltwater.
	 */
	public TankWater(String name, boolean saltwater) {
		super(name, 0, AquariumComponentType.TANK_WATER);
		this.saltwater = saltwater;
		image = saltwater ? getImage("SALTWATER.png") : getImage("FRESHWATER.png");
	}

	/**
	 * @return Whether this TankWater is saltwater.
	 */
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
