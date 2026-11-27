package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.Buildable.ComponentType;

public enum AquariumComponentType implements ComponentType<Aquarium> {

	TANK(FishTank.class, 1, 1),
	TANK_WATER(TankWater.class, 1, 1),
	FISH(Fish.class, 0, 30);

	private final Class<? extends AquariumComponent> componentClass;
	private final int minimum;
	private final int maximum;

	AquariumComponentType(Class<? extends AquariumComponent> componentClass, int minimum, int maximum) {
		this.componentClass = componentClass;
		this.minimum = minimum;
		this.maximum = maximum;
	}

	@Override
	public Class<? extends AquariumComponent> getComponentClass() {
		return componentClass;
	}

	@Override
	public int getMinimum() {
		return minimum;
	}

	@Override
	public int getMaximum() {
		return maximum;
	}

}
