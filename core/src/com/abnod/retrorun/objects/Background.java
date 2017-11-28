package com.abnod.retrorun.objects;


import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Background extends Image {

    private GameScreen gameScreen;
    private Viewport viewport;
    private TextureRegion textureRegion;
    private World world;

    private float firstX;
    private float secondX;

    public Background(GameScreen gameScreen, World world, TextureRegion textureRegion){
        this.gameScreen = gameScreen;
        this.viewport = gameScreen.getViewport();
        this.textureRegion = textureRegion;
        this.world = world;
        firstX = 0;
        secondX = viewport.getWorldWidth();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (secondX + viewport.getWorldWidth() < gameScreen.getCamera().position.x - viewport.getWorldWidth()/2){
            secondX = firstX + viewport.getWorldWidth();
        }
        if (firstX + viewport.getWorldWidth() <= gameScreen.getCamera().position.x - viewport.getWorldWidth()/2){
            firstX = secondX + viewport.getWorldWidth();
        } else {
            if (firstX < secondX){
                firstX += 0.020f;
                secondX = firstX + viewport.getWorldWidth();
            } else {
                secondX += 0.020f;
                firstX = secondX + viewport.getWorldWidth();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, firstX, 0,0,0,viewport.getWorldWidth(),viewport.getWorldHeight(),1,1,0);
        batch.draw(textureRegion, secondX,0,0,0,viewport.getWorldWidth(),viewport.getWorldHeight(),1,1,0);
    }

}
