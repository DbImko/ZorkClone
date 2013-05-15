package org.zorkclone.test.core;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.zorkclone.core.FileHelper;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class FileHelperTest {

	private static final String FILENAME = "src/test/resources/sample.txt.xml";

	private Injector injector;

	public static class Module extends AbstractModule {

		@Override
		protected void configure() {
		}
	}

	@Before
	public void before() {
		injector = Guice.createInjector(new Module());
	}

	@Test
	public void shuold_Read_File_Correctly() {
		FileHelper helper = injector.getInstance(FileHelper.class);
		InputStream stream = helper.fileAsStream(FILENAME);
		assertNotNull(stream);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_Throw_Exception() {
		FileHelper helper = injector.getInstance(FileHelper.class);
		helper.fileAsStream(null);
	}
}
