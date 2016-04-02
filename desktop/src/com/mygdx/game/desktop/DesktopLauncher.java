package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.PlayServices;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pasta Math";
		config.width = 1280;
		config.height = 720;
		//new LwjglApplication(new MyGdxGame(), config);
		//new LwjglApplication(new MyGdxGame(PlayServices, myPlayer), config);

	}
}
