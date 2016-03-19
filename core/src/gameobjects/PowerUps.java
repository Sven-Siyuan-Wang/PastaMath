package gameobjects;

import gameworld.GameObject;

/**
 * Created by valerie_tan on 3/19/2016.
 */
public class PowerUps extends Item {
    public PowerUps (float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public void update_player_situation(Player player){
        player.setShielded();
    };


}
