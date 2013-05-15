package org.zorkclone.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zorkclone.InputHandler;
import org.zorkclone.core.ActionParserHelper;
import org.zorkclone.core.InventoryContainer;
import org.zorkclone.core.OutStream;
import org.zorkclone.core.UserCommand;
import org.zorkclone.core.command.AbstractCommand;
import org.zorkclone.core.command.AttackCommand;
import org.zorkclone.core.command.ExitCommand;
import org.zorkclone.core.command.InventoryCommand;
import org.zorkclone.core.command.NavigationCommand;
import org.zorkclone.core.command.OpenCommand;
import org.zorkclone.core.command.PutCommand;
import org.zorkclone.core.command.ReadCommand;
import org.zorkclone.core.command.TakeCommand;
import org.zorkclone.core.command.TurnOnCommand;
import org.zorkclone.core.model.ActionModel;
import org.zorkclone.core.model.ActionType;
import org.zorkclone.core.model.AttackModel;
import org.zorkclone.core.model.ConditionModel;
import org.zorkclone.core.model.ContainerModel;
import org.zorkclone.core.model.CreatureModel;
import org.zorkclone.core.model.DirectionType;
import org.zorkclone.core.model.GameInitModel;
import org.zorkclone.core.model.ItemModel;
import org.zorkclone.core.model.RoomModel;
import org.zorkclone.core.model.TriggerModel;
import org.zorkclone.core.model.TriggerType;
import org.zorkclone.core.model.TurnOnModel;
import org.zorkclone.core.model.core.HasStatus;
import org.zorkclone.core.model.core.ItemContainer;
import org.zorkclone.inject.CommandProvider;

public abstract class AbstractGame implements Game {

	private List<RoomModel> rooms;
	private List<ItemModel> items;
	private List<ContainerModel> containers;
	private List<CreatureModel> creatures;
	private final List<TriggerModel> triggers;
	private final List<AbstractCommand> executors;
	private RoomModel currentRoom;
	private final Map<String, ItemContainer> itemContainers;

	private final InventoryContainer inventory;

	private final InputHandler inputHandler;
	private final CommandProvider commandProvider;
	private final OutStream outStream;

	public AbstractGame(InputHandler inputHandler,
			CommandProvider commandProvider, InventoryContainer inventory,
			OutStream outStream) {
		rooms = new ArrayList<>();
		executors = new ArrayList<>();
		triggers = new ArrayList<>();
		itemContainers = new HashMap<>();
		this.inventory = inventory;
		this.inputHandler = inputHandler;
		this.commandProvider = commandProvider;
		this.outStream = outStream;
	}

	public void init(GameInitModel gameInitModel) {
		addCommands();

		rooms = gameInitModel.getRooms();
		items = gameInitModel.getItems();
		containers = gameInitModel.getObjects();
		creatures = gameInitModel.getCreatures();

		currentRoom = rooms.get(DEFAULT_CURRENT_ROOM_INDEX);
		initTriggers(gameInitModel);

		itemContainers.put(inventory.getName(), inventory);
		itemContainers.putAll(gameInitModel.getItemContainers());
	}

	public void play() {
		printCurrentRoom();
		inputHandler.start();
	}

	private void addCommands() {
		executors.add(commandProvider.get(NavigationCommand.class));
		executors.add(commandProvider.get(InventoryCommand.class));
		executors.add(commandProvider.get(TakeCommand.class));
		executors.add(commandProvider.get(ReadCommand.class));
		executors.add(commandProvider.get(TurnOnCommand.class));
		executors.add(commandProvider.get(AttackCommand.class));
		executors.add(commandProvider.get(OpenCommand.class));
		executors.add(commandProvider.get(PutCommand.class));
		executors.add(commandProvider.get(ExitCommand.class));
	}

	private void initTriggers(GameInitModel model) {
		for (CreatureModel creatureModel : model.getCreatures()) {
			triggers.add(creatureModel.getTrigger());
		}

		for (RoomModel room : model.getRooms()) {
			for (TriggerModel trigger : room.getTriggers()) {
				if (trigger != null && !triggers.contains(trigger)) {
					triggers.add(trigger);
				}
			}
		}
	}

	public boolean processCommand(UserCommand userCommand) {
		List<TriggerModel> triggers = currentRoom.getTriggers();
		for (TriggerModel trigger : triggers) {
			if (trigger.getCommand().equals(userCommand.getCommand())) {
				if (processTrigger(trigger)) {
					return true;
				}
			}
		}

		for (AbstractCommand executor : executors) {
			if (executor.getCommands().contains(userCommand.getCommand())) {
				return executor.execute(userCommand);
			}
		}

		print(ERROR_MESSAGE);
		return false;
	}

	protected boolean processTrigger(TriggerModel trigger) {
		ConditionModel condition = trigger.getCondition();
		String objectName = condition.getObject();
		String ownerName = condition.getOwner();
		if (ownerName != null) {
			ItemContainer owner = getItemContainerByName(ownerName);
			if (owner == null) {
				throw new RuntimeException("owner cannot be null (" + ownerName
						+ ")");
			}
			boolean has = owner.contains(objectName);
			if (has == condition.isHas()) {
				executeActionIfExists(trigger.getAction());
				print(trigger.getMessages());
				return true;
			}
		} else {
			ItemContainer obj = getItemContainerByName(objectName);
			if (obj != null) {
				if (obj.getStatus().equals(condition.getStatus())) {
					print(trigger.getMessages());
					return true;
				}
			}
		}
		return false;
	}

	private void checkItemTriggers(ItemModel item) {
		for (int i = 0; i < triggers.size(); i++) {
			TriggerModel trigger = triggers.get(i);
			if (isConditionHappened(trigger.getCondition(), item)) {
				if (trigger.getType().equals(TriggerType.SINGLE)) {
					triggers.remove(i);
				}
				print(trigger.getMessages());
			}
		}
	}

	private boolean isConditionHappened(ConditionModel condition, ItemModel item) {
		if (condition.getObject().equals(item.getName())
				&& condition.getStatus().equals(item.getStatus())) {
			return true;
		}
		return false;
	}

	private RoomModel getRoomByName(String name) {
		for (RoomModel r : rooms) {
			if (r.getName().equals(name)) {
				return r;
			}
		}
		return null;
	}

	private boolean executeActionIfExists(String actionString) {
		ActionModel actionModel = ActionParserHelper.parse(actionString);
		if (actionModel == null) {
			return false;
		}
		ActionType type = actionModel.getActionType();
		switch (type) {
		case ADD:
			ItemModel item = getItem(actionModel.getItem());
			if (item != null) {
				String ownerName = actionModel.getOwner();
				ItemContainer owner = getItemContainerByName(ownerName);
				if (owner != null) {
					owner.addItem(item.getName());
				} else {
					throw new RuntimeException("owner cannot be null (" + owner
							+ ")");
				}
			}
			break;
		case DELETE:
			String objName = actionModel.getItem();
			CreatureModel creature = getObjectByName(objName);
			if (creature != null) {
				creature.setExists(false);
			} else {
				throw new RuntimeException("object cannot be null (" + objName
						+ ")");
			}
			break;
		case UPDATE:
			HasStatus owner = getOwnerByName(actionModel.getItem());
			if (owner != null) {
				owner.setStatus(actionModel.getStatus());
			}
			break;
		default:
			return false;
		}
		return true;
	}

	private CreatureModel getObjectByName(String name) {
		return getCreatureByName(name);
	}

	private HasStatus getOwnerByName(String name) {
		for (ContainerModel obj : containers) {
			if (obj.getName().equals(name)) {
				return obj;
			}
		}
		return getInventoryItem(name);
	}

	private ItemContainer getItemContainerByName(String name) {
		return itemContainers.get(name);
	}

	public void printInventory() {
		if (inventory.hasItems()) {
			print(String.format(INVENTORY_CONTAINS_FORMAT,
					inventory.itemsToString()));
		} else {
			print(INVENTORY_EMPTY_MESSAGE);
		}
	}

	public void goInDirection(DirectionType direction) {
		String nextRoomName = currentRoom.nextRoom(direction);
		RoomModel nextRoom = getRoomByName(nextRoomName);
		if (nextRoom == null) {
			print(NO_DIRECTION_MESSAGE);
		} else {
			currentRoom = nextRoom;
			printCurrentRoom();
		}
	}

	private ItemModel getItem(String itemName) {
		for (ItemModel item : items) {
			if (item.keywordMatch(itemName)) {
				return item;
			}
		}
		return null;
	}

	private ItemModel getItemInCurrentRoom(String itemName) {
		if (isCurrentRoomContainsItem(itemName)) {
			return getItem(itemName);
		}
		return null;
	}

	private CreatureModel getCreatureByName(String creatureName) {
		for (CreatureModel creatureModel : creatures) {
			if (creatureModel.isExists()
					&& creatureModel.getName().equals(creatureName)) {
				return creatureModel;
			}
		}
		return null;
	}

	private ContainerModel getContainerByName(String name) {
		for (ContainerModel model : containers) {
			if (model.getName().equals(name)) {
				return model;
			}
		}
		return null;
	}

	public boolean exitGame() {
		try {
			inputHandler.checkAccess();
			inputHandler.interrupt();
			if (inputHandler.isInterrupted()) {
				print(BYE_WORD);
				System.exit(SUCCESS_EXIT_STATUS);
			} else {
				System.exit(ERROR_EXIT_STATUS);	
			}
		} catch (SecurityException e) {
			System.exit(ERROR_EXIT_STATUS);
		}
		return true;
	}

	public boolean addToInventory(String itemName) {
		ItemModel item = getItemInCurrentRoom(itemName);
		if (item != null) {

			if (!inventory.contains(item.getName())) {
				inventory.addItem(item.getName());
			}

			currentRoom.removeItem(item.getName());

			List<ContainerModel> containers = getContainersInCurrentRoom();
			for (ContainerModel container : containers) {
				if (container.contains(item.getName())) {
					container.removeItem(item.getName());
				}
			}
			print(String.format(INVENTORY_ADD_ITEM_FORMAT, item.getName()));
			return true;
		}
		return false;
	}

	public boolean attackCreatureWithItem(String creatureName, String itemName) {
		CreatureModel creature = getCreatureByName(creatureName);
		ItemModel item = getItem(itemName);
		if (creature != null && item != null) {
			if (creature.getVulnerability().equals(item.getName())) {
				AttackModel attackModel = creature.getAttack();
				if (attackModel != null) {
					if (isConditionHappened(attackModel.getCondition(), item)) {
						List<String> actions = attackModel.getActions();
						for (String action : actions) {
							executeActionIfExists(action);
						}

						print(String.format(ATTACK_MESSAGE_FORMAT,
								creature.getName(), item.getName()));
						print(attackModel.getMessage());
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean turnOnItem(String itemName) {
		ItemModel item = getInventoryItem(itemName);
		if (item != null) {
			TurnOnModel turnOnModel = item.getTurnOnModel();
			if (turnOnModel != null) {
				print(String.format(ACTIVATE_FORMAT, item.getName()));
				print(turnOnModel.getMessage());

				if (executeActionIfExists(item.getTurnOnModel().getAction())) {
					checkItemTriggers(item);
				} else {
					print(ERROR_MESSAGE);
				}
				return true;
			}
		}
		return false;
	}

	public boolean putItemToContainer(String itemName, String containerName) {
		ContainerModel container = getContainerInCurrentRoomByName(containerName);
		ItemModel item = getInventoryItem(itemName);
		if (container != null && item != null) {
			if (container.getAccept() != null) {
				if (container.getAccept().equals(item.getName())) {
					proccessPuttingItem(container, item);
					return true;
				}
			} else {
				proccessPuttingItem(container, item);
				return true;
			}
		}
		return false;
	}

	private void proccessPuttingItem(ContainerModel container, ItemModel item) {
		print(String.format(PUT_MESSAGE_FORMAT, item.getName(),
				container.getName()));
		container.addItem(item.getName());
		inventory.removeItem(item.getName());
		if (container.hasTriggers()) {
			processTrigger(container.getTrigger());
		}
	}

	public boolean readItem(String itemName) {
		ItemModel item = getInventoryItem(itemName);
		if (item != null) {
			String message = item.getMessage();
			if (message != null && !message.isEmpty()) {
				print(message);
			} else {
				print(NOTHING_MESSAGE);
			}
			return true;
		}
		return false;
	}

	public boolean openContainer(String containerName) {
		ContainerModel container = getContainerInCurrentRoomByName(containerName);
		if (container != null) {
			if (container.hasItems()) {
				print(String.format(CONTAINS_MESSAGE_FORMAT,
						container.getName(), container.itemsToString()));

			} else {
				print(String.format(EMPTY_MESSAGE_FORMAT, containerName));
			}
			return true;
		}
		return false;
	}

	private ContainerModel getContainerInCurrentRoomByName(String name) {
		List<String> containers = currentRoom.getContainers();
		for (String containerName : containers) {
			if (containerName.equals(name)) {
				return getContainerByName(containerName);
			}
		}
		return null;
	}

	private List<ContainerModel> getContainersInCurrentRoom() {
		List<ContainerModel> result = new ArrayList<ContainerModel>();
		List<String> containers = currentRoom.getContainers();
		for (String containerName : containers) {
			result.add(getContainerByName(containerName));
		}
		return result;
	}

	private ItemModel getInventoryItem(String itemName) {
		if (inventory.contains(itemName)) {
			return getItem(itemName);
		}
		return null;
	}

	public abstract String getGameFile();

	public List<AbstractCommand> getCommandExecutors() {
		return executors;
	}

	private void printCurrentRoom() {
		print(currentRoom.getDescription());
	}

	private boolean isCurrentRoomContainsItem(String itemName) {
		boolean inRoom = currentRoom.getItem(itemName);
		boolean inContainers = false;
		List<String> containers = currentRoom.getContainers();
		for (String containerName : containers) {
			ContainerModel container = getContainerByName(containerName);
			if (container != null && container.contains(itemName)) {
				inContainers = true;
				break;
			}
		}
		return inRoom || inContainers;
	}

	private void print(Collection<String> messages) {
		outStream.print(messages.toArray(new String[] {}));
	}

	protected void print(String... messages) {
		outStream.print(messages);
	}

}
