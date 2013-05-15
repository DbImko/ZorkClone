package org.zorkclone.test.core;

import org.junit.Assert;
import org.junit.Test;
import org.zorkclone.core.model.RoomType;
import org.zorkclone.core.model.jaxb.adapters.RoomTypeAdapter;

public class RoomTypeTest {

	@Test
	public void should_Cast_Type_Correctly() {
		RoomType type = RoomType.getByName("exit");
		Assert.assertEquals(RoomType.EXIT, type);
	}

	@Test
	public void should_Unmarshar_Room_Type_Correctly() {
		RoomTypeAdapter adapter = new RoomTypeAdapter();
		try {
			RoomType type = adapter.unmarshal("exit");
			Assert.assertEquals(RoomType.EXIT, type);
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}

}
