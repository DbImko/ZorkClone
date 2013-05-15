package org.zorkclone.core;

import java.util.Collection;

public interface OutStream {

	public void print(Collection<String> messages);

	public void print(String... messages);

}
