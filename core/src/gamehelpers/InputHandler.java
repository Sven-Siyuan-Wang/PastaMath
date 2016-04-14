package gamehelpers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import gameconstants.GameConstants;
import gameobjects.Player;
import gameworld.GameRenderer;

/**
 * Created by Hazel on 28/2/2016.
 */
public class InputHandler implements InputProcessor {
    private Player myPlayer;
    int joyX;
    int joyY;
    boolean touched;
    GameRenderer renderer;



    public InputHandler(Player player, Stage stage, GameRenderer renderer) {
        this.myPlayer = player;
        this.renderer = renderer;

    }

    public void render() {
        //Render joystick here
        renderer.renderJoystick(joyX, joyY);
        if(touched) {
            renderer.renderJoystickKnob();
            renderer.setTouched(true);
        } else {
            renderer.setTouched(false);
        }

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
    /* Initialize joystick
     */
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        myPlayer.onClick(screenX, screenY);
        touched = true;
        Gdx.app.log("InputHandler", "screenX is " + screenX + " and screenY is " + screenY);
        if (screenX > (1155 + 50)*GameConstants.SCALE_X) {
            myPlayer.setRight(true);
            myPlayer.setLeft(false);
        } else if (screenX < (1155 - 50)*GameConstants.SCALE_X) {
            myPlayer.setLeft(true);
            myPlayer.setRight(false);
        } else {
            myPlayer.setRight(false);
            myPlayer.setLeft(false);
        }
        if (screenY > (125 + 50)*GameConstants.SCALE_Y) {
            myPlayer.setUp(true);
            myPlayer.setDown(false);
        } else if (screenY < (125 - 50)*GameConstants.SCALE_Y) {
            myPlayer.setDown(true);
            myPlayer.setUp(false);
        } else {
            myPlayer.setUp(false);
            myPlayer.setDown(false);
        }

        return false;
    }

    @Override
    /* Stop moving
     */
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("InputHandler", "not clicked anymore");
        myPlayer.onNotClick();
        touched = false;
        return false;
    }

    @Override
    /* If screenX > joyX+50, move right
       If screenx < joyX-50, move left
       If screenY > joyY+50, move up
       If screenY < joyY-50, move down
     */
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touched = true;
//            Gdx.app.log("InputHandler", "touch is dragged to " + screenX + ", " + screenY);
        if (screenX > (1155 + 50)*GameConstants.SCALE_X) {
            myPlayer.setRight(true);
            myPlayer.setLeft(false);
        } else if (screenX < (1155 - 50)*GameConstants.SCALE_X) {
            myPlayer.setLeft(true);
            myPlayer.setRight(false);
        } else {
            myPlayer.setRight(false);
            myPlayer.setLeft(false);
        }
        if (screenY > (720-125 + 50)*GameConstants.SCALE_Y) {
            myPlayer.setUp(true);
            myPlayer.setDown(false);
        } else if (screenY < (720-125 - 50)*GameConstants.SCALE_Y) {
            myPlayer.setDown(true);
            myPlayer.setUp(false);
        } else {
            myPlayer.setUp(false);
            myPlayer.setDown(false);
        }
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