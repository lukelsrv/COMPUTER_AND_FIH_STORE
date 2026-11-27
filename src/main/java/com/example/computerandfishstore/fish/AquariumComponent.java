package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.Buildable.Component;
import com.example.computerandfishstore.classes.StoreItem;

public abstract class AquariumComponent extends StoreItem implements Component<Aquarium> {

	private final AquariumComponentType componentType;

	public AquariumComponent(String name, double price, AquariumComponentType componentType) {
		super(name, price);
		this.componentType = componentType;
	}

	@Override
	public AquariumComponentType getComponentType() {
		return componentType;
	}

}
