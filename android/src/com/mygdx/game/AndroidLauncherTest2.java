package com.mygdx.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMultiplayer;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class AndroidLauncherTest2 extends AppCompatActivity
        implements RoomStatusUpdateListener
        ,RoomUpdateListener
        , RealTimeMessageReceivedListener
//        ,RealTimeMultiplayer.ReliableMessageSentCallback
{
    final static String TAG = "PASTAMATH TEST 2";
    TextView textView;
    private static GoogleApiClient mGoogleApiClient;
    //MyAppApplication myApp;
    private static Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Launcher2 onCreate start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_launcher_test2);

        MyAppApplication myApp = ((MyAppApplication)getApplicationContext());
        mGoogleApiClient = myApp.getClient();
        room = myApp.room;


        textView=(TextView)findViewById(R.id.textView2);


        Log.d(TAG, "Launcher2 onCreate end");
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
        mGoogleApiClient.disconnect();
        Log.d(TAG, "onStop end, googleapiclient disconnect");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_android_launcher_test2, menu);
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




    public void buttonPressed(View view) {
        Log.d(TAG, "button pressed");
        String str = "pressed";
        byte[] message = str.getBytes();

        //Room room = NetworkActivity.room;

        ArrayList<Participant> mParticipants = null;
        mParticipants = room.getParticipants();

        String mMyId = mParticipants.get(0).getParticipantId();
        //String mMyId = Games.Players.getCurrentPlayer(NetworkActivity.myApp.getClient()).getPlayerId();
        Log.d(TAG, "MY ID IS: " + mMyId);
        Log.d(TAG, "Room IS: " + room.getRoomId());
        Log.d(TAG, "mGoogleApiClient IS: " + mGoogleApiClient);

        for (Participant p : mParticipants) {
            //if (!p.getParticipantId().equals(mMyId)) {
            Log.d(TAG, "sending message to " + p.getParticipantId());
            try{

                Games.RealTimeMultiplayer.sendReliableMessage(mGoogleApiClient, null, message,
                        room.getRoomId(), p.getParticipantId());

                //Log.d(TAG, "exception" + onRealTimeMessageSent());
                Log.d(TAG, "sent to " + p.getParticipantId());
            }
            catch(Exception e){
                Log.d(TAG, "exception" + e.toString());
                e.printStackTrace();

            }
            //}
        }

    }

    //onRealTimeMessageSent
//    @Override
//    public void onRealTimeMessageSent(int statusCode, int tokenId, String recipientParticipantId){
//
//    }



    //roomUpdateListener
    public void onJoinedRoom(int statusCode, Room room){}
    public void onLeftRoom(int statusCode, String roomId){}
    public void onRoomConnected(int statusCode, Room room){}
    public void onRoomCreated(int statusCode, Room room){}



    //roomStatusUpdateListener{}
    public void onConnectedToRoom(Room room){}
    public void onDisconnectedFromRoom(Room room){}
    public void onP2PConnected(String participantId){}
    public void onP2PDisconnected(String participantId){}
    public void onPeerDeclined(Room room, List<String> participantIds){}
    public void onPeerInvitedToRoom(Room room, List<String> participantIds){}
    public void onPeerJoined(Room room, List<String> participantIds){}
    public void onPeerLeft(Room room, List<String> participantIds){}
    public void onPeersConnected(Room room, List<String> participantIds){}
    public void onPeersDisconnected(Room room, List<String> participantIds){}
    public void onRoomAutoMatching(Room room){}
    public void onRoomConnecting(Room room){}


    //onRealTimeMessageReceivedListener
    @Override
    public void onRealTimeMessageReceived(RealTimeMessage message){
        Log.d(TAG, "message received");
        byte[] b = message.getMessageData();
        String string = null;
        try{
            string = new String(b, "UTF-8");
        }
        catch(Exception e){
            e.printStackTrace();
        }


        if(string.equals("pressed")){
            Log.d(TAG, "String Received: " + string);
            textView=(TextView)findViewById(R.id.textView2);
            textView.setText("pressed");
        }
    }
}
