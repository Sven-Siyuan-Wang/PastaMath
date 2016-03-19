package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import gameworld.GameObject;
import gameworld.GameRenderer;
import gameworld.GameWorld;

/**
 * Created by WSY on 18/3/16.
 */
public abstract class Item implements GameObject{

    //TODO: obtain game width and height

    //TODO: SIYUAN HAVE TO CODE THE EFFECT ATTRIBUTES SO THAT WHEN I PASS THEM TO GAMERENDERER AND DRAW THEM EASILY AND IMMEDIATELY
    //TODO: all calculations or whatever must be intiialized in this class alr so in gamerenderer can draw directly.
    public String colour;

    //TODO: abstract method to be implemented in the different kind of items
    public abstract void update_player_situation(Player player);
    //TODO: implement getName method here
    public abstract String getName();

    private Vector2 position;

    private int width= 125;
    private int height= 125;

    private float destructionCounter = 10;

    private Rectangle boundingRect;

    public Item(){
        this.assign_random_coord();
        //this.position = new Vector2(x,y);
        boundingRect = new Rectangle(this.position.x, this.position.y, width, height);
        this.colour= "yellow";
    }

    public void destroy() {
        boundingRect=null;
        //ORIGINAL
        GameWorld.objectsCopy.remove(this);
        //NEW
        GameWorld.simple_item_buffer_copy.items_currently_appearing.remove(this);
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

        Random randomizer = new Random();
        //nextInt gives 0 to n-1
        float x = randomizer.nextFloat() * (Gdx.graphics.getWidth()- 4) + 2 ;
        float y = randomizer.nextFloat() * (Gdx.graphics.getHeight() - 4) + 2;

        this.position.x= x;
        this.position.y= y;
    }



}
