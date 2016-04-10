package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import gameobjects.Item;
import gameobjects.Player;
import gameobjects.Simple_Item_Buffer;
import javafx.scene.shape.Circle;
import sun.rmi.runtime.Log;
import screens.GameScreen;



/**
 * Created by Hazel on 28/2/2016.
 */
public class GameWorld {
    //todo: initialize box2d and related componnents here
    public static World box2dworld;
    public static Box2DDebugRenderer box2DDebugRenderer;
    public static OrthographicCamera box2dcamera;


    //TODO: initialize all players and game objects here- SERVER
    public static ArrayList<Player> players = new ArrayList<Player>();

    public static Player myself;  // created by AndroidLauncher

    public static Simple_Item_Buffer simple_item_buffer= new Simple_Item_Buffer();

    public static ArrayList<Item> items;  // items for server to update

    public static int endScore;

    public static boolean isOwner;
    public static boolean win = false;


    //todo: (BOX2D) intiialize Bodies and Fixtures of player and items
    public static Array<Body> player_bodies= new Array<Body>(players.size());
    public static Array<Fixture> player_fixtures= new Array<Fixture>(players.size());
    public static Array<Body> current_item_bodies= new Array<Body>(items.size());
    public static Array<Fixture> current_item_fixtures= new Array<Fixture>(items.size());

    public GameWorld(Player myself) {
        //todo: initialize box2d world - CONSIDER TO DO IN GAMESCREEN OR NOT?
        float w= Gdx.graphics.getWidth();
        float h= Gdx.graphics.getHeight();
        box2dworld = new World(new Vector2(0, 0), false); //gravity vector=0, don't sleep
        box2dcamera = new OrthographicCamera();
        box2dcamera.setToOrtho(false, w, h); //divide to zoom in
        box2DDebugRenderer= new Box2DDebugRenderer();

        this.myself = myself;

        synchronized (players){
            if(players.size()==0){
                players.add(myself);
            }
            else{
                players.set(0,myself);
            }
        }


        //intialize buffer of items and pickups
        simple_item_buffer= new Simple_Item_Buffer();
        items = simple_item_buffer.items_currently_appearing;
        //all the items are initialized inside the buffer already when it is constructed

        if(isOwner) {
            endScore = new Random().nextInt(100) + 50;
            sendEndScore(endScore);

        }

        //TODO: initialize bodies of players and items and setup the user data
        /*
        The easiest way to manage a link between your sprites or game objects and Box2D is with Box2D’s User Data.
        You can set the user data to your game object and
        then update the object's position based on the Box2D body.
         */

        //create Bodies of Players using createPlayerBody method
        //create Bodies of Items using createItemBody method

        //setUserData of corresponding Players
        for (int i=0; i<players.size(); i++){
            //todo: how to create bodies?
            player_bodies.get(i) = createPlayerBody(players.get(i));
            player_bodies.get(i).setUserData(players.get(i));
        }
        //setUserData of corresponding Items
        for (int i=0; i<items.size(); i++){
            current_item_bodies.get(i).setUserData(items.get(i));
        }

        //TODO: SETUSERDATA TO FIXTURES FOR PLAYERS AND ITEMS, need to keep track of their identities for ContactListnener
    }

    //todo: a method to centre camera on player's (MYSELF?) position (extra)
    public void cameraUpdate(float delta){
        Vector3 position= box2dcamera.position;
        position.x= myself.getPosition().x;
        position.y= myself.getPosition().y;
        box2dcamera.position.set(position);
        box2dcamera.update();
    }
    public void update(float delta) {
        //todo:  stepping for the world
        box2dworld.step(1 / 60f, 6, 2);

        //TODO:UPDATE BODIES OF ITEMS AND PLAYERS using userdata- the renderer will just render player
        //fill array with bodies
        box2dworld.getBodies(player_bodies);
        box2dworld.getBodies(current_item_bodies);

        //todo: does the actual items get updated from bodies? need to update from FIXTURES also?
        for (Body player_body: player_bodies){
            Player player= (Player) player_body.getUserData();
            if (player!=null){
                player.setPosition(player_body.getPosition());
            }
        }
        for (Body item_body: current_item_bodies){
            Item item= (Item) item_body.getUserData();
            if(item!=null){
                item.setPosition(item_body.getPosition().x, item_body.getPosition().y);
            }
        }
/* EXAMPLE:
// Create an array to be filled with the bodies
// (better don't create a new one every time though)
Array<Body> bodies = new Array<Body>();
// Now fill the array with all bodies
world.getBodies(bodies);

for (Body b : bodies) {
    // Get the body's user data - in this example, our user
    // data is an instance of the Entity class
    Entity e = (Entity) b.getUserData();

    if (e != null) {
        // Update the entities/sprites position and angle
        e.setPosition(b.getPosition().x, b.getPosition().y);
        // We need to convert our angle from radians to degrees
        e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
    }
}
 */



        //all the items are initialized inside the buffer already when it is constructed
        //todo: obtain player latest coord toprevent new items frm overlapping
        simple_item_buffer.update_player_pos_vec(players);

        for(Player player: players) {
            if(player.getCurrentValue()>=this.endScore) {
                for(Player player2: players) {
                    player2.resetCurrentValue();
                }
                Gdx.app.log("World", "someone has won");
                win = true;
            }
        }

        if(isOwner){
            //Server code
            if(simple_item_buffer.items_currently_appearing.size() < Simple_Item_Buffer.max_items_capacity){
                //make new object every few seconds
                System.out.println("generate new item");
                sendAddItem(simple_item_buffer.generate_random_Item());
            }
            //TODO: PLAYER RESPONSES TO COLLISION
            for(Player each_player: players){
                each_player.update(delta);

                for(Iterator<Item> iterator =simple_item_buffer.items_currently_appearing.iterator(); iterator.hasNext(); ){

                    Item item = iterator.next();
                    item.decreaseLife(delta);
                    if(item.expired()) {
                        iterator.remove();
                        sendRemoveItem(item);
                    }
                    else if(each_player.collides(item)){
                        iterator.remove();
                        sendRemoveItem(item);
                        //todo: remove corresponding coords
                        simple_item_buffer.existing_item_pos_vec.remove(item.getPosition());
                        item.update_player_situation(each_player);
                        sendPlayerScore(each_player);

                    }
                }
                //check if a player collided into another player
                for(Player other_player: players){
                    if (!other_player.equals(each_player)){ //you can't knock into yourself
                        if (each_player.knock_into(other_player)){
                            if(each_player.getShielded()) {
                                other_player.update_collision_count();
                            }
                            else{
                                each_player.decreaseScoreUponKnock();
                                other_player.decreaseScoreUponKnock();
                                sendPlayerScore(each_player);
                                sendPlayerScore(other_player);
                            }

                        }
                    }
                }
            }


        }
        else{
            myself.update(delta);

        }
        sendMyLocation();





    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(){ //TODO: modify to the first player in list.
        return myself;
    }


    //ITEM ID X Y TYPE
    //TYPE: SHIELD, SPEEDUP, PLUS1, MUL2
    //ITEM ID RM
    public void sendAddItem(Item item){
        MyGdxGame.playServices.sendToPlayer("ITEM "+ item.getID() + " " + item.getX() + " " + item.getY()+" "+item.getName());


    }

    public void sendRemoveItem(Item item){
        MyGdxGame.playServices.sendToPlayer("ITEM "+ item.getID()+" "+ "RM");
    }

    //PLAYER ID X Y
    public void sendMyLocation(){
        MyGdxGame.playServices.sendToPlayer("PLAYER "+myself.getId()+" "+myself.getX()+" "+myself.getY());
    }

    public void sendPlayerScore(Player player){
        MyGdxGame.playServices.sendToPlayer("SCORE " + player.getId()+" "+ player.getCurrentValue());
    }

    private void sendEndScore(int endScore) {
        MyGdxGame.playServices.sendToPlayer("ENDSCORE "+ endScore);
    }

    //TODO: create PPM constant cos 1 unit is 1 m in box2d... (PERFORM CALCULATIONS)

    //TODO: implement createPlayerBody and createItemBody methods - with Player and Item as arguments
    public Body createPlayerBody(Player player){ //radius= 75/2
        //todo: create dynamic bodies for players
        Body player_body;
        //1. create body definition
        BodyDef bodyDef= new BodyDef();
        //2. set body to dynamic
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        //Set body's starting position in the world; //todo: obtain player location?
        bodyDef.position.set(player.getPosition());
        //todo: ENSURE MATCH IMAGE positioning: IF COLLISION IS WEIRD, minus 75 on each side to move it to *bottom left8
        //Create body in the box2d world
        player_body= box2dworld.createBody(bodyDef);

        //Create circle shape, set radius to 37.5
        CircleShape circle = new CircleShape();
        circle.setRadius(37.5f);

        //Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape= circle;
        fixtureDef.density= 1f;
        fixtureDef.friction= 0.2f;
        fixtureDef.restitution= 1f; //bounce

        //Create our fixture and attach it to the body
        Fixture fixture= player_body.createFixture(fixtureDef);

        //Dispose of shapes
        circle.dispose();
        return player_body;
    }
    public Body createItemBody(Item item){ //radius= 125
        //todo: create static bodies for items
        Body item_body;
        //1. create body definition
        BodyDef bodyDef= new BodyDef();
        //2. set body to dynamic
        bodyDef.type= BodyDef.BodyType.StaticBody;
        //Set body's starting position in the world; //todo: obtain player location?
        bodyDef.position.set(item.getPosition());
        //todo: ENSURE MATCH IMAGE positioning: IF COLLISION IS WEIRD, minus 75 on each side to move it to *bottom left8

        //Create body in the box2d world
        item_body= box2dworld.createBody(bodyDef);

        //Create circle shape, set radius to 37.5
        CircleShape circle = new CircleShape();
        circle.setRadius(62.5f); //125 /2

        //Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape= circle;
        fixtureDef.density= 1f;
        fixtureDef.friction= 0.2f;
        fixtureDef.restitution= 1f; //bounce

        //Create our fixture and attach it to the body
        Fixture fixture= item_body.createFixture(fixtureDef);

        //Dispose of shapes
        circle.dispose();
        return item_body;
    }
}
