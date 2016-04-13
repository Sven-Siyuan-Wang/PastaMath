package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.MyGdxGame;

import gameobjects.Item;
import gameobjects.NumberAndOperand;
import gameobjects.Player;
import gameobjects.Shield;

/**
 * Created by WSY on 12/4/16.
 */
public class WorldContactListener implements ContactListener {

    final String TAG = "WorldContactListener";
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log(TAG, "beginContact");
        if(GameWorld.isOwner){
            Fixture fixA = contact.getFixtureA();
            Fixture fixB = contact.getFixtureB();

            int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

            switch (cDef){
                case MyGdxGame.PLAYER_BIT | MyGdxGame.PLAYER_BIT:
                    ((Player) fixA.getUserData()).handleCollision();
                    ((Player) fixB.getUserData()).handleCollision();
                    Gdx.app.log("ContactListener", "Player-player");
                    break;
                case MyGdxGame.PLAYER_BIT | MyGdxGame.ITEM_BIT:
                    if(fixA.getFilterData().categoryBits == MyGdxGame.PLAYER_BIT){
                        ((Item) fixB.getUserData()).update_player_situation((Player) fixA.getUserData());
                        ((Item) fixB.getUserData()).toDestroy = true;

                    }
                    else{
                        ((Item) fixA.getUserData()).update_player_situation((Player) fixB.getUserData());
                        ((Item) fixA.getUserData()).toDestroy = true;
                    }

            }

        }


    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log(TAG, "endContact");


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
