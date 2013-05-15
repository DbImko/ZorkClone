package org.zorkclone.core.model;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TurnOnModel {
	private String message;
	private String action;

	public String getMessage() {
		return message;
	}

	@XmlElement(name = "print")
	public void setMessage(String message) {
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	@XmlElement
	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(message).append(action)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TurnOnModel) {
			final TurnOnModel o = (TurnOnModel) obj;
			return new EqualsBuilder().append(message, o.message)
					.append(action, o.action).isEquals();
		} else {
			return false;
		}
	}
}
