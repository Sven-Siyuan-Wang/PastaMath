package gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import gameworld.GameObject;
import gameworld.GameWorld;

/**
 * Created by WSY on 18/3/16.
 */
public abstract class Item implements GameObject{



    //TODO: SIYUAN HAVE TO CODE THE EFFECT ATTRIBUTES SO THAT WHEN I PASS THEM TO GAMERENDERER AND DRAW THEM EASILY AND IMMEDIATELY
    //TODO: all calculations or whatever must be intiialized in this class alr so in gamerenderer can draw directly.
    public String colour;

    //TODO: abstract method to be implemented in the different kind of items
    public abstract void update_player_situation(Player player);


    private Vector2 position;

    private int width;
    private int height;

    private float destructionCounter = 10;

    private Rectangle boundingRect;

    public Item(float x, float y, int width, int height){
        colour= "yellow";

        this.position = new Vector2(x,y);
        this.width = width;
        this.height = height;
        boundingRect = new Rectangle(x,y, width, height);

    }


    public void destroy() {
        boundingRect=null;
        GameWorld.objectsCopy.remove(this);
    }

    public void update(float delta) {
        destructionCounter -= (1*delta);
        if(destructionCounter < 0) {
            this.destroy();
        }

    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Rectangle getCollider() {
        return new Rectangle(this.boundingRect);
    }

    public void assign_random_coord() {
        //generate a random number (use integer, easier to check for overlap)
        //todo: get game info - remember to minus off to leave space
        int game_width = 100 - 4;
        int game_height = 50 - 4;

        Random randomizer = new Random();
        //nextInt gives 0 to n-1
        float x = randomizer.nextFloat() * (game_width + 2);
        float y = randomizer.nextFloat() * (game_height + 2);

        this.position.x= x;
        this.position.y= y;
    }


}
