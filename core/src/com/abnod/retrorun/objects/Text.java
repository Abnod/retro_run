package com.abnod.retrorun.objects;

import com.abnod.retrorun.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {

    private BitmapFont font32;

    private GameScreen gameScreen;
    private Camera textCamera = new OrthographicCamera(1280,720);

    public Text(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        generateFonts();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        textCamera.update();
        batch.setProjectionMatrix(textCamera.combined);
        font32.draw(batch, "Score: " + gameScreen.getPlayer().getScore(), -textCamera.viewportWidth/2 +15, textCamera.viewportHeight/2 -15);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        font32 = generator.generateFont(parameter);
        generator.dispose();
    }
}
