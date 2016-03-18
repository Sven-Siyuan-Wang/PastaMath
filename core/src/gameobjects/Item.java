package gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import gameworld.GameWorld;

/**
 * Created by WSY on 18/3/16.
 */
public class Item implements iPickUp{
    private static String[] effects = {"* 2", "+ 5","* 3","+ 3","+ 10","+ 20","+ 1"};
    private Vector2 position;
    private String effect;

    private int width;
    private int height;

    private float destructionCounter = 10;

    private Rectangle boundingRect;

    public Item(float x, float y, int width, int height){
        this.position = new Vector2(x,y);
        this.width = width;
        this.height = height;
        boundingRect = new Rectangle(x,y, width, height);
        effect = getRandomEffect();

    }

    public String getEffect(){
        return effect;
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

    private String getRandomEffect(){
        Random random = new Random();
        int i = random.nextInt(effects.length);
        return effects[i];
    }
}
