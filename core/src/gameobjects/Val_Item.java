package gameobjects;

import gameworld.GameObject;
import gameworld.GameWorld;

/**
 * Created by valerie_tan on 3/18/2016.
 */

//TODO: WAIT FOR SIYUAN'S CLASS
public abstract class Val_Item implements GameObject{

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



    public Val_Item(){ //default constructor, example
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
    public Val_Item(int value){
        this.value= value;
    }

    //TODO: ensure that this destroy method is implemented in Siyuan's Val_Item class
    public void destroy() {
        GameWorld.objectsCopy.remove(this);
    }

    //TODO: abstract method to be implemented in the different kind of items
    public abstract void update_player_situation(Player player);
}
