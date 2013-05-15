package org.zorkclone.test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.zorkclone.core.GameFileParserImpl;
import org.zorkclone.core.model.BorderItem;
import org.zorkclone.core.model.ConditionModel;
import org.zorkclone.core.model.ContainerModel;
import org.zorkclone.core.model.DirectionType;
import org.zorkclone.core.model.GameInitModel;
import org.zorkclone.core.model.ItemModel;
import org.zorkclone.core.model.RoomModel;
import org.zorkclone.core.model.RoomType;
import org.zorkclone.core.model.TriggerModel;
import org.zorkclone.core.model.TriggerType;
import org.zorkclone.core.model.TurnOnModel;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GameFileParserTest {
	private Injector injector;

	public static class Module extends AbstractModule {

		@Override
		protected void configure() {
			bind(GameFileParserImpl.class);
		}
	}

	@Before
	public void before() {
		injector = Guice.createInjector(new Module());
	}

	@Test
	public void testParse() {
		GameFileParserImpl parser = injector
				.getInstance(GameFileParserImpl.class);
		assertNotNull(parser);
		try {
			GameInitModel model = parser
					.parse("src/test/resources/sample.txt.xml");
			assertNotNull(model);
			List<RoomModel> rooms = model.getRooms();
			RoomModel room = null;
			List<ItemModel> items = model.getItems();
			List<ContainerModel> objects = model.getObjects();
			List<BorderItem> borders;

			assertNotNull(rooms);
			assertEquals(3, rooms.size());

			assertNotNull(items);
			assertEquals(3, items.size());

			int index = 0;
			room = rooms.get(index);

			// Rooms
			assertEquals("Entrance", rooms.get(index).getName());
			assertEquals(
					"You find yourself at the mouth of a cave and decide that in spite of common sense and any sense of self preservation that you're going to go exploring north into it.  It's a little dark, but luckily there are some torches on the wall.",
					rooms.get(index).getDescription());
			borders = rooms.get(index).getBorders();
			assertNotNull(borders);
			assertEquals(1, borders.size());
			assertEquals("MainCavern", room.getBorder(DirectionType.NORTH)
					.getName());
			assertEquals(1, rooms.get(index).getItems().size());
			assertEquals("torch", rooms.get(index).getItems().get(0));
			assertEquals(1, rooms.get(index).getTriggers().size());
			assertEquals("n", rooms.get(index).getTriggers().get(0)
					.getCommand());
			assertEquals("torch", rooms.get(index).getTriggers().get(0)
					.getCondition().getObject());
			assertEquals(false, rooms.get(index).getTriggers().get(0)
					.getCondition().isHas());
			assertEquals("inventory", rooms.get(index).getTriggers().get(0)
					.getCondition().getOwner());
			assertEquals(1, rooms.get(index).getTriggers().get(0).getMessages()
					.size());

			index++;
			room = rooms.get(index);
			assertEquals("MainCavern", rooms.get(index).getName());
			assertEquals(
					"A huge cavern surrounds you with a locked door to the north, a chest in the center, and a very dark corner...",
					rooms.get(index).getDescription());
			borders = rooms.get(index).getBorders();
			assertNotNull(borders);
			assertEquals(2, borders.size());
			assertEquals("Entrance", room.getBorder(DirectionType.SOUTH).getName());
			assertEquals("Staircase", room.getBorder(DirectionType.NORTH).getName());
			assertEquals(0, rooms.get(index).getItems().size());
			assertEquals(1, rooms.get(index).getTriggers().size());
			assertEquals("n", rooms.get(index).getTriggers().get(0)
					.getCommand());
			assertEquals(TriggerType.PERMANENT, rooms.get(index).getTriggers()
					.get(0).getType());
			assertEquals("lock", rooms.get(index).getTriggers().get(0)
					.getCondition().getObject());
			assertEquals("locked", rooms.get(index).getTriggers().get(0)
					.getCondition().getStatus());
			assertEquals(1, rooms.get(index).getTriggers().get(0).getMessages()
					.size());

			index++;
			room = rooms.get(index);
			assertEquals("Staircase", rooms.get(index).getName());
			assertEquals("You found the exit!", rooms.get(index)
					.getDescription());
			assertEquals(RoomType.EXIT, rooms.get(index).getType());
			borders = rooms.get(index).getBorders();
			assertNotNull(borders);
			assertEquals(1, borders.size());
			assertEquals("MainCavern", room.getBorder(DirectionType.SOUTH)
					.getName());
			assertEquals(0, rooms.get(index).getItems().size());
			assertEquals(0, rooms.get(index).getTriggers().size());

			// Items
			ItemModel item = null;
			index = 0;
			item = new ItemModel();
			item.setName("torch");
			item.setMessage("next to a small button it reads \"push for big flame\"");
			item.setStatus("lit");
			item.setTurnOnModel(new TurnOnModel());
			item.getTurnOnModel().setMessage(
					"the torch has erupted into a menacing inferno");
			item.getTurnOnModel().setAction("Update torch to inferno");
			assertEquals(item, items.get(index));

			index++;
			item = new ItemModel();
			item.setName("explosive");
			item.setMessage("turn on for boom :-). Warning!  Keep away from gnomes!");
			item.setStatus("idle");
			item.setTurnOnModel(new TurnOnModel());
			item.getTurnOnModel().setMessage("you hear ticking...");
			item.getTurnOnModel().setAction("Update explosive to ticking");
			assertEquals(item, items.get(index));

			index++;
			item = new ItemModel();
			item.setName("key");
			item.setMessage("Exit");
			assertEquals(item, items.get(index));

			// Containers
			assertEquals(2, objects.size());
			ContainerModel containerModel = null;

			index = 0;
			containerModel = new ContainerModel();
			containerModel.setName("chest");
			containerModel.addItem("explosive");
			assertEquals(containerModel, objects.get(index));

			index++;
			TriggerModel trigger = new TriggerModel(
					Arrays.asList(new String[] { "The lock drops off and the door opens" }));
			trigger.setCondition(new ConditionModel());
			trigger.getCondition().setHas(true);
			trigger.getCondition().setObject("key");
			trigger.getCondition().setOwner("lock");
			trigger.setAction("Update lock to unlocked");

			containerModel = new ContainerModel();
			containerModel.setName("lock");
			containerModel.setStatus("locked");
			containerModel.setAccept("key");
			containerModel.setTrigger(trigger);

			assertEquals(containerModel, objects.get(index));

			assertNotNull(model.getCreatures());

		} catch (Exception e) {
			fail(e.toString());
		}
	}
}
