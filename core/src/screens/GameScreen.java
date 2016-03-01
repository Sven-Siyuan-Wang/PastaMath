package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import gameobjects.Player;

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

    public GameScreen() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight/(screenWidth/gameWidth);

        int midPointY = (int) (gameHeight/2);

        world = new GameWorld(midPointY); //initialize world
        renderer = new GameRenderer(world, (int) gameHeight, midPointY); //initialize renderer
        Gdx.input.setInputProcessor(new InputHandler(world.getPlayer()));
        Gdx.app.log("GameScreen", "attached");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");

    }

    @Override
    public void render(float delta) {
        //sets a colour to fill the screen (in this case, RGB of 10,15,230, with opacity of 1)
//        Gdx.gl.glClearColor(10 / 255.0f, 15 / 255.0f, 230 / 255.0f, 1f);
        //fills screen with selected colour
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        runTime += delta;
        world.update(delta);
        renderer.render(runTime);

//        Gdx.app.log("GameScreen FPS", (1/delta)+"");

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
