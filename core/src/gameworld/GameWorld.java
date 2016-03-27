package gameworld;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import gameobjects.Item;
import gameobjects.PickUps;
import gameobjects.Player;
import gameobjects.Simple_Item_Buffer;



/**
 * Created by Hazel on 28/2/2016.
 */
public class GameWorld {
    public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
    public static ArrayList<GameObject> objectsCopy = new ArrayList<GameObject>();
    private Player player1;

    private Stage stage;
    private PickUps speedUp, shield;

    //TODO: initialize all players and game objects here- SERVER
    private ArrayList<Player> players;

    //TODO: follow template above- make original and copy for buffer

    public static Simple_Item_Buffer simple_item_buffer= new Simple_Item_Buffer();
    public static Simple_Item_Buffer simple_item_buffer_copy= new Simple_Item_Buffer();




    //TODO: follow template above- make original and copy for buffer

    public GameWorld(Stage stage) {
        //initialize player here
        players = new ArrayList<Player>();
        player1 = new Player(200, 200, 100, 100);
        players.add(player1);
        //TODO: initialize players as they connect to server
        //initializing first speed up item
        speedUp = new PickUps(400,400, 75,75);
        objects.add(speedUp);

        //TODO: intiialize buffer of items and pickups
        simple_item_buffer= new Simple_Item_Buffer();
        //all the items are initialized inside the buffer already when it is constructed
        //this.stage = stage;

    }

    //TODO(Extra): consider doing thread version? (complicated)

    //TODO: do all the "threading"- ADD items every few seconds
    public void update(float delta) {





        //all the items are initialized inside the buffer already when it is constructed
        //simple_item_buffer_copy.items_currently_appearing= new ArrayList<Item>(simple_item_buffer.items_currently_appearing);

        //todo: use timer to generate new item everytime (random timing) if not more than 10 items
        //TODO: add object if capacity haven't reached- add to copy or the original?
        if(simple_item_buffer.items_currently_appearing.size() < Simple_Item_Buffer.max_items_capacity){
            //make new object every few seconds
            System.out.println("generate new item");
            simple_item_buffer.generate_random_Item();
        }


        player1.update(delta);
        //todo: remove Players and objects accordingly
        //TODO: PLAYER RESPONSES TO COLLISION
        //int i = 0;
        for(Iterator<Item> iterator =simple_item_buffer.items_currently_appearing.iterator(); iterator.hasNext(); ){

            Item item = iterator.next();
            item.decreaseLife(delta);
            if(item.expired()) iterator.remove();
            else if(player1.collides(item)){
                System.out.println("Collision");
                iterator.remove();
                //todo: remove corresponding coords
                simple_item_buffer.existing_item_pos_vec.remove(item.getPosition());
                item.update_player_situation(player1);

            }

//        for(Player each_player: players){
//            each_player.update(delta);
//            //todo: remove Players and objects accordingly
//            //TODO: PLAYER RESPONSES TO COLLISION
//            //int i = 0;
//            for(Item item: simple_item_buffer.items_currently_appearing){
//                //System.out.println("item"+i++);
//                if(each_player.collides(item)){
//
//
//                    item.destroy();
//                    //todo: remove corresponding coords
//                    simple_item_buffer.existing_item_pos_vec.remove(item.getPosition());
//                    item.update_player_situation(each_player);
//
//
//                }
//            }
            //todo: check if a player collided into another player
//            for(Player other_player: players){
//                if (!other_player.equals(each_player)){ //you can't knock into yourself
//                    if (each_player.knock_into(other_player)){
//                        if(each_player.getShielded()) {
//                            each_player.update_collision_count();
//                        }
//                        else
//                            each_player.decreaseScoreUponKnock();
//                    }
//                }
//            }
        }




    }


    //TODO: implement methods to get all Player and Items
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Simple_Item_Buffer getSimple_item_buffer(){
        return simple_item_buffer;
    }

    public Player getPlayer() {
        return player1;
    }

    public Stage getStage() {return this.stage; }

    public PickUps getSpeedUp() { return speedUp; }
    public ArrayList<GameObject> getObjects() { return this.objects; }

}
