package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.Vector;

import gameworld.GameObject;
import gameworld.GameRenderer;
import gameworld.GameWorld;

/**
 * Created by WSY on 18/3/16.
 */
public abstract class Item implements GameObject{

    public String colour;


    public abstract void update_player_situation(Player player);

    public abstract String getName();

    private Vector2 position;

    private int width= 125;
    private int height= 125;

    private float destructionCounter = 10;

    private Rectangle boundingRect;

    public Item(){
        position = new Vector2();
        //this.assign_random_coord(); - to be done in Buffer


        this.colour= "yellow";
    }

    public void setBoundingRect(){
        boundingRect = new Rectangle(this.position.x, this.position.y, width, height);
    }


    public void destroy() {
        //boundingRect=null;
        //ORIGINAL
        //GameWorld.objectsCopy.remove(this);
        //NEW
        Gdx.app.log("Debug","Item destroyed.");
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

    //todo: add get and set for items
    public void setPosition(float x, float y){
        this.position.x= x;
        this.position.y= y;
    }

    public Vector2 getPosition(){
        return position;
    }


    public Rectangle getCollider() {
        return new Rectangle(this.boundingRect);
    }

    /*
    public void assign_random_coord() {
        //generate a random number (use integer, easier to check for overlap)
        Random randomizer = new Random();
        //nextInt gives 0 to n-1
        float x = randomizer.nextFloat() * (Gdx.graphics.getWidth()- 4) + 2 ;
        float y = randomizer.nextFloat() * (Gdx.graphics.getHeight() - 4) + 2;

        position.x= x;
        position.y= y;
    }
    */



}
