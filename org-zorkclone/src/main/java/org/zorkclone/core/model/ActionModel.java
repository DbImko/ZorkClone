package org.zorkclone.core.model;

public class ActionModel {
	private String item;
	private String status;
	private String owner;
	private final ActionType actionType;

	public ActionModel(ActionType type) {
		this.actionType = type;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public ActionType getActionType() {
		return actionType;
	}

}
