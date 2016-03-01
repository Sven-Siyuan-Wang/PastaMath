package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Hazel on 28/2/2016.
 */
public class Player {
    /*Vector2 --> object that can hold 2 values: the x component and y component
     * thus, position.x refers to x-coord, and position.y the y-coord
     * velocity.x and velocity.y would correspond to the speed in either direction
     * acceleration --> change in velocity
     */
    private Vector2 position;
    private float velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    private Boolean up = false;
    private Boolean down = false;
    private Boolean left = false;
    private Boolean right = false;

    //constructor for Player class
    public Player(float x, float y, int width, int height) {

        position = new Vector2(50, 50);

        this.width = width;
        this.height = height;

        velocity = 25;
    }

    public void update(float delta) {

        if(up) {
            position.y -= velocity*delta;
        } else if(down) {
            position.y += velocity*delta;
        }
        if(right) {
            position.x += velocity*delta;
        } else if(left) {
            position.x -= velocity*delta;
        }

    }

    public void onClick() {
        Gdx.app.log("Player", "clicked");
    }

    public void setLeft(Boolean bool) {
        left = bool;
    }
    public void setRight(Boolean bool) {
        right = bool;
    }
    public void setUp(Boolean bool) {
        up = bool;
    }
    public void setDown(Boolean bool) {
        down = bool;
    }

    //get methods for attributes
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getRotation() {
        return this.rotation;
    }

}
