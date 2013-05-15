package org.zorkclone.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.zorkclone.core.model.core.ItemContainer;

@XmlRootElement(name = "map")
public class GameInitModel {

	@XmlElement(name = "room")
	private final List<RoomModel> rooms;

	@XmlElement(name = "item")
	private final List<ItemModel> items;

	@XmlElement(name = "container")
	private final List<ContainerModel> objects;

	@XmlElement(name = "creature")
	private final List<CreatureModel> creatures;

	public GameInitModel() {
		rooms = new ArrayList<>();
		items = new ArrayList<>();
		objects = new ArrayList<>();
		creatures = new ArrayList<>();
	}

	public List<RoomModel> getRooms() {
		return rooms;
	}

	public List<ItemModel> getItems() {
		return items;
	}

	public List<ContainerModel> getObjects() {
		return objects;
	}

	public List<CreatureModel> getCreatures() {
		return creatures;
	}

	public Map<String, ItemContainer> getItemContainers() {
		Map<String, ItemContainer> result = new HashMap<String, ItemContainer>();
		for (RoomModel obj : rooms) {
			result.put(obj.getName(), obj);
		}
		for (ContainerModel obj : objects) {
			result.put(obj.getName(), obj);
		}
		return result;
	}
}
