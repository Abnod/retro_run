package com.abnod.retrorun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EndgameScreen implements Screen {

    private RunnerGame runnerGame;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private Viewport viewport;

    private BitmapFont font96;
    private BitmapFont font32;

    EndgameScreen(RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
        viewport = new FitViewport(1280, 720);
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("runner.pack");
        generateFonts();
        generateGUI();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        batch.begin();
        font96.draw(batch, "Your score: " + RunnerGame.getScore(), 0, 500, 1280, 1, false);
        batch.end();
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
        skin = new Skin(atlas);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font32;
        skin.add("textButtonStyle", textButtonStyle);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = skin.getDrawable("menuBtn");
        textFieldStyle.font = font32;
        textFieldStyle.fontColor = Color.WHITE;
        skin.add("textFieldStyle", textFieldStyle);

        TextButton buttonPlay = new TextButton("Restart", skin, "textButtonStyle");
        TextButton buttonExitGame = new TextButton("Exit", skin, "textButtonStyle");
        buttonPlay.setPosition(520, 150);
        buttonExitGame.setPosition(520, 50);

        stage = new Stage(viewport, batch);
        stage.addActor(buttonPlay);
        stage.addActor(buttonExitGame);
        Gdx.input.setInputProcessor(stage);

        buttonExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        buttonPlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runnerGame.changeScreen(RunnerGame.ScreenType.GAME);
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewport.apply();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        atlas.dispose();
        font32.dispose();
        font96.dispose();
    }
}
