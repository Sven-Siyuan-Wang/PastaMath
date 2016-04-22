package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import gameconstants.GameConstants;

/**
 * Created by valerie_tan on 3/18/2016.
 */
public class Simple_Item_Buffer {
    //THIS CLASS HANDLES ALL KINDS OF ITEMS: NUMBERS WITH OPERATIONS, BUFFS.

    public ArrayList<Item> items_currently_appearing; //static ArrayList in buffer class
    public static int max_items_capacity = 6;
    private int random_item_chooser;


    public ArrayList<Float> x_choices = new ArrayList<Float>(); //float objects, to be converted back into float primitives
    public ArrayList<Float> y_choices = new ArrayList<Float>();
    public final int min_x= 100 ;
    public final int min_y= 100 ;
    public final int max_x= 900 ;
    public final int max_y= 500 ;
    public final int xgap = 125;
    public final int ygap = 110;
    private Random randomizerX;
    private Random randomizerY;
    private boolean[][] itemPosGrid;

    //debug
    private int preXindex;
    private int preYindex;

    public Simple_Item_Buffer() { //default constructor
        items_currently_appearing = new ArrayList<Item>();
        randomizerX = new Random();
        randomizerY = new Random();

        // GENERATE X Y WITH GAPS OF item TO ENSURE GOT SPACE FOR RECTANGLE
        // use intervals based on 125 by 125, and then use random.nextInt(#intervals) to find corresponding x and y
        for(float x= min_x; x < max_x; x+=xgap){
            x_choices.add(new Float(x));
        }
        for (float y= min_y; y< max_y; y+=ygap){
            y_choices.add(new Float(y));
        }

        int numCol = x_choices.size();
        int numRow = y_choices.size();

        itemPosGrid = new boolean[numRow][numCol];

    }


    // implement a method to generate new items randomly
    public Item generate_random_Item(){
        Random random = new Random();
        random_item_chooser= random.nextInt(10);
        Item newItem;

        if(random_item_chooser<8){
            newItem = new NumberAndOperand();
        }
        else if(random_item_chooser==8){
            newItem = new SpeedUp();
        }
        else{
            newItem = new Shield();
        }
        assign_random_coord(newItem);
        items_currently_appearing.add(newItem);
        return newItem;

    }

    // MOVED RANDOM COORD ASSIGNMENT FROM ITEM CLASS SO CAN STORE THEM IN A TEMPORARILY LIST

    public void assign_random_coord(Item item) {

        int x_index;
        int y_index;
        do{
            x_index = randomizerX.nextInt(x_choices.size());
            y_index = randomizerY.nextInt(y_choices.size());
            Gdx.app.log("Random", x_index+","+y_index);
        } while(itemPosGrid[y_index][x_index] || (x_index==preXindex && y_index==preYindex)); //regenerate position in grid if the position is taken

        preXindex = x_index;
        preYindex = y_index;


        float x = x_choices.get(x_index);
        float y= y_choices.get(y_index);
        item.setPosition(x, y);

        itemPosGrid[y_index][x_index] = true; //add position

        Gdx.app.log("SimpleItemBuffer", "new "+x_index+" "+y_index);

        item.setBoundingRect();
    }


    public void removeItemPos(Vector2 position){
        int x = (int) position.x;
        int y = (int) position.y;
        int x_index = (x-min_x) / xgap;
        int y_index = (y-min_y) / ygap ;
        itemPosGrid[y_index][x_index] = false;
    }
}


