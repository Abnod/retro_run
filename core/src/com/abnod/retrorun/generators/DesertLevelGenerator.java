package com.abnod.retrorun.generators;

import com.abnod.retrorun.GameScreen;
import com.abnod.retrorun.objects.Floor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;

public class DesertLevelGenerator extends Image {
    private GameScreen gameScreen;
    private Viewport viewport;

    private TextureRegion textureGround;
    private TextureRegion textureGroundUp;
    private TextureRegion textureGroundDown;
    private TextureRegion textureGroundEmpty;

    private LinkedList<Floor> floorList;
    private int someTileCount = 0;
    private int someGroundCount = 0;
    private int floorTypeIndex;
    private float positionPreviousX;
    private float positionPreviousY;
    private int floorTypeIndexPrevious;

    public DesertLevelGenerator(GameScreen gameScreen, World world) {
        this.gameScreen = gameScreen;
        this.viewport = gameScreen.getViewport();
        TextureAtlas textureAtlas = new TextureAtlas("ground_desert.pack");
        float groundHeight = gameScreen.getGroundHeight();

        textureGround = textureAtlas.findRegion("ground-01");
        textureGroundUp = textureAtlas.findRegion("ground-02");
        textureGroundDown = textureAtlas.findRegion("ground-03");
        textureGroundEmpty = textureAtlas.findRegion("ground-04");

        floorList = new LinkedList<Floor>();
        for (int i = 0; i < 32; i += 2) {
            floorList.add(new Floor(world, textureGround, new Vector2(i + 1f, groundHeight / 4), 0));
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (floorList.getFirst().getBody().getPosition().x + 1f < gameScreen.getCamera().position.x - viewport.getWorldWidth() / 2) {
            Floor current = floorList.pollFirst();
            Floor previous = floorList.getLast();
            floorList.offer(current);

            positionPreviousX = previous.getBody().getPosition().x;
            positionPreviousY = previous.getBody().getPosition().y;
            floorTypeIndexPrevious = previous.getFloorTypeIndex();

            do {
                floorTypeIndex = (int) (Math.random() * 7);
            } while (checkFloor(floorTypeIndex));
            if (floorTypeIndex != 3) {
                someTileCount = 0;
                someGroundCount++;
            }

            updateFloorType(floorTypeIndex, current);
        }

    }

    private void updateFloorType(int floorTypeIndex, Floor current) {
        float positionNew = positionPreviousX + 2;
        switch (floorTypeIndex) {
            case 0: {
                current.updateType(floorTypeIndex, textureGround, positionNew, randomizeHeight(floorTypeIndex));
                break;
            }
            case 1: {
                current.updateType(floorTypeIndex, textureGroundUp, positionNew, randomizeHeight(floorTypeIndex));
                break;
            }
            case 2: {
                current.updateType(floorTypeIndex, textureGroundDown, positionNew, randomizeHeight(floorTypeIndex));
                break;
            }
            case 3: {
                current.updateType(floorTypeIndex, textureGroundEmpty, positionNew, randomizeHeight(floorTypeIndex));
                break;
            }
        }
    }

    private float randomizeHeight(int floorTypeIndex) {
        if (floorTypeIndex == 1 && floorTypeIndexPrevious != 2) {
            return positionPreviousY + 0.5f;
        } else if (floorTypeIndex == 3 && floorTypeIndexPrevious == 2) {
            return positionPreviousY - 0.5f;
        } else if (floorTypeIndex == 0 && floorTypeIndexPrevious == 2) {
            return positionPreviousY - 0.5f;
        } else return positionPreviousY;
    }

    private boolean checkFloor(int floorIndex) {
        if (floorIndex == 1) {
            floorTypeIndex = 0;
        } else if (floorIndex == 2 || floorIndex == 3) {
            floorTypeIndex = 1;
        } else if (floorIndex == 4 || floorIndex == 5) {
            floorTypeIndex = 2;
        } else if (floorIndex == 6) {
            floorTypeIndex = 3;
        }

        if (floorTypeIndex == 1) {
            return floorTypeIndex == floorTypeIndexPrevious || floorTypeIndexPrevious != 2 && positionPreviousY != 0.5f;
        } else if (floorTypeIndex == 2) {
            return floorTypeIndex == floorTypeIndexPrevious || (floorTypeIndexPrevious == 0 || floorTypeIndexPrevious == 3) && positionPreviousY != 1;
        } else if (floorTypeIndex == 3) {
            if (someTileCount > 1) {
                return true;
            } else {
                if (someTileCount == 0 && someGroundCount < 3) {
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
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (Floor floor : floorList) {
            floor.draw(batch, parentAlpha);
        }
    }

    public LinkedList<Floor> getFloorList() {
        return floorList;
    }
}
