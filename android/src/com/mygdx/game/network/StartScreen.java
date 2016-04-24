package com.mygdx.game.network;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
//import android.support.v7.app.AppCompatActivity;


/**
 * Created by Keith on 18/3/16.
 */
public class StartScreen extends AndroidApplication {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        //initialize(new CatMath(), config);
        //setContentView(R.layout.activity_main);
    }
}
