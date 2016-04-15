package gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

import java.io.Serializable;
import java.util.Random;

import gameconstants.GameConstants;
import gameworld.GameObject;
import gameworld.GameRenderer;
import gameworld.GameWorld;

/**
 * Created by Hazel on 28/2/2016.
 */
public class Player implements GameObject, Serializable {
    /*Vector2 --> object that can hold 2 values: the x component and y component
     * thus, position.x refers to x-coord, and position.y the y-coord
     * velocity.x and velocity.y would correspond to the speed in either direction
     * acceleration --> change in velocity
     */
    private String id;
    private Vector2 position;
    private float velocity;
    private int width;
    private int height;

    private Boolean up = false;
    private Boolean down = false;
    private Boolean left = false;
    private Boolean right = false;

    private Circle boundingCircle;
    private boolean speedUp;

    float speedUpCounter = 0;
    public static final int speedValue = 200;

    //todo: initialize booleans for other attributes(to change upon collision)
    private boolean shielded = false; //its score won't be affected
    private int currentValue;

    private boolean inContact;


    //constructor for Player class
    public Player(String id){
        this.id = id;
        Random random = new Random();
        position = new Vector2(random.nextInt(500), random.nextInt(500));

        this.width = 100;
        this.height = 100;
        this.boundingCircle = new Circle();

        velocity = speedValue;

        this.currentValue = 0;
        inContact = false;

    }
    public Player(float x, float y, int width, int height) {

        position = new Vector2(x, y);

        this.width = width;
        this.height = height;
        this.boundingCircle = new Circle();

        velocity = speedValue;

        this.currentValue = 0;
        inContact = false;
    }

    public void update(float delta) {

       // if(!inContact || contactTimer)
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
}
    public void updateBoundingCircle(){
        boundingCircle.set(position.x, position.y, 50f);
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
            this.velocity *= 2;

        }
    }

    public void speedDown() {
        if(speedUp) {
            this.velocity = speedValue;
            Gdx.app.log("Player", "sped down");
            speedUp = false;
            MyGdxGame.playServices.sendToPlayer("SPEEDUP "+getId()+" false");
        }
    }


    public boolean collides(Item item){
        boolean collision = Intersector.overlaps(boundingCircle, item.getCollider());
        return collision;
    }

    public boolean knock_into(Player other){
        return (Intersector.overlaps(boundingCircle, other.boundingCircle));
    }

    public void handleCollsion(){
        if(!inContact){
            inContact = true;
            if(getShielded()) {
                shielded = false;
                MyGdxGame.playServices.sendToPlayer("SHIELDED "+getId()+" false");
            }
            else{
                decreaseScoreUponKnock();
                MyGdxGame.playServices.sendToPlayer("SCORE " + getId()+" "+ getCurrentValue());
            }
        }
    }

    public void clearContact(){
        inContact = false;
    }
    public void decreaseScoreUponKnock(){
        String penalty = GameWorld.collisionPenalty;

        String operation = penalty.substring(0,penalty.length()-1);
        int value = Character.getNumericValue(penalty.charAt(penalty.length() - 1));

        if(operation.equals("divide")) currentValue /= value;
        else currentValue -= value;
    }



    //TODO: methods FOR ITEMS to change player's situation attributes
    public void setShielded(boolean shielded){
        this.shielded= shielded;
    }
    public boolean getShielded(){
        return this.shielded;
    }

    public void setSpeedUp(boolean speedUp){
        this.speedUp = speedUp;
    }



    public void setCurrentValue(int number, String operand){
        if (operand.equals("plus")){
            currentValue+= number;
        }

        if(operand.equals("mul")){
            currentValue*= number;
        }
    }

    public void setCurrentValue(int value){
        currentValue = value;
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

    public boolean getLeft() {
        return left;
    }
    public boolean getRight() {
        return right;
    }
    public boolean getUp() {
        return up;
    }
    public boolean getDown() {
        return down;
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


    public Circle getCollider() { return this.boundingCircle; }

    public int getCurrentValue() { return this.currentValue; }

    public void resetCurrentValue() {
        this.currentValue=0;
    }

    public String getId(){
        return this.id;
    }

    //todo: get pos
    public Vector2 getPosition(){
        return this.position;
    }

    public boolean getSpeedUp() {
        return this.speedUp;
    }

    public boolean getShield() {
        return this.shielded;
    }



}
