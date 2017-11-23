package com.abnod.retrorun.objects;

import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image{

    private GameScreen gameScreen;
    private World world;
    private TextureRegion[][] textureRegion;
    private Body playerBody;

    private float time;
    private int doubleJumpTime;

    private final int WIDTH = 100;
    private final int HEIGHT = 100;

    public Player(GameScreen gameScreen, World world, TextureRegion textureRegion, float positionX, float positionY) {
        this.gameScreen = gameScreen;
        this.world = world;
        this.textureRegion = textureRegion.split(WIDTH, HEIGHT);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        playerBody = world.createBody(bodyDef);
        playerBody.setFixedRotation(true);

        for (int i = 0; i < 3; i++) {
            PolygonShape shape = new PolygonShape();
            shape.set(getVertices(i));

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1f;
            fixtureDef.friction = 0f;
            fixtureDef.restitution = 0f;
            Fixture fixture = playerBody.createFixture(fixtureDef);
            shape.dispose();
        }
//        PolygonShape sensor = new PolygonShape();
//        sensor.set(new Vector2[]{
//                new Vector2(-20,-50),
//                new Vector2(15,-50),
//                new Vector2(15,-47),
//                new Vector2(-20,-47)});
//
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = sensor;
//        fixtureDef.isSensor = true;
//        Fixture sensorFixture = body.createFixture(fixtureDef);
//        sensor.dispose();

        playerBody.setBullet(true);
        playerBody.setTransform(positionX,positionY+playerBody.getLocalCenter().y, 0);
    }

    private Vector2 [] getVertices(int i){
        Vector2[] vertices;
        switch (i){
            case 0:{
                vertices = new Vector2[4];
                vertices[0] = new Vector2(-0.02f, -0.47f);
                vertices[1] = new Vector2(0.15f, -0.47f);
                vertices[2] = new Vector2(0.12f, -0.10f);
                vertices[3] = new Vector2(-0.15f, -0.10f);
                return vertices;
            }
            case 1:{
                vertices = new Vector2[5];
                vertices[0] = new Vector2(0.12f, -0.20f);
                vertices[1] = new Vector2(0.20f, -0.10f);
                vertices[2] = new Vector2(0.20f, 0f);
                vertices[3] = new Vector2(-0.08f, 0.10f);
                vertices[4] = new Vector2(-0.20f, -0.10f);
                return vertices;
            }
            case 2:{
                vertices = new Vector2[7];
                vertices[0] = new Vector2(0.20f, 0f);
                vertices[1] = new Vector2(0.30f, 0.5f);
                vertices[2] = new Vector2(0.32f, 0.25f);
                vertices[3] = new Vector2(0.25f, 0.38f);
                vertices[4] = new Vector2(0f, 0.38f);
                vertices[5] = new Vector2(-0.08f, 0.25f);
                vertices[6] = new Vector2(-0.08f, 0.10f);
                return vertices;
            }
        }
        return new Vector2[0];
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        int frame = (int)(time / 0.1f);
        frame = frame % 6;
//        batch.draw(textureRegion[0][frame], playerBody.getPosition().x-WIDTH/2, playerBody.getPosition().y-HEIGHT/2, WIDTH/2, HEIGHT/2,
//                WIDTH, HEIGHT, 1, 1, (float)Math.toDegrees(playerBody.getAngle()));
        gameScreen.getCamera().position.set(gameScreen.getViewport().getWorldWidth()/2-gameScreen.getPlayerAnchor()+playerBody.getPosition().x, gameScreen.getViewport().getWorldHeight()/2, 0);
        gameScreen.getCamera().update();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += 240f * delta / 300.0f;
//        if (body.getPosition().y <= gameScreen.getGroundHeight()+HEIGHT/2){
//            body.setLinearVelocity(240f, 0);
//            doubleJumpTime = 0;
//        }
//        if (Gdx.input.justTouched() && (body.getPosition().y <= gameScreen.getGroundHeight()+48 || doubleJumpTime <=0)){
        if (Gdx.input.justTouched()){
            System.out.println("click");
            playerBody.applyForceToCenter(0f,50f,true);
//            body.applyLinearImpulse(new Vector2(body.getMass() * body.getLinearVelocity().x * delta, body.getMass() * 1440 ), body.getWorldCenter(),false);
//            playerBody.applyLinearImpulse(0f, 8f, playerBody.getPosition().x, playerBody.getPosition().y, true);
//            doubleJumpTime = 2;
//            soundJump.play();
//            if(position.y > gameScreen.getGroundHeight()){
//                doubleJumpTime = 5.0f;
//                rotateAngle = 1.0f;
//            }
        }
    }

    public Body getBody() {
        return playerBody;
    }
}
