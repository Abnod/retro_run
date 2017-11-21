package com.abnod.retrorun.objects;

import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Vector2 position;
    private Body body;

    private float time;

    private final int WIDTH = 100;
    private final int HEIGHT = 100;

    public Player(GameScreen gameScreen, World world, TextureRegion textureRegion, Vector2 position) {
        this.gameScreen = gameScreen;
        this.world = world;
        this.textureRegion = textureRegion.split(WIDTH, HEIGHT);
        this.position = position;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(WIDTH / 2.5f, HEIGHT / 2.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 5f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 1f;
        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        int frame = (int)(time / 0.1f);
        frame = frame % 6;
        batch.draw(textureRegion[0][frame], body.getPosition().x-WIDTH/2, body.getPosition().y-HEIGHT/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += 240f * delta / 300.0f;
    }
}
