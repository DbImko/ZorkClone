package org.zorkclone;

import org.zorkclone.base.Game;
import org.zorkclone.core.GameFileParserImpl;
import org.zorkclone.core.exception.GameFileParseException;
import org.zorkclone.core.model.GameInitModel;

import com.google.inject.Inject;

public class GameExecutor {

	private Game game;
	private final GameFileParserImpl gameFileParserImpl;

	@Inject
	public GameExecutor(Game game, GameFileParserImpl gameFileParserImpl) {
		this.gameFileParserImpl = gameFileParserImpl;
		this.game = game;
	}

	public void init() {
		try {
			GameInitModel gameModel = parseGameFile();
			game.init(gameModel);
		} catch (Exception e) {
			System.err.println(e.toString());
			System.exit(1);
		}
	}

	public void play() {
		game.play();
	}

	private GameInitModel parseGameFile() throws GameFileParseException {
		return gameFileParserImpl.parse(game.getGameFile());
	}
}
