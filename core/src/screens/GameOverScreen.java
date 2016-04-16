package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;

import gamehelpers.GameOverInput;
import gamehelpers.InputHandler;
import gameworld.GameRenderer;
import gameworld.GameWorld;

/**
 * Created by kisa on 2/4/2016.
 */
public class GameOverScreen implements Screen {
    private GameRenderer renderer;
    private GameOverInput myInput;
    private MyGdxGame game;
    private int[] scores;

    public GameOverScreen(GameRenderer renderer, MyGdxGame game, int[] scores) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        this.scores = scores;

        Gdx.app.log("GameScreen", "ScreenWidth is " + screenWidth + " and ScreenHeight is " + screenHeight);
        Gdx.gl.glViewport(0, 0, (int) screenWidth, (int) screenHeight);

        this.renderer = renderer;
        this.game = game;
        myInput = new GameOverInput(renderer, this);
        Gdx.input.setInputProcessor(myInput);

        Gdx.app.log("GameOverScreen", "attached");
    }

    public void changeScreen() throws InterruptedException {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        renderer.renderGameOverScreen(this.scores);

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
