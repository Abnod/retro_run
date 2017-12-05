package com.abnod.retrorun.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Enemy extends Image {

    protected enum EnemyType{
        BIRD, CACTUS
    }

    @Override
    public abstract void act(float delta);

    @Override
    public abstract void draw(Batch batch, float parentAlpha);

}
