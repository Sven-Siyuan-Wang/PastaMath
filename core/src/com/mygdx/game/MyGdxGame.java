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
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 360;

	public static PlayServices playServices;

	public static Player myPlayer;

	public static int numberOfPlayers;

	public static final short NOTHING_BIT = 0;
	public static final short PLAYER_BIT = 1;
	public static final short ITEM_BIT = 2;
	public static final short DESTROYED_BIT = 4;

	public MyGdxGame(PlayServices playServices, Player myself, int numberOfPlayers){


		this.playServices = playServices;
		myPlayer =  myself;
		this.numberOfPlayers = numberOfPlayers;
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
