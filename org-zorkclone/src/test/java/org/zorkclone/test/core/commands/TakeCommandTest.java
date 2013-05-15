package org.zorkclone.test.core.commands;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zorkclone.GameExecutor;
import org.zorkclone.base.Game;
import org.zorkclone.core.UserCommand;
import org.zorkclone.core.command.AbstractCommand;
import org.zorkclone.core.command.TakeCommand;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class TakeCommandTest {
	private Injector injector;
	private Game gameInstance;

	private class Module extends AbstractModule {

		@Override
		protected void configure() {
			gameInstance = EasyMock.createMock(Game.class);
			bind(GameExecutor.class).in(Singleton.class);
			bind(Game.class).toInstance(gameInstance);
		}
	}

	@Before
	public void before() {
		injector = Guice.createInjector(new Module());
	}

	@Test
	public void should_Execute_Command_Correctly() {
		String[] args = { "torch" };
		EasyMock.expect(
				gameInstance.addToInventory(args[TakeCommand.ITEM_INDEX]))
				.andReturn(true).times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand command = injector.getInstance(TakeCommand.class);
		Assert.assertNotNull(command);
		boolean result = command.execute(new UserCommand("take", args));
		Assert.assertTrue(result);
		EasyMock.verify(gameInstance);
	}

	@Test
	public void should_Execute_Command_With_Wrong_Arguments_Correctly() {
		String[] args = { " test args" };
		EasyMock.expect(
				gameInstance.addToInventory(args[TakeCommand.ITEM_INDEX]))
				.andReturn(false).times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand command = injector.getInstance(TakeCommand.class);
		Assert.assertNotNull(command);
		Assert.assertTrue(!command.execute(new UserCommand("take", args)));
		EasyMock.verify(gameInstance);
	}

	@Test
	public void should_Execute_Command_With_Null_Argument_Correctly() {
		String[] args = null;
		EasyMock.expect(gameInstance.addToInventory(null)).andReturn(false)
				.times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand command = injector.getInstance(TakeCommand.class);
		Assert.assertNotNull(command);
		Assert.assertTrue(!command.execute(new UserCommand("take", args)));
		EasyMock.verify(gameInstance);
	}
}
