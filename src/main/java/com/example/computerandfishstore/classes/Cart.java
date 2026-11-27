package com.example.computerandfishstore.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cart {

	private static final Map<StoreItem, Integer> CART = new HashMap<>();

	public static String getDisplay() {
		String text = "Cart: \n";
		double total = 0;
		for (Entry<StoreItem, Integer> entry : CART.entrySet()) {
			StoreItem part = entry.getKey();
			Integer amount = entry.getValue();
			text += part.getName() + " x" + amount + "\n";
			total += (part.getPrice() * amount);
		}
		text += "\nTotal: $" + String.format("%.2f", total);
		return text;
	}

	public static Set<StoreItem> getItems() {
		return CART.keySet();
	}

	public static int getAmount(StoreItem part) {
		if (!CART.containsKey(part))
			return 0;
		return CART.get(part);
	}

	public static void add(StoreItem part) {
		add(part, 1);
	}

	public static void add(StoreItem part, int amount) {
		CART.put(part, getAmount(part) + amount);
	}

	public static void remove(StoreItem part) {
		remove(part, 1);
	}

	public static void remove(StoreItem part, int amount) {
		int current = getAmount(part);
		current -= amount;
		if (current <= 0) {
			CART.remove(part);
		} else {
			CART.put(part, current);
		}
	}

}
