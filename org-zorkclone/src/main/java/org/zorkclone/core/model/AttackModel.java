package org.zorkclone.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class AttackModel {
	private ConditionModel condition;

	private List<String> actions;

	private List<String> message;

	public AttackModel() {
		actions = new ArrayList<String>();
		message = new ArrayList<String>();
	}

	public ConditionModel getCondition() {
		return condition;
	}

	public void setCondition(ConditionModel condition) {
		this.condition = condition;
	}

	public List<String> getActions() {
		return actions;
	}

	@XmlElement(name = "action")
	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	public List<String> getMessage() {
		return message;
	}

	@XmlElement(name = "print")
	public void setMessage(List<String> message) {
		this.message = message;
	}

}
