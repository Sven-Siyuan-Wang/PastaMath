package gameobjects;

import java.util.ArrayList;
import java.util.Random;
import gameobjects.Item;
/**
 * Created by valerie_tan on 3/18/2016.
 */
public class Simple_Item_Buffer {
    //THIS CLASS HANDLES ALL KINDS OF ITEMS: NUMBERS WITH OPERATIONS, BUFFS.

    private int game_width;
    private int game_height;
    private static ArrayList<Item> items_currently_appearing; //static ArrayList in buffer class
    public static int max_items_capacity;

    public Simple_Item_Buffer() { //default constructor
        items_currently_appearing = new ArrayList<Item>();
        max_items_capacity = 5; //
        for (int i = 0; i < max_items_capacity; i++) {
            generate_Items();
        }
    }

    //METHODS TO INTIIALIZE VARIOUS ITEMS

    //todo: generate numbers and powerups that are extension of Items

    public void generate_Items() {
        //generates 3 number_n_operands + 1 power-ups
        //todo: assign random coords to a newly randomly generated item and add into the list
        Item item_without_coord = new NumberAndOperand();

        assign_random_coord(item_without_coord);
        items_currently_appearing.add(item_without_coord);
    }


    //TODO: create function to generate random coord within screen range
    //when item calls for it, they are assigned coords
    //USED BY PRODUCER


    //TODO: consider if this should be put in Item class directly
    public void assign_random_coord(Item item_to_assign_location) {
        //generate a random number (use integer, easier to check for overlap)
        //todo: get game info - remember to minus off to leave space
        int game_width = 100 - 4;
        int game_height = 50 - 4;

        Random randomizer = new Random();
        //nextInt gives 0 to n-1
        int x = randomizer.nextInt(game_width + 2);
        int y = randomizer.nextInt(game_height + 2);

        item_to_assign_location.x_coord = x;
        item_to_assign_location.y_coord = y;
    }

}