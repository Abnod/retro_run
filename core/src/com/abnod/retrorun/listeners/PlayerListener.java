package com.abnod.retrorun.listeners;

import com.abnod.retrorun.GameScreen;
import com.abnod.retrorun.objects.Player;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;


public class PlayerListener implements ContactListener {

    GameScreen gameScreen;
    Player player;

    public PlayerListener(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        this.player = gameScreen.getPlayer();
    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() != null  && contact.getFixtureA().getUserData().equals("groundPit")){
            gameScreen.setGameOver(true);
        }
        if (contact.getFixtureB().getUserData() != null  && contact.getFixtureB().getUserData().equals("groundPit")){
            gameScreen.setGameOver(true);
        }
        if (contact.getFixtureA().getUserData() != null  && contact.getFixtureB().getUserData() != null){
            if (contact.getFixtureA().getUserData().equals("ground") && contact.getFixtureB().getUserData().equals("playerFeetSensor")){
                player.setGrounded(true);
            }
            if (contact.getFixtureA().getUserData().equals("ground") && contact.getFixtureB().getUserData().equals("playerFrontSensor")){
                gameScreen.setGameOver(true);
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
