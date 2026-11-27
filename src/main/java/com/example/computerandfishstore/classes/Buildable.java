package com.example.computerandfishstore.classes;

import com.example.computerandfishstore.utils.Validation;
import com.example.computerandfishstore.utils.ValidationResult;

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
	interface ComponentType<T extends Buildable> {

		/**
		 * @return The minimum amount required for this component type.
		 */
		int getMinimum();

		/**
		 * @return The maximum amount allowed for this component type.
		 */
		int getMaximum();

		/**
		 * @return Whether this component type can only have one component.
		 */
		default boolean isSingle() {
			return getMaximum() == 1;
		}

		/**
		 * @return Whether this component type can have no components.
		 */
		default boolean isOptional() {
			return getMinimum() <= 0;
		}

		/**
		 * @return The class linked to this component type.
		 */
		Class<? extends Component<T>> getComponentClass();

	}

	/**
	 * Interface for displaying a class is a component for a Buildable class.
	 * @param <T> A class that is Buildable.
	 */
	interface Component<T extends Buildable> {

		/**
		 * @return The component type associated with this component.
		 */
		ComponentType<T> getComponentType();

	}

	/**
	 * Interface for implementing a Builder to build a Buildable.
	 * @param <T> A class that is Buildable.
	 * @param <C> A class that has component types for the Buildable class.
	 */
	interface Builder<T extends Buildable, C extends ComponentType<T>> {

		/**
		 * @return The title of this builder. Used for BuilderMenu,
		 */
		String getTitle();

		/**
		 * @return All component types for the Buildable class.
		 */
		C[] getTypes();

		/**
		 * @return The current component type being customized.
		 */
		C getCurrentType();

		/**
		 * Goes to the next component to be customized.
		 * Stops at the last component type.
		 * Should never be called directly unless you know what you are doing.
		 * @return A Validation object on its success or not.
		 */
		Validation selfNext();

		/**
		 * Goes to the next component to be customized.
		 * Stops at the last component type.
		 * General handler before being directed to {@link #selfNext()}.
		 * @return A Validation object on its success or not.
		 */
		default Validation next() {
			if (!canNext())
				return new Validation(false, "The current item type has not been fulfilled yet.");
			return selfNext();
		}

		/**
		 * @return Whether the current component has met its requirements before going to next component.
		 */
		default boolean canNext() {
			C current = getCurrentType();
			// Ensure the number of components for the current component type has fulfilled the minimum requirement.
			return getData(current).size() >= current.getMinimum();
		}

		/**
		 * Goes to the previous component being customized.
		 * Stops at the first component type.
		 * @return A Validation object on its success or not.
		 */
		Validation prev();

		/**
		 * @return Whether the current component type is the last one.
		 */
		boolean isEnd();

		/**
		 * @return Whether the current component type is the first one.
		 */
		boolean isStart();

		/**
		 * @return The current components for each component type.
		 */
		Map<C, List<Component<T>>> getData();

		/**
		 * The current components for the provided component type.
		 * @param type The component type to get the components for.
		 * @return The current components.
		 */
		default <E extends Component<T>> List<E> getData(C type) {
			//noinspection unchecked
			return (List<E>) getData().get(type);
		}

		/**
		 * The component for the provided component type.
		 * @param type The component type to get the component for.
		 * @return The component, otherwise {@code null} if not selected yet or is not single.
		 */
		default <E extends Component<T>> E getSingle(C type) {
			if (!type.isSingle())
				return null;
			List<E> list = getData(type);
			if (list.isEmpty())
				return null;
			return list.get(0);
		}

		/**
		 * Gets the sum of all components for all component types using the provided function to get the value to sum up.
		 * @param func The function to get the value to sum up.
		 * @return The sum.
		 */
		default double getSum(Function<Component<T>, Number> func) {
			double sum = 0;
			for (C type : getTypes()) {
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
		<E extends Component<T>> boolean selfFilter(E component);

		/**
		 * Whether the provided component is allowed to be selected.
		 * General handler before being directed to {@link #selfFilter(Component)}.
		 * @param component The component to check.
		 * @return {@code true} if the component can be selected, otherwise {@code false}.
		 */
		default <E extends Component<T>> boolean filter(E component) {
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
		default <E extends Component<T>> Validation add(E component, boolean next) {
			if (!isCorrectType(component))
				return new Validation(false, "The provided item is not of the current type being selected.");
			if (!hasRoom(1))
				return new Validation(false, "This type of item has already reached the max number allowed.");
			getData().get(getCurrentType()).add(component);
			if (next)
				next();
			return new Validation(true);
		}

		/**
		 * Adds the provided components to the build.
		 * @param components The components to add.
		 * @param next Whether after adding the components, if the Builder should move on to the next component type to be customized.
		 * @return A Validation object on the success of all components are not.
		 */
		default <E extends Component<T>> Validation add(List<E> components, boolean next) {
			if (!hasRoom(components.size()))
				return new Validation(false, "The provided items go over the maximum amount allowed. None have been added.");
			for (Component<T> component : components) {
				Validation validation = add(component, false);
				if (!validation.valid())
					return validation;
			}
			if (next)
				next();
			return new Validation(true);
		}

		/**
		 * Removes the provided component from the build.
		 * @param component The component to remove.
		 * @return {@code true} if the component was found and removed, otherwise {@code false}.
		 */
		default <E extends Component<T>> boolean remove(E component) {
			return getData(getCurrentType()).remove(component);
		}

		/**
		 * Helper method for ensuring the provided component is of the current component type being customized.
		 * @param component The component to check.
		 * @return {@code true} if the component is of the correct type, otherwise {@code false}.
		 */
		default <E extends Component<T>> boolean isCorrectType(E component) {
			C current = getCurrentType();
			return current.getComponentClass().isInstance(component);
		}

		/**
		 * Helper method for checking if the current component type has enough room for number of {@code with} via {@link ComponentType#getMaximum()}.
		 * @param with The number to check room for.
		 * @return {@code true} if there is enough room, otherwise {@code false}.
		 */
		default boolean hasRoom(int with) {
			C current = getCurrentType();
			List<Component<T>> list = getData(current);
			return list.size() + with <= current.getMaximum();
		}

		/**
		 * @return Additional details to be displayed in BuilderMenu.
		 */
		List<String> getDetails();

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
			for (C type : getTypes()) {
				int size = getData(type).size();
				if (size < type.getMinimum()) {
					return new ValidationResult<>(false, "One of the item types did not meet the minimum requirements.");
				} else if (size > type.getMaximum()) {
					return new ValidationResult<>(false, "One of the item types has more components than the maximum limit.");
				}
			}
			return selfBuild();
		}

	}

}
