package org.zorkclone.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.zorkclone.core.model.core.ItemContainer;
import org.zorkclone.core.model.jaxb.adapters.RoomTypeAdapter;

/**
 * Represents container for all object (include player), which usually has
 * borders
 * 
 * @author dbimko
 * 
 */
public class RoomModel implements ItemContainer {
	private String name;
	private String description;
	private String status;

	@XmlElement(name = "border")
	private final List<BorderItem> borders;

	@XmlElement(name = "item")
	private final List<String> items;

	@XmlElement(name = "trigger")
	private final List<TriggerModel> triggers;

	@XmlElement(name = "container")
	private final List<String> containers;
	private RoomType type;

	public RoomModel() {
		setType(RoomType.NONE);
		items = new ArrayList<String>();
		triggers = new ArrayList<TriggerModel>();
		containers = new ArrayList<String>();
		borders = new ArrayList<>();
	}

	public List<String> getItems() {
		return items;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	public List<BorderItem> getBorders() {
		return borders;
	}

	public BorderItem getBorder(DirectionType direction) {
		BorderItem result = null;
		for (BorderItem b : borders) {
			if (b.getDirection().equals(direction)) {
				result = b;
				break;
			}
		}
		return result;
	}

	public RoomType getType() {
		return type;
	}

	@XmlElement
	@XmlJavaTypeAdapter(RoomTypeAdapter.class)
	public void setType(RoomType type) {
		this.type = type;
	}

	public String nextRoom(DirectionType direction) {
		BorderItem border = getBorder(direction);
		if (border != null) {
			return border.getName();
		}
		return null;
	}

	public boolean getItem(String name) {
		for (String itemName : items) {
			if (itemName.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}

	public void addTrigger(TriggerModel trigger) {
		triggers.add(trigger);
	}

	public List<TriggerModel> getTriggers() {
		return triggers;
	}

	public List<String> getContainers() {
		return containers;
	}

	public void addItem(String item) {
		items.add(item);
	}

	public void removeItem(String item) {
		items.remove(item);
	}

	public boolean contains(String itemName) {
		if (itemName == null) {
			return false;
		}
		for (String item : items) {
			if (item.equals(itemName)) {
				return true;
			}
		}
		return false;
	}

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
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(type).append(items)
				.append(triggers).append(borders).append(containers)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RoomModel) {
			final RoomModel o = (RoomModel) obj;
			return new EqualsBuilder().append(name, o.name)
					.append(name, o.name).append(type, o.type)
					.append(items, o.items).append(triggers, o.triggers)
					.append(borders, o.borders)
					.append(containers, o.containers).isEquals();
		}
		return false;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean hasTriggers() {
		return triggers.size() > 0;
	}
}
