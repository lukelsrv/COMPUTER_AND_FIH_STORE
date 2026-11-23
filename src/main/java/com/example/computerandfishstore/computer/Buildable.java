package com.example.computerandfishstore.computer;

import com.example.computerandfishstore.computer.utils.Validation;
import com.example.computerandfishstore.computer.utils.ValidationResult;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface Buildable {

	interface ComponentEnum<T> {

		int getMinimum();

		int getMaximum();

		Class<? extends Component<T>> getComponentClass();

	}

	interface Component<T> { }

	interface Builder<T extends Buildable, C extends ComponentEnum<T>> {

		C[] getEnum();

		C getCurrent();

		void next();

		Map<C, List<Component<T>>> getData();

		default List<Component<T>> getData(C type) {
			return getData().get(type);
		}

		default Component<T> getSingle(C type) {
			if (!isSingle(type))
				return null;
			List<Component<T>> list = getData(type);
			if (list.isEmpty())
				return null;
			return list.get(0);
		}

		default boolean isSingle(C type) {
			return type.getMaximum() == 1;
		}

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

		boolean selfFilter(Component<T> component);

		default boolean filter(Component<T> component) {
			if (!isCorrectType(component))
				return false;
			return selfFilter(component);
		}

		default Validation add(Component<T> component, boolean next) {
			if (!isCorrectType(component))
				return new Validation(false, "Provided component is not of the current type being selected.");
			if (!hasRoom())
				return new Validation(false, "This type of component has already reached the max number allowed.");
			getData().get(getCurrent()).add(component);
			if (next)
				next();
			return new Validation(true);
		}

		default Validation add(List<Component<T>> components, boolean next) {
			for (Component<T> component : components) {
				Validation validation = add(component, false);
				if (!validation.valid())
					return validation;
			}
			if (next)
				next();
			return new Validation(true);
		}

		default boolean isCorrectType(Component<T> component) {
			C current = getCurrent();
			return current.getComponentClass().isInstance(component);
		}

		default boolean hasRoom() {
			C current = getCurrent();
			List<Component<T>> list = getData(current);
			return list.size() < current.getMaximum();
		}

		ValidationResult<T> selfBuild();

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
