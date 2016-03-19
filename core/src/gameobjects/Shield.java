package gameobjects;

/**
 * Created by valerie_tan on 3/19/2016.
 */
public class Shield extends PowerUps{

    public void update_player_situation(Player player){
        player.setShielded();
    };

    public String getName(){
        return "shield";
    }
}
