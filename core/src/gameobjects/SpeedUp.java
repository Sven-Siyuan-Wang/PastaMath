package gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

/**
 * Created by valerie_tan on 3/19/2016.
 */
public class SpeedUp extends Item {


    public SpeedUp(float x, float y){
        position = new Vector2(x,y);
        setBoundingRect();

    }

    public SpeedUp(){
        position = new Vector2();
        //this.assign_random_coord(); - to be done in Buffer
        lifeTime = 10 + (new Random()).nextFloat()*10;

    }

    public void update_player_situation(Player player){
        player.setSpeedUp(true);
        MyGdxGame.playServices.sendToPlayer("SPEEDUP "+player.getId()+" true");
    }

    public String getName(){
        return "speedUp";
    }
}
