package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.Vector;

import gameconstants.GameConstants;
import gameworld.GameObject;
import gameworld.GameRenderer;
import gameworld.GameWorld;

/**
 * Created by WSY on 18/3/16.
 */
public abstract class Item implements GameObject{




    public abstract void update_player_situation(Player player);

    public abstract String getName();

    Vector2 position;

    private int width;
    private int height;

    private float destructionCounter = 10;

    private Rectangle boundingRect;

    private String ID;

    float lifeTime;
    public static int itemID=0;

    public Item(){
        position = new Vector2();
        //this.assign_random_coord(); - to be done in Buffer
        lifeTime = 10 + (new Random()).nextFloat()*10;
        this.ID = String.valueOf(itemID++);
        width = 75;
        height = 75;


    }

    public Item(float x, float y){
        position = new Vector2(x,y);
        width = 75;
        height = 75;
        setBoundingRect();


    }
    public String getID(){
        return ID;
    }

    public void decreaseLife(float delta){
        lifeTime -= delta;
    }

    public boolean expired(){
        return (lifeTime<=0);
    }

    public void setBoundingRect(){
        boundingRect = new Rectangle(this.position.x-width/2, this.position.y-height/2, width, height); // DUNNO why x,y have to be shifted by half the dimension
    }


    public void destroy() {
        Gdx.app.log("Debug","Item destroyed.");

        GameWorld.items.remove(this);


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




}
