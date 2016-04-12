package gameobjects;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import screens.GameScreen;

/**
 * Created by valerie_tan on 3/19/2016.
 */
public class SpeedUp extends Item {


    public SpeedUp(float x, float y){
        position = new Vector2(x,y);
        setBoundingRect();

        this.world = GameScreen.world;
        defineItem();


    }

    public SpeedUp(){
        position = new Vector2();
        //this.assign_random_coord(); - to be done in Buffer
        lifeTime = 10 + (new Random()).nextFloat()*10;


        this.world = GameScreen.world;
        defineItem();

    }

    public void update_player_situation(Player player){
        player.speedUp();
    };

    public String getName(){
        return "speedUp";
    }
}
