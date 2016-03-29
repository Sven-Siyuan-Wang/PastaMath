package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameconstants.GameConstants;
import gamehelpers.AssetLoader;
import gameobjects.Item;
import gameobjects.PickUps;
import gameobjects.Player;
import gameobjects.Simple_Item_Buffer;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameRenderer {
    //720*1280
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    public SpriteBatch batcher;
    private Rectangle viewport;

    private int gameHeight;
    private int gameWidth;
    private float aspect_ratio;

    private BitmapFont font;

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

        this.font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
    }

    public void render(float runTime) {
//        Gdx.app.log("GameRenderer", "render");
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //original
        Player player = myWorld.getPlayer();

        //Todo: keep getting Players and Items
        ArrayList<Player> players= myWorld.getPlayers();
        //todo:use one hard-coded player for testing- remove later
        players.add(player);



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

        batcher.enableBlending();

        //render background!
        batcher.draw(AssetLoader.gameBackground, 0, 0, 1280*GameConstants.SCALE_X, 720*GameConstants.SCALE_Y);

        this.font.draw(batcher, "Get 15 points!", 500, 700);

        renderItems(myWorld.getSimple_item_buffer().items_currently_appearing);
        renderPlayers(players);
        renderSideBar(players);
        //end spritebatch
        batcher.end();

//        myWorld.getStage().act();
//        myWorld.getStage().draw();

    }

    //TODO: renderItems inside the item_buffer
    public void renderItems(ArrayList<Item> list){
        for(Item item: list){
            batcher.enableBlending();
            //System.out.println(item.getName());
            batcher.draw(AssetLoader.textures.get(item.getName()), item.getX()*GameConstants.SCALE_X, item.getY()*GameConstants.SCALE_Y, item.getWidth()*GameConstants.SCALE_X, item.getHeight()*GameConstants.SCALE_Y);
        }
    }

    public void renderPlayers(ArrayList<Player> players){
        for(Player player: players){
            batcher.enableBlending();
            batcher.draw(AssetLoader.textures.get("player"), player.getX()*GameConstants.SCALE_X, player.getY()*GameConstants.SCALE_Y, player.getWidth()*GameConstants.SCALE_X, player.getHeight()*GameConstants.SCALE_Y);
        }

    }

    public void renderSideBar(ArrayList<Player> players) {
        int count = 1;
        for(Player player: players) {
            batcher.enableBlending();
            font.draw(batcher, Integer.toString(player.getCurrentValue()), 1050*GameConstants.SCALE_X, (720-150*count)*GameConstants.SCALE_Y);
            batcher.draw(AssetLoader.textures.get("player"), 1130*GameConstants.SCALE_X, (720-220)*count*GameConstants.SCALE_Y, player.getWidth()*GameConstants.SCALE_X, player.getHeight()*GameConstants.SCALE_Y);
        }
    }

    public void renderJoystick(int joyX, int joyY) {
        batcher.begin();
        batcher.enableBlending();
//            Gdx.app.log("render","y: " +  (joyY+100) * GameConstants.SCALE_Y);
        batcher.draw(AssetLoader.touchBackground, (joyX - 100) * GameConstants.SCALE_X, (720 - joyY - 100) * GameConstants.SCALE_Y, 200 * GameConstants.SCALE_X, 200 * GameConstants.SCALE_Y);
        batcher.draw(AssetLoader.touchKnob, (joyX - 25) * GameConstants.SCALE_X, (720 - joyY - 25) * GameConstants.SCALE_Y, 50 * GameConstants.SCALE_X, 50 * GameConstants.SCALE_Y);
        batcher.end();
    }





}
