package org.zorkclone.test.base;

import java.lang.reflect.Field;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zorkclone.GameExecutor;
import org.zorkclone.GameImpl;
import org.zorkclone.InputHandler;
import org.zorkclone.base.AbstractGame;
import org.zorkclone.base.Game;
import org.zorkclone.core.GameFileParserImpl;
import org.zorkclone.core.InventoryContainer;
import org.zorkclone.core.InventoryContainerImpl;
import org.zorkclone.core.OutStream;
import org.zorkclone.core.StdOut;
import org.zorkclone.core.command.NavigationCommand;
import org.zorkclone.core.model.ContainerModel;
import org.zorkclone.core.model.GameInitModel;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class AbstractGame_PutToContainer_Test {
	private Injector injector;
	private Game game;
	private InputHandler parser;
	private StdOut stream;
	private InventoryContainer inventory;

	private class Module extends AbstractModule {

		@Override
		protected void configure() {
			inventory = EasyMock.createMock(InventoryContainer.class);
			parser = EasyMock.createMock(InputHandler.class);
			stream = EasyMock.createMock(StdOut.class);
			bind(Game.class).to(GameImpl.class).in(Singleton.class);

			bind(InventoryContainer.class).toInstance(inventory);

			bind(GameExecutor.class).in(Singleton.class);
			bind(NavigationCommand.class);
			bind(InputHandler.class).toInstance(parser);
			bind(OutStream.class).toInstance(stream);
		}
	}

	@Before
	public void before() {
		injector = Guice.createInjector(new Module());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void should_Put_Item_To_Lock_Correctly() throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);
		stream.print("Item torch added to lock.");
		EasyMock.expectLastCall().times(1);

		EasyMock.expect(inventory.getName())
				.andReturn(InventoryContainerImpl.DEFAULT_NAME).times(1);

		EasyMock.expect(inventory.contains("torch")).andReturn(true).times(1);
		inventory.removeItem("torch");
		EasyMock.expectLastCall().times(1);

		EasyMock.replay(this.inventory);
		EasyMock.replay(this.stream);

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("containers");
		currentRoomField.setAccessible(true);

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/room-without-item-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		List<ContainerModel> containers = (List<ContainerModel>) currentRoomField
				.get(game);
		ContainerModel lockContainer = containers.get(0);

		Assert.assertTrue(!lockContainer.hasItems());
		Assert.assertEquals("lock", lockContainer.getName());

		Assert.assertTrue(game.putItemToContainer("torch", "lock"));

		Assert.assertTrue(lockContainer.hasItems());
		Assert.assertTrue(lockContainer.contains("torch"));

		EasyMock.verify(stream);
		EasyMock.verify(inventory);
	}

	@Test
	public void should_Execute_Put_Item_To_Container_Correctly()
			throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);

		EasyMock.expect(inventory.getName())
				.andReturn(InventoryContainerImpl.DEFAULT_NAME).times(1);

		EasyMock.expect(inventory.contains(null)).andReturn(false).times(1);

		EasyMock.replay(this.inventory);
		EasyMock.replay(this.stream);

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("containers");
		currentRoomField.setAccessible(true);

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/room-without-item-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		Assert.assertTrue(!game.putItemToContainer(null, null));

		EasyMock.verify(stream);
		EasyMock.verify(inventory);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void should_Put_Item_To_Container_With_Constraints_Correctly()
			throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);
		stream.print("Item torch added to lock.");
		EasyMock.expectLastCall().times(1);

		EasyMock.expect(inventory.getName())
				.andReturn(InventoryContainerImpl.DEFAULT_NAME).times(1);

		EasyMock.expect(inventory.contains("torch")).andReturn(true).times(1);
		inventory.removeItem("torch");
		EasyMock.expectLastCall().times(1);

		EasyMock.replay(this.inventory);
		EasyMock.replay(this.stream);

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("containers");
		currentRoomField.setAccessible(true);

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/room-without-item-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		List<ContainerModel> containers = (List<ContainerModel>) currentRoomField
				.get(game);
		ContainerModel lockContainer = containers.get(0);

		Assert.assertTrue(!lockContainer.hasItems());
		Assert.assertEquals("lock", lockContainer.getName());

		Assert.assertTrue(game.putItemToContainer("torch", "lock"));

		Assert.assertTrue(lockContainer.hasItems());
		Assert.assertTrue(lockContainer.contains("torch"));

		EasyMock.verify(stream);
		EasyMock.verify(inventory);
	}
	
}
