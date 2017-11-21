package com.abnod.retrorun.objects;

import com.abnod.retrorun.GameScreen;
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

        for (int i = 0; i < 3; i++) {
            PolygonShape shape = new PolygonShape();
            shape.set(getVertices(i));

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 5f;
            fixtureDef.friction = 0f;
            fixtureDef.restitution = 0.3f;
            Fixture fixture = body.createFixture(fixtureDef);
            shape.dispose();
        }
    }

    private Vector2 [] getVertices(int i){
        Vector2[] vertices;
        switch (i){
            case 0:{
                vertices = new Vector2[4];
                vertices[0] = new Vector2(-20, -47);
                vertices[1] = new Vector2(15, -47);
                vertices[2] = new Vector2(12, -10);
                vertices[3] = new Vector2(-15, -10);
                return vertices;
            }
            case 1:{
                vertices = new Vector2[5];
                vertices[0] = new Vector2(12, -20);
                vertices[1] = new Vector2(20, -10);
                vertices[2] = new Vector2(20, 0);
                vertices[3] = new Vector2(-8, 10);
                vertices[4] = new Vector2(-20, -10);
                return vertices;
            }
            case 2:{
                vertices = new Vector2[7];
                vertices[0] = new Vector2(20, 0);
                vertices[1] = new Vector2(30, 5);
                vertices[2] = new Vector2(32, 25);
                vertices[3] = new Vector2(25, 38);
                vertices[4] = new Vector2(0, 38);
                vertices[5] = new Vector2(-8, 25);
                vertices[6] = new Vector2(-8, 10);
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
        batch.draw(textureRegion[0][frame], body.getPosition().x-WIDTH/2, body.getPosition().y-HEIGHT/2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += 240f * delta / 300.0f;
    }
}
