package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Pasta Math";
		config.width = 640;
		config.height = 360;
		new LwjglApplication(new MyGdxGame(), config);
		//ensure fps
		config.backgroundFPS= 60;
		config.backgroundFPS= 60;
	}
}
