package org.zorkclone.test.core.commands;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zorkclone.GameExecutor;
import org.zorkclone.base.Game;
import org.zorkclone.core.UserCommand;
import org.zorkclone.core.command.AbstractCommand;
import org.zorkclone.core.command.PutCommand;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class PutCommandTest {
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
		String[] args = { "torch", "chest" };
		EasyMock.expect(gameInstance.putItemToContainer("torch", "chest"))
				.andReturn(true).times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand command = injector.getInstance(PutCommand.class);
		Assert.assertNotNull(command);
		boolean result = command.execute(new UserCommand("put", args));
		Assert.assertTrue(result);
		EasyMock.verify(gameInstance);
	}

	@Test
	public void should_Execute_Command_With_Wrong_Arguments_Correctly() {
		String[] args = { "torch" };
		EasyMock.expect(gameInstance.putItemToContainer("torch", null))
				.andReturn(false).times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand command = injector.getInstance(PutCommand.class);
		Assert.assertNotNull(command);
		Assert.assertTrue(!command.execute(new UserCommand("put", args)));
		EasyMock.verify(gameInstance);
	}

	@Test
	public void should_Execute_Command_With_Null_Argument_Correctly() {
		String[] args = null;
		EasyMock.expect(gameInstance.putItemToContainer(null, null))
		.andReturn(false).times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand command = injector.getInstance(PutCommand.class);
		Assert.assertNotNull(command);
		Assert.assertTrue(!command.execute(new UserCommand("put", args)));
		EasyMock.verify(gameInstance);
	}
}
