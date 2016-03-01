package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

import gameobjects.PickUps;
import gameobjects.Player;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameWorld {
    private Player player1;
    private PickUps speedUp, shield;

    public GameWorld(int midPointY) {
        //initialize player here
        player1 = new Player(33, midPointY-5, 17, 12);
        //initializing first speed up item
        speedUp = new PickUps(90,90, 17, 12);
    }

    public void update(float delta) {
        player1.update(delta);
        speedUp.update(delta);
        if(player1.collides(speedUp)) {
            speedUp.destroy();
            player1.speedUp();
        }
    }

    public Player getPlayer() {
        return player1;
    }

    public PickUps getSpeedUp() { return speedUp; }

}
