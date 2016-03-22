package com.mygdx.game.network;

/**
 * Created by Keith on 18/3/16.
 */
public interface PlayServices {
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement();
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
