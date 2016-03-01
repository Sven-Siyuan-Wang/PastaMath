package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import gameobjects.Player;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameWorld {
    private Player player1;

    public GameWorld(int midPointY) {
        //initialize player here
        player1 = new Player(33, midPointY-5, 17, 12);
    }

    public void update(float delta) {
        player1.update(delta);
    }

    public Player getPlayer() {
        return player1;
    }

}
