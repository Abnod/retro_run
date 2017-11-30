package com.abnod.retrorun.objects;


import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;

public class Floor extends Image{


    private TextureRegion textureRegion;
    private Body body;

    private EdgeShape shapeSquare;
    private EdgeShape shapeUp;
    private EdgeShape shapeDown;
    private EdgeShape shapePitLeft;
    private EdgeShape shapePitRight;
    private PolygonShape shapePitMiddle;

    private FixtureDef fixtureDef;
    private Fixture fixture;
    private Fixture fixturePitL;
    private Fixture fixturePitM;
    private Fixture fixturePitR;
    Array <Fixture> fixtures;

    private int floorTypeIndex;

    private final int WIDTH = 2;
    private final int HEIGHT = 2;

    public Floor(World world, TextureRegion textureRegion, Vector2 position, int floorTypeIndex) {
        this.textureRegion = textureRegion;
        this.floorTypeIndex = floorTypeIndex;

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(position);
        body = world.createBody(groundBodyDef);

        shapeSquare = new EdgeShape();
        shapeUp = new EdgeShape();
        shapeDown = new EdgeShape();
        shapePitLeft = new EdgeShape();
        shapePitRight = new EdgeShape();
        shapePitMiddle = new PolygonShape();

        shapeSquare.set(-WIDTH/2, HEIGHT/2, WIDTH/2, HEIGHT/2);
        shapeUp.set(-WIDTH/2, HEIGHT/2-0.5f, WIDTH/2, HEIGHT/2);
        shapeDown.set(-WIDTH/2, HEIGHT/2, WIDTH/2, HEIGHT/2-0.5f);
        shapePitLeft.set(-WIDTH/2, HEIGHT/2 -0.20f, -WIDTH/2, 0);
        shapePitMiddle.setAsBox(WIDTH/2, WIDTH/4);
        shapePitRight.set(WIDTH/2, 0, WIDTH/2, HEIGHT/2 -0.5f);

        fixtureDef = new FixtureDef();
        fixtureDef.density = 1;
        fixtureDef.friction = 0.0f;

        setShape();
    }

    private void setShape(){
        switch (floorTypeIndex){
            case 0:{
                fixtureDef.shape = shapeSquare;
                break;
            }
            case 1: {
                fixtureDef.shape = shapeUp;
                break;
            }
            case 2:{
                fixtureDef.shape = shapeDown;
                break;
            }
            case 3:{
                fixtureDef.shape = shapePitLeft;
                fixtureDef.isSensor = true;
                fixturePitL = body.createFixture(fixtureDef);
                fixtureDef.shape = shapePitMiddle;
                fixturePitM = body.createFixture(fixtureDef);
                fixtureDef.shape = shapePitRight;
                fixturePitR = body.createFixture(fixtureDef);

                fixturePitL.setUserData("groundPit");
                fixturePitM.setUserData("groundPit");
                fixturePitR.setUserData("groundPit");

                return;
            }
        }
        fixtureDef.isSensor = false;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData("ground");
    }

    public void updateType(int floorTypeIndex, TextureRegion textureRegion, float positionX, float positionY){
        if (floorTypeIndex != this.floorTypeIndex){
            this.floorTypeIndex = floorTypeIndex;
            this.textureRegion = textureRegion;
//            body.destroyFixture(fixture);
            fixtures = body.getFixtureList();
            while (fixtures.size > 0){
                body.destroyFixture(fixtures.first());
            }
            setShape();
        }
        body.setTransform(positionX, positionY, 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, body.getPosition().x - WIDTH/2-0.03f,body.getPosition().y - HEIGHT/2, 0f, 0f, WIDTH+0.015f, HEIGHT, 1f, 1f, 0);
    }

    public Body getBody() {
        return body;
    }

    public int getFloorTypeIndex() {
        return floorTypeIndex;
    }
}
