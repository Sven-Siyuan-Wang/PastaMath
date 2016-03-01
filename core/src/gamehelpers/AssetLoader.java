package gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import gameworld.GameObject;

/**
 * Created by Hazel on 29/2/2016.
 */
public class AssetLoader {
    public static Texture texture, speedUp;
    public static TextureRegion bg, grass;

    public static Animation playerAnimation;
    public static TextureRegion player, playerUp, playerDown;

    public static TextureRegion skullUp, skullDown, bar;
    public static ArrayList<Texture> textures = new ArrayList<Texture>();

    public static void load() {
        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        bg = new TextureRegion(texture, 0,0,136, 43);
        //arguments: don't flip x, but flip y (since libGDX uses a Y Down coord system)
        bg.flip(false, true);

        playerDown = new TextureRegion(texture, 136, 0, 17, 12);
        playerDown.flip(false, true);

        player = new TextureRegion(texture, 153, 0, 17, 12);
        player.flip(false, true);

        playerUp = new TextureRegion(texture, 170, 0, 17,12);
        playerUp.flip(false, true);

        TextureRegion[] players = {playerDown, player, playerUp};
        //0.06f --> each frame will be 0.06s  long
        playerAnimation = new Animation(0.06f, players);
        //LOOP_PINGPONG --> animation mode where there will be a bounce
        playerAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        //create skullDown by flipping skullUp
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22,3);
        bar.flip(false, true);

        speedUp = new Texture(Gdx.files.internal("data/speedup.png"));
        textures.add(speedUp);



    }

    public static void dispose() {
        //dispose of texture when finished
        texture.dispose();

    }


}
