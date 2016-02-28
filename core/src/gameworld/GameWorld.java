package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import gameobjects.Character;

/**
 * Created by Hazel on 28/2/2016.
 */
public class GameWorld {
    private Character player1;

    public GameWorld(int midPointY) {
        //initialize bird here
        player1 = new Character(33, midPointY-5, 17, 12);
    }

    public void update(float delta) {
        player1.update(delta);
    }

}
