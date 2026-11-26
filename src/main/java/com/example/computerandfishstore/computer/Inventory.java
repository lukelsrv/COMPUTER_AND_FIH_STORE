package com.example.computerandfishstore.computer;

import com.example.computerandfishstore.computer.classes.*;
import com.example.computerandfishstore.computer.components.*;
import com.example.computerandfishstore.computer.parts.*;
import com.example.computerandfishstore.computer.parts.Rig.RigBuilder;
import com.example.computerandfishstore.computer.utils.ValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Inventory {

	private static final List<ComputerPart> INVENTORY = new ArrayList<>();
	private static boolean inventoryInitialized = false;

	private static void init() {
		inventoryInitialized = true;
		// Components
		//- CPUs
		INVENTORY.addAll(List.of(
			new CPU("Intel Core i5-14600K", 319.99, 125, BrandType.INTEL, 14, 1),
			new CPU("AMD Ryzen 7 7800X3D", 399.99, 120, BrandType.AMD, 8, 1),
			new CPU("Intel Core i9-14900K", 589.99, 253, BrandType.INTEL, 24, 1)
		));

		//- HeatSinks
		INVENTORY.addAll(List.of(
			new HeatSink("Cooler Master Hyper 212", 39.99, 5),
			new HeatSink("Noctua NH-D15", 119.99, 5)
		));

		//- LiquidCoolers
		INVENTORY.addAll(List.of(
			new LiquidCooler("Arctic Liquid Freezer III 240", 109.99, 10),
			new LiquidCooler("NZXT Kraken 360", 179.99, 15)
		));

		//- GPUs
		INVENTORY.addAll(List.of(
			new GPU("NVIDIA GeForce RTX 4060", 299.99, 115, 8),
			new GPU("AMD Radeon RX 7800 XT", 499.99, 263, 16),
			new GPU("NVIDIA GeForce RTX 4080 SUPER", 999.99, 320, 16)
		));

		//- Motherboards
		INVENTORY.addAll(List.of(
			new Motherboard("MSI B760 Tomahawk WiFi DDR5", 189.99, 60, 5, Set.of(BrandType.INTEL)),
			new Motherboard("ASUS TUF Gaming B650-Plus", 199.99, 60, 5, Set.of(BrandType.AMD)),
			new Motherboard("Gigabyte Z790 AORUS Elite AX", 269.99, 70, 5, Set.of(BrandType.INTEL, BrandType.AMD))
		));

		//- PowerSupplies
		INVENTORY.addAll(List.of(
			new PowerSupply("Corsair RM650e 650W", 89.99, 650),
			new PowerSupply("Seasonic Focus GX-850 850W", 139.99, 850),
			new PowerSupply("Corsair RM1000x 1000W", 189.99, 1000)
		));

		//- RAM
		INVENTORY.addAll(List.of(
			new RAM("Corsair Vengeance DDR5 16GB", 54.99, 5, 5, 5600, 16),
			new RAM("G.Skill Flare X5 DDR5 32GB", 99.99, 8, 5, 6000, 32),
			new RAM("Kingston Fury Beast DDR5 64GB", 189.99, 10, 5, 6000, 64)
		));

		//- HDDs
		INVENTORY.addAll(List.of(
			new HDD("Seagate BarraCuda 2TB", 59.99, 8, 2000, true),
			new HDD("WD Elements External 5TB", 119.99, 8, 5000, false)
		));

		//- SSDs
		INVENTORY.addAll(List.of(
			new SSD("Samsung 990 EVO 1TB", 89.99, 5, 1000, true),
			new SSD("Samsung T7 Portable SSD 2TB", 169.99, 5, 2000, false)
		));

		// Parts
		//- Keyboards
		INVENTORY.addAll(List.of(
			new Keyboard("Logitech K120", 19.99, KeyboardSize.FULL, false),
			new Keyboard("Keychron K8", 89.99, KeyboardSize.TKL, true),
			new Keyboard("Razer Huntsman Mini", 119.99, KeyboardSize.MINI, true)
		));

		//- Monitors
		INVENTORY.addAll(List.of(
			new Monitor("LG UltraGear 27-inch 4K", 399.99, 27, 27, Resolution.FOUR_K),
			new Monitor("ASUS ROG Swift 32-inch 4K", 899.99, 32, 32, Resolution.FOUR_K),
			new Monitor("Dell UltraSharp 32-inch 8K", 3999.99, 32, 32, Resolution.EIGHT_K)
		));

		//- Mice
		INVENTORY.addAll(List.of(
			new Mouse("Logitech G203", 29.99, MouseType.OPTICAL),
			new Mouse("Logitech G502 X", 79.99, MouseType.LASER),
			new Mouse("SteelSeries Rival 3", 39.99, MouseType.MECHANICAL)
		));

		//- Rigs
		INVENTORY.addAll(List.of(
			buildRig("Intel Starter", getSingle(Motherboard.class), getSingle(CPU.class), List.of(getSingle(RAM.class)),
				getSingle(GPU.class), List.of(getSingle(SSD.class)), getSingle(LiquidCooler.class), getSingle(PowerSupply.class)),

			buildRig("AMD Gaming", getSingle(Motherboard.class, 1), getSingle(CPU.class, 1), getInventory(RAM.class, 1, 1),
				getSingle(GPU.class, 1), List.of(getSingle(SSD.class)), getSingle(LiquidCooler.class), getSingle(PowerSupply.class, 1)),

			buildRig("Intel Enthusiast", getSingle(Motherboard.class, 2), getSingle(CPU.class, 2), getInventory(RAM.class, 2, 2),
				getSingle(GPU.class, 1), List.of(getSingle(HDD.class), getSingle(HDD.class, 1)),
				getSingle(HeatSink.class), getSingle(PowerSupply.class, 2))
		));
	}

	public static List<ComputerPart> getInventory() {
		if (!inventoryInitialized)
			init();
		return INVENTORY;
	}

	public static <T> List<T> getInventory(Class<T> type) {
		List<T> list = new ArrayList<>();
		for (ComputerPart part : getInventory()) {
			if (type.isInstance(part)) {
				//noinspection unchecked
				list.add((T) part);
			}
		}
		return list;
	}

	public static <T extends ComputerPart> List<T> getInventory(Class<T> type, int... indices) {
		List<T> items = getInventory(type);
		int size = items.size();
		List<T> list = new ArrayList<>();
		assert size > 0;
		for (int index : indices) {
			if (index <= 0) {
				list.add(items.get(0));
			} else if (index >= size) {
				list.add(items.get(size - 1));
			} else {
				list.add(items.get(index));
			}
		}
		return list;
	}

	public static <T extends ComputerPart> T getSingle(Class<T> type) {
		return getSingle(type, 0);
	}

	public static <T extends ComputerPart> T getSingle(Class<T> type, int index) {
		List<T> items = getInventory(type);
		int size = items.size();
		assert size > 0;
		if (index <= 0)
			return items.get(0);
		if (index >= size)
			return items.get(size - 1);
		return items.get(index);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private static Rig buildRig(
		String name,
		Motherboard motherboard,
		CPU cpu,
		List<RAM> ram,
		GPU gpu,
		List<Storage> storage,
		CPUCooler cooler,
		PowerSupply powerSupply
	) {
		RigBuilder builder = new RigBuilder();
		builder.setName(name);
		builder.add(motherboard, true);
		builder.add(cpu, true);
		builder.add((List) ram, true);
		builder.add(gpu, true);
		builder.add((List) storage, true);
		builder.add(cooler, true);
		builder.add(powerSupply, true);
		ValidationResult<Rig> result = builder.build();
		if (!result.valid())
			throw new IllegalStateException("Invalid sample rig: " + result.message());
		return result.result();
	}

}
