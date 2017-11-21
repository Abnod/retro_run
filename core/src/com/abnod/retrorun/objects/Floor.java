package com.abnod.retrorun.objects;


import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Floor extends Image{

        private GameScreen gameScreen;
        private World world;
        private TextureRegion textureRegion;
        private Vector2 position;
        private Body body;

        private float time;

        private final int WIDTH = 200;
        private final int HEIGHT = 200;

        public Floor(GameScreen gameScreen, World world, TextureRegion textureRegion, Vector2 position) {
            this.gameScreen = gameScreen;
            this.world = world;
            this.textureRegion = textureRegion;
            this.position = position;

            BodyDef groundBodyDef = new BodyDef();
            groundBodyDef.position.set(position);

            body = world.createBody(groundBodyDef);

            PolygonShape groundBox = new PolygonShape();
            groundBox.setAsBox(WIDTH * 16, HEIGHT);

            body.createFixture(groundBox, 0.0f);

            groundBox.dispose();
        }

        public void update(float delta){
        }

        public void draw(SpriteBatch batch){

        }
}
