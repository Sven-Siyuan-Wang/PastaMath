package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 * Created by valerie_tan on 3/18/2016.
 */
public class Simple_Item_Buffer {
    //THIS CLASS HANDLES ALL KINDS OF ITEMS: NUMBERS WITH OPERATIONS, BUFFS.

    public ArrayList<Item> items_currently_appearing; //static ArrayList in buffer class
    public static int max_items_capacity;
    private int random_item_chooser;

    //todo: keep track of existing x-y position float tuples
    public ArrayList<Vector2> existing_item_pos_vec= new ArrayList<Vector2>();

    public Simple_Item_Buffer() { //default constructor
        items_currently_appearing = new ArrayList<Item>();
        max_items_capacity = 15;
        generate_Items();

    }

    public void generate_Items() {
        //generates 2 number_n_operands + 1 power-ups
        //todo: assign random coords to a newly randomly generated item and add into the list
        ///items_currently_appearing.add(item_without_coord);
        for (int i = 0; i < 5; i++) {
            items_currently_appearing.add(new NumberAndOperand());
        }
        items_currently_appearing.add(new Shield());
        items_currently_appearing.add(new SpeedUp());
        items_currently_appearing.add(new SpeedUp());

        //assign random coords in THIS buffer class after Item is initialized
        for(Item item: items_currently_appearing){
            assign_random_coord(item);
        }
    }

    public ArrayList<Item> getItems_currently_appearing(){
        return items_currently_appearing;
    }

    //TODO: implement a method to generate new items randomly
    public void generate_random_Item(){
        Random random = new Random();
        random_item_chooser= random.nextInt(5);
        Item newItem;

        if(random_item_chooser==1 || random_item_chooser== 3 || random_item_chooser== 5){
            newItem = new NumberAndOperand();
        }
        else if(random_item_chooser==4){
            newItem = new SpeedUp();
        }
        else{
            newItem = new Shield();
        }
        assign_random_coord(newItem);
        items_currently_appearing.add(newItem);

    }

    //TODO: MOVED RANDOM COORD ASSIGNMENT FROM ITEM CLASS SO CAN STORE THEM IN A TEMPORARILY LIST

    public void assign_random_coord(Item item) {
        //generate a random number (use integer, easier to check for overlap)
        //todo: get game info - remember to minus off to leave space

        Random randomizer = new Random();
        //nextInt gives 0 to n-1
        //todo: CALCULATE BORDERS TO ENSURE ITEMS WITHIN SCREEN
        float x = randomizer.nextFloat() * (Gdx.graphics.getWidth()- 4) + 2 ;
        float y = randomizer.nextFloat() * (Gdx.graphics.getHeight() - 4) + 2;

        //set position if it doesnt alr exist

        //TODO: VALERIE MUST FIX THIS:
        //TODO: FIXED RANDOM COORDS- NO OVERLAP AND WITHIN SCREEN- NEED TO COMMUNICATE WITH SIMPLE ITEM BUFFER?
        //TODO: GENERATE X Y WITH GAPS OF item TO ENSURE GOT SPACE FOR RECTANGLE
        //TODO: CHECK IF RECTANGLE COLLIDES- BAD METHOD

        if (!check_if_vector_exist(new Vector2(x, y))) {
            item.setPosition(x, y);
            existing_item_pos_vec.add(item.getPosition()); //add position
        }
        else{
            assign_random_coord(item); //recursive- keep generating random coord if it exist alr
        }

        item.setBoundingRect();
    }


    //a method to check if vector alr exists
    public boolean check_if_vector_exist(Vector2 vector_to_check){
        boolean exist= false;
        for (Vector2 existing_vector: existing_item_pos_vec){
            //compare both x and y
            if (existing_vector.x == vector_to_check.x && existing_vector.y== vector_to_check.y){
                exist= true;
                continue;
            }
        }
        return exist;
    }

}


