package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.MyGdxGame;

import java.io.Serializable;
import java.util.Random;

import gameconstants.GameConstants;
import gameworld.GameObject;
import gameworld.GameRenderer;
import screens.GameScreen;

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
    public float velocity;
    public static final float speedValue = 400;

    private int width;
    private int height;

    public Boolean up = false;
    public Boolean down = false;
    public Boolean left = false;
    public Boolean right = false;


    private boolean speedUp;

    float speedUpCounter = 0;

    //todo: initialize booleans for other attributes(to change upon collision)
    private boolean shielded= false; //its score won't be affected
    private int currentValue;
    private int collision_count=0; //after 3 collisions, the shield will not work anymore

    // Box 2d variables
    public World world;
    public Body b2body;
    protected Fixture fixture;




    //constructor for Player class
    public Player(String id){
        this.id = id;
        Random random = new Random();
        position = new Vector2(random.nextInt(500), random.nextInt(500));

        this.width = 100;
        this.height = 100;

        velocity = speedValue;

        this.currentValue = 0;


    }
    public Player(float x, float y, int width, int height) {

        position = new Vector2(x, y);

        this.width = width;
        this.height = height;

        velocity = speedValue;

        this.currentValue = 0;
    }

    public void initialize(World world){
        this.world = world;
        definePlayer();

    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(50);

        fdef.filter.categoryBits = MyGdxGame.PLAYER_BIT;
        fdef.filter.maskBits = MyGdxGame.ITEM_BIT;
        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);


}


    public void update(float delta) {

        float deltaX = 0;
        float deltaY = 0;
        if(up) {
            if(!(b2body.getPosition().y <= 0)) {
                deltaY = -velocity;


            }
        } else if(down) {
            if(!(b2body.getPosition().y  >= 520)) {
                deltaY = velocity;
            }
        }
        if(right) {
            if(!(b2body.getPosition().x >=930)) {
                deltaX = velocity;
            }
        } else if(left) {
            if(!(b2body.getPosition().x <=0)) {
                deltaX = -velocity;
            }
        }

        b2body.setLinearVelocity(new Vector2(deltaX,deltaY));



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

    public void setPosition(Vector2 position){
        try{
            float oldX = getX();
            float oldY = getY();
            b2body.setTransform(position, 0);
            b2body.setLinearVelocity(0.1f*(position.x - oldX), 0.1f*(position.y - oldY));
        } catch (NullPointerException e){
            setPosition(position);
        }
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
            this.velocity /= 2;
            Gdx.app.log("Player", "sped down");
            speedUp = false;
        }
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
        return b2body.getPosition().x;
    }


    public float getY() {
        return b2body.getPosition().y;
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



    public int getCurrentValue() { return this.currentValue; }

    public void resetCurrentValue() {
        this.currentValue=0;
    }

    public String getId(){
        return this.id;
    }

    //todo: get pos
    public Vector2 getPosition(){
        if (b2body == null) return position;
        return b2body.getPosition();
    }

    public boolean getSpeedUp() {
        return this.speedUp;
    }

    public boolean getShield() {
        return this.shielded;
    }

    public void handleCollision(){
        if(!shielded) decreaseScoreUponKnock();
        else update_collision_count();

    }



}
