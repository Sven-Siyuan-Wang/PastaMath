package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Random;

import gameobjects.PickUps;
import gameobjects.Player;



/**
 * Created by Hazel on 28/2/2016.
 */
public class GameWorld {
    public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
    public static ArrayList<GameObject> objectsCopy = new ArrayList<GameObject>();

    private Player player1;
    private PickUps speedUp, shield;

    //TODO: FIND PORTION OF CODE TO RUN THREADS

    public GameWorld(int midPointY) {
        //initialize player here
        player1 = new Player(33, midPointY-5, 17, 12);
        //initializing first speed up item
        speedUp = new PickUps(90,90, 17, 12);
        objects.add(speedUp);
        //TODO: intiialize buffer of items and pickups here, and run threads.
    }

    //todo: merge removeItem thread into this method?  (maybe it is unnecessary)
    public void update(float delta) {
        player1.update(delta);
        speedUp.update(delta);
        objectsCopy = new ArrayList<GameObject>(objects);

        //todo: remove the object
        for(GameObject i: objects) {
            if (i instanceof PickUps) {
                if (player1.collides(i)) {
                    i.destroy();
                    player1.speedUp();
                }
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
