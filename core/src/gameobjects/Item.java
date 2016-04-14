package gameobjects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;

import java.util.Random;
import java.util.Vector;

import gameconstants.GameConstants;
import gameworld.GameObject;
import gameworld.GameRenderer;
import gameworld.GameWorld;
import screens.GameScreen;

/**
 * Created by WSY on 18/3/16.
 */
public abstract class Item implements GameObject{




    public abstract void update_player_situation(Player player);

    public abstract String getName();

    Vector2 position;

    private int width;
    private int height;


    private String ID;

    float lifeTime;
    public static int itemID=0;


    //Box 2d
    public World world = GameScreen.world;
    public Body b2body;

    // destroy
    public boolean toDestroy = false;
    public boolean destroyed = false;

    protected Fixture fixture;

    public Item(){
        position = new Vector2();

        lifeTime = 10 + (new Random()).nextFloat()*10;
        this.ID = String.valueOf(itemID++);
        width = 75;
        height = 75;
    }

    public Item(float x, float y){
        position = new Vector2(x,y);
        width = 75;
        height = 75;



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



    public void destroy() {

        Gdx.app.log("Debug", "Item destroyed.");

        world.destroyBody(b2body);
        setCategoryFilter(MyGdxGame.DESTROYED_BIT);
        destroyed = true;
    }

    public void update() {
        if(toDestroy && !destroyed){
            destroy();
            if(GameWorld.isOwner) GameWorld.numberOfActiveItems--;

        }

    }

    public float getX() {
        return b2body.getPosition().x;
    }

    public float getY() {
        return b2body.getPosition().y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    //todo: add get and set for items
    public void setPosition(float x, float y){
        b2body.setTransform(new Vector2(x,y),0);
    }




    public void defineItem(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(37.5f);

        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.ITEM_BIT);

    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }




}
