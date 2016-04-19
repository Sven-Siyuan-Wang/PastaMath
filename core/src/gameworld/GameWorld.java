package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import gameconstants.GameConstants;
import gameobjects.Item;
import gameobjects.NumberAndOperand;
import gameobjects.Player;
import gameobjects.Simple_Item_Buffer;
import screens.GameScreen;


/**
 * Created by Hazel on 28/2/2016.
 */
public class GameWorld {


    //TODO: initialize all players and game objects here- SERVER
    public static ArrayList<Player> players = new ArrayList<Player>();

    public static Player myself;  // created by AndroidLauncher

    public static Simple_Item_Buffer simple_item_buffer;

    public static ArrayList<Item> items = new ArrayList<Item>();  // items for server to update


    public static int endScore = 999;

    public static boolean isOwner;
    public static boolean win = false;
    public static int numberOfPlayers = 0;
    private static boolean allInitialized = false;

    private int removalTimer;
    private int penaltyTimer;
    public static String collisionPenalty = "";
    public static final int[] minusValues = new int[]{2, 5, 10, 20, 50};

    public static int[] scoreForGameOver = new int[4];

    public static Music music;
    public static boolean musicLooped = false;
    public static Sound addPickupSound;
    public static Sound multiplyUpPickupSound;
    public static Sound shieldPickupSound;
    public static Sound speedUpPickupSound;
    public static Sound[] catSounds = new Sound[4];

    private float screenWidth = 1280;
    private float screenHeight = 720;
    public static final int mapWidth = 2*1280;
    public static final int mapHeight = 2*720;


    public GameWorld(Player myself) {

        music =  Gdx.audio.newMusic(Gdx.files.internal("data/background.mp3"));
        addPickupSound = Gdx.audio.newSound(Gdx.files.internal("data/pickup_add.wav"));
        multiplyUpPickupSound = Gdx.audio.newSound(Gdx.files.internal("data/pickup_multiply.wav"));
        shieldPickupSound = Gdx.audio.newSound(Gdx.files.internal("data/pickup_shield.wav"));
        speedUpPickupSound = Gdx.audio.newSound(Gdx.files.internal("data/pickup_speedup.wav"));
        catSounds[0] = Gdx.audio.newSound(Gdx.files.internal("data/cat1.wav"));
        catSounds[1] = Gdx.audio.newSound(Gdx.files.internal("data/cat2.wav"));
        catSounds[2] = Gdx.audio.newSound(Gdx.files.internal("data/cat3.wav"));
        catSounds[3] = Gdx.audio.newSound(Gdx.files.internal("data/cat4.wav"));



        this.myself = myself;

        synchronized (players){
            if(players.size()==0){
                players.add(myself);
            }
            else{
                players.set(0,myself);
            }
        }

        //Send ready message to other players

        MyGdxGame.playServices.sendToPlayer("INIT " + myself.getId());


        if(isOwner) {
            endScore = new Random().nextInt(100) + 50;
            sendEndScore(endScore);

            simple_item_buffer = new Simple_Item_Buffer();
            items = simple_item_buffer.items_currently_appearing;

            penaltyTimer = 0;
            removalTimer = 0;

        }



    }



    public void update(float delta) {



        if(players.size()==numberOfPlayers && !allInitialized){
            ArrayList<Player> sortPlayers = new ArrayList<Player>(players);
            Collections.sort(sortPlayers);
            int index = 0;
            for(Player player: sortPlayers){
                player.setIndex(index++);
            }
            allInitialized = true;
        }

        else{
            if(!musicLooped) {
                music.setLooping(true);
                music.play();
                musicLooped = true;
            }



            for(Player player: players) {
                int count = 0;

                if(player.getCurrentValue()==this.endScore) {
                    for(Player player2: players) {
                        scoreForGameOver[count] = player.getCurrentValue();
                        count +=1;
                        player2.resetCurrentValue();
                    }
                    Gdx.app.log("World", "someone has won");
                    win = true;
                }
            }

            if(isOwner){
                //Server code

                // GENERATE ITEMS
                if(simple_item_buffer.items_currently_appearing.size() < Simple_Item_Buffer.max_items_capacity){
                    synchronized (items){
                        sendAddItem(simple_item_buffer.generate_random_Item());
                    }
                }


                // UPDATE COLLISION PENALTY
                if(penaltyTimer>500 || penaltyTimer == 0) {
                    collisionPenalty = generateCollisionEffect();
                    penaltyTimer = 0;
                    MyGdxGame.playServices.sendToPlayer("PENALTY " + collisionPenalty);
                }
                penaltyTimer += 1;

                // PLAYER RESPONSES TO COLLISION
                for(Player each_player: players){

                    each_player.updateBoundingCircle();

                    for(Iterator<Item> iterator =simple_item_buffer.items_currently_appearing.iterator(); iterator.hasNext(); ){

                        Item item = iterator.next();
                        item.decreaseLife(delta);
                        if(item.expired()) {
                            synchronized (items){
                                iterator.remove();
                            }
                            sendRemoveItem(item);
                            simple_item_buffer.removeItemPos(item.getPosition());
                        }
                        else if(each_player.collides(item)){
                            Gdx.app.log("GameWorld", "Player-Item collision");
                            synchronized (items){
                                iterator.remove();
                            }
                            sendRemoveItem(item);
                            //remove corresponding coords
                            simple_item_buffer.removeItemPos(item.getPosition());
                            item.update_player_situation(each_player);
                            if(item instanceof NumberAndOperand) {
                                sendPlayerScore(each_player, ((NumberAndOperand) item).getOperation());
                            }

                        }
                    }
                    //check if a player collided into another player
                    boolean inContact = false;
                    for(Player other_player: players){
                        if (!other_player.equals(each_player)){ //you can't knock into yourself
                            if (each_player.knock_into(other_player)){
                                each_player.handleCollsion();
                                inContact = true;
                            }
                        }
                    }
                    if(!inContact) each_player.clearContact();
                }

                //sync item list every 5 seconds
                if(removalTimer>300){
                    removalTimer = 0;
                    sendItemList();
                }
                removalTimer++;

            }

            //my player update
            myself.update(delta);
            sendMyLocation();

            //update gamecam

            if(myself.getX() < screenWidth / 2)
                GameRenderer.cam.position.x = screenWidth/2;
            else if (myself.getX() > mapWidth - screenWidth/2)
                GameRenderer.cam.position.x = mapWidth - screenWidth/2;
            else
                GameRenderer.cam.position.x = myself.getX();

            if(myself.getY() < screenHeight / 2)
                GameRenderer.cam.position.y = screenHeight/2;
            else if(myself.getY() > mapHeight - screenHeight/2)
                GameRenderer.cam.position.y = mapHeight - screenHeight/2;
            else
                GameRenderer.cam.position.y = myself.getY() ;

            GameRenderer.cam.position.x *= GameConstants.SCALE_X;
            GameRenderer.cam.position.y *= GameConstants.SCALE_Y;
            GameRenderer.cam.update();

            Gdx.app.log("FrameRate ", Float.toString(1/delta));

        }
    }



    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(){ //TODO: modify to the first player in list.
        return myself;
    }


    //ITEM ID X Y TYPE
    //TYPE: SHIELD, SPEEDUP, PLUS1, MUL2
    //ITEM ID RM
    public void sendAddItem(Item item){
        MyGdxGame.playServices.sendToPlayer("ITEM "+ item.getID() + " " + item.getX() + " " + item.getY()+" "+item.getName());


    }

    public void sendRemoveItem(Item item){
        //MyGdxGame.playServices.sendToPlayer("ITEM "+ item.getID()+" "+ "RM");
        MyGdxGame.playServices.sendToPlayer("ITEM "+ item.getID()+" "+ "RM");

    }

    public void sendItemList(){
        String msg = "ITEMLIST";
        for(Item item: items){
            msg += " "+item.getID();
        }
        MyGdxGame.playServices.sendToPlayer(msg);
    }

    //PLAYER ID X Y
    public void sendMyLocation(){
        MyGdxGame.playServices.sendToPlayer("PLAYER "+myself.getId()+" "+myself.getX()+" "+myself.getY());
    }

    public void sendPlayerScore(Player player, String operation){
        MyGdxGame.playServices.sendToPlayer("SCORE " + player.getId()+" "+ player.getCurrentValue() + " "+ operation);
    }

    private void sendEndScore(int endScore) {
        MyGdxGame.playServices.sendToPlayer("ENDSCORE "+ endScore);
    }

    private String generateCollisionEffect(){
        Random random = new Random();
        int index = random.nextInt(5);
        int value = minusValues[index];
        int operand_chooser = random.nextInt(50);
        String operation;
        if (operand_chooser<30){
            operation= "÷";
        }
        else{
            operation= "-";
        }
        //overwrite value cos only need mul2 and mul3
        if (operation.equals("/")){
            int choose_2_or_3 = random.nextInt(3);
            if (choose_2_or_3<2){
                value=2;
            }
            else{
                value=3;
            }
        }
        return operation + String.valueOf(value);
    }

}
