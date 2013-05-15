package org.zorkclone.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public final class UserCommandParser {

	private static final List<String> IGNORED_WORDS = Arrays
			.asList(new String[] { "at", "the", "a", "an", "in" });

	/**
	 * Parses user's string
	 * 
	 * @param line
	 *            Input user string
	 * @return Model with parameters
	 * 
	 * @see UserCommand
	 */
	public static UserCommand resolve(String line) {
		UserCommand result = null;
		if (line != null) {
			StringTokenizer tokinazer = new StringTokenizer(line);
			if (tokinazer.hasMoreTokens()) {
				String cmd = tokinazer.nextToken();
				List<String> params = new ArrayList<>();
				while (tokinazer.hasMoreTokens()) {
					String token = tokinazer.nextToken();
					if (!isIgnoredWord(token)) {
						params.add(token);
					}
				}
				result = new UserCommand(cmd, params.toArray(new String[] {}),
						true);
			}
		}
		return result;
	}

	private static boolean isIgnoredWord(String word) {
		if (word != null && !word.isEmpty()) {
			return IGNORED_WORDS.contains(word.toLowerCase());
		}
		return false;
	}
}
