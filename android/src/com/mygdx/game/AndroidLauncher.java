package com.mygdx.game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.example.games.basegameutils.GameHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gameobjects.Item;
import gameobjects.NumberAndOperand;
import gameobjects.Player;
import gameobjects.Shield;
import gameobjects.SpeedUp;
import gameworld.GameWorld;

public class AndroidLauncher extends AndroidApplication implements PlayServices, RealTimeMessageReceivedListener,
		RoomUpdateListener, RoomStatusUpdateListener {

	//keith network
	private GameHelper gameHelper;
	private final static int requestCode = 1;
	private String mRoomId = null;
	// The participants in the currently active game
	ArrayList<Participant> mParticipants = null;

	String myId = null;
	String ownerId = null;
	final static int RC_WAITING_ROOM = 10002;
	final static String TAG="AndroidLauncher";

    private static HashMap<String,Integer> playerMap = new HashMap<>();
	private  static HashMap<String, Item> itemMap = new HashMap<>();

	public static GoogleApiClient mGoogleApiClient;

	public static Room room = null;

	public static MyAppApplication myApp;

	//keith network end

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		Log.e(TAG, "onCreate");
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGdxGame(this), config); // updated by siyuan

//		startQuickGame();

		//keith network
		Log.e(TAG, "onCreate ends");
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }
		};

		gameHelper.setup(gameHelperListener);


		MyAppApplication myApp = ((MyAppApplication)getApplicationContext());
		mGoogleApiClient = myApp.getClient();
		room = myApp.room;
		//keith network and






	} //end of onCreate


	//keith network
	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame()
	{
		String str = "Your PlayStore Link";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement()
	{
//		Games.Achievements.unlock(gameHelper.getApiClient(),
//				getString(R.string.achievement_dum_dum));
	}

	@Override
	public void submitScore(int highScore)
	{
		if (isSignedIn() == true)
		{
//			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
//					getString(R.string.leaderboard_highest), highScore);
		}
	}

	@Override
	public void showAchievement()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode); //, getString(R.string.achievement_dum_dum)
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showScore()
	{
		if (isSignedIn() == true)
		{
//			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
//					getString(R.string.leaderboard_highest)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return gameHelper.isSignedIn();
	}

	@Override
	public void startQuickGame() {
		Log.e(TAG,"startQuickGame");
		final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 2;
		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
				MAX_OPPONENTS, 0);
		RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(this);
		roomConfigBuilder.setMessageReceivedListener(this);
		roomConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
		RoomConfig roomConfig= roomConfigBuilder.build();

		//create room:
		Games.RealTimeMultiplayer.create(gameHelper.getApiClient(), roomConfig);

		//NEXT: onRoomCreated

	}

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {
        String msg = new String(realTimeMessage.getMessageData());
        String[] words = msg.split(" ");

        //general message
        if(words[0].equals("INIT")){
            String id = words[1];
            Player player = new Player(id);
            playerMap.put(id,GameWorld.players.size());
            GameWorld.players.add(player);
        }

        //sent to both player and server
        else if(words[0].equals("PLAYER")){
            String id = words[1];
            float x = Float.parseFloat(words[2]);
            float y = Float.parseFloat(words[3]);
            Player player = GameWorld.players.get(playerMap.get(id));
            player.setX(x);
            player.setY(y);
        }

        //TODO: ONE MORE CASE
        //ITEM ID X Y TYPE
		//TYPE: SHIELD, SPEEDUP, PLUS1, MUL2
        //ITEM ID RM
		else if(words[0].equals("ITEM")){
			String id = words[1];
			if(words[2].equals("RM")){
				Item toRemove = itemMap.get(id);
				itemMap.remove(id);
				toRemove.destroy();
			}
			else{
				float x = Float.parseFloat(words[2]);
				float y = Float.parseFloat(words[3]);
				String type = words[4];
				Item toAdd;
				if(type.equals("SHIELD")) toAdd = new Shield(x,y);
				else if(type.equals("SPEEDUP")) toAdd =new SpeedUp(x,y);
				else {
					String operation = type.substring(0,type.length()-1);
					int value = Character.getNumericValue(type.charAt(type.length() - 1));
					toAdd = new NumberAndOperand(operation,value,x,y);
				}
				GameWorld.items.add(toAdd);
			}

		}

	}

	public void sendToServer(String message){
		byte[] mMsgBuf = message.getBytes();
		Games.RealTimeMultiplayer.sendUnreliableMessage(gameHelper.getApiClient(),mMsgBuf,mRoomId,
				room.getCreatorId());
	}

	public void sendToPlayer(String message){
		byte[] mMsgBuf = message.getBytes();
		if (mParticipants!=null){
			for (Participant p:mParticipants){
				if (!p.getParticipantId().equals(myId)){
					Games.RealTimeMultiplayer.sendUnreliableMessage(gameHelper.getApiClient(),mMsgBuf,mRoomId,
							p.getParticipantId());
				}
			}
		}
	}

	@Override
	public void onRoomConnecting(Room room) {

	}

	@Override
	public void onRoomAutoMatching(Room room) {

	}

	@Override
	public void onPeerInvitedToRoom(Room room, List<String> list) {

	}

	@Override
	public void onPeerDeclined(Room room, List<String> list) {

	}

	@Override
	public void onPeerJoined(Room room, List<String> list) {

	}

	@Override
	public void onPeerLeft(Room room, List<String> list) {

	}

	@Override
	public void onConnectedToRoom(Room room) {
		myId=room.getParticipantId(Games.Players.getCurrentPlayerId(gameHelper.getApiClient()));

		if (mRoomId==null){
			mRoomId=room.getRoomId();
		}

		// print out the list of participants (for debug purposes)
		Log.d(TAG, "Room ID: " + mRoomId);
		Log.d(TAG, "My ID " + myId);
		Log.d(TAG, "<< CONNECTED TO ROOM>>");

	}

	@Override
	public void onDisconnectedFromRoom(Room room) {

	}

	@Override
	public void onPeersConnected(Room room, List<String> list) {

	}

	@Override
	public void onPeersDisconnected(Room room, List<String> list) {

	}

	@Override
	public void onP2PConnected(String s) {

	}

	@Override
	public void onP2PDisconnected(String s) {

	}

	@Override
	public void onRoomCreated(int statusCode, Room room) {
		if(statusCode != GamesStatusCodes.STATUS_OK){
			showGameError();
			return;
		}
		mRoomId = room.getRoomId();
		// show the waiting room UI
		Intent i= Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, Integer.MAX_VALUE);
		startActivityForResult(i, RC_WAITING_ROOM);




	}

	@Override
	public void onJoinedRoom(int statusCode, Room room) {
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			showGameError();
			return;
		}

		// show the waiting room UI
		Intent i= Games.RealTimeMultiplayer.getWaitingRoomIntent(gameHelper.getApiClient(), room, Integer.MAX_VALUE);
		startActivityForResult(i, RC_WAITING_ROOM);
	}


	@Override
	public void onLeftRoom(int i, String s) {

	}

	@Override
	public void onRoomConnected(int statusCode, Room room) {
		Log.e(TAG,"onRoomConnected");
		if (statusCode != GamesStatusCodes.STATUS_OK) {
			showGameError();
			return;
		}
		mParticipants=room.getParticipants();
		//myId=room.getParticipantId(Games.Players.getCurrentPlayerId(gameHelper.getApiClient()));
		ownerId = room.getCreatorId();
		GameWorld.isOwner = myId.equals(ownerId);
        GameWorld.myself = new Player(myId);
        GameWorld.players.add(GameWorld.myself);

        playerMap.put(myId, new Integer(1));
		Log.e(TAG,"Got isOwner");



	}


	void showGameError() {
		BaseGameUtils.makeSimpleDialog(this, "error");
	}
}
