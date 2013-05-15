package org.zorkclone.base;

import java.util.List;

import org.zorkclone.core.UserCommand;
import org.zorkclone.core.command.AbstractCommand;
import org.zorkclone.core.model.DirectionType;
import org.zorkclone.core.model.GameInitModel;

public interface Game {

	public static final int SUCCESS_EXIT_STATUS = 0;
	public static final int ERROR_EXIT_STATUS = 1;
	public static final int DEFAULT_CURRENT_ROOM_INDEX = 0;
	public static final String INVENTORY_EMPTY_MESSAGE = "Your inventory is empty";
	public static final String INVENTORY_CONTAINS_FORMAT = "Your inventory: %s";
	public static final String NO_DIRECTION_MESSAGE = "You can't go that way!";
	public static final String NEW_LINE = "\n";
	public static final String ERROR_MESSAGE = "Error";
	static final String INVENTORY_ADD_ITEM_FORMAT = "The %s was added to your inventory";
	static final String NOTHING_TO_TAKE_MESSAGE = "There's nothing to take with that name!";
	static final String CONTAINS_MESSAGE_FORMAT = "%s contains %s.";
	static final String EMPTY_MESSAGE_FORMAT = "% is empty.";
	static final String NOTHING_MESSAGE = "Nothing written.";
	static final String PUT_MESSAGE_FORMAT = "Item %s added to %s.";
	static final String ACTIVATE_FORMAT = "You activate the %s.";
	static final String ATTACK_MESSAGE_FORMAT = "You assault the %s with the %s.";
	static final String BYE_WORD = "Bye";

	void init(GameInitModel gameInitModel);

	void play();

	List<AbstractCommand> getCommandExecutors();

	boolean processCommand(UserCommand userCommand);

	String getGameFile();

	/**
	 * Moving player in direction or print error message
	 * 
	 * @param direction
	 *            Direction of movement
	 */
	void goInDirection(DirectionType direction);

	/**
	 * Prints all inventory items on screen
	 */
	void printInventory();

	/**
	 * Changes ownership of item from room or container to player and prints
	 * message
	 * 
	 * @param string
	 *            Item name
	 * @return Returns <tt>true</tt> if room contains specific element
	 */
	boolean addToInventory(String string);

	/**
	 * Prints on screen contents of container
	 * 
	 * @param containerName
	 *            Name of container
	 * @return Returns <tt>true</tt> if room contains specific container
	 */
	boolean openContainer(String containerName);

	/**
	 * Prints on screen writings on item
	 * 
	 * @param itemName
	 *            Item name
	 * @return Returns <tt>true</tt> if inventory contains item
	 */
	boolean readItem(String itemName);

	/**
	 * Puts item in specific container in room
	 * 
	 * @param itemStr
	 * @param containerName
	 * @return Returns <tt>true</tt> if inventory contains item and specific
	 *         container in current room
	 */
	boolean putItemToContainer(String itemName, String containerName);

	/**
	 * Activates item and execute "turnon" commands
	 * 
	 * @param itemName
	 *            Item name
	 * @return Returns <tt>true</tt> if inventory contains item
	 */
	boolean turnOnItem(String itemName);

	/**
	 * Attack creature with specific item
	 * 
	 * @param creatureName
	 *            Creature name
	 * @param itemName
	 *            Item name
	 * @return Returns <tt>true</tt> if inventory contains item and room
	 *         contains creature
	 */
	boolean attackCreatureWithItem(String creatureName, String itemName);

	boolean exitGame();
}
