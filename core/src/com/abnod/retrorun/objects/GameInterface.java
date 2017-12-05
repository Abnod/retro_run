package com.abnod.retrorun.objects;

import com.abnod.retrorun.GameScreen;
import com.abnod.retrorun.RunnerGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameInterface extends Stage {

    private BitmapFont font32;
    private Batch batch;

    private GameScreen gameScreen;
    private Camera textCamera = new OrthographicCamera(1280, 720);

    public GameInterface(GameScreen gameScreen, TextureAtlas atlas, Batch batch) {
        super(new FitViewport(1280, 720), batch);
        this.batch = batch;
        this.gameScreen = gameScreen;
        generateFonts();
        generateGUI(atlas);
    }

    @Override
    public void draw() {
        super.draw();
        textCamera.update();
        batch.setProjectionMatrix(textCamera.combined);
        batch.begin();
        font32.draw(batch, "Score: " + RunnerGame.getScore(), -textCamera.viewportWidth / 2 + 15, textCamera.viewportHeight / 2 - 15);
        batch.end();
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

    private void generateGUI(TextureAtlas atlas) {
        Skin skin = new Skin(atlas);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font32;
        skin.add("textButtonStyle", textButtonStyle);

        TextButton buttonPause = new TextButton("|| Pause", skin, "textButtonStyle");
        buttonPause.setPosition(textCamera.viewportWidth - 255, textCamera.viewportHeight - 95);
        buttonPause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.setPaused(true);
                return true;
            }
        });

        this.addActor(buttonPause);
    }
}
