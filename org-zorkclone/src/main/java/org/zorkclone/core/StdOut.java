package org.zorkclone.core;

import java.util.Collection;

public class StdOut implements OutStream {

	public void print(Collection<String> messages) {
		print(messages.toArray(new String[] {}));
	}

	public void print(String... messages) {
		for (String s : messages) {
			System.out.println(s);
		}
	}
}
