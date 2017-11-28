package com.abnod.retrorun.listeners;

import com.abnod.retrorun.GameScreen;
import com.abnod.retrorun.objects.Player;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;


public class PlayerListener implements ContactListener {

    Player player;

    public PlayerListener(GameScreen gameScreen){
        this.player = gameScreen.getPlayer();
    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() != null  && contact.getFixtureB().getUserData() != null){
            if (contact.getFixtureA().getUserData().equals("ground") && contact.getFixtureB().getUserData().equals("playerFeetSensor")){
                player.setGrounded(true);
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
