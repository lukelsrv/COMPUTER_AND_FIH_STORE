package com.example.computerandfishstore.computer;

import com.example.computerandfishstore.computer.utils.Validation;
import com.example.computerandfishstore.computer.utils.ValidationResult;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Interface for displaying that something can be built.
 */
public interface Buildable {

	/**
	 * Interface for displaying the component types that make up a Buildable.
	 * @param <T> A class that is Buildable.
	 */
	interface ComponentEnum<T extends Buildable> {

		/**
		 * @return The minimum amount required for this component type.
		 */
		int getMinimum();

		/**
		 * @return The maximum amount allowed for this component type.
		 */
		int getMaximum();

		/**
		 * @return The class linked to this component type.
		 */
		Class<? extends Component<T>> getComponentClass();

	}

	/**
	 * Interface for displaying a class is a component for a Buildable class.
	 * @param <T> A class that is Buildable.
	 */
	interface Component<T extends Buildable> { }

	/**
	 * Interface for implementing a Builder to build a Buildable.
	 * @param <T> A class that is Buildable.
	 * @param <C> A class that has component types for the Buildable class.
	 */
	interface Builder<T extends Buildable, C extends ComponentEnum<T>> {

		/**
		 * @return All component types for the Buildable class.
		 */
		C[] getEnum();

		/**
		 * @return The current component type being customized.
		 */
		C getCurrent();

		/**
		 * State that the current component type is done being customized and to move on to the next.
		 * Should never be called directly unless you know what you are doing.
		 * @return A Validation object on its success or not.
		 */
		Validation selfNext();

		/**
		 * State that the current component type is done being customized and to move on to the next.
		 * General handler before being directed to {@link #selfNext()}.
		 * @return A Validation object on its success or not.
		 */
		default Validation next() {
			C current = getCurrent();
			// Ensure the number of components for the current component type has fulfilled the minimum requirement.
			if (getData(current).size() < current.getMinimum())
				return new Validation(false, "The current component type has not been fulfilled yet.");
			return selfNext();
		}

		/**
		 * @return The current components for each component type.
		 */
		Map<C, List<Component<T>>> getData();

		/**
		 * The current components for the provided component type.
		 * @param type The component type to get the components for.
		 * @return The current components.
		 */
		default List<Component<T>> getData(C type) {
			return getData().get(type);
		}

		/**
		 * The component for the provided component type.
		 * @param type The component type to get the component for.
		 * @return The component, otherwise {@code null} if not selected yet or is not single.
		 */
		default Component<T> getSingle(C type) {
			if (!isSingle(type))
				return null;
			List<Component<T>> list = getData(type);
			if (list.isEmpty())
				return null;
			return list.get(0);
		}

		/**
		 * Whether the provided component type is singular.
		 * @param type The component type to check is singular.
		 * @return {@code true} if the component type is singular, otherwise {@code false}.
		 */
		default boolean isSingle(C type) {
			return type.getMaximum() == 1;
		}

		/**
		 * Gets the sum of all components for all component types using the provided function to get the value to sum up.
		 * @param func The function to get the value to sum up.
		 * @return The sum.
		 */
		default double getSum(Function<Component<T>, Number> func) {
			double sum = 0;
			for (C type : getEnum()) {
				List<Component<T>> components = getData(type);
				for (Component<T> component : components) {
					sum += func.apply(component).doubleValue();
				}
			}
			return sum;
		}

		/**
		 * Whether the provided component is allowed to be selected.
		 * Should never be called directly unless you know what you are doing.
		 * @param component The component to check.
		 * @return {@code true} if the component can be selected, otherwise {@code false}.
		 */
		boolean selfFilter(Component<T> component);

		/**
		 * Whether the provided component is allowed to be selected.
		 * General handler before being directed to {@link #selfFilter(Component)}.
		 * @param component The component to check.
		 * @return {@code true} if the component can be selected, otherwise {@code false}.
		 */
		default boolean filter(Component<T> component) {
			if (!isCorrectType(component))
				return false;
			return selfFilter(component);
		}

		/**
		 * Adds the provided component to the build.
		 * @param component The component to add.
		 * @param next Whether after adding this component, if the Builder should move on to the next component type to be customized.
		 * @return A Validation object on its success or not.
		 */
		default Validation add(Component<T> component, boolean next) {
			if (!isCorrectType(component))
				return new Validation(false, "Provided component is not of the current type being selected.");
			if (!hasRoom(1))
				return new Validation(false, "This type of component has already reached the max number allowed.");
			getData().get(getCurrent()).add(component);
			if (next)
				selfNext();
			return new Validation(true);
		}

		/**
		 * Adds the provided components to the build.
		 * @param components The components to add.
		 * @param next Whether after adding the components, if the Builder should move on to the next component type to be customized.
		 * @return A Validation object on the success of all components are not.
		 */
		default Validation add(List<Component<T>> components, boolean next) {
			if (!hasRoom(components.size()))
				return new Validation(false, "The provided components go over the maximum amount allowed. None have been added.");
			for (Component<T> component : components) {
				Validation validation = add(component, false);
				if (!validation.valid())
					return validation;
			}
			if (next)
				selfNext();
			return new Validation(true);
		}

		/**
		 * Helper method for ensuring the provided component is of the current component type being customized.
		 * @param component The component to check.
		 * @return {@code true} if the component is of the correct type, otherwise {@code false}.
		 */
		default boolean isCorrectType(Component<T> component) {
			C current = getCurrent();
			return current.getComponentClass().isInstance(component);
		}

		/**
		 * Helper method for checking if the current component type has enough room for number of {@code with} via {@link ComponentEnum#getMaximum()}.
		 * @param with The number to check room for.
		 * @return {@code true} if there is enough room, otherwise {@code false}.
		 */
		default boolean hasRoom(int with) {
			C current = getCurrent();
			List<Component<T>> list = getData(current);
			return list.size() + with <= current.getMaximum();
		}

		/**
		 * Finalizes this Builder.
		 * Should never be called directly unless you know what you are doing.
		 * @return The ValidationResult object with the Buildable object if passed, otherwise reasoning on failure.
		 */
		ValidationResult<T> selfBuild();

		/**
		 * Finalizes this Builder.
		 * General handler before being directed to {@link #selfBuild()}.
		 * @return The ValidationResult object with the Buildable object if passed, otherwise reasoning on failure.
		 */
		default ValidationResult<T> build() {
			for (C type : getEnum()) {
				int size = getData(type).size();
				if (size < type.getMinimum()) {
					return new ValidationResult<>(false, "One of the component types did not meet the minimum requirements.");
				} else if (size > type.getMaximum()) {
					return new ValidationResult<>(false, "One of the component types has more components than the maximum limit.");
				}
			}
			return selfBuild();
		}

	}

}
