package gameobjects;

import com.badlogic.gdx.math.Rectangle;
/**
 * Created by WSY on 18/3/16.
 */
public interface iPickUp {
    public void destroy();
    public void update(float delta);
    public float getX();
    public float getY();
    public int getWidth();
    public int getHeight();
    public Rectangle getCollider();
}
