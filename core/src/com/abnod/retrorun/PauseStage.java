package com.abnod.retrorun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseStage extends Stage {

    private GameScreen gameScreen;
    private Batch batch;
    private BitmapFont font96;
    private BitmapFont font32;
    private TextureAtlas atlas;

    PauseStage(GameScreen gameScreen, Viewport viewport, Batch batch, TextureAtlas atlas) {
        super(viewport, batch);
        this.gameScreen = gameScreen;
        this.batch = batch;
        this.atlas = atlas;
        generateFonts();
        generateGUI();
    }

    private void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.shadowColor = Color.BLACK;
        font32 = generator.generateFont(parameter);
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        generator.dispose();
    }

    private void generateGUI() {
        Skin skin = new Skin(atlas);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font32;
        skin.add("textButtonStyle", textButtonStyle);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = skin.getDrawable("menuBtn");
        textFieldStyle.font = font32;
        textFieldStyle.fontColor = Color.WHITE;
        skin.add("textFieldStyle", textFieldStyle);

        TextButton buttonResume = new TextButton("Resume", skin, "textButtonStyle");
        TextButton buttonExitGame = new TextButton("Exit to Menu", skin, "textButtonStyle");
        buttonResume.setPosition(520, 150);
        buttonExitGame.setPosition(520, 50);

        this.addActor(buttonResume);
        this.addActor(buttonExitGame);

        buttonExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                gameScreen.getRunnerGame().changeScreen(RunnerGame.ScreenType.MENU);
            }
        });
        buttonResume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                gameScreen.setPaused(false);
            }
        });
    }

    @Override
    public void draw() {
        super.draw();
        batch.begin();
        font96.draw(batch, "Game Paused", 0, 500, 1280, 1, false);
        batch.end();
    }
}
