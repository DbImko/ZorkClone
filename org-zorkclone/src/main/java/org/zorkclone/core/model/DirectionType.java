package org.zorkclone.core.model;

public enum DirectionType {
	NORTH("north", "n"), SOUTH("south", "s"), EAST("east", "e"), WEST("west",
			"w");

	private String name;
	private String shortName;

	DirectionType(String name, String shortName) {
		this.name = name;
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public static DirectionType getByName(String name) {
		DirectionType[] directions = DirectionType.values();
		for (DirectionType d : directions) {
			if (d.name.equals(name)) {
				return d;
			}
		}
		return null;
	}

	public static DirectionType getByShortName(String name) {
		DirectionType[] directions = DirectionType.values();
		for (DirectionType d : directions) {
			if (d.shortName.equals(name)) {
				return d;
			}
		}
		return null;
	}
}