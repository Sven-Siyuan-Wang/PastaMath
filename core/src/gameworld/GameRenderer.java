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
import java.util.Iterator;

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
    public static OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    public SpriteBatch batcher;
    private Rectangle viewport;

    private int gameHeight;
    private int gameWidth;
    private float aspect_ratio;


    private BitmapFont font, scorefont, penaltyfont;

    private float runTime;

    private boolean touched;

    public GameRenderer(GameWorld world, int gameWidth, int gameHeight) {
        this.myWorld = world;

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.aspect_ratio = (float)gameWidth/(float)gameHeight;

        this.batcher = new SpriteBatch();

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(true, gameWidth*GameConstants.SCALE_X, gameHeight*GameConstants.SCALE_Y);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        this.font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        this.font.getData().setScale(GameConstants.SCALE_X*(float)0.9, GameConstants.SCALE_Y*(float)0.9);

        this.scorefont = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        this.scorefont.getData().setScale(GameConstants.SCALE_X*(float)0.8, GameConstants.SCALE_Y*(float)0.8);

        this.penaltyfont = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        this.penaltyfont.getData().setScale(GameConstants.SCALE_X*(float)0.7, GameConstants.SCALE_Y*(float)0.7);
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void render(float runTime) {

        this.runTime = runTime;
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        // keep getting Players and Items
        ArrayList<Player> players= myWorld.getPlayers();


        //begin SpriteBatch
        batcher.begin();

        batcher.enableBlending();


        //render background!
        batcher.draw(AssetLoader.gameBackground, 0, 0, 1280*GameConstants.SCALE_X, 720*GameConstants.SCALE_Y);

        if(GameWorld.gameTimer > 7200 - 600) {
            renderTimeOverlay();
        }

        String collisionPenalty = GameWorld.collisionPenalty;
        String debugTag = GameWorld.isOwner ? "Server: " : "Player: " ;
        this.font.draw(batcher, "Get " + myWorld.endScore + " points!", 10*GameConstants.SCALE_X, 700* GameConstants.SCALE_Y);
        this.penaltyfont.draw(batcher, ""+(120-(GameWorld.gameTimer/60)),666*GameConstants.SCALE_X, 690* GameConstants.SCALE_Y);

        this.penaltyfont.draw(batcher, collisionPenalty + " points", 985 * GameConstants.SCALE_X, 690 * GameConstants.SCALE_Y);

        synchronized (GameWorld.items){
            renderItems(myWorld.items);
        }
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
            for(Iterator<Item> iterator = list.iterator(); iterator.hasNext(); ){
                Item item = iterator.next();
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
        
        for(Player player: players) {
            batcher.enableBlending();
            if(player.equals(GameWorld.myself)) {
                batcher.draw(AssetLoader.characterOverlay,
                        (player.getX()-5)*GameConstants.SCALE_X,
                        player.getY()*GameConstants.SCALE_Y,
                        (player.getWidth()+10)*GameConstants.SCALE_X,
                        (player.getHeight())*GameConstants.SCALE_Y);
            }
            if (player.frozen) {
                batcher.draw(AssetLoader.characterAnimations.get(player.getIndex()+16).getKeyFrame(runTime),
                        player.getX() * GameConstants.SCALE_X,
                        player.getY() * GameConstants.SCALE_Y,
                        player.getWidth() * GameConstants.SCALE_X,
                        player.getHeight() * GameConstants.SCALE_Y);
            }
            else if(player.getSpeedUp() && player.getShield()) {
                batcher.draw(AssetLoader.characterAnimations.get(player.getIndex()+12).getKeyFrame(runTime),
                        player.getX() * GameConstants.SCALE_X,
                        player.getY() * GameConstants.SCALE_Y,
                        player.getWidth() * GameConstants.SCALE_X,
                        player.getHeight() * GameConstants.SCALE_Y);
            } else if (player.getSpeedUp()) {
                batcher.draw(AssetLoader.characterAnimations.get(player.getIndex()+4).getKeyFrame(runTime),
                        player.getX() * GameConstants.SCALE_X,
                        player.getY() * GameConstants.SCALE_Y,
                        player.getWidth() * GameConstants.SCALE_X,
                        player.getHeight() * GameConstants.SCALE_Y);
            } else if (player.getShield()) {
                batcher.draw(AssetLoader.characterAnimations.get(player.getIndex()+8).getKeyFrame(runTime),
                        player.getX() * GameConstants.SCALE_X,
                        player.getY() * GameConstants.SCALE_Y,
                        player.getWidth() * GameConstants.SCALE_X,
                        player.getHeight() * GameConstants.SCALE_Y);
            }  else {
                    batcher.draw(AssetLoader.characterAnimations.get(player.getIndex()).getKeyFrame(runTime),
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
            font.draw(batcher, Integer.toString(player.getCurrentValue()),
                    1120 * GameConstants.SCALE_X,
                    (690-100*count) * GameConstants.SCALE_Y);
            batcher.draw(AssetLoader.characters.get(player.getIndex()),
                    1040 * GameConstants.SCALE_X,
                    (620-100*count) * GameConstants.SCALE_Y,
                    75 * GameConstants.SCALE_X,
                    75 * GameConstants.SCALE_Y);
            count += 1;
        }
    }

    public void renderJoystick(int joyX, int joyY) {
        batcher.begin();
        batcher.enableBlending();
//            Gdx.app.log("render","y: " +  (joyY+100) * GameConstants.SCALE_Y);
        batcher.draw(AssetLoader.touchBackground,
                (1030)* GameConstants.SCALE_X,
                (0)* GameConstants.SCALE_Y,
                250 * GameConstants.SCALE_X,
                250 * GameConstants.SCALE_Y);
        batcher.end();
    }

    public void renderJoystickButtons(boolean up, boolean down, boolean left, boolean right) {
        batcher.begin();
        batcher.enableBlending();
        if(down) {
            if (!left && !right) {
                batcher.draw(AssetLoader.up,
                        (1030) * GameConstants.SCALE_X,
                        (0) * GameConstants.SCALE_Y,
                        250 * GameConstants.SCALE_X,
                        250 * GameConstants.SCALE_Y);
            } else if (left) {
                batcher.draw(AssetLoader.upleft,
                        (1030) * GameConstants.SCALE_X,
                        (0) * GameConstants.SCALE_Y,
                        250 * GameConstants.SCALE_X,
                        250 * GameConstants.SCALE_Y);
            } else if (right) {
                batcher.draw(AssetLoader.upright,
                        (1030) * GameConstants.SCALE_X,
                        (0) * GameConstants.SCALE_Y,
                        250 * GameConstants.SCALE_X,
                        250 * GameConstants.SCALE_Y);
            }
        } else if(up) {
            if(!left && !right) {
                batcher.draw(AssetLoader.down,
                        (1030)* GameConstants.SCALE_X,
                        (0)* GameConstants.SCALE_Y,
                        250 * GameConstants.SCALE_X,
                        250 * GameConstants.SCALE_Y);
            } else if (left) {
                batcher.draw(AssetLoader.downleft,
                        (1030)* GameConstants.SCALE_X,
                        (0)* GameConstants.SCALE_Y,
                        250 * GameConstants.SCALE_X,
                        250 * GameConstants.SCALE_Y);
            } else if (right) {
                batcher.draw(AssetLoader.downright,
                        (1030)* GameConstants.SCALE_X,
                        (0)* GameConstants.SCALE_Y,
                        250 * GameConstants.SCALE_X,
                        250 * GameConstants.SCALE_Y);
            }
        } else if(left) {
            batcher.draw(AssetLoader.left,
                    (1030)* GameConstants.SCALE_X,
                    (0)* GameConstants.SCALE_Y,
                    250 * GameConstants.SCALE_X,
                    250 * GameConstants.SCALE_Y);
        } else if(right) {
            batcher.draw(AssetLoader.right,
                    (1030)* GameConstants.SCALE_X,
                    (0)* GameConstants.SCALE_Y,
                    250 * GameConstants.SCALE_X,
                    250 * GameConstants.SCALE_Y);
        }

        batcher.end();
    }

    public void renderTimeOverlay() {
        batcher.enableBlending();

        batcher.draw(AssetLoader.timeOverlayAnimation.getKeyFrame(runTime),
                560*GameConstants.SCALE_X,
                620*GameConstants.SCALE_Y,
                100*GameConstants.SCALE_X,
                100*GameConstants.SCALE_Y);
    }

    public void renderGameOverScreen() {
        batcher.begin();
        batcher.enableBlending();

        batcher.draw(AssetLoader.gameOverBackground, 0, 0, 1280*GameConstants.SCALE_X, 720*GameConstants.SCALE_Y);
        int count = 1;

        for(Player player: GameWorld.players){
            batcher.draw(AssetLoader.characters.get(player.getIndex()),
                    300 * GameConstants.SCALE_X,
                    (440-100*count) * GameConstants.SCALE_Y,
                    75 * GameConstants.SCALE_X,
                    75 * GameConstants.SCALE_Y);

            if(player.isWinner) {
                scorefont.draw(batcher, Integer.toString(player.getCurrentValue())+" *WINNER!*",
                        400*GameConstants.SCALE_X,
                        (500-100*count)*GameConstants.SCALE_Y);

            } else {
                scorefont.draw(batcher, Integer.toString(player.getCurrentValue()),
                        400 * GameConstants.SCALE_X,
                        (500 - 100 * count) * GameConstants.SCALE_Y);
            }
                count +=1;
        }
        if(GameWorld.myself.isWinner){
            //Congratulations!
            scorefont.draw(batcher, "YOU WIN! :D",
                    400*GameConstants.SCALE_X,
                    500*GameConstants.SCALE_Y);
        }
        else{
            //You lose...
            scorefont.draw(batcher, "YOU LOSE...",
                    400*GameConstants.SCALE_X,
                    500*GameConstants.SCALE_Y);
        }

        batcher.draw(AssetLoader.startOverButton,
                800 * GameConstants.SCALE_X,
                120 * GameConstants.SCALE_Y,
                250 * GameConstants.SCALE_X,
                100 * GameConstants.SCALE_Y);

        batcher.end();
    }





}
