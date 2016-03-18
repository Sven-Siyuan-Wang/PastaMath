package gameobjects;

/**
 * Created by valerie_tan on 3/18/2016.
 */
public class Item {

    //public int lifespan; //todo: quite difficult to check for the time up
    //public boolean expired; //difficult

    //TODO: siyuan these are the attributes I need
    //todo: collected- changes to true once the player collects them, this one depends on Keith server
    public boolean collected;
    //todo: coords: variables that are not yet assigned values, I will do the assigning of random coords.
    public int x_coord;
    public int y_coord;

    //TODO: SIYUAN HAVE TO CODE THE EFFECT ATTRIBUTES SO THAT WHEN I PASS THEM TO GAMERENDERER AND DRAW THEM EASILY AND IMMEDIATELY
    //TODO: all calculations or whatever must be intiialized in this class alr so in gamerenderer can draw directly.
    public String colour;
    public int value;
    public String operation;
    public String number_n_operand;


    public Item(){ //default constructor, example
        //lifespan= 3;
        colour= "yellow";
        value= 6;
        operation="*";
        number_n_operand= operation + value;
        collected= false;
        x_coord= 50;
        y_coord=50;
    }

    //customized constructor
    public Item(int value){
        this.value= value;
    }
}
