package com.example.computerandfishstore.fish;

import com.example.computerandfishstore.classes.Buildable;
import com.example.computerandfishstore.classes.StoreItem;
import com.example.computerandfishstore.classes.Validation;
import com.example.computerandfishstore.classes.ValidationResult;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Typed class for Aquariums.
 */
public class Aquarium extends StoreItem implements Buildable {

	private static final Image IMAGE = getImage("AQUARIUM.png");

	private final FishTank tank;
	private final List<Fish> fishes;

	/**
	 * Constructs a new Aquarium.
	 * @param name The name of this Aquarium.
	 * @param price The price of this Aquarium.
	 * @param tank The tank of this Aquarium.
	 * @param fishes The fish of this Aquarium.
	 */
	public Aquarium(String name, double price, FishTank tank, List<Fish> fishes) {
		super(name, price);
		this.tank = tank;
		this.fishes = fishes;
	}

	/**
	 * @return The tank of this Aquarium.
	 */
	public FishTank getTank() {
		return tank;
	}

	/**
	 * @return The fish of this Aquarium.
	 */
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
			"Fish: " + combine(fishes, ", ")
		);
	}

	/**
	 * Builder class for customizing components of an {@link Aquarium} before finalization.
	 */
	public static class AquariumBuilder implements Builder<Aquarium, AquariumComponentType> {

		private static final AquariumComponentType[] COMPONENT_TYPES = AquariumComponentType.values();

		private final Map<AquariumComponentType, List<Component<Aquarium>>> data = new HashMap<>();
		private int currentIndex = 0;
		private AquariumComponentType currentType = COMPONENT_TYPES[0];
		private String name = "Custom Aquarium";

		/**
		 * Constructs a new AquariumBuilder.
		 */
		public AquariumBuilder() {
			for (AquariumComponentType type : COMPONENT_TYPES)
				data.put(type, new ArrayList<>());
		}

		/**
		 * Sets the name the finalized Aquarium will have.
		 * @param name The name of the Aquarium.
		 */
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
			if (!components.isEmpty()) { // This component type already has components, remove ones that don't fit anymore.
				components.removeIf(component -> !filter(component));
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
		public <E extends Component<Aquarium>> boolean selfFilter(E component) {
			if (currentType == AquariumComponentType.FISH) { // Filters displayed Fish by checking against tank water and fish compatibility.
				Fish currentFish = (Fish) component;
				TankWater water = getSingle(AquariumComponentType.TANK_WATER);
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

		// Custom add, as BuilderMenu for Aquarium does not refresh fish everytime one is added.
		@Override
		public <E extends Component<Aquarium>> Validation add(E component, boolean next) {
			if (currentType == AquariumComponentType.FISH) {
				Fish currentFish = (Fish) component;
				TankWater water =getSingle(AquariumComponentType.TANK_WATER);
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
			FishTank tank = getSingle(AquariumComponentType.TANK);
			TankWater water = getSingle(AquariumComponentType.TANK_WATER);
			double gallonsUsing = 0;
			boolean hasCarnivores = false;
			boolean hasHerbivores = false;
			List<Fish> fishes = getData(AquariumComponentType.FISH);
			for (Fish fish : fishes) {
				gallonsUsing += fish.getDesignatedGallons();
				if (water.isSaltwater() != fish.isSaltwater()) // Ensures fish are compatible with the water.
					return new ValidationResult<>(false, "One or more of the selected fish can not live in the water.");
				// Ensures fish are compatible with each other.
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
			if (gallonsUsing > tank.getGallons()) // Ensures the tank can support all the fish.
				return new ValidationResult<>(false, "The selected tank does not provide enough room for all the selected fish.");
			double price = getSum(comp -> ((AquariumComponent) comp).getPrice());
			Aquarium aquarium = new Aquarium(name, price, tank, fishes);
			return new ValidationResult<>(aquarium);
		}

	}

}
