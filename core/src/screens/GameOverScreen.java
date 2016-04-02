package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

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

    public GameOverScreen(GameRenderer renderer) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        Gdx.app.log("GameScreen", "ScreenWidth is " + screenWidth + " and ScreenHeight is " + screenHeight);
        Gdx.gl.glViewport(0, 0, (int) screenWidth, (int) screenHeight);

        this.renderer = renderer;
        myInput = new GameOverInput(renderer);
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
