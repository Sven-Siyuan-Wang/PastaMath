package gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

import gameobjects.Item;
import gameobjects.Player;
import gameobjects.Simple_Item_Buffer;
import screens.GameScreen;


/**
 * Created by Hazel on 28/2/2016.
 */
public class GameWorld {


    //TODO: initialize all players and game objects here- SERVER
    public static ArrayList<Player> players = new ArrayList<Player>();

    public static Player myself;  // created by AndroidLauncher

    public static Simple_Item_Buffer simple_item_buffer= new Simple_Item_Buffer();

    public static ArrayList<Item> items = new ArrayList<Item>();  // items for server to update

    public static int endScore = 999;

    public static boolean isOwner;
    public static boolean win = false;

    public static int playerCounter = 0;

    public static boolean allInitialized = false;

    public static int numberOfActiveItems;



    public GameWorld(Player myself) {
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


        //all the items are initialized inside the buffer already when it is constructed



        if(isOwner) {
            endScore = new Random().nextInt(100) + 50;
            sendEndScore(endScore);
            items = simple_item_buffer.items_currently_appearing;
            numberOfActiveItems = 0;


        }

    }

    public void update(float delta) {

        if(!allInitialized ){
            if( players.size()== MyGdxGame.numberOfPlayers && !players.get(0).getId().equals("null")){
                for(Player player: players){
                    player.initialize(GameScreen.world);
                }
                allInitialized = true;
                Gdx.app.log("GameWorld", "All players initialized");
            }
        }

        else{

            for(Player player: players) {
                if(player.getCurrentValue()==this.endScore) {
                    for(Player player2: players) {
                        player2.resetCurrentValue();
                    }
                    Gdx.app.log("World", "someone has won");
                    win = true;
                }
            }


            if(isOwner){
                //Server code

                //make new object every few seconds
                if (numberOfActiveItems < Simple_Item_Buffer.max_items_capacity) {
                    sendAddItem(simple_item_buffer.generate_random_Item());
                    numberOfActiveItems++;
                    Gdx.app.log("GameWorld","Number of items: "+numberOfActiveItems);
                }


                for (Iterator<Item> iterator = simple_item_buffer.items_currently_appearing.iterator(); iterator.hasNext(); ) {
                    Item item = iterator.next();
                    item.decreaseLife(delta);
                    if (!item.destroyed && item.expired()) {
                        sendRemoveItem(item);
                        item.toDestroy = true;

                    }
                }

            }
            // My player code

            myself.update(delta);
            Gdx.app.log("GameWorld", "Player Update");

            try{
                for(Item item: items){
                    item.update();
                }
            } catch (ConcurrentModificationException e){
                Gdx.app.log("GameWorld", "CatchConcurrentModificationException");
            }

            sendMyLocation();


        }






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
