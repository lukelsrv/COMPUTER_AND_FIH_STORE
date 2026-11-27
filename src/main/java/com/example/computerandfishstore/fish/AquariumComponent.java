package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.Buildable.Component;
import com.example.computerandfishstore.classes.StoreItem;

/**
 * Typed class for components that are included in making an {@link Aquarium}.
 */
public abstract class AquariumComponent extends StoreItem implements Component<Aquarium> {

	private final AquariumComponentType componentType;

	/**
	 * Constructs a new AquariumComponent.
	 * @param name The name of this AquariumComponent.
	 * @param price The price of this AquariumComponent.
	 * @param componentType The type of this AquariumComponent.
	 */
	public AquariumComponent(String name, double price, AquariumComponentType componentType) {
		super(name, price);
		this.componentType = componentType;
	}

	@Override
	public AquariumComponentType getComponentType() {
		return componentType;
	}

}
