package com.mygdx.game;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.multiplayer.realtime.Room;

/**
 * Created by Keith on 27/3/16.
 */
public class MyAppApplication extends Application {
    final static String TAG = "PASTAMATH MyApp";
    public static GoogleApiClient mGoogleApiClient;
    //private static  MyAppApplication mInstance;
    public static Room room;

    public synchronized GoogleApiClient getClient() {
        Log.d(TAG, "getClient");
        return this.mGoogleApiClient;
    }

    public synchronized void setClient(GoogleApiClient client){
        Log.d(TAG, "setClient");
        this.mGoogleApiClient = client;
    }

    public synchronized Room getRoom(){
        Log.d(TAG, "getRoom");
        return this.room;
    }
    public synchronized void setRoom(Room room){
        Log.d(TAG, "setRoom");
        this.room = room;
    }
}
