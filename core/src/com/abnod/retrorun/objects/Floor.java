package com.abnod.retrorun.objects;


import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.Screen;
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

public class Floor extends Image{

        private Screen gameScreen;
        private TextureRegion textureRegion;
        private Body body;

        private final int WIDTH = 2;
        private final int HEIGHT = 2;

        public Floor(Screen gameScreen, World world, TextureRegion textureRegion, Vector2 position) {
            this.gameScreen = gameScreen;
            this.textureRegion = textureRegion;

            BodyDef groundBodyDef = new BodyDef();
            groundBodyDef.type = BodyDef.BodyType.StaticBody;
            groundBodyDef.position.set(position);

            body = world.createBody(groundBodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(WIDTH/2, HEIGHT/2);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 1;
            fixtureDef.friction = 0;
            Fixture fixture = body.createFixture(fixtureDef);
            shape.dispose();
        }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(body.getPosition().x +0.5f < 0){
            System.out.println("replace floor");
            body.setTransform(body.getPosition().x + 12.8f,body.getPosition().y, 0);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, body.getPosition().x - WIDTH/2,body.getPosition().y - HEIGHT/2, 0f, 0f, WIDTH, HEIGHT, 1, 1, 0);
    }
}
