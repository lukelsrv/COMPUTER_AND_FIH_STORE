package com.example.computerandfishstore.computer.parts;

import com.example.computerandfishstore.classes.Buildable;
import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.classes.RigComponent;
import com.example.computerandfishstore.computer.classes.ComputerPart;
import com.example.computerandfishstore.computer.components.*;
import com.example.computerandfishstore.utils.Validation;
import com.example.computerandfishstore.utils.ValidationResult;
import javafx.scene.image.Image;

import java.util.*;

/**
 * Typed class for Rigs.
 */
public class Rig extends ComputerPart implements Buildable {

	private static final Image IMAGE = getImage("RIG.png");

	private final Motherboard motherboard;
	private final CPU cpu;
	private final List<RAM> rams;
	private final GPU gpu;
	private final List<Storage> storages;
	private final CPUCooler cpuCooler;
	private final PowerSupply powerSupply;

	/**
	 * Constructs a new Rig.
	 * @param name The name of this Rig.
	 * @param price The price of this Rig.
	 * @param motherboard The motherboard of this Rig.
	 * @param cpu The CPU of this Rig.
	 * @param rams The RAM of this Rig.
	 * @param gpu The GPU of this Rig.
	 * @param storages The Storages of this Rig.
	 * @param cpuCooler The CPU cooler of this Rig.
	 * @param powerSupply The power supply of this Rig.
	 */
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

	/**
	 * @return The motherboard of this Rig.
	 */
	public Motherboard getMotherboard() {
		return motherboard;
	}

	/**
	 * @return The CPU of this Rig.
	 */
	public CPU getCPU() {
		return cpu;
	}

	/**
	 * @return The RAM of this Rig.
	 */
	public List<RAM> getRams() {
		return rams;
	}

	/**
	 * @return The GPU of this Rig.
	 */
	public GPU getGPU() {
		return gpu;
	}

	/**
	 * @return The Storages of this Rig.
	 */
	public List<Storage> getStorages() {
		return storages;
	}

	/**
	 * @return The CPU cooler of this Rig.
	 */
	public CPUCooler getCPUCooler() {
		return cpuCooler;
	}

	/**
	 * @return The power supply of this Rig.
	 */
	public PowerSupply getPowerSupply() {
		return powerSupply;
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of(
			"Motherboard: " + motherboard.getName(),
			"CPU: " + cpu.getName(),
			"RAM: " + rams,
			"GPU: " + gpu.getName(),
			"Storages: " + storages,
			"CPU Cooler: " + cpuCooler.getName(),
			"Power Supply: " + powerSupply.getName()
		);
	}

	/**
	 * Builder class for customizing the components of a {@link Rig} before finalization.
	 */
	public static class RigBuilder implements Builder<Rig, RigComponentType> {

		private static final RigComponentType[] COMPONENT_TYPES = RigComponentType.values();

		private final Map<RigComponentType, List<Component<Rig>>> data = new HashMap<>();
		private int currentIndex = 0;
		private RigComponentType currentType = COMPONENT_TYPES[0];
		private String name = "Custom Build";

		/**
		 * Constructs a new RigBuilder.
		 */
		public RigBuilder() {
			for (RigComponentType rigComponentType : COMPONENT_TYPES)
				data.put(rigComponentType, new ArrayList<>());
		}

		/**
		 * Sets the name the finalized Rig will have.
		 * @param name The name of the Rig.
		 */
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getTitle() {
			return "Computer Builder";
		}

		@Override
		public RigComponentType[] getTypes() {
			return COMPONENT_TYPES;
		}

		@Override
		public RigComponentType getCurrentType() {
			return currentType;
		}

		@Override
		public Validation selfNext() {
			// Move on to the next component for customizing.
			if (currentIndex < 0) {
				currentIndex = 0;
			} else if (currentIndex >= COMPONENT_TYPES.length - 1) {
				currentIndex = COMPONENT_TYPES.length - 1;
			} else {
				currentIndex++;
			}
			currentType = COMPONENT_TYPES[currentIndex];
			List<Component<Rig>> components = getData(currentType);
			if (!components.isEmpty()) { // This component type already has components, remove ones that don't fit anymore.
				for (Component<Rig> component : components) {
					if (!filter(component))
						components.remove(component);
				}
			}
			return new Validation(true);
		}

		@Override
		public Validation prev() {
			if (currentIndex < 0) {
				currentIndex = 0;
			} else if (currentIndex >= COMPONENT_TYPES.length) {
				currentIndex = COMPONENT_TYPES.length - 1;
			} else {
				currentIndex--;
			}
			currentType = COMPONENT_TYPES[currentIndex];
			return new Validation(true);
		}

		@Override
		public boolean isStart() {
			return currentIndex == 0;
		}

		@Override
		public boolean isEnd() {
			return currentIndex == COMPONENT_TYPES.length - 1;
		}

		@Override
		public Map<RigComponentType, List<Component<Rig>>> getData() {
			return data;
		}

		@Override
		public boolean selfFilter(Component<Rig> component) {
			RigComponent compComponent = (RigComponent) component;
			return switch (currentType) {
				case CPU -> { // Filters displayed CPUs by checking the brand types the selected Motherboard offers.
					CPU cpu = (CPU) compComponent;
					yield ((Motherboard) getSingle(RigComponentType.MOTHERBOARD)).getSupportedBrands().stream().anyMatch(cpu::supports);
				}
				case RAM -> { // Filters displayed RAM by checking the DDR type of the RAM and Motherboard are the same.
					RAM ram = (RAM) compComponent;
					yield ((Motherboard) getSingle(RigComponentType.MOTHERBOARD)).getRamDDR() == ram.getRamDDR();
				}
				case POWER_SUPPLY -> { // Filters displayed PowerSupplys by checking the supplied power meets the total required power minimum.
					PowerSupply powerSupply = (PowerSupply) compComponent;
					yield powerSupply.getSuppliedPower() >= getSum(rigComponent -> ((RigComponent) rigComponent).getRequiredPower());
				}
				default -> true;
			};
		}

		@Override
		public List<String> getDetails() {
			return List.of("Required Power: " + getSum(comp -> ((RigComponent) comp).getRequiredPower()));
		}

		@Override
		public ValidationResult<Rig> selfBuild() {
			// Ensures the brand type of the selected CPU is supported by the selected Motherboard.
			Motherboard motherboard = (Motherboard) getSingle(RigComponentType.MOTHERBOARD);
			CPU cpu = (CPU) getSingle(RigComponentType.CPU);
			if (motherboard.getSupportedBrands().stream().noneMatch(cpu::supports))
				return new ValidationResult<>(false, "The selected CPU is not supported by the selected Motherboard.");

			// Ensures the DDR type of all the selected RAM matches the DDR type of the Motherboard.
			List<RAM> rams = getData(RigComponentType.RAM).stream().map(comp -> (RAM) comp).toList();
			if (rams.stream().anyMatch(ram -> ram.getRamDDR() != motherboard.getRamDDR()))
				return new ValidationResult<>(false, "One of the selected RAM is not supported by the selected Motherboard.");

			// Ensures the selected Power Supply supplies enough power to all of selected components that require power.
			PowerSupply powerSupply = (PowerSupply) getSingle(RigComponentType.POWER_SUPPLY);
			if (powerSupply.getSuppliedPower() < getSum(rigComponent -> ((RigComponent) rigComponent).getRequiredPower()))
				return new ValidationResult<>(false, "The selected power supply can not support all the components selected.");

			GPU gpu = (GPU) getSingle(RigComponentType.GPU);
			List<Storage> storages = getData(RigComponentType.STORAGE).stream().map(comp -> (Storage) comp).toList();
			CPUCooler cpuCooler = (CPUCooler) getSingle(RigComponentType.CPU_COOLER);
			double price = getSum(rigComponent -> ((RigComponent) rigComponent).getPrice());

			Rig rig = new Rig(name, price, motherboard, cpu, rams, gpu, storages, cpuCooler, powerSupply);
			return new ValidationResult<>(rig);
		}

	}

}
