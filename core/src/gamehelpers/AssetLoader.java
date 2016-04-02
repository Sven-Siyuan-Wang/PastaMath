package gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;

import gameworld.GameObject;

/**
 * Created by Hazel on 29/2/2016.
 */
public class AssetLoader {
    public static Texture texture, speedUp, player, shield;
    public static Texture gameBackground, gameOverBackground;

    public static Texture up, down, left, right;
    public static Texture touchBackground, touchKnob;

    public static HashMap<String,Texture> textures = new HashMap();

    public static void load() {
        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        player = new Texture(Gdx.files.internal("data/Character/Character1.png"));
        textures.put("player",player);

        speedUp = new Texture(Gdx.files.internal("data/speedup.png"));
        textures.put("speedUp",speedUp);

        shield = new Texture(Gdx.files.internal("data/shield.png"));
        textures.put("shield",shield);





        Texture mul2 = new Texture(Gdx.files.internal("data/NormalPickups/multiply2.png"));
        Texture mul3 = new Texture(Gdx.files.internal("data/NormalPickups/multiply3.png"));
        Texture plus1 = new Texture(Gdx.files.internal("data/NormalPickups/plus1.png"));
        Texture plus2 = new Texture(Gdx.files.internal("data/NormalPickups/plus2.png"));
        Texture plus3 = new Texture(Gdx.files.internal("data/NormalPickups/plus3.png"));
        Texture plus4 = new Texture(Gdx.files.internal("data/NormalPickups/plus4.png"));
        Texture plus5 = new Texture(Gdx.files.internal("data/NormalPickups/plus5.png"));
        Texture plus6 = new Texture(Gdx.files.internal("data/NormalPickups/plus6.png"));
        Texture plus7 = new Texture(Gdx.files.internal("data/NormalPickups/plus7.png"));
        Texture plus8 = new Texture(Gdx.files.internal("data/NormalPickups/plus8.png"));
        Texture plus9 = new Texture(Gdx.files.internal("data/NormalPickups/plus9.png"));

        textures.put("mul2",mul2);
        textures.put("mul3",mul3);
        textures.put("plus1",plus1);
        textures.put("plus2",plus2);
        textures.put("plus3",plus3);
        textures.put("plus4",plus4);
        textures.put("plus5",plus5);
        textures.put("plus6",plus6);
        textures.put("plus7",plus7);
        textures.put("plus8",plus8);
        textures.put("plus9",plus9);

        up = new Texture(Gdx.files.internal("data/Buttons/up.png"));
        down = new Texture(Gdx.files.internal("data/Buttons/down.png"));
        left = new Texture(Gdx.files.internal("data/Buttons/left.png"));
        right = new Texture(Gdx.files.internal("data/Buttons/right.png"));

        touchBackground = new Texture(Gdx.files.internal("data/Buttons/touchbackground.png"));
        touchKnob = new Texture(Gdx.files.internal("data/Buttons/knob.png"));

        gameBackground = new Texture(Gdx.files.internal("data/Background/gamebg.png"));
        gameOverBackground = new Texture(Gdx.files.internal("data/Background/GameOverbg.png"));



    }

    public static void dispose() {
        //dispose of texture when finished
        texture.dispose();

    }


}
