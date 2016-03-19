package gameworld;

import java.util.ArrayList;

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

    private PickUps speedUp, shield;


    //todo: initialize Simple_Item_Buffer
    //TODO: initialize all players and game objects here
    private ArrayList<Player> players;

    //TODO: follow template above- make original and copy for buffer
    //private static ArrayList<Val_Item> game_items= new ArrayList<Val_Item>();
    //private static ArrayList<Val_Item> game_items_copy= new ArrayList<Val_Item>();
    private static Simple_Item_Buffer simple_item_buffer= new Simple_Item_Buffer();
    private static Simple_Item_Buffer simple_item_buffer_copy= new Simple_Item_Buffer();

    public GameWorld(int midPointY) {
        //initialize player here
        player1 = new Player(33, midPointY-5, 17, 12);

        //TODO: initialize players as they connect to server
        //initializing first speed up item
        speedUp = new PickUps(90,90, 17, 12);
        objects.add(speedUp);

        //TODO: intiialize buffer of items and pickups
        simple_item_buffer= new Simple_Item_Buffer();
        //all the items are initialized inside the buffer already when it is constructed

    }

    //TODO(Extra): consider doing thread version? (complicated)

    //TODO: do all the "threading"- ADD items every few seconds
    public void update(float delta) {
        player1.update(delta);
        speedUp.update(delta);
        objectsCopy = new ArrayList<GameObject>(objects);


        //TODO: update Players and objects using for-loop

        for(Player each_player: Pl)


        //todo: remove Players and objects accordingly
        for(GameObject i: objects) {
            if (i instanceof PickUps) {
                if (player1.collides(i)) {
                    i.destroy();
                    player1.speedUp();
                }
            }
            //todo: wait for Siyuan item class(similar to PickUps) to be made
            //todo: instead of using instanceOf- create a method for player to respond to collision to diff Items accordingly
            if(player1.collides(i)){
                i.destroy();

            }
        }


        //todo: use timer to generate new item everytime (random timing) if not more than 10 items



        objects = new ArrayList<GameObject>(objectsCopy);


    }

    public Player getPlayer() {
        return player1;
    }

    public PickUps getSpeedUp() { return speedUp; }

    public ArrayList<GameObject> getObjects() { return this.objects; }

}
