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

public class AbstractGame_AddToInventory_Test {
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

	@Test
	public void should_Add_Item_To_Inventory_Correctly() throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);

		EasyMock.expect(inventory.getName())
				.andReturn(InventoryContainerImpl.DEFAULT_NAME).times(1);

		EasyMock.expect(inventory.contains("torch")).andReturn(false).times(1);
		inventory.addItem("torch");
		EasyMock.expectLastCall().times(1);

		stream.print("The torch was added to your inventory");
		EasyMock.expectLastCall().times(1);

		EasyMock.replay(this.inventory);
		EasyMock.replay(this.stream);

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/procces-trigger-without-owner-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		Assert.assertTrue(game.addToInventory("torch"));

		EasyMock.verify(stream);
		EasyMock.verify(inventory);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void should_Add_Item_From_Container_To_Inventory_Correctly()
			throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);

		EasyMock.expect(inventory.getName())
				.andReturn(InventoryContainerImpl.DEFAULT_NAME).times(1);

		EasyMock.expect(inventory.contains("torch")).andReturn(false).times(1);
		inventory.addItem("torch");
		EasyMock.expectLastCall().times(1);

		stream.print("The torch was added to your inventory");
		EasyMock.expectLastCall().times(1);

		EasyMock.replay(this.inventory);
		EasyMock.replay(this.stream);

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/room-with-item-in-container-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("containers");
		currentRoomField.setAccessible(true);

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		List<ContainerModel> before = (List<ContainerModel>) currentRoomField
				.get(game);
		ContainerModel chestBefore = before.get(0);

		Assert.assertTrue(chestBefore.hasItems());

		Assert.assertTrue(game.addToInventory("torch"));

		List<ContainerModel> after = (List<ContainerModel>) currentRoomField
				.get(game);

		ContainerModel chestAfter = after.get(0);

		Assert.assertTrue(!chestAfter.hasItems());

		EasyMock.verify(stream);
		EasyMock.verify(inventory);
	}

	@Test
	public void should_Excecute_Add_Item_To_Inventory_Without_Item_In_Room_Correctly()
			throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);

		EasyMock.expect(inventory.getName())
				.andReturn(InventoryContainerImpl.DEFAULT_NAME).times(1);

		EasyMock.replay(this.inventory);
		EasyMock.replay(this.stream);

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

		Assert.assertTrue(!game.addToInventory("torch"));

		EasyMock.verify(stream);
		EasyMock.verify(inventory);
	}
}
