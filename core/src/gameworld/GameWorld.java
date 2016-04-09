package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import gameobjects.Item;
import gameobjects.Player;
import gameobjects.Simple_Item_Buffer;
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

    //todo: intiialize Bodies of player and items- in GameWorld?
    public static Array<Body> player_bodies= new Array<Body>();
    public static Array<Body> item_bodies= new Array<Body>();

    //TODO: initialize all players and game objects here- SERVER
    public static ArrayList<Player> players = new ArrayList<Player>();

    public static Player myself;  // created by AndroidLauncher

    public static Simple_Item_Buffer simple_item_buffer= new Simple_Item_Buffer();

    public static ArrayList<Item> items;  // items for server to update

    public static int endScore;

    public static boolean isOwner;
    public static boolean win = false;


    public GameWorld(Player myself) {
        //todo: initialize box2d world
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

    }


    public void update(float delta) {
        //TODO:UPDATE BODIES OF ITEMS AND PLAYERS using userdata- the renderer will just render player
        //fill array with bodies
        box2dworld.getBodies(player_bodies);
        box2dworld.getBodies(item_bodies);
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

}
