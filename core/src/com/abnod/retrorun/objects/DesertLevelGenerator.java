package com.abnod.retrorun.objects;

import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;

public class DesertLevelGenerator extends Image{
    private GameScreen gameScreen;
    private Viewport viewport;

    private TextureAtlas textureAtlas;
    private TextureRegion textureGround;
    private TextureRegion textureGroundUp;
    private TextureRegion textureGroundDown;
    private TextureRegion textureGroundEmpty;
    private Body body;

    private LinkedList<Floor> floorList;
    private int someTileCount = 0;
    private int someGroundCount = 0;
    private int floorTypeIndex;

    public DesertLevelGenerator(GameScreen gameScreen, World world) {
        this.gameScreen = gameScreen;
        this.viewport = gameScreen.getViewport();
        this.textureAtlas = new TextureAtlas("ground_desert.pack");

        textureGround = textureAtlas.findRegion("ground-01");
        textureGroundUp = textureAtlas.findRegion("ground-02");
        textureGroundDown = textureAtlas.findRegion("ground-03");
        textureGroundEmpty = textureAtlas.findRegion("ground-04");

        floorList = new LinkedList<Floor>();
        for (int i = 0; i < 16; i+=2) {
            floorList.add(new Floor(gameScreen, world, textureGround, new Vector2(i + 1f, gameScreen.getGroundHeight() / 4), 0));
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (floorList.getFirst().getBody().getPosition().x +1f < gameScreen.getCamera().position.x -viewport.getWorldWidth()/2){
            Floor current = floorList.pollFirst();
            Floor previous = floorList.getLast();
            floorList.offer(current);

            do{
                 floorTypeIndex = (int)(Math.random()*7);
            } while (checkFloor(floorTypeIndex, previous));
            if (floorTypeIndex != 3){someTileCount = 0; someGroundCount++;}

            updateFloorType(floorTypeIndex, current, previous);
        }

    }

    private void updateFloorType(int floorTypeIndex, Floor current, Floor previous){
        switch (floorTypeIndex){
            case 0:{
                current.updateType(floorTypeIndex, textureGround, previous.getBody().getPosition().x + 2, randomizeHeight(floorTypeIndex, previous));
                break;
            }
            case 1:{
                current.updateType(floorTypeIndex, textureGroundUp, previous.getBody().getPosition().x + 2, randomizeHeight(floorTypeIndex, previous));
                break;
            }
            case 2:{
                current.updateType(floorTypeIndex, textureGroundDown, previous.getBody().getPosition().x + 2, randomizeHeight(floorTypeIndex, previous));
                break;
            }
            case 3:{
                current.updateType(floorTypeIndex, textureGroundEmpty, previous.getBody().getPosition().x + 2, randomizeHeight(floorTypeIndex, previous));
                break;
            }
        }
    }

    private float randomizeHeight(int floorTypeIndex, Floor previous){
        if (floorTypeIndex == 1 &&previous.getFloorTypeIndex() != 2){
            return previous.getBody().getPosition().y + 0.5f;
        }
        if (floorTypeIndex == 3 && previous.getFloorTypeIndex() == 2){
            return previous.getBody().getPosition().y - 0.5f;
        }
        if (floorTypeIndex == 0 && previous.getFloorTypeIndex() == 2) {
            return previous.getBody().getPosition().y - 0.5f;
        }
        return previous.getBody().getPosition().y;
    }

    private boolean checkFloor(int floorIndex, Floor previousFloor){
        if (floorIndex == 1){floorTypeIndex = 0;}
        else if (floorIndex == 2 || floorIndex == 3){floorTypeIndex = 1;}
        else if (floorIndex == 4 || floorIndex == 5){floorTypeIndex = 2;}
        else if (floorIndex == 6){floorTypeIndex = 3;}

        if (floorTypeIndex == 1) {
            return floorTypeIndex == previousFloor.getFloorTypeIndex() || previousFloor.getFloorTypeIndex() != 2 && previousFloor.getBody().getPosition().y != 0.5f;
        }
        if (floorTypeIndex == 2) {
            return floorTypeIndex == previousFloor.getFloorTypeIndex() || (previousFloor.getFloorTypeIndex() == 0 || previousFloor.getFloorTypeIndex() == 3) && previousFloor.getBody().getPosition().y != 1;
        }
        if (floorTypeIndex == 3){
            if (someTileCount > 1 ){
                return true;
            } else {
                if (someTileCount == 0 && someGroundCount < 3){
                    return true;
                }
                someTileCount++;
                someGroundCount = 0;
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        for (Floor floor : floorList){
            floor.draw(batch, parentAlpha);
        }
    }
}
