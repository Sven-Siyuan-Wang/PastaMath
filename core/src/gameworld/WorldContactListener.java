package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;

import gameobjects.Item;
import gameobjects.NumberAndOperand;
import gameobjects.Player;
import gameobjects.Shield;

/**
 * Created by WSY on 12/4/16.
 */
public class WorldContactListener implements ContactListener {
    //private static ArrayList items;
    private static int player_body_to_remove= 0;
    private static int item_body_to_remove= 0;


    ArrayList<Player> holding_2_players = new ArrayList<Player>();

    final String TAG = "WorldContactListener";
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log(TAG, "beginContact");
        if(GameWorld.isOwner){
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();

            /*
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
            */
            //ACTIONS FOR PLAYER COLLIDING
            if (players_collided(fixtureA, fixtureB)) {
                Player playerA = (Player) fixtureA.getUserData();
                Player playerB = (Player) fixtureB.getUserData();
                holding_2_players.add(playerA);
                holding_2_players.add(playerB);
                for (Player each_player : holding_2_players) {
                    if (each_player.getShielded()) {
                        each_player.update_collision_count();
                    } else {
                        each_player.decreaseScoreUponKnock();
                    }
                    //gameWorld.sendPlayerScore(each_player);
                    //todo: reverse player
                    each_player.b2body.applyForce(2.0f, 2.0f, each_player.getX(), each_player.getY(), true);
                }

                //todo: bouncing effect
            }
            //ACTIONS FOR PLAYER-ITEM OR ITEM-PLAYER COLLISION
            if (playerA_itemB(fixtureA, fixtureB)) {
                Player player = (Player) fixtureA.getUserData();
                Item item = (Item) fixtureB.getUserData();
                item.update_player_situation(player);
                //obtain index of item to remove its corresponding Body
                item_body_to_remove = GameWorld.items.indexOf(item);
                GameWorld.items.remove(item);
                //GameWorld.sendRemoveItem(item);
                //todo: remove corresponding Body of item
                //GameWorld.current_item_bodies.removeIndex(item_body_to_remove);
                //GameWorld.sendPlayerScore(player);
            }
            if (itemA_playerB(fixtureA, fixtureB)) {
                Player player = (Player) fixtureB.getUserData();
                Item item = (Item) fixtureA.getUserData();
                item.update_player_situation(player);

                GameWorld.items.remove(item);
                //gameWorld.sendRemoveItem(item);
                //gameWorld.sendPlayerScore(player);
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


    //todo: functions to check for what type of objects collided
    public boolean players_collided(Fixture a, Fixture b) {
        return (a.getUserData() instanceof Player && b.getUserData() instanceof Player);
    }


    //todo: make it more efficient
    public boolean playerA_itemB(Fixture a, Fixture b) {
        //checks if a player collected an item
        return (a.getUserData() instanceof Player && b.getUserData() instanceof Item);
    }

    public boolean itemA_playerB(Fixture a, Fixture b) {
        return (a.getUserData() instanceof Item && b.getUserData() instanceof Player);
    }
}
