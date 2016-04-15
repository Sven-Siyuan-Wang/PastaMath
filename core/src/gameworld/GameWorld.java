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
    //todo: initialize box2d and related componnents here (static or NOT?)
    public static World box2dworld;
    public static Box2DDebugRenderer box2DDebugRenderer;
    public static OrthographicCamera box2dcamera;
    /*
    If your game must work in pixel units then you should convert your length units
    from pixels to meters when passing values from Box2D. Likewise you should convert
    the values received from Box2D from meters to pixels.
    This will improve the stability of the physics simulation.
     */

    //TODO: http://box2d.org/2011/12/pixels/ refer to this - see how to convert PPM value

    public final int PPM= 40; //used to scale in box2d (PIXELS PER METER)
    //DIVIDE BY PPM TO CONVERT

    //TODO: initialize all players and game objects here- SERVER
    public static ArrayList<Player> players = new ArrayList<Player>();

    public static Player myself;  // created by AndroidLauncher

    public static Simple_Item_Buffer simple_item_buffer= new Simple_Item_Buffer();

    public static ArrayList<Item> items;  // items for server to update

    public static Iterator<Item> items_iterator;

        public static int endScore;

    public static boolean isOwner;
    public static boolean win = false;


    //todo: static deduction score that changes every 10 seconds, and have a timer
    public static int deduction_score=0;
    public static float deduction_score_lifespan= 10; //10 seconds


    //todo: keep track of whether all players r here alr
    public boolean all_players_joined= false;
    public boolean bodies_initialized=false;

    //todo: (BOX2D) intiialize Bodies and Fixtures of player and items
    public static Array<Body> player_bodies;
    public static Array<Fixture> player_fixtures;
    public static Array<Body> current_item_bodies;
    //public static Array<Fixture> current_item_fixtures;

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

        System.out.println("players: "+ players.size());
        //intialize buffer of items and pickups
        simple_item_buffer= new Simple_Item_Buffer();
        items = simple_item_buffer.items_currently_appearing;
        //added the iterator here.
        items_iterator= simple_item_buffer.items_currently_appearing.iterator();
        //does the iterator get updated automatically?

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

        //TODO: create all bodies only when players is filled.

        /*
        //TODO: where to re-initialize the Arrays and update them? in update() method?
        player_bodies= new Array<Body>(players.size());
        player_fixtures= new Array<Fixture>(players.size());
        current_item_bodies= new Array<Body>(items.size());

        //todo: create bodies for players and items -- ERROR: player size=0...
        //setUserData of corresponding Players
        for (int i=0; i<players.size(); i++){
            player_bodies.set(i, createPlayerBody(players.get(i)));
            player_bodies.get(i).setUserData(players.get(i));
        }
        //setUserData of corresponding Items
        for (int i=0; i<items.size(); i++){
            current_item_bodies.set(i, createItemBody(items.get(i)));
            current_item_bodies.get(i).setUserData(items.get(i));
        }
        */

        //TODO: SETUSERDATA TO FIXTURES FOR PLAYERS AND ITEMS, need to keep track of their identities for ContactListnener
    }

    //todo: a method to centre camera on player's (MYSELF?) position (extra)
    public void cameraUpdate(float delta){
        Vector3 position= box2dcamera.position;
        position.x= myself.getPosition().x * PPM;
        position.y= myself.getPosition().y * PPM;
        box2dcamera.position.set(position);
        box2dcamera.update();
    }

    public boolean deduction_score_lifespan_expired(){
        return deduction_score_lifespan<=0;
    }
    public void decrease_deduction_score_lifespan(float delta){
        deduction_score_lifespan-= delta;
    }
    public void update(float delta) {
        //todo:  stepping for the world
        box2dworld.step(1 / 60f, 6, 2);

        //todo: create bodies only when all players are here
        Gdx.app.log("World", "players.size = " + players.size());
        all_players_joined= (players.size()==2);
        if (all_players_joined && !bodies_initialized){
            //TODO: where to re-initialize the Arrays and update them? in update() method?
            player_bodies= new Array<Body>(2);
            player_fixtures= new Array<Fixture>(2);
            current_item_bodies= new Array<Body>(2);

            //todo: create bodies for players and items -- ERROR: player size=0...
            //setUserData of corresponding Players

            //try {
                for (int i = 0; i < 2; i++) {

                    player_bodies.insert(i, createPlayerBody(players.get(i)));
                    player_bodies.get(i).setUserData(players.get(i)); //in another loop?
                }
                //setUserData of corresponding Items
                for (int i = 0; i < 2; i++) {
                    current_item_bodies.insert(i, createItemBody(items.get(i)));
                    current_item_bodies.get(i).setUserData(items.get(i));
                }
            //}
            //catch (Exception e){
                //System.out.println("cannot print");
                //Gdx.app.log("Unable to create player and item bodies");
            //}

            bodies_initialized= true;
        }


        //TODO: CHANGE DEDUCTION POINTS EVERY 10 SECONDS
        if(deduction_score_lifespan_expired()){ //i.e. "expire
            deduction_score= new Random().nextInt(10);
        }
        else{
            decrease_deduction_score_lifespan(delta);
        }


        //TODO: SOLVE THIS CHICKEN AND EGG PROBLEM
        //TODO: where to re-initialize the Arrays and update them? in update() method? cos Players and items always changing also
        //todo: cannot get and set at the same time, the ContactListener must happen in between first

        //todo: assume bodies always stay there(intiialized at the start), aft that players just follow it
        //TODO: solving problem: do the deleting and creating along WITH EACH ITEM- u don't keep reinitializing!
        //todo: the bodies should stay there


        if (bodies_initialized) {
            //TODO:UPDATE BODIES OF ITEMS AND PLAYERS using userdata- the renderer will just render player
            //fill array with bodies
            box2dworld.getBodies(player_bodies);
            box2dworld.getBodies(current_item_bodies);
            //box2dworld.getFixtures(player_fixtures);
            //box2dworld.getFixtures(current_item_fixtures);

            //TODO: SETTING POSITIONS FROM BODIES IS DONE IN UPDATE LOOP
            //TODO: is this necessary? yes, cos based on existing players and items

            //TODO: BODIES UPDATE POSITIONS BASED ON ? (ISNT IT DONE IN CONTACT?)
            //TODO:  **player bounce off each other important
            for (Body player_body : player_bodies) {
                Player player = (Player) player_body.getUserData();
                if (player != null) {
                    player.setPosition(player_body.getPosition().x/PPM, player_body.getPosition().y/PPM);
                }
            }

            //todo: item doesn't matter
            for (Body item_body : current_item_bodies) {
                Item item = (Item) item_body.getUserData();
                if (item != null) {
                    item.setPosition(item_body.getPosition().x / PPM, item_body.getPosition().y / PPM);
                }
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

        for (Player player : players) {
            if (player.getCurrentValue() >= this.endScore) {
                for (Player player2 : players) {
                    player2.resetCurrentValue();
                }
                Gdx.app.log("World", "someone has won");
                win = true;
            }
        }

        if (isOwner) {
            //Server code
            if (simple_item_buffer.items_currently_appearing.size() < Simple_Item_Buffer.max_items_capacity) {
                //make new object every few seconds
                System.out.println("generate new item");
                sendAddItem(simple_item_buffer.generate_random_Item());
            }

            //TODO: EXTRACTED THE NECESSARY CODE- THE OTHERS MOVED INTO GAMELISTENER
            for (Player each_player : players) {
                each_player.update(delta);
            }

            for (Iterator<Item> iterator = simple_item_buffer.items_currently_appearing.iterator(); iterator.hasNext(); ) {

                Item item = iterator.next();
                item.decreaseLife(delta);
                if (item.expired()) {
                    iterator.remove();
                    sendRemoveItem(item);
                }
            }
        }

        //TODO: COMMENT OUT ALL THESE IF GAMELISTENER WORKS

            //TODO: PLAYER RESPONSES TO COLLISION
            /*
            for(Player each_player: players){
                each_player.update(delta);

                //todo: move iterator as an attribute of gameworld, to be accessed in gamelistener
                for(Iterator<Item> iterator =simple_item_buffer.items_currently_appearing.iterator(); iterator.hasNext(); ){

                    Item item = iterator.next();
                    item.decreaseLife(delta);
                    if(item.expired()) {
                        iterator.remove();
                        sendRemoveItem(item);
                    }

                    //todo: move into GameListener
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
                //todo: move into GameListener

                for(Player other_player: players){
                    if (!other_player.equals(each_player)){ //you can't knock into yourself
                        if (each_player.knock_into(other_player)){
                            if(each_player.getShielded()) {
                                //other_player.update_collision_count();
                                each_player.update_collision_count();
                                //TODO: EH THIS ONE GOT ERROR LA... U SHIELDED SO U UPDATE COUNT
                            }
                            else{
                                //todo: don't double decrease! its suppose to do for each one alr
                                each_player.decreaseScoreUponKnock();
                                //other_player.decreaseScoreUponKnock();
                                sendPlayerScore(each_player);
                                //sendPlayerScore(other_player);
                            }

                        }
                    }
                }
            }
            */

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
        //todo: or 75/2?
        //Create body in the box2d world
        player_body= box2dworld.createBody(bodyDef);

        //Create circle shape, set radius to 37.5
        CircleShape circle = new CircleShape();
        circle.setRadius(37.5f / PPM);

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
        circle.setRadius(62.5f/PPM); //125 /2

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
