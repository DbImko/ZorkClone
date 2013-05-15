package org.zorkclone.core.command;

import java.util.Arrays;
import java.util.List;

import org.zorkclone.core.UserCommand;

/**
 * Command prints writing on object if any available, else prints
 * "Nothing written."
 * 
 * @author dbimko
 * 
 */
public final class ReadCommand extends AbstractCommand {

	public static final int ITEM_INDEX = 0;

	private static final List<String> commands = Arrays
			.asList(new String[] { "read" });

	@Override
	public List<String> getCommands() {
		return commands;
	}

	@Override
	public boolean execute(UserCommand command) {
		return game.readItem(command.getArg(ITEM_INDEX));
	}

}
