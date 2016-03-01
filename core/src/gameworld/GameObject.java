package gameworld;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

/**
 * Created by Hazel on 1/3/2016.
 */
public interface GameObject {
    public float getX();
    public float getY();
    public int getWidth();
    public int getHeight();
    public void destroy();
    public Shape2D getCollider();
}
