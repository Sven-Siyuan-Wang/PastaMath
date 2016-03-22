package gamehelpers;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import gameconstants.GameConstants;
import gameobjects.Player;

/**
 * Created by Hazel on 28/2/2016.
 */
public class InputHandler implements InputProcessor {
    private Player myPlayer;
    Touchpad touchpad;
    Touchpad.TouchpadStyle touchpadStyle;
    Skin touchpadskin;
    Drawable touchBackground;
    Drawable touchKnob;
    Stage stage;



    public InputHandler(Player player, Stage stage) {
        this.myPlayer = player;
        touchpadskin = new Skin();
        touchpadskin.add("touchBg", AssetLoader.touchBackground);
        touchpadskin.add("touchKnob", AssetLoader.touchKnob);
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadskin.getDrawable("touchBg");
        touchKnob = touchpadskin.getDrawable("touchKnob");

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;

        touchpad = new Touchpad(10, touchpadStyle);

        touchpad.setBounds(20*GameConstants.SCALE_X, 20*GameConstants.SCALE_Y, 235* GameConstants.SCALE_X, 235*GameConstants.SCALE_Y);

        this.stage = stage;
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
    }

    public void render() {
        myPlayer.setX(myPlayer.getX() + touchpad.getKnobPercentX()*myPlayer.getVelocity());
        myPlayer.setY(myPlayer.getY() + touchpad.getKnobPercentY()*myPlayer.getVelocity());
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
        myPlayer.onClick(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("InputHandler", "not clicked anymore");
        myPlayer.onNotClick();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Gdx.app.log("InputHandler", "touch is dragged to " + screenX + ", " + screenY);
        myPlayer.onClick(screenX, screenY);
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