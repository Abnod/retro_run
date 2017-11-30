package com.abnod.retrorun.listeners;

import com.abnod.retrorun.GameScreen;
import com.abnod.retrorun.objects.Player;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


public class PlayerListener implements ContactListener {

    private GameScreen gameScreen;
    private Player player;
    private Fixture fixtureA;
    private Fixture fixtureB;


    public PlayerListener(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        this.player = gameScreen.getPlayer();
    }

    @Override
    public void beginContact(Contact contact) {
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();
        if (fixtureA.getUserData() != null  && fixtureA.getUserData().equals("groundPit")){
            gameScreen.setGameOver(true);
        }
        if (fixtureB.getUserData() != null  && fixtureB.getUserData().equals("groundPit")){
            gameScreen.setGameOver(true);
        }
        if (fixtureA.getUserData() != null  && fixtureB.getUserData() != null){
            if (fixtureA.getUserData().equals("ground")){
                if (fixtureB.getUserData().equals("playerFeetSensor")){
                    player.setGrounded(true);
                } else if (fixtureB.getUserData().equals("playerFrontSensor")){
                    gameScreen.setGameOver(true);
                }
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
