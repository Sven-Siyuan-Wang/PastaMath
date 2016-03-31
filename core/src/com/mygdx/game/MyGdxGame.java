package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameconstants.GameConstants;
import gamehelpers.AssetLoader;
import gameobjects.Player;
import javafx.stage.Screen;
import screens.GameScreen;

public class MyGdxGame extends Game {
	GameScreen myScreen;

	public static PlayServices playServices;

	public MyGdxGame(PlayServices playServices){
		this.playServices = playServices;
	}

	@Override
	public void create () {
		Gdx.app.log("PastaMath", "created");

		GameConstants.SCALE_X = Gdx.graphics.getWidth()/1280.0f;
		GameConstants.SCALE_Y = Gdx.graphics.getHeight()/720.0f;

		AssetLoader.load();

		try {
			myScreen = new GameScreen(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setScreen(myScreen);

	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
