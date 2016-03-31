/**
 * Created by WSY on 30/3/16.
 */
package com.mygdx.game;
public interface PlayServices{

        public void signIn();
        public void signOut();
        public void rateGame();
        public void unlockAchievement();
        public void submitScore(int highScore);
        public void showAchievement();
        public void showScore();
        public boolean isSignedIn();
        public void startQuickGame();
        public void sendToServer(String message);
        public void sendToPlayer(String message);
}
