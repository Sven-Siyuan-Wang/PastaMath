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


    //todo: keep track of player pos
    public ArrayList<Vector2> existing_player_pos_vec= new ArrayList<Vector2>();

    //TODO: 2 ArrayList<float> values to choose x and y from to assign coords
    public ArrayList<Float> x_choices = new ArrayList<Float>(); //float objects, to be converted back into float primitives
    public ArrayList<Float> y_choices = new ArrayList<Float>();
    public float min_x= 150f ;
    public float min_y= 150f ;
    public float max_x= 1050f - 125f;
    public float max_y= 720f -125f;
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

        //TODO: GENERATE X Y WITH GAPS OF item TO ENSURE GOT SPACE FOR RECTANGLE
        //todo: use intervals based on 125 by 125, and then use random.nextInt(#intervals) to find corresponding x and y
        for(float x= min_x; x < max_x; x+=125f){
            x_choices.add(new Float(x));
        }
        for (float y= min_y; y< max_y; y+=125f){
            y_choices.add(new Float(y));
        }

        int numCol = x_choices.size();
        int numRow = y_choices.size();

        itemPosGrid = new boolean[numRow][numCol];

    }


    //TODO: implement a method to generate new items randomly
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

    //TODO: MOVED RANDOM COORD ASSIGNMENT FROM ITEM CLASS SO CAN STORE THEM IN A TEMPORARILY LIST

    public void assign_random_coord(Item item) {

        int x_index;
        int y_index;
        do{
            x_index = randomizerX.nextInt(x_choices.size());
            y_index = randomizerY.nextInt(y_choices.size());
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



    //todo: check if generated item will overlap with player
    public boolean overlaps_a_player(Vector2 vector_to_check){
        boolean overlap= false;
        for(Vector2 player_pos: existing_player_pos_vec){
            if(vector_to_check.x==player_pos.x && vector_to_check.y == player_pos.y){
                overlap= true;
                continue;
            }
        }
        return overlap;
    }

    public void update_player_pos_vec(ArrayList<Player> players){
        for(Player player: players){
            existing_player_pos_vec.add(player.getPosition());
        }
    }

    public void removeItemPos(Vector2 position){
        int x = (int) position.x;
        int y = (int) position.y;
        int x_index = (x-150) / 125;
        int y_index = (y-150) / 125;
        itemPosGrid[y_index][x_index] = false;
    }
}


