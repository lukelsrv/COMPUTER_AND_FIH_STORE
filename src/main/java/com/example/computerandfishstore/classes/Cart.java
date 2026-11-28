package com.example.computerandfishstore.classes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Helper class for storing items selected and amount.
 */
public class Cart {

	private final Map<StoreItem, Integer> items = new HashMap<>();

	/**
	 * Constructs a new Cart.
	 */
	public Cart() {}

	/**
	 * @return A string of all items with their amounts listed on separate lines.
	 */
	public String getItemsDisplay() {
		String text = "Cart: \n";
		for (Entry<StoreItem, Integer> entry : items.entrySet()) {
			StoreItem item = entry.getKey();
			Integer amount = entry.getValue();
			text += item.getName() + " x" + amount + "\n";
		}
		return text;
	}

	/**
	 * @return A string of all items with their amounts and price listed on separate lines.
	 */
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

	/**
	 * @return All items in the Cart.
	 */
	public Set<StoreItem> getItems() {
		return items.keySet();
	}

	/**
	 * Gets how many of the provided item is in the cart.
	 * @param item The item to get the amount for.
	 * @return The amount of the item in the cart.
	 */
	public int getAmount(StoreItem item) {
		if (!items.containsKey(item))
			return 0;
		return items.get(item);
	}

	/**
	 * @return The total cost of all items multiplied by their amounts.
	 */
	public double getTotalCost() {
		double total = 0;
		for (Entry<StoreItem, Integer> entry : items.entrySet()) {
			StoreItem item = entry.getKey();
			Integer amount = entry.getValue();
			total += (item.getPrice() * amount);
		}
		return total;
	}

	/**
	 * Adds the provided item into the cart.
	 * @param item The item to add.
	 */
	public void add(StoreItem item) {
		add(item, 1);
	}

	/**
	 * Adds the provided amount of the provided item into the cart.
	 * @param item The item to add.
	 * @param amount The amount to add by.
	 */
	public void add(StoreItem item, int amount) {
		items.put(item, getAmount(item) + amount);
	}

	/**
	 * Removes the provided item from the cart by 1.
	 * @param item The item to remove.
	 */
	public void remove(StoreItem item) {
		remove(item, 1);
	}

	/**
	 * Removes the provided amount of the provided item from the cart.
	 * @param item The item to remove.
	 * @param amount The amount to remove by.
	 */
	public void remove(StoreItem item, int amount) {
		int current = getAmount(item);
		current -= amount;
		if (current <= 0) {
			items.remove(item);
		} else {
			items.put(item, current);
		}
	}

	/**
	 * Removes the provided item entirely from the cart.
	 * @param item The item to remove.
	 */
	public void removeAll(StoreItem item) {
		items.remove(item);
	}

	/**
	 * Adds the items from another cart into this cart.
	 * @param other The other cart to add to this cart.
	 */
	public void add(Cart other) {
		for (Entry<StoreItem, Integer> entry : other.items.entrySet()) {
			StoreItem item = entry.getKey();
			Integer amount = entry.getValue();
			add(item, amount);
		}
	}

	public void clearCart(){
		items.clear();
	}
}
