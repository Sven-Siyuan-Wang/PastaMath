package com.mygdx.game;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.BaseGameUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import gameobjects.Item;
import gameobjects.NumberAndOperand;
import gameobjects.Player;
import gameobjects.Shield;
import gameobjects.SpeedUp;
import gameworld.GameWorld;

public class NetworkActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        RoomStatusUpdateListener, RoomUpdateListener, RealTimeMessageReceivedListener
{


    final static String TAG = "PASTAMATH NETWORKING";

    public static GoogleApiClient mGoogleApiClient;

    public static Room room = null;

    public static MyAppApplication myApp;

    private static HashMap<String,Integer> playerMap = new HashMap<>();
    private  static HashMap<String, Item> itemMap = new HashMap<>();

    private static int playerCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Log.d(TAG, "onCreate start");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_network);

            myApp = ((MyAppApplication)getApplicationContext());

            mGoogleApiClient = myApp.getClient();                                                   //REMOVE WHEN NECESSARY

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                    .build();

            myApp.setClient(mGoogleApiClient);

            room = myApp.room;
            Log.d(TAG,"ROOM:"+room.getRoomId());
            Log.d(TAG,"owner:"+room.getCreatorId());


            Log.d(TAG, "onCreate ends");
        }
        catch(Exception e){
            Log.d(TAG, "exception " + e.toString());
        }


    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart start");
        super.onStart();
        mGoogleApiClient.connect();
        //myApp.getClient().connect();                                                            //REMOVE WHEN NECESSARY
        Log.d(TAG, "onStart ends");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop start");
        super.onStop();
//        mGoogleApiClient.disconnect();
//        Log.d(TAG, "onStop end, googleapiclient disconnect");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_network, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "onConnected start");
        // The player is signed in. Hide the sign-in button and allow the
        // player to proceed.
        Log.d(TAG, "onConnected end");
    }




    private static int RC_SIGN_IN = 9001;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInFlow = true;
    private boolean mSignInClicked = false;

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, "@string/signin_other_error")) {
                mResolvingConnectionFailure = false;
            }
        }
        // Put code here to display the sign-in button
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    private RoomConfig.Builder makeBasicRoomConfigBuilder() {
        Log.d(TAG, "makeBasicRoomConfigBuilder start");
        RoomConfig.Builder builder = RoomConfig.builder(this);
        builder.setMessageReceivedListener(this);
        builder.setRoomStatusUpdateListener(this);
        //builder.setAutoMatchCriteria(this);

        myApp.setClient(mGoogleApiClient);                                                      //REMOVE WHEN NECESSARY

        // ...add other listeners as needed...
        Log.d(TAG, "makeBasicRoomConfigBuilder start");
        return builder;
    }






    @Override   //RoomStatusUpdateListener
    public void onPeerInvitedToRoom(Room room, List<String> arg1) {
        updateRoom(room);
    }

    @Override   //RoomStatusUpdateListener
    public void onP2PDisconnected(String participant) {

    }

    @Override   //RoomStatusUpdateListener
    public void onP2PConnected(String participant) {

    }

    @Override   //RoomStatusUpdateListener
    public void onPeerJoined(Room room, List<String> arg1) {
        updateRoom(room);
    }

    ArrayList<Participant> mParticipants = null;

    void updateRoom(Room room) {
        Log.d(TAG, "UpdateRoom: "+room.getParticipants().size());
        mParticipants = room.getParticipants();
    }






    /////////////////////////////////////////////////////////////////////////////////////////////

    //RoomStatusUpdateListener stuff
    @Override
    public void onRoomAutoMatching(Room room) {
        updateRoom(room);
    }
    @Override
    public void onRoomConnecting(Room room) {
        updateRoom(room);
    }

    @Override
    public void onPeersConnected(Room room, List<String> peers) {
        updateRoom(room);
    }

    @Override
    public void onPeersDisconnected(Room room, List<String> peers) {
        updateRoom(room);
    }

    @Override
    public void onPeerLeft(Room room, List<String> peersWhoLeft) {
        updateRoom(room);
    }

    @Override
    public void onPeerDeclined(Room room, List<String> arg1) {
        updateRoom(room);
    }

    @Override
    public void onDisconnectedFromRoom(Room room){

    }

    @Override
    public void onConnectedToRoom(Room room) {

    }
    //end of RoomStatusUpdateListener stuff

    /////////////////////////////////////////////////////////////////////////////////////////////







    //stuff for RoomUpdateListener
    final static int RC_WAITING_ROOM = 10002;
    public static String mRoomId = null;

    @Override
    public void onRoomCreated(int statusCode, Room room) {
        Log.d(TAG, "onRoomCreated(statusCode: " + statusCode + ", room: " + room + ")");

        this.room = room;
        MyAppApplication.room = room;                                                               //REMOVE WHEN NECESSARY

        if (statusCode != GamesStatusCodes.STATUS_OK) {
            Log.d(TAG, "onRoomCreated Error");
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }


        // show the waiting room UI
//        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(mGoogleApiClient, room, Integer.MAX_VALUE);   //REMOVE WHEN REQUIRED
        //Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(myApp.getClient(), room, Integer.MAX_VALUE);


        showWaitingRoom(room);
//        startActivityForResult(i, RC_WAITING_ROOM);

        //mRoomId = room.getRoomId();

    }

    @Override
    public void onJoinedRoom(int statusCode, Room room) {
        Log.d(TAG, "onJoinedRoom(statusCode: " + statusCode + ", room: " + room + ")");

        if (statusCode != GamesStatusCodes.STATUS_OK) {
            Log.d(TAG, "onJoinedRoom Error");
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            return;
        }

        this.room = room;
        MyAppApplication.room = room;                                                               //REMOVE WHEN NECESSARY
        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(mGoogleApiClient, room, Integer.MAX_VALUE);
        startActivityForResult(i, RC_WAITING_ROOM);
        //updateRoom(room);
    }

    @Override
    public void onRoomConnected(int statusCode, Room room) {
        Log.d(TAG, "onRoomConnected(statusCode: " + statusCode + ", room: " + room + ")");

        if (statusCode != GamesStatusCodes.STATUS_OK) {
            Log.d(TAG, "onRoomConnected Error");
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        this.room = room;
        MyAppApplication.room = room;                                                               //REMOVE WHEN NECESSARY
        showWaitingRoom(room);
    }
    //for info about status codes https://developers.google.com/android/reference/com/google/android/gms/games/GamesStatusCodes#constant-summary

    @Override
    public void onLeftRoom(int statusCode, String roomId){

    }

    //end of stuff for RoomUpdateListener









    //new stuff

    //quick game button behaviour
    public void startQuickGame(View view) {
        Log.d(TAG, "StartQuickGame Entered");
        // auto-match criteria to invite one random automatch opponent.
        final int minNumOfOpponents = 1;
        final int maxNumOfOpponents = 3;
        Bundle am = RoomConfig.createAutoMatchCriteria(minNumOfOpponents, maxNumOfOpponents, 0);


        // build the room config:
        RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();

        roomConfigBuilder.setAutoMatchCriteria(am);
        roomConfigBuilder.setMessageReceivedListener(this); //necessary for messaging

        RoomConfig roomConfig = roomConfigBuilder.build();

        // create room:
        Games.RealTimeMultiplayer.create(mGoogleApiClient, roomConfig);                           //REMOVE WHEN NECESSARY
//        Games.RealTimeMultiplayer.create(myApp.getClient(), roomConfig);

        // prevent screen from sleeping during handshake
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Log.d(TAG, "StartQuickGame run till end");
    }


    // request code for the "select players" UI
    // can be any number as long as it's unique
    final static int RC_SELECT_PLAYERS = 10000;



    public void invitePlayers(View view){
        Log.d(TAG, "invitePlayers entered");
        Intent intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(mGoogleApiClient, 1, 3);
        startActivityForResult(intent, RC_SELECT_PLAYERS);
        Log.d(TAG, "invitePlayers done");
    }
//    for invite players only


    void showWaitingRoom(Room room) {
        Log.d(TAG, "showWaitingRoom entered");
//        Intent intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(mGoogleApiClient, 1, 3);
//        startActivityForResult(intent, RC_SELECT_PLAYERS);

        Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(mGoogleApiClient, room, 2);           //REMOVE WHEN REQUIRED
        startActivityForResult(i, RC_WAITING_ROOM); //returns onActivityResult

        Log.d(TAG, "showWaitingRoom done");
    }


    final static int RC_INVITATION_INBOX = 10001;

    //show invitations button
    public void showInvitations(View view){
        Log.d(TAG, "showInvitations entered");
        Intent intent = Games.Invitations.getInvitationInboxIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_INVITATION_INBOX);
        Log.d(TAG, "showInvitations done");
    }


    @Override
    public void onActivityResult(int request, int response, Intent data) {
        Log.d(TAG, "onActivityResult(request: " + request + ", response: " + response + ")");
        if (request == RC_INVITATION_INBOX) {
            Log.d(TAG, "OnActivityResult RC_INVITATION_INBOX");
            if (response != Activity.RESULT_OK) {
                // canceled
                return;
            }

            // get the selected invitation
            Bundle extras = data.getExtras();
            Invitation invitation =
                    extras.getParcelable(Multiplayer.EXTRA_INVITATION);

            // accept it!
            RoomConfig roomConfig = makeBasicRoomConfigBuilder()
                    .setInvitationIdToAccept(invitation.getInvitationId())
                    .build();
            Games.RealTimeMultiplayer.join(mGoogleApiClient, roomConfig);

            // prevent screen from sleeping during handshake
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            // go to game screen
            Log.d(TAG, "Enter Game Screen");
        }

        if (request == RC_SELECT_PLAYERS) {
            if (response != Activity.RESULT_OK) {
                // user canceled
                return;
            }

            // get the invitee list
            Bundle extras = data.getExtras();
            final ArrayList<String> invitees =
                    data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // get auto-match criteria
            Bundle autoMatchCriteria = null;
            int minAutoMatchPlayers =
                    data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoMatchPlayers =
                    data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

            if (minAutoMatchPlayers > 0) {
                autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
                        minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            } else {
                autoMatchCriteria = null;
            }

            // create the room and specify a variant if appropriate
            RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
            roomConfigBuilder.addPlayersToInvite(invitees);
            if (autoMatchCriteria != null) {
                roomConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
            }
            RoomConfig roomConfig = roomConfigBuilder.build();
            Games.RealTimeMultiplayer.create(mGoogleApiClient, roomConfig);

            // prevent screen from sleeping during handshake
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        if (request == RC_WAITING_ROOM) {
            if (response == Activity.RESULT_OK) {
                // (start game)
                Log.d(TAG, "waiting room result ok");



                myApp.setClient(mGoogleApiClient);
                myApp.setRoom(room);
                Log.d(TAG, "Room IS: " + room.getRoomId());
                Log.d(TAG, "mGoogleApiClient IS: " + mGoogleApiClient);

                Player myself = new Player(room.getParticipantId(Games.Players.getCurrentPlayerId(myApp.getClient())));
                Intent intent = new Intent(this, AndroidLauncher.class);
                intent.putExtra("myself", myself);

                playerMap.put(room.getParticipantId(Games.Players.getCurrentPlayerId(myApp.getClient())), 0);

                startActivity(intent);


                Log.d(TAG, "waiting room result ok done");
            }
            else if (response == Activity.RESULT_CANCELED) {
                // Waiting room was dismissed with the back button.

                Log.d(TAG, "waiting room result cancelled");
                //Games.RealTimeMultiplayer.leave(mGoogleApiClient, null, mRoomId);
                //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                if (mRoomId != null) {
                    Games.RealTimeMultiplayer.leave(mGoogleApiClient, this, mRoomId);                  //REMOVE WHEN NECESSARY
//                    Games.RealTimeMultiplayer.leave(myApp.getClient(), this, mRoomId);
                    mRoomId = null;
                    Log.d(TAG, "waiting room result cancelled if");
                    //finish();

                } else {
                    Log.d(TAG, "waiting room result cancelled else");
                    //finish();
                }
                Log.d(TAG, "waiting room result cancelled done");
            }
            else if (response == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
                // player wants to leave the room.
                Log.d(TAG, "waiting room result left room");
                Games.RealTimeMultiplayer.leave(mGoogleApiClient, null, mRoomId);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                Log.d(TAG, "waiting room result left room done");
            }
        }

    }



    @Override
    public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage){

        String msg = new String(realTimeMessage.getMessageData());
        Log.d(TAG,"Received: "+msg);
        String[] words = msg.split(" ");

        //general message: INIT PLAYERID
        if(words[0].equals("INIT")){
            String id = words[1];
            Player player = new Player(id);

            synchronized (GameWorld.players){
                if(GameWorld.players.size()==0){
                    playerMap.put("null",0);
                    GameWorld.players.add(new Player("null"));
                }

                playerMap.put(id, GameWorld.players.size());
                GameWorld.players.add(player);

            }

        }
        //Update player location
        //sent to both player and server
        //PLAYER ID X Y
        else if(words[0].equals("PLAYER")){
            String id = words[1];
            float x = Float.parseFloat(words[2]);
            float y = Float.parseFloat(words[3]);
            Player player = GameWorld.players.get(playerMap.get(id));
            player.setPosition(new Vector2(x,y));
        }


        //ITEM ID X Y TYPE
        //TYPE: SHIELD, SPEEDUP, PLUS1, MUL2
        //ITEM ID RM
        else if(words[0].equals("ITEM")){
            String id = words[1];
            if(words[2].equals("RM")){
                Log.d(TAG,"RM CONDITION");

                Item toRemove = itemMap.get(id);
                itemMap.remove(id);

                try{
                    toRemove.toDestroy = true;
                }catch(NullPointerException e){
                    Log.d(TAG, msg);

                }
                Log.d(TAG,"REMOVE ITEM");


            }
            else{
                float x = Float.parseFloat(words[2]);
                float y = Float.parseFloat(words[3]);
                String type = words[4];
                Item toAdd;
                if(type.equals("shield")) toAdd = new Shield(x,y);
                else if(type.equals("speedUp")) toAdd =new SpeedUp(x,y);
                else {
                    String operation = type.substring(0,type.length()-1);
                    int value = Character.getNumericValue(type.charAt(type.length() - 1));
                    toAdd = new NumberAndOperand(operation,value,x,y);
                }
                itemMap.put(id,toAdd);
                GameWorld.items.add(toAdd);
                Log.d(TAG,"RECEIVE: item added");
            }

        }

        //Update score
        else if(words[0].equals("SCORE")){
            String id = words[1];
            Log.d(TAG,"SCORE PLAYER ID: "+id);
            int score = Integer.parseInt(words[2]);
            Player player = GameWorld.players.get(playerMap.get(id));
            player.setCurrentValue(score);

        }

        else if(words[0].equals("ENDSCORE")){
            int endscore = Integer.parseInt(words[1]);
            GameWorld.endScore = endscore;
            Log.d(TAG, "Received ENDSCORE "+ endscore);
        }
        else if (words[0].equals("READY")){
            playerCounter++;
            GameWorld.playerCounter = playerCounter;
            Log.d(TAG, "Received READY, playerCounter :" + playerCounter);
        }


    }


}
