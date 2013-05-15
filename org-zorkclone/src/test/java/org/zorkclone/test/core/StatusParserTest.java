package org.zorkclone.test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import junit.framework.Assert;

import org.junit.Test;
import org.zorkclone.core.ActionParserHelper;
import org.zorkclone.core.model.ActionModel;

public class StatusParserTest {

	@Test
	public void should_Parse_Update_Status_Correctly() {
		String fullStatus = "Update torch to inferno";
		ActionModel action = ActionParserHelper.parse(fullStatus);
		assertNotNull(action);
		assertEquals("torch", action.getItem());
		assertEquals("inferno", action.getStatus());
	}

	@Test()
	public void should_Parse_Wrong_Update_Status_Correctly() {
		String fullStatus = "Update torch to";
		ActionModel action = ActionParserHelper.parse(fullStatus);
		Assert.assertTrue(action == null);
	}

}
