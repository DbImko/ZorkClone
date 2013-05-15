package org.zorkclone.core.model;

public enum RoomType {
	NONE("none"), EXIT("exit");

	private String name;

	private RoomType(String name) {
		this.name = name;
	}

	public static RoomType getByName(String name) {
		RoomType[] roomTypes = RoomType.values();
		for (RoomType d : roomTypes) {
			if (name.equals(d.name)) {
				return d;
			}
		}
		return null;
	}
}
