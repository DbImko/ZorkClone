package org.zorkclone.test.core;

import org.junit.Assert;
import org.junit.Test;
import org.zorkclone.core.UserCommand;
import org.zorkclone.core.UserCommandParser;

public class UserCommandParserTest {

	@Test
	public void should_Parse_Empty_Line_Correctly() {
		UserCommand command = UserCommandParser.resolve("");
		Assert.assertTrue(command == null);
	}

	@Test
	public void should_Parse_Null_Correctly() {
		UserCommand command = UserCommandParser.resolve(null);
		Assert.assertTrue(command == null);
	}

	@Test
	public void should_Parse_Line_Correctly() {
		UserCommand command = UserCommandParser.resolve("take torch");
		Assert.assertNotNull(command);
		Assert.assertEquals("take, [torch]", command.toString());
	}

	@Test
	public void should_Parse_Line_Without_Params_Correctly() {
		UserCommand command = UserCommandParser.resolve("n");
		Assert.assertNotNull(command);
		Assert.assertEquals("n", command.toString());
	}

	@Test
	public void should_Parse_Line_With_Many_Params_Correctly() {
		UserCommand command = UserCommandParser.resolve("put torch in lock");
		Assert.assertNotNull(command);
		Assert.assertEquals("put, [torch lock]", command.toString());
	}
	
}
