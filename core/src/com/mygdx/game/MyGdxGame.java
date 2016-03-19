package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameconstants.GameConstants;
import gamehelpers.AssetLoader;
import javafx.stage.Screen;
import screens.GameScreen;

public class MyGdxGame extends Game {
	GameScreen myScreen;

	@Override
	public void create () {
		Gdx.app.log("PastaMath", "created");

		GameConstants.SCALE_X = Gdx.graphics.getWidth()/1280.0f;
		GameConstants.SCALE_Y = Gdx.graphics.getHeight()/720.0f;

		AssetLoader.load();
		myScreen = new GameScreen(this);
		setScreen(myScreen);


	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
