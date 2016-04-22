package gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hazel on 29/2/2016.
 */
public class AssetLoader {
    public static Texture texture, speedUp, shield;
    public static Texture gameBackground, gameOverBackground;

    public static Texture startOverButton;
    public static Texture touchBackground, touchKnob;
    public static Texture downright, downleft, upright, upleft, up, down, left, right;

    public static Texture spriteSheet;
    public static Texture characterOverlay;

    public static Texture timeOverlay;

    public static TextureRegion timeOverlay1, timeOverlay2;

    public static Animation timeOverlayAnimation;

    public static TextureRegion char1_1, char1_2, char1_3, char1_4, char1_5, char1_6;
    public static TextureRegion char2_1, char2_2, char2_3, char2_4, char2_5, char2_6;
    public static TextureRegion char3_1, char3_2, char3_3, char3_4, char3_5, char3_6;
    public static TextureRegion char4_1, char4_2, char4_3, char4_4, char4_5, char4_6;

    public static Animation char1Animation, char2Animation, char3Animation, char4Animation;

    public static TextureRegion char1speed_1, char1speed_2, char1speed_3, char1speed_4, char1speed_5, char1speed_6;
    public static TextureRegion char2speed_1, char2speed_2, char2speed_3, char2speed_4, char2speed_5, char2speed_6;
    public static TextureRegion char3speed_1, char3speed_2, char3speed_3, char3speed_4, char3speed_5, char3speed_6;
    public static TextureRegion char4speed_1, char4speed_2, char4speed_3, char4speed_4, char4speed_5, char4speed_6;

    public static Animation char1speedAnimation, char2speedAnimation, char3speedAnimation, char4speedAnimation;

    public static TextureRegion char1shield_1, char1shield_2, char1shield_3, char1shield_4, char1shield_5, char1shield_6;
    public static TextureRegion char2shield_1, char2shield_2, char2shield_3, char2shield_4, char2shield_5, char2shield_6;
    public static TextureRegion char3shield_1, char3shield_2, char3shield_3, char3shield_4, char3shield_5, char3shield_6;
    public static TextureRegion char4shield_1, char4shield_2, char4shield_3, char4shield_4, char4shield_5, char4shield_6;

    public static Animation char1shieldAnimation, char2shieldAnimation, char3shieldAnimation, char4shieldAnimation;

    public static TextureRegion char1ss_1, char1ss_2, char1ss_3, char1ss_4, char1ss_5, char1ss_6;
    public static TextureRegion char2ss_1, char2ss_2, char2ss_3, char2ss_4, char2ss_5, char2ss_6;
    public static TextureRegion char3ss_1, char3ss_2, char3ss_3, char3ss_4, char3ss_5, char3ss_6;
    public static TextureRegion char4ss_1, char4ss_2, char4ss_3, char4ss_4, char4ss_5, char4ss_6;

    public static Animation char1ssAnimation, char2ssAnimation, char3ssAnimation, char4ssAnimation;

    public static TextureRegion char1frozen_1, char1frozen_2, char1frozen_3, char1frozen_4, char1frozen_5, char1frozen_6;
    public static TextureRegion char2frozen_1, char2frozen_2, char2frozen_3, char2frozen_4, char2frozen_5, char2frozen_6;
    public static TextureRegion char3frozen_1, char3frozen_2, char3frozen_3, char3frozen_4, char3frozen_5, char3frozen_6;
    public static TextureRegion char4frozen_1, char4frozen_2, char4frozen_3, char4frozen_4, char4frozen_5, char4frozen_6;

    public static Animation char1frozenAnimation, char2frozenAnimation, char3frozenAnimation, char4frozenAnimation;

    public static HashMap<String,Texture> textures = new HashMap();

    public static ArrayList<Texture> characters = new ArrayList<Texture>();
    public static ArrayList<Animation> characterAnimations = new ArrayList<Animation>();

    public static void load() {
        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Texture player1 = new Texture(Gdx.files.internal("data/Character/Character1.png"));
        characters.add(player1);
        Texture player2 = new Texture(Gdx.files.internal("data/Character/Character2.png"));
        characters.add(player2);
        Texture player3 = new Texture(Gdx.files.internal("data/Character/Character3.png"));
        characters.add(player3);
        Texture player4 = new Texture(Gdx.files.internal("data/Character/Character4.png"));
        characters.add(player4);

        characterOverlay = new Texture(Gdx.files.internal("data/Character/characteroverlay.png"));

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
        downleft = new Texture(Gdx.files.internal("data/Buttons/downleft.png"));
        downright = new Texture(Gdx.files.internal("data/Buttons/downright.png"));
        upleft = new Texture(Gdx.files.internal("data/Buttons/upleft.png"));
        upright = new Texture(Gdx.files.internal("data/Buttons/upright.png"));

        touchBackground = new Texture(Gdx.files.internal("data/Buttons/touchbackground.png"));
        touchKnob = new Texture(Gdx.files.internal("data/Buttons/knob.png"));

        gameBackground = new Texture(Gdx.files.internal("data/Background/gamebg.png"));
        gameOverBackground = new Texture(Gdx.files.internal("data/Background/GameOverbg.png"));

        startOverButton = new Texture(Gdx.files.internal("data/Buttons/startOverButton.png"));

        spriteSheet = new Texture(Gdx.files.internal("data/Character/charspritesheet.png"));

        TextureRegion[] char1 = {char1_1, char1_2, char1_3, char1_4, char1_5, char1_6};
        TextureRegion[] char2 = {char2_1, char2_2, char2_3, char2_4, char2_5, char2_6};
        TextureRegion[] char3 = {char3_1, char3_2, char3_3, char3_4, char3_5, char3_6};
        TextureRegion[] char4 = {char4_1, char4_2, char4_3, char4_4, char4_5, char4_6};

        TextureRegion[] char1speed = {char1speed_1, char1speed_2, char1speed_3, char1speed_4, char1speed_5, char1speed_6};
        TextureRegion[] char2speed = {char2speed_1, char2speed_2, char2speed_3, char2speed_4, char2speed_5, char2speed_6};
        TextureRegion[] char3speed = {char3speed_1, char3speed_2, char3speed_3, char3speed_4, char3speed_5, char3speed_6};
        TextureRegion[] char4speed = {char4speed_1, char4speed_2, char4speed_3, char4speed_4, char4speed_5, char4speed_6};

        TextureRegion[] char1shield = {char1shield_1, char1shield_2, char1shield_3, char1shield_4, char1shield_5, char1shield_6};
        TextureRegion[] char2shield = {char2shield_1, char2shield_2, char2shield_3, char2shield_4, char2shield_5, char2shield_6};
        TextureRegion[] char3shield = {char3shield_1, char3shield_2, char3shield_3, char3shield_4, char3shield_5, char3shield_6};
        TextureRegion[] char4shield = {char4shield_1, char4shield_2, char4shield_3, char4shield_4, char4shield_5, char4shield_6};

        TextureRegion[] char1ss = {char1ss_1, char1ss_2, char1ss_3, char1ss_4, char1ss_5, char1ss_6};
        TextureRegion[] char2ss = {char2ss_1, char2ss_2, char2ss_3, char2ss_4, char2ss_5, char2ss_6};
        TextureRegion[] char3ss = {char3ss_1, char3ss_2, char3ss_3, char3ss_4, char3ss_5, char3ss_6};
        TextureRegion[] char4ss = {char4ss_1, char4ss_2, char4ss_3, char4ss_4, char4ss_5, char4ss_6};

        TextureRegion[] char1frozen = {char1frozen_1, char1frozen_2, char1frozen_3, char1frozen_4, char1frozen_5, char1frozen_6};
        TextureRegion[] char2frozen = {char2frozen_1, char2frozen_2, char2frozen_3, char2frozen_4, char2frozen_5, char2frozen_6};
        TextureRegion[] char3frozen = {char3frozen_1, char3frozen_2, char3frozen_3, char3frozen_4, char3frozen_5, char3frozen_6};
        TextureRegion[] char4frozen = {char4frozen_1, char4frozen_2, char4frozen_3, char4frozen_4, char4frozen_5, char4frozen_6};

        int count = 0;
        for(int i = 0; i<6; i++) {
            char1[i] = new TextureRegion(spriteSheet, count, 0, 100, 100);
            char2[i] = new TextureRegion(spriteSheet, count, 100, 100, 100);
            char3[i] = new TextureRegion(spriteSheet, count, 200, 100, 100);
            char4[i] = new TextureRegion(spriteSheet, count, 300, 100, 100);
            char1speed[i] = new TextureRegion(spriteSheet, count, 400, 100, 100);
            char2speed[i] = new TextureRegion(spriteSheet, count, 500, 100, 100);
            char3speed[i] = new TextureRegion(spriteSheet, count, 600, 100, 100);
            char4speed[i] = new TextureRegion(spriteSheet, count, 700, 100, 100);
            char1shield[i] = new TextureRegion(spriteSheet, count, 800, 100, 100);
            char2shield[i] = new TextureRegion(spriteSheet, count, 900, 100, 100);
            char3shield[i] = new TextureRegion(spriteSheet, count, 1000, 100, 100);
            char4shield[i] = new TextureRegion(spriteSheet, count, 1100, 100, 100);
            char1ss[i] = new TextureRegion(spriteSheet, count, 1200, 100, 100);
            char2ss[i] = new TextureRegion(spriteSheet, count, 1300, 100, 100);
            char3ss[i] = new TextureRegion(spriteSheet, count, 1400, 100, 100);
            char4ss[i] = new TextureRegion(spriteSheet, count, 1500, 100, 100);
            char1frozen[i] = new TextureRegion(spriteSheet, count, 1600, 100, 100);
            char2frozen[i] = new TextureRegion(spriteSheet, count, 1700, 100, 100);
            char3frozen[i] = new TextureRegion(spriteSheet, count, 1800, 100, 100);
            char4frozen[i] = new TextureRegion(spriteSheet, count, 1900, 100, 100);
            count +=100;
        }

        float frameTime = 0.09f;

        char1Animation = new Animation(frameTime, char1);
        char2Animation = new Animation(frameTime, char2);
        char3Animation = new Animation(frameTime, char3);
        char4Animation = new Animation(frameTime, char4);

        char1speedAnimation = new Animation(frameTime, char1speed);
        char2speedAnimation = new Animation(frameTime, char2speed);
        char3speedAnimation = new Animation(frameTime, char3speed);
        char4speedAnimation = new Animation(frameTime, char4speed);

        char1shieldAnimation = new Animation(frameTime, char1shield);
        char2shieldAnimation = new Animation(frameTime, char2shield);
        char3shieldAnimation = new Animation(frameTime, char3shield);
        char4shieldAnimation = new Animation(frameTime, char4shield);

        char1ssAnimation = new Animation(frameTime, char1ss);
        char2ssAnimation = new Animation(frameTime, char2ss);
        char3ssAnimation = new Animation(frameTime, char3ss);
        char4ssAnimation = new Animation(frameTime, char4ss);

        char1frozenAnimation = new Animation(frameTime, char1frozen);
        char2frozenAnimation = new Animation(frameTime, char2frozen);
        char3frozenAnimation = new Animation(frameTime, char3frozen);
        char4frozenAnimation = new Animation(frameTime, char4frozen);

        characterAnimations.add(char1Animation);
        characterAnimations.add(char2Animation);
        characterAnimations.add(char3Animation);
        characterAnimations.add(char4Animation);
        characterAnimations.add(char1speedAnimation);
        characterAnimations.add(char2speedAnimation);
        characterAnimations.add(char3speedAnimation);
        characterAnimations.add(char4speedAnimation);
        characterAnimations.add(char1shieldAnimation);
        characterAnimations.add(char2shieldAnimation);
        characterAnimations.add(char3shieldAnimation);
        characterAnimations.add(char4shieldAnimation);
        characterAnimations.add(char1ssAnimation);
        characterAnimations.add(char2ssAnimation);
        characterAnimations.add(char3ssAnimation);
        characterAnimations.add(char4ssAnimation);
        characterAnimations.add(char1frozenAnimation);
        characterAnimations.add(char2frozenAnimation);
        characterAnimations.add(char3frozenAnimation);
        characterAnimations.add(char4frozenAnimation);

        for(Animation a: characterAnimations) {
            a.setPlayMode(Animation.PlayMode.LOOP);
        }

        timeOverlay = new Texture(Gdx.files.internal("data/Character/timeoverlay.png"));
        timeOverlay1 = new TextureRegion(timeOverlay, 0, 0, 100, 100);
        timeOverlay2 = new TextureRegion(timeOverlay, 100, 0, 100, 100);
        TextureRegion[] timeOverlayArray = {timeOverlay1, timeOverlay2};

        timeOverlayAnimation = new Animation(0.5f, timeOverlayArray);
        timeOverlayAnimation.setPlayMode(Animation.PlayMode.LOOP);

    }

    public static void dispose() {
        //dispose of texture when finished
        texture.dispose();

    }


}
