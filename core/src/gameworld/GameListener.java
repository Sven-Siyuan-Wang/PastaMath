package gameworld;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

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

    private ContactListener createContactListener()
    {
        ContactListener contactListener = new ContactListener()
        {
            @Override
            public void beginContact(Contact contact)
            {
                final Fixture x1 = contact.getFixtureA();
                final Fixture x2 = contact.getFixtureB();

                //MOST OF THE CODE COMES HERE
            }

            @Override
            public void endContact(Contact contact)
            {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold)
            {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse)
            {

            }
        };
        return contactListener;
    }
}
