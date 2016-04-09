package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import gameconstants.GameConstants;
import gamehelpers.AssetLoader;
import gameobjects.Player;
import gameworld.GameWorld;
import javafx.stage.Screen;
import screens.GameScreen;

public class MyGdxGame extends Game {


	//todo: intiialize Bodies of player and items- in GameWorld?

	GameScreen myScreen;

	public static PlayServices playServices;

	public static Player myPlayer;

	public MyGdxGame(PlayServices playServices, Player myself){

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
		//box2d dispose:
		GameWorld.box2dworld.dispose();
		GameWorld.box2DDebugRenderer.dispose();

	}

}
