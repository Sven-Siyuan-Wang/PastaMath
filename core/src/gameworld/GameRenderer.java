package gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Rectangle;
import java.util.ArrayList;

import gameconstants.GameConstants;
import gamehelpers.AssetLoader;
import gameobjects.Item;
import gameobjects.Player;

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
    private BitmapFont scorefont;

    private float runTime;

    private boolean touched;

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
        this.font.getData().setScale(GameConstants.SCALE_X, GameConstants.SCALE_Y);

        this.scorefont = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        this.scorefont.getData().setScale(GameConstants.SCALE_X*(float)0.8, GameConstants.SCALE_Y*(float)0.8);
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void render(float runTime) {
        this.runTime = runTime;
//        Gdx.app.log("GameRenderer", "render");
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        


        //Todo: keep getting Players and Items
        ArrayList<Player> players= myWorld.getPlayers();
        //todo:use one hard-coded player for testing- remove later
        

        //begin SpriteBatch
        batcher.begin();

        batcher.enableBlending();

        //render background!
        batcher.draw(AssetLoader.gameBackground, 0, 0, 1280*GameConstants.SCALE_X, 720*GameConstants.SCALE_Y);


        String debugTag = GameWorld.isOwner ? "Server: " : "Player: ";
        this.font.draw(batcher, debugTag + "Get " + myWorld.endScore + " points!", 500*GameConstants.SCALE_X, 700* GameConstants.SCALE_Y);

        renderItems(new ArrayList<Item>(myWorld.items));
        renderPlayers(players);
        renderSideBar(players);

        if(myWorld.win) {
            this.font.draw(batcher, "Game over!", 350, 360);
        }

        //end spritebatch
        batcher.end();

//        myWorld.getStage().act();
//        myWorld.getStage().draw();

    }

    //TODO: renderItems inside the item_buffer
    public void renderItems(ArrayList<Item> list){
        if(list!=null){
            for(Item item: list){
                batcher.enableBlending();
                //System.out.println("Rendering item: "+item.getName());
                batcher.draw(AssetLoader.textures.get(item.getName()),
                        item.getX() * GameConstants.SCALE_X,
                        item.getY() * GameConstants.SCALE_Y,
                        item.getWidth() * GameConstants.SCALE_X, item.getHeight() * GameConstants.SCALE_Y);
            }
        }
    }

    public void renderPlayers(ArrayList<Player> players){
        int count = 0;
        for(Player player: players) {
            batcher.enableBlending();
            if (player.getSpeedUp()) {
                batcher.draw(AssetLoader.characterAnimations.get((count++)+4).getKeyFrame(runTime),
                        player.getX() * GameConstants.SCALE_X,
                        player.getY() * GameConstants.SCALE_Y,
                        player.getWidth() * GameConstants.SCALE_X,
                        player.getHeight() * GameConstants.SCALE_Y);
            } else if (player.getShield()) {
                batcher.draw(AssetLoader.characterAnimations.get((count++)+8).getKeyFrame(runTime),
                        player.getX() * GameConstants.SCALE_X,
                        player.getY() * GameConstants.SCALE_Y,
                        player.getWidth() * GameConstants.SCALE_X,
                        player.getHeight() * GameConstants.SCALE_Y);
            } else {
                batcher.draw(AssetLoader.characterAnimations.get(count++).getKeyFrame(runTime),
                        player.getX() * GameConstants.SCALE_X,
                        player.getY() * GameConstants.SCALE_Y,
                        player.getWidth() * GameConstants.SCALE_X,
                        player.getHeight() * GameConstants.SCALE_Y);
//            batcher.draw(AssetLoader.characters.get(count++), player.getX()*GameConstants.SCALE_X, player.getY()*GameConstants.SCALE_Y, player.getWidth()*GameConstants.SCALE_X, player.getHeight()*GameConstants.SCALE_Y);
            }
        }

    }

    public void renderSideBar(ArrayList<Player> players) {
        int count = 1;
        for(Player player: players) {
            batcher.enableBlending();
            font.draw(batcher, Integer.toString(player.getCurrentValue()), 1120 * GameConstants.SCALE_X, (690-100*count) * GameConstants.SCALE_Y);
            batcher.draw(AssetLoader.characters.get(count-1), 1040 * GameConstants.SCALE_X, (620-100*count) * GameConstants.SCALE_Y, 75 * GameConstants.SCALE_X, 75 * GameConstants.SCALE_Y);
            count += 1;
        }
    }

    public void renderJoystick(int joyX, int joyY) {
        batcher.begin();
        batcher.enableBlending();
//            Gdx.app.log("render","y: " +  (joyY+100) * GameConstants.SCALE_Y);
        batcher.draw(AssetLoader.touchBackground, (1030)* GameConstants.SCALE_X, (0)* GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
        batcher.end();
    }

    public void renderJoystickButtons(boolean up, boolean down, boolean left, boolean right) {
        batcher.begin();
        batcher.enableBlending();
        if(down) {
            if (!left && !right) {
                batcher.draw(AssetLoader.up, (1030) * GameConstants.SCALE_X, (0) * GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
            } else if (left) {
                batcher.draw(AssetLoader.upleft, (1030) * GameConstants.SCALE_X, (0) * GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
            } else if (right) {
                batcher.draw(AssetLoader.upright, (1030) * GameConstants.SCALE_X, (0) * GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
            }
        } else if(up) {
            if(!left && !right) {
                batcher.draw(AssetLoader.down, (1030)* GameConstants.SCALE_X, (0)* GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
            } else if (left) {
                batcher.draw(AssetLoader.downleft, (1030)* GameConstants.SCALE_X, (0)* GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
            } else if (right) {
                batcher.draw(AssetLoader.downright, (1030)* GameConstants.SCALE_X, (0)* GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
            }
        } else if(left) {
            batcher.draw(AssetLoader.left, (1030)* GameConstants.SCALE_X, (0)* GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
        } else if(right) {
            batcher.draw(AssetLoader.right, (1030)* GameConstants.SCALE_X, (0)* GameConstants.SCALE_Y, 250 * GameConstants.SCALE_X, 250 * GameConstants.SCALE_Y);
        }

        batcher.end();
    }

    public void renderGameOverScreen() {
        batcher.begin();
        batcher.enableBlending();

        batcher.draw(AssetLoader.gameOverBackground, 0, 0, 1280*GameConstants.SCALE_X, 720*GameConstants.SCALE_Y);
        int count = 1;
        for(Player player:myWorld.getPlayers()) {
            batcher.draw(AssetLoader.characters.get(count-1),
                    300 * GameConstants.SCALE_X,
                    (540-100*count) * GameConstants.SCALE_Y,
                    75 * GameConstants.SCALE_X,
                    75 * GameConstants.SCALE_Y);
            scorefont.draw(batcher, Integer.toString(player.getCurrentValue()), 400*GameConstants.SCALE_X, (570-100*count)*GameConstants.SCALE_Y);
            count +=1;
        }

        batcher.draw(AssetLoader.startOverButton,
                800 * GameConstants.SCALE_X,
                120 * GameConstants.SCALE_Y,
                250 * GameConstants.SCALE_X,
                100 * GameConstants.SCALE_Y);

        batcher.end();
    }





}
