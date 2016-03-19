package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameconstants.GameConstants;
import gamehelpers.AssetLoader;
import gameobjects.PickUps;
import gameobjects.Player;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameRenderer {
    //720*1280
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;
    private Rectangle viewport;

    private int gameHeight;
    private int gameWidth;
    private float aspect_ratio;


    public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
        this.myWorld = world;

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.aspect_ratio = (float)gameWidth/(float)gameHeight;

        this.batcher = new SpriteBatch();

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(true, 1280, 720);
        this.cam.update();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

    }

    public void render(float runTime) {
//        Gdx.app.log("GameRenderer", "render");
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Player player = myWorld.getPlayer();
        PickUps speedUp = myWorld.getSpeedUp();

        //1. draw background to prevent flickering
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //2. draw the filled rectangle
        //tell shapeRenderer to begin drawing filled shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //draw background colour
        shapeRenderer.setColor(55/255.0f, 80/255.0f, 100/255.0f, 1);
        shapeRenderer.rect(0, 0, this.gameWidth, this.gameHeight);

        //tells shapeRenderer to finish rendering (IMPORTANT; must be done every time)
        shapeRenderer.end();

        //begin SpriteBatch
        batcher.begin();

        //enable transparency for objects WITH transparency
        batcher.enableBlending();
        batcher.draw(AssetLoader.playerAnimation.getKeyFrame(runTime), player.getX(), player.getY(), player.getWidth(), player.getHeight());

        renderObjects(myWorld.getObjects());

        //end spritebatch
        batcher.end();

    }

    public void renderObjects(ArrayList<GameObject> list) {
        int count = 0;
        for(GameObject i: list) {
            float scale = this.gameWidth/i.getWidth();
            System.out.println(scale);
            batcher.draw(AssetLoader.textures.get(count), i.getX()*GameConstants.SCALE_X, i.getY()*GameConstants.SCALE_Y, i.getWidth()*GameConstants.SCALE_X, i.getHeight()*GameConstants.SCALE_Y);
            count +=1;
        }
    }


}
