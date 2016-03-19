package gamehelpers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import gameobjects.Player;

/**
 * Created by Hazel on 28/2/2016.
 */
public class InputHandler implements InputProcessor {
    private Player myPlayer;
    Touchpad touchpad;
    Touchpad.TouchpadStyle touchpadStyle;


    public InputHandler(Player player) {
        this.myPlayer = player;
    }

    

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case (Input.Keys.DOWN):
                //down
                myPlayer.setUp(true);
                break;
            case (Input.Keys.UP):
                //up
                myPlayer.setDown(true);
                break;
            case (Input.Keys.LEFT):
                //left
                myPlayer.setLeft(true);
                break;
            case (Input.Keys.RIGHT):
                //right
                myPlayer.setRight(true);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case (Input.Keys.DOWN):
                //down
                myPlayer.setUp(false);
                break;
            case (Input.Keys.UP):
                //up
                myPlayer.setDown(false);
                break;
            case (Input.Keys.LEFT):
                //left
                myPlayer.setLeft(false);
                break;
            case (Input.Keys.RIGHT):
                //right
                myPlayer.setRight(false);
                break;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        myPlayer.onClick();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
