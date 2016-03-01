package gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hazel on 29/2/2016.
 */
public class PickUps {
    private Vector2 position;

    private int width;
    private int height;

    private int counter = 100;

    private Rectangle boundingRect;

    public PickUps(float x, float y, int width, int height) {
        this.position = new Vector2(x,y);
        this.width = width;
        this.height = height;
        boundingRect = new Rectangle(x,y, width, height);

    }

    public void destroy() {
        

    }

    public void update(float delta) {
        counter -= (1*delta);
        if(counter < 0) {

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

    public Rectangle getBoundingRect() {
        return this.boundingRect;
    }





}
