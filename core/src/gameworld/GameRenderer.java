package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import gamehelpers.AssetLoader;
import gameobjects.Player;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameRenderer {
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;
    private int gameHeight;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        this.myWorld = world;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        cam = new OrthographicCamera();

        //arg 1: yes to ortho cam (to view things in 2D); arg 2: width of cam; arg 3: height of cam;
        cam.setToOrtho(true, 136, gameHeight);
        cam.update();


        batcher = new SpriteBatch();
        //attach batcher to cam
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        //attach the shapeRenderer to our camera
        shapeRenderer.setProjectionMatrix(cam.combined);

    }

    public void render(float runTime) {
//        Gdx.app.log("GameRenderer", "render");

        Player player = myWorld.getPlayer();

        //1. draw background to prevent flickering
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //2. draw the filled rectangle
        //tell shapeRenderer to begin drawing filled shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //draw background colour
        shapeRenderer.setColor(55/255.0f, 80/255.0f, 100/255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        //draw grass
//        shapeRenderer.setColor(111/255.0f, 186/255.0f, 45/255.0f,1);
//        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        //draw dirt
//        shapeRenderer.setColor(147/255.0f, 80/255.0f, 27/255.0f, 1);
//        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        //tells shapeRenderer to finish rendering (IMPORTANT; must be done every time)
        shapeRenderer.end();

        //begin SpriteBatch
        batcher.begin();
        //disable transparency; this is good for drawing images that do not require transparency
//        batcher.disableBlending();
//        batcher.draw(AssetLoader.bg, 0, midPointY +23, 136, 43);

        //enable transparency for objects WITH transparency
        batcher.enableBlending();
        batcher.draw(AssetLoader.playerAnimation.getKeyFrame(runTime), player.getX(), player.getY(), player.getWidth(), player.getHeight());

        //end spritebatch
        batcher.end();

    }
}
