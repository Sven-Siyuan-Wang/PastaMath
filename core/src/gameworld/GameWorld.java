package gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
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

    public static int endScore;

    public static boolean isOwner;
    public static boolean win = false;

    public static int playerCounter = 0;

    public static boolean allInitialized = false;



    public GameWorld(Player myself) {
        this.myself = myself;

        synchronized (players){
            if(players.size()==0){
                players.add(myself);
            }
            else{
                myself.initialize(GameScreen.world);
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

        }

    }



    //TODO(Extra): consider doing thread version? (complicated)

    //TODO: do all the "threading"- ADD items every few seconds
    public void update(float delta) {

        if(!allInitialized && players.size()== MyGdxGame.numberOfPlayers){
            for(Player player: players){
                player.initialize(GameScreen.world);
            }
            allInitialized = true;
            System.out.println("All players initialized");
        }



        //all the items are initialized inside the buffer already when it is constructed
        //todo: obtain player latest coord toprevent new items frm overlapping
        simple_item_buffer.update_player_pos_vec(players);

        for(Player player: players) {
            if(player.getCurrentValue()==this.endScore) {
                for(Player player2: players) {
                    player2.resetCurrentValue();
                }
                Gdx.app.log("World", "someone has won");
                win = true;
            }
        }

        //update box 2d world
        GameScreen.world.step(1 / 60f, 6, 2);

        if(isOwner){
            //Server code


            if (simple_item_buffer.items_currently_appearing.size() < Simple_Item_Buffer.max_items_capacity) {
                //make new object every few seconds
                System.out.println("generate new item");
                sendAddItem(simple_item_buffer.generate_random_Item());
            }
            //TODO: PLAYER RESPONSES TO COLLISION
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
        // Player code
        else{
            myself.update(delta);
            Gdx.app.log("GameWorld","Player Update");

        }
        sendMyLocation();

        Gdx.app.log("Gameworld","Number of players: "+players.size());





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
