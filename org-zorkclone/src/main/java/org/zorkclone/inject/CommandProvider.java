package org.zorkclone.inject;

import org.apache.commons.lang.NotImplementedException;
import org.zorkclone.core.command.AbstractCommand;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * Inject provider for commands as factory
 * 
 * @author dbimko
 * 
 */
public class CommandProvider implements Provider<AbstractCommand> {

	@Inject
	private Injector injector;

	public AbstractCommand get() {
		throw new NotImplementedException();
	}

	public AbstractCommand get(Class<? extends AbstractCommand> cls) {
		return injector.getInstance(cls);
	}

}
