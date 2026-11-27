package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.Buildable;
import com.example.computerandfishstore.classes.Buildable.Component;
import com.example.computerandfishstore.classes.StoreItem;
import com.example.computerandfishstore.computer.classes.RigComponent;
import com.example.computerandfishstore.computer.classes.RigComponentType;
import com.example.computerandfishstore.computer.components.CPU;
import com.example.computerandfishstore.computer.components.Motherboard;
import com.example.computerandfishstore.computer.components.PowerSupply;
import com.example.computerandfishstore.computer.components.RAM;
import com.example.computerandfishstore.computer.parts.Rig;
import com.example.computerandfishstore.utils.Validation;
import com.example.computerandfishstore.utils.ValidationResult;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Aquarium extends StoreItem implements Buildable {

	private static final Image IMAGE = getImage("");

	private final FishTank tank;
	private final List<Fish> fishes;

	public Aquarium(String name, double price, FishTank tank, List<Fish> fishes) {
		super(name, price);
		this.tank = tank;
		this.fishes = fishes;
	}

	public FishTank getTank() {
		return tank;
	}

	public List<Fish> getFishes() {
		return fishes;
	}

	@Override
	public Image getImage() {
		return IMAGE;
	}

	@Override
	public List<String> getProperties() {
		return List.of(
			"Tank: " + tank.getName(),
			"Fish: " + fishes
		);
	}

	public static class AquariumBuilder implements Builder<Aquarium, AquariumComponentType> {

		private static final AquariumComponentType[] COMPONENT_TYPES = AquariumComponentType.values();

		private final Map<AquariumComponentType, List<Component<Aquarium>>> data = new HashMap<>();
		private int currentIndex = 0;
		private AquariumComponentType currentType = COMPONENT_TYPES[0];
		private String name = "Custom Aquarium";

		public AquariumBuilder() {
			for (AquariumComponentType type : COMPONENT_TYPES)
				data.put(type, new ArrayList<>());
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getTitle() {
			return "Aquarium Builder";
		}

		@Override
		public AquariumComponentType[] getTypes() {
			return COMPONENT_TYPES;
		}

		@Override
		public AquariumComponentType getCurrentType() {
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
			List<Component<Aquarium>> components = getData(currentType);
			if (!components.isEmpty()) {
				for (Component<Aquarium> component : components) {
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
		public Map<AquariumComponentType, List<Component<Aquarium>>> getData() {
			return data;
		}

		@Override
		public boolean selfFilter(Component<Aquarium> component) {
			AquariumComponent aquariumComponent = (AquariumComponent) component;
			if (currentType == AquariumComponentType.FISH) {
				Fish currentFish = (Fish) aquariumComponent;
				TankWater water = (TankWater) getSingle(AquariumComponentType.TANK_WATER);
				if (currentFish.isSaltwater() != water.isSaltwater())
					return false;
				double gallonsUsing = 0;
				for (Component<Aquarium> comp : getData(currentType)) {
					Fish fish = (Fish) comp;
					gallonsUsing += fish.getDesignatedGallons();
					if (fish.isCarnivorous() != currentFish.isCarnivorous())
						return false;
				}
				double gallonsTotal = ((FishTank) getSingle(AquariumComponentType.TANK)).getGallons();
				return !(currentFish.getDesignatedGallons() > (gallonsTotal - gallonsUsing));
			}
			return true;
		}

		@Override
		public Validation add(Component<Aquarium> component, boolean next) {
			AquariumComponent aquariumComponent = (AquariumComponent) component;
			if (currentType == AquariumComponentType.FISH) {
				Fish currentFish = (Fish) aquariumComponent;
				TankWater water = (TankWater) getSingle(AquariumComponentType.TANK_WATER);
				if (currentFish.isSaltwater() != water.isSaltwater())
					return new Validation(false, "A saltwater fish can not be added to a freshwater aquarium.");
				double gallonsUsing = 0;
				for (Component<Aquarium> comp : getData(currentType)) {
					Fish fish = (Fish) comp;
					gallonsUsing += fish.getDesignatedGallons();
					if (fish.isCarnivorous() != currentFish.isCarnivorous())
						return new Validation(false, "Can not have carnivorous and non-carnivorous fish in the same aquarium.");
				}
				double gallonsTotal = ((FishTank) getSingle(AquariumComponentType.TANK)).getGallons();
				if (currentFish.getDesignatedGallons() > (gallonsTotal - gallonsUsing))
					return new Validation(false, "There is not enough room in the aquarium for the selected fish.");
			}
			return Builder.super.add(component, next);
		}

		@Override
		public List<String> getDetails() {
			String remaining = "Remaining Capacity: ";
			Component<Aquarium> comp = getSingle(AquariumComponentType.TANK);
			if (comp == null) {
				remaining += "None";
			} else {
				FishTank tank = (FishTank) comp;
				double using = getSum(aquariumComponent -> {
					if (!(aquariumComponent instanceof Fish fish))
						return 0;
					return fish.getDesignatedGallons();
				});
				remaining += (tank.getGallons() - using) + " gallons";
			}
			return List.of(remaining);
		}

		@Override
		public ValidationResult<Aquarium> selfBuild() {
			FishTank tank = (FishTank) getSingle(AquariumComponentType.TANK);
			double gallonsUsing = 0;
			boolean hasCarnivores = false;
			boolean hasHerbivores = false;
			List<Fish> fishes = getData(AquariumComponentType.FISH).stream().map(comp -> (Fish) comp).toList();
			for (Fish fish : fishes) {
				gallonsUsing += fish.getDesignatedGallons();
				if (fish.isCarnivorous()) {
					if (hasHerbivores)
						return new ValidationResult<>(false, "Cannot have carnivorous and non-carnivorous fish in the same tank.");
					hasCarnivores = true;
				} else {
					if (hasCarnivores)
						return new ValidationResult<>(false, "Cannot have carnivorous and non-carnivorous fish in the same tank.");
					hasHerbivores = true;
				}
			}
			if (gallonsUsing > tank.getGallons())
				return new ValidationResult<>(false, "The selected tank does not provide enough room for all the selected fish.");
			double price = getSum(comp -> ((AquariumComponent) comp).getPrice());
			Aquarium aquarium = new Aquarium(name, price, tank, fishes);
			return new ValidationResult<>(aquarium);
		}

	}

}
