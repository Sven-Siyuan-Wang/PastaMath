package gameobjects;

/**
 * Created by valerie_tan on 3/19/2016.
 */
public class PowerUps extends Item {
    public void update_player_situation(Player player){
        player.setShielded();
    };
}
