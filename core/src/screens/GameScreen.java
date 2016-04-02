package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import gamehelpers.InputHandler;
import gameworld.GameRenderer;
import gameworld.GameWorld;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameScreen implements Screen {
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;
    private Game game;
    private Stage stage;
    private InputHandler myInput;

    private boolean thisScreen = true;

    public GameScreen(Game game) {
        this.game = game;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        Gdx.app.log("GameScreen", "ScreenWidth is " + screenWidth + " and ScreenHeight is " + screenHeight);
        Gdx.gl.glViewport(0, 0, (int)screenWidth, (int)screenHeight);

        this.stage = new Stage(new StretchViewport(1280, 720));

        world = new GameWorld(this); //initialize world
        renderer = new GameRenderer(world, (int) screenWidth, (int)screenHeight); //initialize renderer
        myInput = new InputHandler(world.getPlayer(), this.stage, renderer);
        Gdx.input.setInputProcessor(myInput);

        Gdx.app.log("GameScreen", "attached");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");

    }

    @Override
    public void render(float delta) {

        runTime += delta;
        if(world.win) {
            thisScreen = false;
            changeScreen("game over");
        }
        System.out.println(thisScreen);
        if(thisScreen) {
            world.update(delta);
            renderer.render(runTime);
            myInput.render();
        }

        //Gdx.app.log("GameScreen FPS", (1/delta)+"");

    }

    public void changeScreen(String id) {
        if(id == "game over") {
            game.setScreen(new GameOverScreen(renderer));
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");

    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");

    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");

    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");

    }

    @Override
    public void dispose() {

    }
}
