package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.MyGdxGame;

import gamehelpers.InputHandler;
import gameworld.GameRenderer;
import gameworld.GameWorld;
import gameworld.WorldContactListener;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameScreen implements Screen {
    private GameWorld gameWorld;
    private GameRenderer renderer;
    private float runTime;
    private MyGdxGame game;
    private Stage stage;
    private InputHandler myInput;

    private boolean thisScreen = true;

    //Box2d variables
    public static World world = new World(new Vector2(0,0), true);;
    public static Box2DDebugRenderer b2dr;

    // gamecam and viewport
    public static OrthographicCamera gamecam;
    private Viewport gamePort;
    
    public GameScreen(MyGdxGame game) throws InterruptedException {
        this.game = game;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        Gdx.app.log("GameScreen", "ScreenWidth is " + screenWidth + " and ScreenHeight is " + screenHeight);
        Gdx.gl.glViewport(0, 0, (int) screenWidth, (int) screenHeight);

        // Box2d stuff

        b2dr = new Box2DDebugRenderer();

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(screenWidth,screenHeight,gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);


        this.stage = new Stage(new StretchViewport(1280, 720));

        gameWorld = new GameWorld(game.myPlayer); //initialize world
        renderer = new GameRenderer(gameWorld, (int) screenWidth, (int)screenHeight); //initialize renderer
        myInput = new InputHandler(game.myPlayer, this.stage, renderer);
        Gdx.input.setInputProcessor(myInput);

        Gdx.app.log("GameScreen", "attached");

        world.setContactListener(new WorldContactListener());








    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");

    }

    @Override
    public void render(float delta) {
        b2dr.render(world,gamecam.combined);

        runTime += delta;
        if(gameWorld.win) {
            thisScreen = false;
            changeScreen("game over");
        }
        System.out.println("thisScreen is " + thisScreen);
        System.out.println("gameWorld.win is " + gameWorld.win);
        thisScreen = true;
        gameWorld.win = false;
        if(thisScreen) {
            gameWorld.update(delta);
            renderer.render(runTime);
            myInput.render();
            if(runTime>1) world.step(1 / 60f, 6, 2);
        }

        //Gdx.app.log("GameScreen FPS", (1/delta)+"");

    }

    public void changeScreen(String id) {
        if(id == "game over") {
            game.setScreen(new GameOverScreen(renderer, game));
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
