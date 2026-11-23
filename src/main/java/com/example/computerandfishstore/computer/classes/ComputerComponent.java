package com.example.computerandfishstore.computer.classes;

import com.example.computerandfishstore.computer.Buildable.Component;
import com.example.computerandfishstore.computer.parts.Rig;

public abstract class ComputerComponent extends ComputerPart implements Component<Rig> {

	private final ComponentType componentType;
	private final int requiredPower;

	public ComputerComponent(String name, double price, ComponentType componentType, int requiredPower) {
		super(name, price);
		this.componentType = componentType;
		this.requiredPower = requiredPower;
	}

	public ComponentType getComponentType() {
		return componentType;
	}

	public int getRequiredPower() {
		return requiredPower;
	}

}
