package com.abnod.retrorun.objects;


import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Background extends Image {

    private GameScreen gameScreen;
    private TextureRegion textureRegion;

    private float firstX;
    private float secondX;
    private float worldWidth;
    private float worldHeight;

    public Background(GameScreen gameScreen, TextureRegion textureRegion) {
        this.gameScreen = gameScreen;
        this.textureRegion = textureRegion;
        worldWidth = gameScreen.getViewport().getWorldWidth();
        worldHeight = gameScreen.getViewport().getWorldHeight();
        firstX = 0;
        secondX = worldWidth;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        float screenCornerX = gameScreen.getCamera().position.x - worldWidth / 2;
        float bodypos = gameScreen.getPlayer().getVelocity();
        if (secondX + worldWidth < screenCornerX) {
            secondX = firstX + worldWidth;
        } else if (firstX + worldWidth <= screenCornerX) {
            firstX = secondX + worldWidth;
        }
        if (firstX < secondX) {
            firstX += bodypos*0.01f;
//            firstX += 0.020f;
            secondX = firstX + worldWidth;
        } else {
            secondX += bodypos*0.01f;
//            secondX += 0.020f;
            firstX = secondX + worldWidth;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, firstX, 0, 0, 0, worldWidth, worldHeight, 1, 1, 0);
        batch.draw(textureRegion, secondX, 0, 0, 0, worldWidth, worldHeight, 1, 1, 0);
    }

}
