package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.CatMath;

import gamehelpers.GameOverInput;
import gameworld.GameRenderer;

/**
 * Created by kisa on 2/4/2016.
 */
public class GameOverScreen implements Screen {
    private GameRenderer renderer;
    private GameOverInput myInput;
    private CatMath game;


    public GameOverScreen(GameRenderer renderer, CatMath game) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        Gdx.app.log("GameScreen", "ScreenWidth is " + screenWidth + " and ScreenHeight is " + screenHeight);
        Gdx.gl.glViewport(0, 0, (int) screenWidth, (int) screenHeight);

        this.renderer = renderer;
        this.game = game;
        myInput = new GameOverInput(renderer, this);
        Gdx.input.setInputProcessor(myInput);

        Gdx.app.log("GameOverScreen", "attached");
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        renderer.renderGameOverScreen();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {

    }
}
