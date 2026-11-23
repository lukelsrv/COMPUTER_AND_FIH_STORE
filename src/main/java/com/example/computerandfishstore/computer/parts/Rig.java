package com.example.computerandfishstore.computer.parts;

import com.example.computerandfishstore.computer.Buildable;
import com.example.computerandfishstore.computer.classes.ComponentType;
import com.example.computerandfishstore.computer.classes.ComputerComponent;
import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.components.*;
import com.example.computerandfishstore.computer.utils.ValidationResult;

import java.util.*;

public class Rig extends ComputerPart implements Buildable {

	private final Motherboard motherboard;
	private final CPU cpu;
	private final List<RAM> rams;
	private final GPU gpu;
	private final List<Storage> storages;
	private final CPUCooler cpuCooler;
	private final PowerSupply powerSupply;

	private Rig(
		String name,
		double price,
		Motherboard motherboard,
		CPU cpu,
		List<RAM> rams,
		GPU gpu,
		List<Storage> storages,
		CPUCooler cpuCooler,
		PowerSupply powerSupply
	) {
		super(name, price);
		this.motherboard = motherboard;
		this.cpu = cpu;
		this.rams = rams;
		this.gpu = gpu;
		this.storages = storages;
		this.cpuCooler = cpuCooler;
		this.powerSupply = powerSupply;
	}

	public Motherboard getMotherboard() {
		return motherboard;
	}

	public CPU getCPU() {
		return cpu;
	}

	public List<RAM> getRams() {
		return rams;
	}

	public GPU getGPU() {
		return gpu;
	}

	public List<Storage> getStorages() {
		return storages;
	}

	public CPUCooler getCPUCooler() {
		return cpuCooler;
	}

	public PowerSupply getPowerSupply() {
		return powerSupply;
	}

	public static class RigBuilder implements Builder<Rig, ComponentType> {

		private static final ComponentType[] COMPONENT_TYPES = ComponentType.values();

		private final Map<ComponentType, List<Component<Rig>>> data = new HashMap<>();
		private int currentIndex = 0;
		private ComponentType currentComponent = COMPONENT_TYPES[0];
		private String name = "Custom Build";

		public RigBuilder() {
			for (ComponentType componentType : COMPONENT_TYPES)
				data.put(componentType, new ArrayList<>());
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public ComponentType[] getEnum() {
			return COMPONENT_TYPES;
		}

		@Override
		public ComponentType getCurrent() {
			return currentComponent;
		}

		@Override
		public void next() {
			if (currentIndex >= COMPONENT_TYPES.length) {
				currentComponent = null;
			} else {
				currentComponent = COMPONENT_TYPES[currentIndex++];
			}
		}

		@Override
		public Map<ComponentType, List<Component<Rig>>> getData() {
			return data;
		}

		@Override
		public boolean selfFilter(Component<Rig> component) {
			ComputerComponent compComponent = (ComputerComponent) component;
			return switch (currentComponent) {
				case CPU -> {
					CPU cpu = (CPU) compComponent;
					yield ((Motherboard) getSingle(ComponentType.MOTHERBOARD)).getSupportedBrands().stream().anyMatch(cpu::supports);
				}
				case RAM -> {
					RAM ram = (RAM) compComponent;
					yield ((Motherboard) getSingle(ComponentType.MOTHERBOARD)).getRamDDR() == ram.getRamDDR();
				}
				case POWER_SUPPLY -> {
					PowerSupply powerSupply = (PowerSupply) compComponent;
					yield powerSupply.getSuppliedPower() >= getSum(rigComponent -> ((ComputerComponent) rigComponent).getRequiredPower());
				}
				default -> true;
			};
		}

		@Override
		public ValidationResult<Rig> selfBuild() {
			Motherboard motherboard = (Motherboard) getSingle(ComponentType.MOTHERBOARD);
			CPU cpu = (CPU) getSingle(ComponentType.CPU);
			if (motherboard.getSupportedBrands().stream().noneMatch(cpu::supports))
				return new ValidationResult<>(false, "The selected CPU is not supported by the selected Motherboard.");
			List<RAM> rams = getData(ComponentType.RAM).stream().map(comp -> (RAM) comp).toList();
			if (rams.stream().anyMatch(ram -> ram.getRamDDR() != motherboard.getRamDDR()))
				return new ValidationResult<>(false, "One of the selected RAM is not supported by the selected Motherboard.");
			PowerSupply powerSupply = (PowerSupply) getSingle(ComponentType.POWER_SUPPLY);
			if (powerSupply.getSuppliedPower() < getSum(rigComponent -> ((ComputerComponent) rigComponent).getRequiredPower()))
				return new ValidationResult<>(false, "The selected power supply can not support all the components selected.");

			GPU gpu = (GPU) getSingle(ComponentType.GPU);
			List<Storage> storages = getData(ComponentType.STORAGE).stream().map(comp -> (Storage) comp).toList();
			CPUCooler cpuCooler = (CPUCooler) getSingle(ComponentType.CPU_COOLER);
			double price = getSum(rigComponent -> ((ComputerComponent) rigComponent).getPrice());

			Rig rig = new Rig(name, price, motherboard, cpu, rams, gpu, storages, cpuCooler, powerSupply);
			return new ValidationResult<>(rig);
		}

	}

}
