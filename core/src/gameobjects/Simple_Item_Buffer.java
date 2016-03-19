package gameobjects;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by valerie_tan on 3/18/2016.
 */
public class Simple_Item_Buffer {
    //THIS CLASS HANDLES ALL KINDS OF ITEMS: NUMBERS WITH OPERATIONS, BUFFS.

    public ArrayList<Item> items_currently_appearing; //static ArrayList in buffer class
    public static int max_items_capacity;
    private int random_item_chooser;

    public Simple_Item_Buffer() { //default constructor
        items_currently_appearing = new ArrayList<Item>();
        max_items_capacity = 15;
        generate_Items();

    }

    public void generate_Items() {
        //generates 2 number_n_operands + 1 power-ups
        //todo: assign random coords to a newly randomly generated item and add into the list
        ///items_currently_appearing.add(item_without_coord);
        for (int i = 0; i < 8; i++) {
            items_currently_appearing.add(new NumberAndOperand());
        }
        items_currently_appearing.add(new Shield());
        items_currently_appearing.add(new SpeedUp());
        items_currently_appearing.add(new SpeedUp());

    }

    public ArrayList<Item> getItems_currently_appearing(){
        return items_currently_appearing;
    }

    //TODO: implement a method to generate new items randomly
    public void generate_random_Item(){
        Random random = new Random();
        random_item_chooser= random.nextInt(5);

        if(random_item_chooser==1 || random_item_chooser== 3 || random_item_chooser== 5){
            items_currently_appearing.add(new NumberAndOperand());
        }
        else if(random_item_chooser==4){
            items_currently_appearing.add(new SpeedUp());
        }
        else{
            items_currently_appearing.add(new Shield());
        }

    }

}