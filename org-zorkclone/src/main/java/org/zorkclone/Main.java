package org.zorkclone;

import org.zorkclone.inject.MainModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MainModule());
		GameExecutor game = injector.getInstance(GameExecutor.class);
		game.init();
		game.play();
	}
}
