package org.zorkclone.test.core.commands;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zorkclone.base.Game;
import org.zorkclone.core.UserCommand;
import org.zorkclone.core.command.AbstractCommand;
import org.zorkclone.core.command.NavigationCommand;
import org.zorkclone.core.model.DirectionType;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class NavigationCommandTest {

	private Injector injector;
	private Game gameInstance;

	private class Module extends AbstractModule {

		@Override
		protected void configure() {
			gameInstance = EasyMock.createMock(Game.class);

			bind(Game.class).toInstance(gameInstance);
		}
	}

	@Before
	public void before() {
		injector = Guice.createInjector(new Module());
	}

	@Test
	public void should_Execute_Command_Correctly() {
		gameInstance.goInDirection(DirectionType.NORTH);
		EasyMock.expectLastCall().times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand navigationCommand = injector
				.getInstance(NavigationCommand.class);
		Assert.assertNotNull(navigationCommand);
		Assert.assertTrue(navigationCommand.execute(new UserCommand("n",
				new String[] { "" })));
		EasyMock.verify(gameInstance);
	}

	@Test
	public void should_Execute_Command_With_Arguments_Correctly() {
		gameInstance.goInDirection(DirectionType.SOUTH);
		EasyMock.expectLastCall().times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand navigationCommand = injector
				.getInstance(NavigationCommand.class);
		Assert.assertNotNull(navigationCommand);
		Assert.assertTrue(navigationCommand.execute(new UserCommand("s",
				new String[] { "test", "args" })));
		EasyMock.verify(gameInstance);
	}

	@Test
	public void should_Execute_Command_With_Null_Argument_Correctly() {
		gameInstance.goInDirection(DirectionType.WEST);
		EasyMock.expectLastCall().times(1);
		EasyMock.replay(gameInstance);

		AbstractCommand navigationCommand = injector
				.getInstance(NavigationCommand.class);
		Assert.assertNotNull(navigationCommand);
		Assert.assertTrue(navigationCommand.execute(new UserCommand("w", null)));
		EasyMock.verify(gameInstance);
	}
}
