package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import gameworld.GameRenderer;
import gameworld.GameWorld;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameScreen implements Screen {
    private GameWorld world;
    private GameRenderer renderer;

    public GameScreen() {
        world = new GameWorld(); //initialize world
        renderer = new GameRenderer(world); //initialize renderer
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

        world.update(delta);
        renderer.render();

        Gdx.app.log("GameScreen FPS", (1/delta)+"");

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
