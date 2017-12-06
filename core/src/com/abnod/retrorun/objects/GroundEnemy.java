package com.abnod.retrorun.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GroundEnemy extends Image {

    private final float WIDTH = 0.8f;
    private final float HEIGHT = 0.8f;

    public enum EnemyType{
        CACTUS
    }

    private TextureRegion textureRegion;
    private Body cactusBody;
    private EnemyType type;

    public GroundEnemy(World world, TextureRegion textureRegion, EnemyType type, float positionX, float positionY) {
        this.textureRegion = textureRegion;
        this.type = type;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(positionX,positionY + HEIGHT/2);
        cactusBody = world.createBody(bodyDef);

//        PolygonShape cactusShape = new PolygonShape();
//        cactusShape.setAsBox(WIDTH/2, HEIGHT/2);
        CircleShape cactusShape = new CircleShape();
        cactusShape.setRadius(WIDTH/2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.friction = 0;

        fixtureDef.shape = cactusShape;
        Fixture fixture = cactusBody.createFixture(fixtureDef);
        fixture.setUserData("cactus");

        cactusShape.dispose();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, cactusBody.getPosition().x - WIDTH/2-0.04f, cactusBody.getPosition().y - HEIGHT/2, 0, 0, WIDTH, HEIGHT+0.02f, 1, 1, 0);
    }

    public void update(float positionX, float positionY, int groundType){
        if (groundType == 0){
            this.cactusBody.setTransform(positionX, positionY + HEIGHT/2, 0);
        } else {
            this.cactusBody.setTransform(positionX, positionY, 0);
        }
    }

    public EnemyType getType() {
        return type;
    }

    public float getPositionX() {
        return cactusBody.getPosition().x;
    }

    public Body getCactusBody() {
        return cactusBody;
    }
}
