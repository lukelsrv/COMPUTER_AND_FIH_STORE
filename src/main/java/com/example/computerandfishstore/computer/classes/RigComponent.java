package com.example.computerandfishstore.computer.classes;

import com.example.computerandfishstore.classes.Buildable.Component;
import com.example.computerandfishstore.computer.parts.Rig;

/**
 * Typed class for components that are included in making a {@link Rig}.
 */
public abstract class RigComponent extends ComputerPart implements Component<Rig> {

	private final RigComponentType componentType;
	private final int requiredPower;

	/**
	 * Constructs a new RigComponent.
	 * @param name The name of this RigComponent.
	 * @param price The price of this RigComponent.
	 * @param componentType The type of this RigComponent.
	 * @param requiredPower The required power of this RigComponent.
	 */
	public RigComponent(String name, double price, RigComponentType componentType, int requiredPower) {
		super(name, price);
		this.componentType = componentType;
		this.requiredPower = requiredPower;
	}

	@Override
	public RigComponentType getComponentType() {
		return componentType;
	}

	/**
	 * @return The required power of this RigComponent.
	 */
	public int getRequiredPower() {
		return requiredPower;
	}

}
