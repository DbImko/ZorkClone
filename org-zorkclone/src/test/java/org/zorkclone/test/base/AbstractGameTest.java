package org.zorkclone.test.base;

import java.lang.reflect.Field;

import org.apache.commons.lang.NullArgumentException;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zorkclone.GameExecutor;
import org.zorkclone.GameImpl;
import org.zorkclone.InputHandler;
import org.zorkclone.base.AbstractGame;
import org.zorkclone.base.Game;
import org.zorkclone.core.UserCommand;
import org.zorkclone.core.GameFileParserImpl;
import org.zorkclone.core.InventoryContainer;
import org.zorkclone.core.InventoryContainerImpl;
import org.zorkclone.core.OutStream;
import org.zorkclone.core.StdOut;
import org.zorkclone.core.command.NavigationCommand;
import org.zorkclone.core.model.DirectionType;
import org.zorkclone.core.model.GameInitModel;
import org.zorkclone.core.model.RoomModel;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

public class AbstractGameTest {

	private Injector injector;
	private Game game;
	private InputHandler parser;
	private StdOut stream;
	private InventoryContainer inventory;

	private class Module extends AbstractModule {

		@Override
		protected void configure() {
			parser = EasyMock.createMock(InputHandler.class);
			stream = EasyMock.createMock(StdOut.class);
			inventory = EasyMock.createMock(InventoryContainer.class);
			bind(Game.class).to(GameImpl.class).in(Singleton.class);
			bind(GameExecutor.class).in(Singleton.class);
			bind(NavigationCommand.class);
			bind(InputHandler.class).toInstance(parser);
			bind(OutStream.class).toInstance(stream);
			bind(InventoryContainer.class).toInstance(inventory);
		}
	}

	@Before
	public void before() {
		injector = Guice.createInjector(new Module());
	}

	@Test
	public void should_Create_Game_Correctly() {

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser.parse("src/test/resources/sample.txt.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();
	}

	@Test
	public void should_Proccess_Empty_Command_Correctly() {

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser.parse("src/test/resources/sample.txt.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		Assert.assertTrue(game.processCommand(new UserCommand("", null)) == false);
	}

	@Test
	public void should_Execute_Go_In_Direction_Without_Triggers_Correctly()
			throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);
		stream.print("MainCavern");
		EasyMock.expectLastCall().times(1);

		EasyMock.replay(this.stream);
		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/right-way-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("currentRoom");
		currentRoomField.setAccessible(true);

		RoomModel before = (RoomModel) currentRoomField.get(game);

		game.goInDirection(DirectionType.NORTH);

		RoomModel after = (RoomModel) currentRoomField.get(game);

		Assert.assertTrue(!before.equals(after));
		Assert.assertTrue(before.equals(gameInitModel.getRooms().get(0)));
		Assert.assertTrue(after.equals(gameInitModel.getRooms().get(1)));
		EasyMock.verify(stream);
	}

	@Test
	public void should_Execute_Go_In_Direction_With_Triggers_Correctly()
			throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);
		stream.print("trigger");
		EasyMock.expectLastCall().times(1);

		EasyMock.expect(inventory.contains("torch")).andReturn(false);
		EasyMock.expect(inventory.getName()).andReturn(
				InventoryContainerImpl.DEFAULT_NAME);

		EasyMock.replay(this.stream);
		EasyMock.replay(this.inventory);

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/right-way-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("currentRoom");
		currentRoomField.setAccessible(true);

		RoomModel before = (RoomModel) currentRoomField.get(game);

		game.processCommand(new UserCommand("n", null));

		RoomModel after = (RoomModel) currentRoomField.get(game);

		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(before.equals(gameInitModel.getRooms().get(0)));
		EasyMock.verify(stream);
		EasyMock.verify(inventory);
	}

	@Test
	public void should_Proccess_Command_Without_Triggers_Correctly()
			throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);
		stream.print("MainCavern");
		EasyMock.expectLastCall().times(1);

		EasyMock.replay(this.stream);

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/right-way-without-triggers-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("currentRoom");
		currentRoomField.setAccessible(true);

		RoomModel before = (RoomModel) currentRoomField.get(game);

		game.processCommand(new UserCommand("n", null));

		RoomModel after = (RoomModel) currentRoomField.get(game);

		Assert.assertTrue(!before.equals(after));
		Assert.assertTrue(before.equals(gameInitModel.getRooms().get(0)));
		Assert.assertTrue(after.equals(gameInitModel.getRooms().get(1)));
		EasyMock.verify(stream);
	}

	@Test
	public void should_Execute_Go_In_Wrong_Direction_Correctly()
			throws Exception {

		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);
		stream.print(Game.NO_DIRECTION_MESSAGE);
		EasyMock.expectLastCall().times(1);

		EasyMock.replay(this.stream);

		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);

		Assert.assertNotNull(parser);
		GameInitModel gameInitModel = null;
		try {
			gameInitModel = parser
					.parse("src/test/resources/wrong-way-test-sample.xml");
		} catch (Exception e) {
			Assert.fail(e.toString());
		}

		game = injector.getInstance(Game.class);
		game.init(gameInitModel);
		game.play();

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("currentRoom");
		currentRoomField.setAccessible(true);

		RoomModel before = (RoomModel) currentRoomField.get(game);

		game.goInDirection(DirectionType.SOUTH);

		RoomModel after = (RoomModel) currentRoomField.get(game);

		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!before.equals(gameInitModel.getRooms().get(1)));
		Assert.assertTrue(after.equals(gameInitModel.getRooms().get(0)));
		EasyMock.verify(stream);
	}

	@Test
	public void should_Proccess_Trigger_Without_Owner_In_Scope_Of_CommandProccesing_Correctly()
			throws Exception {
		stream.print("Initialization...");
		EasyMock.expectLastCall().times(1);
		stream.print("Entrance");
		EasyMock.expectLastCall().times(1);
		stream.print();
		EasyMock.expectLastCall().times(1);

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

		Field currentRoomField = AbstractGame.class
				.getDeclaredField("currentRoom");
		currentRoomField.setAccessible(true);

		RoomModel before = (RoomModel) currentRoomField.get(game);

		game.processCommand(new UserCommand("n", null));

		RoomModel after = (RoomModel) currentRoomField.get(game);

		Assert.assertTrue(before.equals(after));
		Assert.assertTrue(!before.equals(gameInitModel.getRooms().get(1)));
		Assert.assertTrue(after.equals(gameInitModel.getRooms().get(0)));
		EasyMock.verify(stream);
	}

	// @Test
	// public void should_Proccess_Wrong_Command_Correctly() {
	// GameFileParserImpl parser = injector
	// .getInstance(GameFileParserImpl.class);
	//
	// Assert.assertNotNull(parser);
	// GameInitModel gameInitModel = null;
	// try {
	// gameInitModel = parser.parse("src/test/resources/sample.txt.xml");
	// } catch (Exception e) {
	// Assert.fail(e.toString());
	// }
	//
	// game = injector.getInstance(Game.class);
	// game.init(gameInitModel);
	// game.play();
	//
	// Assert.assertTrue(game.processCommand(new Command("", "")) == false);
	// }

	@Test(expected = NullArgumentException.class)
	public void should_Throw_NullArgumentException_In_Init() {
		game = injector.getInstance(Game.class);
		game.init(null);
	}
}
