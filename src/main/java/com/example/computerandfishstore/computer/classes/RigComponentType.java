package com.example.computerandfishstore.computer.classes;

import com.example.computerandfishstore.computer.Buildable.ComponentType;
import com.example.computerandfishstore.computer.components.*;
import com.example.computerandfishstore.computer.parts.Rig;

public enum RigComponentType implements ComponentType<Rig> {

	MOTHERBOARD(Motherboard.class, 1),
	CPU(CPU.class, 1),
	RAM(RAM.class, 1, 4),
	GPU(GPU.class, 1),
	STORAGE(Storage.class, 1, 10),
	CPU_COOLER(CPUCooler.class, 1),
	POWER_SUPPLY(PowerSupply.class, 1);

	private final Class<? extends RigComponent> componentClass;
	private final int minimum;
	private final int maximum;

	RigComponentType(Class<? extends RigComponent> componentClass, int amount) {
		this(componentClass, amount, amount);
	}

	RigComponentType(Class<? extends RigComponent> componentClass, int minimum, int maximum) {
		this.componentClass = componentClass;
		this.minimum = minimum;
		this.maximum = maximum;
	}

	@Override
	public int getMinimum() {
		return minimum;
	}

	@Override
	public int getMaximum() {
		return maximum;
	}

	@Override
	public Class<? extends RigComponent> getComponentClass() {
		return componentClass;
	}

}
