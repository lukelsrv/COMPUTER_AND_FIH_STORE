package com.example.computerandfishstore.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cart {

	private final Map<StoreItem, Integer> items = new HashMap<>();

	public Cart() {}

	public String getItemsDisplay() {
		String text = "Cart: \n";
		for (Entry<StoreItem, Integer> entry : items.entrySet()) {
			StoreItem item = entry.getKey();
			Integer amount = entry.getValue();
			text += item.getName() + " x" + amount + "\n";
		}
		return text;
	}

	public String getDisplay() {
		String text = "Cart: \n";
		double total = 0;
		for (Entry<StoreItem, Integer> entry : items.entrySet()) {
			StoreItem item = entry.getKey();
			Integer amount = entry.getValue();
			text += item.getName() + " x" + amount + "\n";
			total += (item.getPrice() * amount);
		}
		text += "\nTotal: $" + String.format("%,.2f", total);
		return text;
	}

	public Set<StoreItem> getItems() {
		return items.keySet();
	}

	public int getAmount(StoreItem item) {
		if (!items.containsKey(item))
			return 0;
		return items.get(item);
	}

	public double getTotalCost() {
		double total = 0;
		for (Entry<StoreItem, Integer> entry : items.entrySet()) {
			StoreItem item = entry.getKey();
			Integer amount = entry.getValue();
			total += (item.getPrice() * amount);
		}
		return total;
	}

	public void add(StoreItem item) {
		add(item, 1);
	}

	public void add(StoreItem item, int amount) {
		items.put(item, getAmount(item) + amount);
	}

	public void remove(StoreItem item) {
		remove(item, 1);
	}

	public void remove(StoreItem item, int amount) {
		int current = getAmount(item);
		current -= amount;
		if (current <= 0) {
			items.remove(item);
		} else {
			items.put(item, current);
		}
	}

	public void add(Cart other) {
		for (Entry<StoreItem, Integer> entry : other.items.entrySet()) {
			StoreItem item = entry.getKey();
			Integer amount = entry.getValue();
			add(item, amount);
		}
	}

}
