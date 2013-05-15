package org.zorkclone.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zorkclone.core.model.ActionModel;
import org.zorkclone.core.model.ActionType;

public class ActionParserHelper {

	private static final String UPDATE_PATTERN = "Update (\\w*) to (\\w*)";
	private static final String DELETE_PATTERN = "Delete (\\w*)";
	private static final String ADD_PATTERN = "Add (\\w*) to (\\w*)";

	public static ActionModel parse(String action) {
		if (action == null || action.isEmpty()) {
			return null;
		}

		Pattern pattern = Pattern.compile(UPDATE_PATTERN,
				Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(action);
		if (m.find()) {
			ActionModel result = new ActionModel(ActionType.UPDATE);
			result.setItem(m.group(1));
			result.setStatus(m.group(2));
			return result;
		}

		pattern = Pattern.compile(DELETE_PATTERN, Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(action);
		if (m.find()) {
			ActionModel result = new ActionModel(ActionType.DELETE);
			result.setItem(m.group(1));
			return result;
		}

		pattern = Pattern.compile(ADD_PATTERN, Pattern.CASE_INSENSITIVE);
		m = pattern.matcher(action);
		if (m.find()) {
			ActionModel result = new ActionModel(ActionType.ADD);
			result.setItem(m.group(1));
			result.setOwner(m.group(2));
			return result;
		}

		return null;
	}
}
