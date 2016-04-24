package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import gameconstants.GameConstants;
import gamehelpers.AssetLoader;
import gameobjects.Player;
import screens.GameScreen;

public class CatMath extends Game {
	GameScreen myScreen;

	public static PlayServices playServices;

	public static Player myPlayer;

	public CatMath(PlayServices playServices, Player myself){

		this.playServices = playServices;
		myPlayer =  myself;
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
