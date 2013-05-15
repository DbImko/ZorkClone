package org.zorkclone.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.zorkclone.core.model.core.ItemContainer;

/**
 * Represents container for items, which may has own triggers
 * 
 * @author dbimko
 * 
 */
public class ContainerModel implements ItemContainer {

	private String name;

	private String status;

	/**
	 * Item which is needed
	 */
	private String accept;

	@XmlElement(name="item")
	private final List<String> items;

	private TriggerModel trigger;

	public ContainerModel() {
		items = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TriggerModel getTrigger() {
		return trigger;
	}

	public void setTrigger(TriggerModel trigger) {
		this.trigger = trigger;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public List<String> getItems() {
		return items;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(status).append(accept)
				.append(items).append(trigger).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ContainerModel) {
			final ContainerModel o = (ContainerModel) obj;
			return new EqualsBuilder().append(name, o.name)
					.append(accept, o.accept).append(items, o.items)
					.append(trigger, o.trigger).append(status, o.status)
					.isEquals();
		} else {
			return false;
		}
	}

	public void removeItem(String itemName) {
		items.remove(itemName);
	}

	public void addItem(String itemName) {
		items.add(itemName);
	}

	public boolean contains(String itemName) {
		return items.contains(itemName);
	}

	@Override
	public String toString() {
		return super.toString() + " " + name;
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
	public boolean hasTriggers() {
		return trigger != null;
	}
}
