package org.zorkclone.core.model;

public enum TriggerType {
	PERMANENT("permanent"), SINGLE("single");
	private String type;

	private TriggerType(String type) {
		this.type = type;
	}

	public static TriggerType getByTypeName(String type) {
		TriggerType[] triggerTypes = TriggerType.values();
		for (TriggerType d : triggerTypes) {
			if (type.equals(d.type)) {
				return d;
			}
		}
		return null;
	}
}
