package org.zorkclone.core;

import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {

	public InputStream fileAsStream(String filename) {
		InputStream stream = null;
		try {
			Path path = FileSystems.getDefault().getPath(filename);
			stream = Files.newInputStream(path);
		} catch (Exception e) {
			throw new IllegalArgumentException("File (" + filename
					+ ") not found or cannot read");
		}

		return stream;
	}

}
