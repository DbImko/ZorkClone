package org.zorkclone.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.zorkclone.core.model.jaxb.adapters.TriggerTypeAdapter;

public class TriggerModel {

	private TriggerType type;

	@XmlElement(name = "print")
	private final List<String> messages;

	private ConditionModel condition;

	private String command;

	private String action;

	public TriggerModel() {
		messages = new ArrayList<>();
	}

	public TriggerModel(List<String> messages) {
		this.messages = messages;
	}

	public ConditionModel getCondition() {
		return condition;
	}

	public void setCondition(ConditionModel condition) {
		this.condition = condition;
	}

	public TriggerType getType() {
		return type;
	}

	@XmlJavaTypeAdapter(TriggerTypeAdapter.class)
	public void setType(TriggerType type) {
		this.type = type;
	}

	public List<String> getMessages() {
		return messages;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder().append(type)
				.append(condition).append(command).append(action);
		for (String message : messages) {
			hashCodeBuilder.append(message);
		}
		return hashCodeBuilder.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TriggerModel) {
			final TriggerModel other = (TriggerModel) obj;
			EqualsBuilder equalsBuilder = new EqualsBuilder()
					.append(type, other.type).append(action, other.action)
					.append(messages.toArray(), other.messages.toArray())
					.append(condition, other.condition)
					.append(command, other.command);
			return equalsBuilder.isEquals();
		} else {
			return false;
		}
	}

}
