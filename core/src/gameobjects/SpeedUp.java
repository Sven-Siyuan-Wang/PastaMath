package gameobjects;

/**
 * Created by valerie_tan on 3/19/2016.
 */
public class SpeedUp extends PowerUps{

    public void update_player_situation(Player player){
        player.speedUp();
    };

    public String getName(){
        return "speedup";
    }
}
