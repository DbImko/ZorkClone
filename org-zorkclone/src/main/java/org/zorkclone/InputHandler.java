package org.zorkclone;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jline.ArgumentCompletor;
import jline.ConsoleReader;
import jline.SimpleCompletor;

import org.zorkclone.base.Game;
import org.zorkclone.core.OutStream;
import org.zorkclone.core.UserCommand;
import org.zorkclone.core.UserCommandParser;
import org.zorkclone.core.command.AbstractCommand;

import com.google.inject.Inject;

public class InputHandler extends Thread {

	private final Game game;
	private final OutStream outStream;
	private final ConsoleReader reader;

	@Inject
	public InputHandler(Game game, OutStream outStream, ConsoleReader reader) {
		this.game = game;
		this.outStream = outStream;
		this.reader = reader;
	}

	@Override
	public void run() {
		String line = null;
		List<Object> completors = new LinkedList<Object>();
		List<String> allCommands = new ArrayList<>();

		try {
			reader.setBellEnabled(false);
			for (AbstractCommand executor : game.getCommandExecutors()) {
				allCommands.addAll(executor.getCommands());
			}
			completors.add(new SimpleCompletor(allCommands
					.toArray(new String[] {})));
			reader.addCompletor(new ArgumentCompletor(completors));

			while ((line = reader.readLine("\n>")) != null) {
				if (!handleInput(line)) {
					outStream.print(Game.ERROR_MESSAGE);
				}
			}
		} catch (Exception ex) {
			outStream.print(Game.ERROR_MESSAGE + ex.toString());
		} finally {
			System.exit(1);
		}
	}

	protected boolean handleInput(String line) {
		boolean handled = false;
		UserCommand command = UserCommandParser.resolve(line);
		if (command != null) {
			for (AbstractCommand executor : game.getCommandExecutors()) {
				if (executor.getCommands().contains(command.getCommand())) {
					outStream.print();
					handled = game.processCommand(command);
					break;
				}
			}
		}
		return handled;
	}
}