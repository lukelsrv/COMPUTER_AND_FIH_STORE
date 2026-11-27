package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.Buildable.ComponentType;

/**
 * Component types that make up an Aquarium.
 */
public enum AquariumComponentType implements ComponentType<Aquarium> {

	TANK(FishTank.class, 1, 1),
	TANK_WATER(TankWater.class, 1, 1),
	FISH(Fish.class, 0, 30);

	private final Class<? extends AquariumComponent> componentClass;
	private final int minimum;
	private final int maximum;

	/**
	 * Constructs a new AquariumComponentType.
	 * @param componentClass The class of the AquariumComponent associated with this AquariumComponentType.
	 * @param minimum The minimum amount of AquariumComponents for this AquariumComponentType requires to have to build an Aquarium.
	 * @param maximum The maximum amount of AquariumComponents for this AquariumComponentType requires to have to build an Aquarium.
	 */
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
