package gamehelpers;

import com.badlogic.gdx.InputProcessor;

import gameworld.GameRenderer;
import screens.GameOverScreen;

/**
 * Created by kisa on 2/4/2016.
 */
public class GameOverInput implements InputProcessor {
    private GameRenderer renderer;
    private GameOverScreen goscreen;

    public GameOverInput(GameRenderer renderer, GameOverScreen screen) {
        this.renderer = renderer;
        this.goscreen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Clicked at " + screenX + ", " + screenY);
        //x -> from 540 to to 740, y -> 260 to 360
        if(screenX <= 740 && screenX >= 540 && screenY<=360 && screenY>=260) {
            goscreen.changeScreen();
        }
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
