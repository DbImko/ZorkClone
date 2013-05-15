package org.zorkclone.core.command;

import java.util.Arrays;
import java.util.List;

import org.zorkclone.core.UserCommand;

public class ExitCommand extends AbstractCommand {

	private static final List<String> commands = Arrays.asList(new String[] {
			"quit", "exit" });

	@Override
	public List<String> getCommands() {
		return commands;
	}

	@Override
	public boolean execute(UserCommand command) {
		return game.exitGame();
	}

}
