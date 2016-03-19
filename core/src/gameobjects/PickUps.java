package gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import gameworld.GameObject;
import gameworld.GameWorld;

/**
 * Created by Hazel on 29/2/2016.
 */
public class PickUps implements GameObject {
    private Vector2 position;

    private int width;
    private int height;

    private float destructionCounter = 10;

    private Rectangle boundingRect;

    public PickUps(float x, float y, int width, int height) {
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




}
