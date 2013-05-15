package org.zorkclone.inject;

import org.zorkclone.GameExecutor;
import org.zorkclone.GameImpl;
import org.zorkclone.InputHandler;
import org.zorkclone.base.Game;
import org.zorkclone.core.GameFileParserImpl;
import org.zorkclone.core.InventoryContainer;
import org.zorkclone.core.InventoryContainerImpl;
import org.zorkclone.core.OutStream;
import org.zorkclone.core.StdOut;
import org.zorkclone.core.command.NavigationCommand;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class MainModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GameFileParserImpl.class);
		bind(Game.class).to(GameImpl.class).in(Singleton.class);
		bind(InventoryContainer.class).to(InventoryContainerImpl.class).in(
				Singleton.class);
		bind(GameExecutor.class).in(Singleton.class);
		bind(NavigationCommand.class);
		bind(InputHandler.class).in(Singleton.class);

		bind(OutStream.class).to(StdOut.class);
	}
}
