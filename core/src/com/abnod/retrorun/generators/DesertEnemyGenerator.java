package com.abnod.retrorun.generators;

import com.abnod.retrorun.GameScreen;
import com.abnod.retrorun.objects.GroundEnemy;
import com.abnod.retrorun.objects.Floor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;

public class DesertEnemyGenerator extends Image{

    private GameScreen gameScreen;
    private Viewport viewport;
    private World world;

    private TextureRegion textureCactus;
    private TextureRegion textureBird;

    private LinkedList<GroundEnemy> groundEnemies;
    private LinkedList<Floor> floors;
    private GroundEnemy previousGroundEnemy;
    private int floorWidthHalf;
    private float floorPosY;
    private float posX;
    private float posY;
    private int groundType;

    public DesertEnemyGenerator(GameScreen gameScreen, World world, TextureAtlas textureAtlas){
        this.gameScreen = gameScreen;
        this.viewport = gameScreen.getViewport();
        this.world = world;
        floors = gameScreen.getDesertLevelGenerator().getFloorList();
        floorWidthHalf = floors.getFirst().getWIDTH()/2;

        textureCactus = textureAtlas.findRegion("cactus");
        textureBird = textureAtlas.findRegion("bird");

        groundEnemies = new LinkedList<GroundEnemy>();
        for (int i = 0; i < 3; i++) {
            if (i == 0){
                posX = 11.0f;
                groundEnemies.add(new GroundEnemy(world, textureCactus, GroundEnemy.EnemyType.CACTUS, posX, 1.5f));
            } else {
                previousGroundEnemy = groundEnemies.getLast();
                do{
                    posX = previousGroundEnemy.getPositionX() + 5.0f + ((int)(Math.random()*11)/2);
                } while (checkGround(posX));
                posY = floorPosY + floorWidthHalf;
                groundEnemies.add(new GroundEnemy(world, textureCactus, GroundEnemy.EnemyType.CACTUS, posX, posY));
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (groundEnemies.getFirst().getCactusBody().getPosition().x + 0.4f < gameScreen.getCamera().position.x - viewport.getWorldWidth() / 2) {
            GroundEnemy current = groundEnemies.pollFirst();
            GroundEnemy previous = groundEnemies.getLast();
            groundEnemies.offer(current);

            do{
                posX = previous.getPositionX() + 5 + ((int)(Math.random()*11)/2);
            } while (checkGround(posX));
            posY = floorPosY + floorWidthHalf;

            current.update(posX, posY, groundType);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (GroundEnemy groundEnemy : groundEnemies){
            groundEnemy.draw(batch, parentAlpha);
        }
    }

    private boolean checkGround(float posX){
        for(Floor floor : floors){
            if (posX > floor.getBody().getPosition().x - floorWidthHalf && posX < floor.getBody().getPosition().x + floorWidthHalf){
                floorPosY = floor.getBody().getPosition().y;
                groundType = floor.getFloorTypeIndex();
                return groundType == 3;
            }
        }
        return true;
    }
}
