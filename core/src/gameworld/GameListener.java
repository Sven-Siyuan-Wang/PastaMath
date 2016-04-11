package gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import java.util.ArrayList;

import gameobjects.Item;
import gameobjects.Player;

/**
 * Created by valerie_tan on 4/9/2016.
 */
public class GameListener implements ContactListener {
    /*
    The Contact Listeners listen for collisions events on a specific fixture.
    The methods are passed a Contact object, which contain information about the two bodies involved. The beginContact method is called when the object overlap another.
    When the objects are no longer colliding, the endContact method is called.
 */
    //TODO: transfer all the gameworld update checking for different collisions into here
    //ANY 2 OBJECTS

    //TODO: need to use inside the update() method? or it constantly listens?

    //todo: IN ORDER TO change information, scores of players or get info from items
    //todo: cast the getUserData to respective objects first from Fixtures
    //todo: fixtures are updated over here, afterwhich actual objects will update their positions based on fixtures

    //todo: determine what to update in Listener(everything else),
    //todo: what to update in GameWorld(set position only?- takes on the body/fixture's positon)
    private GameWorld gameWorld;
    private static ArrayList items;
    private static int player_body_to_remove= 0;
    private static int item_body_to_remove= 0;

    public GameListener(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        items= gameWorld.simple_item_buffer.items_currently_appearing;
        //todo: actions to take for various collisions (make use of existing methods in the object alr)

    }
        ArrayList<Player> holding_2_players = new ArrayList<Player>();

        @Override
        public void beginContact (Contact contact)
        {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();

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
                        each_player.deductChangingScoreUponKnock(gameWorld.deduction_score);
                    }
                    gameWorld.sendPlayerScore(each_player);
                }
            }
            //ACTIONS FOR PLAYER-ITEM OR ITEM-PLAYER COLLISION
            if (playerA_itemB(fixtureA, fixtureB)) {
                Player player = (Player) fixtureA.getUserData();
                Item item = (Item) fixtureB.getUserData();
                item.update_player_situation(player);
                //obtain index of item to remove its corresponding Body
                item_body_to_remove = items.indexOf(item);
                items.remove(item);
                gameWorld.sendRemoveItem(item);
                //todo: remove corresponding Body of item
                gameWorld.current_item_bodies.removeIndex(item_body_to_remove);
                gameWorld.sendPlayerScore(player);
            }
            if (itemA_playerB(fixtureA, fixtureB)) {
                Player player = (Player) fixtureB.getUserData();
                Item item = (Item) fixtureA.getUserData();
                item.update_player_situation(player);

                items.remove(item);
                gameWorld.sendRemoveItem(item);
                gameWorld.sendPlayerScore(player);
            }

        }

        @Override
        public void endContact (Contact contact)
        {

        }

        //@Override

    public void preSolve(Contact contact, Manifold oldManifold) {
        System.out.println("preSolve");
        //todo: implement for players- bounce off from each other

    }

    //@Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        System.out.println("postSolve");
        //todo: obtain collision impulse results
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


