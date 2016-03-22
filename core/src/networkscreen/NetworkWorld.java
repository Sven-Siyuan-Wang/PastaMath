package networkscreen;

import java.util.ArrayList;

import gameobjects.PickUps;
import gameobjects.Player;
import gameworld.GameObject;

/**
 * Created by Keith on 18/3/16. still a framework. WIP
 */
public class NetworkWorld {

    public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
    public static ArrayList<GameObject> objectsCopy = new ArrayList<GameObject>();

    private Player player1;
    private PickUps speedUp, shield;


    public NetworkWorld(int midPointY) {
        //initialize player here
        player1 = new Player(33, midPointY-5, 17, 12);
        //initializing first speed up item
        speedUp = new PickUps(90,90, 17, 12);
        objects.add(speedUp);

    }

    public void update(float delta) {
        player1.update(delta);
        speedUp.update(delta);
        objectsCopy = new ArrayList<GameObject>(objects);
        for(GameObject i: objects) {
            if (i instanceof PickUps) {
                if (player1.collides(i)) {
                    i.destroy();
                    player1.speedUp();
                }
            }
        }
        objects = new ArrayList<GameObject>(objectsCopy);


    }

    public Player getPlayer() {
        return player1;
    }

    public PickUps getSpeedUp() { return speedUp; }

    public ArrayList<GameObject> getObjects() { return this.objects; }

}
