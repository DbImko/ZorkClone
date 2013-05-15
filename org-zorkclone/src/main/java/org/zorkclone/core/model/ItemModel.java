package org.zorkclone.core.model;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.zorkclone.core.model.core.HasStatus;

/**
 * Item
 * 
 * @author Alexander Katanov
 * 
 */
public class ItemModel implements HasStatus {
	private String name;
	private String message;
	private String status;
	private TurnOnModel turnOnModel;

	public boolean keywordMatch(String itemName) {
		return name.equalsIgnoreCase(itemName);
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	@XmlElement(name = "writing")
	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TurnOnModel getTurnOnModel() {
		return turnOnModel;
	}

	@XmlElement(name = "turnon")
	public void setTurnOnModel(TurnOnModel turnOnModel) {
		this.turnOnModel = turnOnModel;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(message)
				.append(status).append(turnOnModel).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemModel) {
			final ItemModel o = (ItemModel) obj;
			return new EqualsBuilder().append(name, o.name)
					.append(status, o.status)
					.append(turnOnModel, o.turnOnModel)
					.append(message, o.message).isEquals();
		}
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + " " + name;
	}

}
