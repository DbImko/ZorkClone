package org.zorkclone.core;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.zorkclone.core.exception.GameFileParseException;
import org.zorkclone.core.model.GameInitModel;

import com.google.inject.Inject;

public class GameFileParserImpl {

	@Inject
	private FileHelper fileHelper;

	public GameInitModel parse(String filename) throws GameFileParseException {
		try {
			JAXBContext context = JAXBContext.newInstance(GameInitModel.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream streamContent = fileHelper.fileAsStream(filename);
			return (GameInitModel) unmarshaller.unmarshal(streamContent);
		} catch (Exception e) {
			throw new GameFileParseException(e);
		}
	}
}
