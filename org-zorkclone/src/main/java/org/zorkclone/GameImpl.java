package org.zorkclone;

import org.apache.commons.lang.NullArgumentException;
import org.zorkclone.base.AbstractGame;
import org.zorkclone.base.Game;
import org.zorkclone.core.InventoryContainer;
import org.zorkclone.core.OutStream;
import org.zorkclone.core.model.GameInitModel;
import org.zorkclone.inject.CommandProvider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameImpl extends AbstractGame implements Game {

	@Inject
	public GameImpl(InputHandler parser, CommandProvider commandProvider,
			InventoryContainer inventory, OutStream outStream) {
		super(parser, commandProvider, inventory, outStream);
	}

	@Override
	public void init(GameInitModel gameInitModel) {
		if (gameInitModel == null) {
			throw new NullArgumentException("gameInitModel");
		}
		print("Initialization...");
		super.init(gameInitModel);
	}

	@Override
	public String getGameFile() {
		return "src/main/resources/sample.txt.xml";
	}

}
