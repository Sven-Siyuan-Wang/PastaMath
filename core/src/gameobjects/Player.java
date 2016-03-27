package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import gameconstants.GameConstants;
import gameworld.GameObject;

/**
 * Created by Hazel on 28/2/2016.
 */
public class Player implements GameObject {
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

    private Circle boundingCircle;
    private boolean speedUp;
    float speedUpCounter = 0;

    //todo: initialize booleans for other attributes(to change upon collision)
    private boolean shielded= false; //its score won't be affected
    private int currentValue= 0;
    private int collision_count=0; //after 3 collisions, the shield will not work anymore


    //constructor for Player class
    public Player(float x, float y, int width, int height) {

        position = new Vector2(x, y);

        this.width = width;
        this.height = height;
        this.boundingCircle = new Circle();

        velocity = 50;
    }

    public void update(float delta) {

//        Gdx.app.log("delta", Float.toString(delta));
        if(up) {
            if(!(position.y <= 0)) {
                position.y -= velocity*delta;

            }
        } else if(down) {
            if(!(position.y >= 520)) {
                position.y += velocity*delta;
            }
        }
        if(right) {
            if(!(position.x >=930)) {
                position.x += velocity * delta;
            }
        } else if(left) {
            if(!(position.x<=0)) {
                position.x -= velocity * delta;
            }
        }



        if(speedUp) {
            if(speedUpCounter>5) {
                this.speedUpCounter = 0;
                this.speedDown();
            } else {
                this.speedUpCounter += 1*delta;
//                Gdx.app.log("Player", "speedUpCounter is " + this.speedUpCounter + " and delta is " + delta);
            }
        }

        boundingCircle.set(position.x, position.y, 50f);

        //todo: if player is shielded, dont let its score change
        if(shielded){
            //do nothing;
        }
        else{
            //change score
        }
        //todo: if player collided into something, adjust effects accordingly- change score or speed
    }

    public void onNotClick() {
        Gdx.app.log("Player", "click lifted");
        up = false;
        down = false;
        left = false;
        right = false;
    }

    public void speedUp() {
        if(!speedUp) {
            speedUp = true;
            Gdx.app.log("Player", "sped up");
            this.velocity += 1;

        }
    }

    public void speedDown() {
        if(speedUp) {
            this.velocity -= 1;
            Gdx.app.log("Player", "sped down");
            speedUp = false;
        }
    }

    public boolean collides(GameObject a) {
        if(position.x < (a.getX() + a.getWidth())) {
//            Gdx.app.log("Player", "collided, and x is " + position.x + " and pickup's x is " + pickup.getX() + " and pickup's width is" + pickup.getWidth());
            return (Intersector.overlaps(boundingCircle, (Rectangle) a.getCollider()));

        }
        return false;
    }

    public boolean collides(Item item){
        boolean collision = Intersector.overlaps(boundingCircle, item.getCollider());
        System.out.println("Collision: "+collision);
        return collision;
    }

    public boolean knock_into(Player other){
        return (Intersector.overlaps(boundingCircle, other.boundingCircle));
    }

    public void decreaseScoreUponKnock(){
        if (currentValue<= 10){
            currentValue= 0;
        }
        else {
            currentValue -= 10;
        }
    }

    //TODO: methods FOR ITEMS to change player's situation attributes
    public void setShielded(boolean shielded){
        this.shielded= shielded;
    }
    public boolean getShielded(){
        return this.shielded;
    }
    public int getCollision_count(){
        return this.collision_count;
    }
    public void update_collision_count(){
        if(collision_count==3){
            resetCollision_count();
            setShielded(false); //player loses shield after colliding for 3 times
        }
        else{
            collision_count++; //will only start counting if the person has a shield
        }
    }
    public void resetCollision_count(){
        this.collision_count=0;
    }

    public void setCurrentValue(int number, String operand){
        if (operand.equals("plus")){
            currentValue+= number;
        }

        if(operand.equals("mul")){
            currentValue*= number;
        }
    }




    public void destroy() {

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

    public void setX(float x) {
        position.x = x;
    }

    public float getY() {
        return position.y;
    }

    public void setY(float y) {
        position.y = y;
    }

    public float getVelocity() {
        return this.velocity;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public float getRotation() {
        return this.rotation;
    }

    public Circle getCollider() { return this.boundingCircle; }



}
