package gameobjects;

import com.badlogic.gdx.math.Vector2;

import java.io.File;
import java.util.Random;

import gameworld.GameObject;

/**
 * Created by valerie_tan on 3/19/2016.
 */
public class NumberAndOperand extends Item { //All these are a form of item
    public int value;
    public String operation;
    public String number_n_operand;

    private static int largest_number=9;
    private static int operand_chooser;

    //TODO: SIYUAN INSERT CORRESPONDING TEXT IMAGE

    private File image;

    public NumberAndOperand(float x, float y, int width, int height){
        super(x, y, width, height);
        Random random = new Random();
        value= random.nextInt(9);
        Random random_operand= new Random();
        operand_chooser= random_operand.nextInt(50);
        if (operand_chooser<40){
            operation= "+";
        }
        else{
            operation= "*";
        }
        number_n_operand= String.valueOf(value) + operation;
    }

    public void update_player_situation(Player player){
        player.setCurrentValue(value, operation);
    };


}
