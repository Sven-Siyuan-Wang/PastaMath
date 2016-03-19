package gameobjects;

/**
 * Created by valerie_tan on 3/19/2016.
 */
public class NumberAndOperand extends Item{ //All these are a form of item

    public int value;
    public String operation;
    public String number_n_operand;

    public void update_player_situation(Player player){
        player.setCurrentValue(value, operation);
    };



}
