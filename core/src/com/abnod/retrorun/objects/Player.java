package com.abnod.retrorun.objects;

import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Player extends Image{

    private GameScreen gameScreen;
    private World world;
    private TextureRegion[][] textureRegion;
    private TextureRegion textureRegionJump;
    private Body playerBody;

    private float time;
    private int doubleJumpTime=0;
    private float velocity;
    private boolean isGrounded;
    private float timer = 0;

    private final float WIDTH = 1;
    private final float HEIGHT = 1;

    public Player(GameScreen gameScreen, World world, TextureRegion textureRegion, TextureRegion textureRegionJump, float positionX, float positionY) {
        this.gameScreen = gameScreen;
        this.world = world;
        this.textureRegion = textureRegion.split(100, 100);
        this.textureRegionJump = textureRegionJump;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(positionX+0.1f,positionY+0.3f);


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

        PolygonShape sensor = new PolygonShape();
        sensor.set(new Vector2[]{
                new Vector2(-0.20f,-0.50f),
                new Vector2(0.07f,-0.50f),
                new Vector2(-0.20f,-0.45f),
                new Vector2(0.07f,-0.45f)});
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = sensor;
        fixtureDef.isSensor = true;
        Fixture sensorFixture = playerBody.createFixture(fixtureDef);
        sensorFixture.setUserData("playerFeetSensor");
        sensor.dispose();

        playerBody.setBullet(true);
        velocity = 0.8f;
        isGrounded = false;
    }

    private Vector2 [] getVertices(int i){
        Vector2[] vertices;
        switch (i){
            case 0:{
                vertices = new Vector2[6];
                vertices[0] = new Vector2(-0.18f, -0.47f);
                vertices[1] = new Vector2(0.05f, -0.47f);
                vertices[2] = new Vector2(0.11f, -0.34f);
                vertices[3] = new Vector2(0.10f, -0.20f);
                vertices[4] = new Vector2(-0.12f, -0.15f);
                vertices[5] = new Vector2(-0.22f, -0.30f);
                return vertices;
            }
            case 1:{
                vertices = new Vector2[5];
                vertices[0] = new Vector2(0.12f, -0.20f);
                vertices[1] = new Vector2(0.20f, -0.10f);
                vertices[2] = new Vector2(0.20f, 0f);
                vertices[3] = new Vector2(-0.08f, 0.10f);
                vertices[4] = new Vector2(-0.15f, -0.15f);
                return vertices;
            }
            case 2:{
                vertices = new Vector2[7];
                vertices[0] = new Vector2(0.20f, 0f);
                vertices[1] = new Vector2(0.30f, 0.05f);
                vertices[2] = new Vector2(0.35f, 0.25f);
                vertices[3] = new Vector2(0.25f, 0.36f);
                vertices[4] = new Vector2(0.05f, 0.36f);
                vertices[5] = new Vector2(-0.05f, 0.28f);
                vertices[6] = new Vector2(-0.03f, 0.09f);
                return vertices;
            }
        }
        return new Vector2[0];
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        gameScreen.getCamera().position.set(gameScreen.getViewport().getWorldWidth()/2-gameScreen.getPlayerAnchor()+playerBody.getPosition().x, gameScreen.getViewport().getWorldHeight()/2, 0);
        gameScreen.getCamera().update();
        batch.setProjectionMatrix(gameScreen.getCamera().combined);
        int frame = (int)(time / 0.1f);
        frame = frame % 6;

        //jumptime: 1 jump, 2 doublejump, 0 - grounded;
        if (doubleJumpTime == 1){
            batch.draw(textureRegion[0][4], playerBody.getPosition().x-WIDTH/2, playerBody.getPosition().y-HEIGHT/2, WIDTH/2, HEIGHT/2,
                    WIDTH, HEIGHT, 1, 1, 0);
        } else if (doubleJumpTime == 2){
            batch.draw(textureRegionJump, playerBody.getPosition().x-WIDTH/2, playerBody.getPosition().y-HEIGHT/2, WIDTH/2, HEIGHT/2,
                    WIDTH, HEIGHT, 1, 1, 0);
        } else {
            batch.draw(textureRegion[0][frame], playerBody.getPosition().x-WIDTH/2, playerBody.getPosition().y-HEIGHT/2, WIDTH/2, HEIGHT/2,
                    WIDTH, HEIGHT, 1, 1, 0);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (playerBody.getLinearVelocity().x < velocity && isGrounded){
            //if player velocity < 5 make acceleration
            if (velocity < 5.0f){
                velocity += 0.001f;
            }

            playerBody.setLinearVelocity(velocity, 0);


        } else {
            if (isGrounded){
                //if player velocity < 5 make acceleration
                if (velocity < 5.0f){
                    velocity += 0.001f;
                }
                playerBody.setLinearVelocity(velocity, playerBody.getMass()*world.getGravity().y);

            }
        }

        //when grounded run animation (time)
        if (isGrounded){
//            time += 240f * delta / 300.0f;
            time += 240*(1+playerBody.getLinearVelocity().x/2) * delta / 600.0f;
            if (doubleJumpTime > 0){
                doubleJumpTime = 0;
            }
        }

        //jump
        if (Gdx.input.justTouched() && (isGrounded || doubleJumpTime == 1)){
            isGrounded = false;
            doubleJumpTime++;
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x, 0);
            playerBody.applyForceToCenter(0f, 100f,true);
//            soundJump.play();.
        }
    }

    public Body getBody() {
        return playerBody;
    }

    public void setGrounded(boolean grounded) {
        this.isGrounded = grounded;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public float getTimer() {
        return timer;
    }
}
