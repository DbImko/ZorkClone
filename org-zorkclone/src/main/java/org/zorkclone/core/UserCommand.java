package org.zorkclone.core;

public class UserCommand {
	private final String command;
	private final String[] args;

	// private final String argsString;

	public UserCommand(String command, String[] args, boolean t) {
		this.command = command;
		if (args != null) {
			this.args = args;
		} else {
			this.args = new String[] {};
		}
		// argsString = null;
	}

	public UserCommand(String command, String[] args) {
		this.command = command;
		if (args != null) {
			this.args = args;
		} else {
			this.args = new String[] {};
		}
		// argsString = null;
	}

	// public UserCommand(String command, String args) {
	// this.command = command;
	// argsString = args;
	// if (args != null && !args.isEmpty()) {
	// this.args = args.split(" ");
	// } else {
	// this.args = new String[] {};
	// }
	// }

	public String getCommand() {
		return command;
	}

	public String getArg(int index) {
		if (index <= args.length - 1) {
			return args[index];
		} else {
			return null;
		}
	}

	public int argCount() {
		return args.length;
	}

	// public String getArgsString() {
	// return argsString;
	// }

	@Override
	public String toString() {
		StringBuilder argsStr = new StringBuilder(command);
		if (args.length > 0) {
			argsStr.append(", [");
			for (String s : args) {
				argsStr.append(s);
				argsStr.append(" ");
			}
			argsStr.setLength(argsStr.length() - 1);
			argsStr.append("]");
		}
		return argsStr.toString();
	}
}
