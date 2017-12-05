package com.abnod.retrorun.generators;

import com.abnod.retrorun.GameScreen;
import com.abnod.retrorun.objects.Cactus;
import com.abnod.retrorun.objects.Enemy;
import com.abnod.retrorun.objects.Floor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.LinkedList;

public class DesertEnemyGenerator extends Image{

    private GameScreen gameScreen;
    private World world;

    private TextureRegion textureCactus;
    private TextureRegion textureBird;

    private LinkedList<Enemy> enemies;
    private LinkedList<Floor> floors;

    public DesertEnemyGenerator(GameScreen gameScreen, World world, TextureAtlas textureAtlas){
        this.gameScreen = gameScreen;
        this.world = world;
        floors = gameScreen.getDesertLevelGenerator().getFloorList();

        textureCactus = textureAtlas.findRegion("cactus");
        textureBird = textureAtlas.findRegion("bird");

        enemies = new LinkedList<Enemy>();
        for (int i = 0; i < 8; i++) {
            enemies.add(new Cactus());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (Enemy enemy : enemies){
            enemy.act(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (Enemy enemy : enemies){
            enemy.draw(batch, parentAlpha);
        }
    }

    private void generateEnemy(){

    }
}
