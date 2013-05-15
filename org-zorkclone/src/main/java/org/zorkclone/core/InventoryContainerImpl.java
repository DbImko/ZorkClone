package org.zorkclone.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zorkclone.core.model.core.ItemContainer;

public class InventoryContainerImpl implements ItemContainer,
		InventoryContainer, Iterable<String> {
	public static final String DEFAULT_NAME = "inventory";
	private final String name = DEFAULT_NAME;

	private final List<String> items;

	public InventoryContainerImpl() {
		items = new ArrayList<String>();
	}

	@Override
	public void addItem(String itemName) {
		items.add(itemName);
	}

	@Override
	public void removeItem(String itemName) {
		items.remove(itemName);
	}

	@Override
	public boolean contains(String itemName) {
		return items.contains(itemName);
	}

	@Override
	public Iterator<String> iterator() {
		return items.iterator();
	}

	@Override
	public String getName() {
		return name;
	}

	// public void setName(String name) {
	// this.name = name;
	// }

	@Override
	public boolean hasItems() {
		return items.size() > 0;
	}

	@Override
	public String itemsToString() {
		StringBuilder sb = new StringBuilder();
		for (String item : items) {
			sb.append(item);
			sb.append(", ");
		}
		sb.setLength(sb.length() - 2);
		return sb.toString();
	}

	@Override
	public String getStatus() {
		return null;
	}

	@Override
	public void setStatus(String status) {

	}

	@Override
	public boolean hasTriggers() {
		return false;
	}

}
