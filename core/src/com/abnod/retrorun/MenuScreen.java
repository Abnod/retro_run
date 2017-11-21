package com.abnod.retrorun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class MenuScreen implements Screen {

    private RunnerGame runnerGame;
    private SpriteBatch batch;
    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;

    private BitmapFont font96;
    private BitmapFont font32;

    public MenuScreen(RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
    }


    @Override
    public void show() {
        atlas = new TextureAtlas("runner.pack");
        generateFonts();
        generateGUI();
    }

    @Override
    public void render(float delta) {
//        update(delta);
        batch.begin();
        font96.draw(batch, "Retro_Runner", 0, 500, 1280, 1, false);
        batch.end();
        stage.draw();
    }

    private void generateFonts(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("zorque.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX=3;
        parameter.shadowOffsetY=3;
        parameter.shadowColor=Color.BLACK;
        font32 = generator.generateFont(parameter);
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        generator.dispose();
    }

    private void generateGUI(){
        stage = new Stage(runnerGame.getViewport(), batch);
        skin = new Skin(atlas);
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menuBtn");
        textButtonStyle.font = font32;
        skin.add("textButtonStyle", textButtonStyle);
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = skin.getDrawable("menuBtn");
        textFieldStyle.font = font32;
        textFieldStyle.fontColor = Color.WHITE;
        skin.add("textFieldStyle", textFieldStyle);

        TextButton buttonPlay = new TextButton("Play", skin, "textButtonStyle");
        TextButton buttonExitGame = new TextButton("Exit", skin, "textButtonStyle");
        final TextField textFieldNickname = new TextField("Player", skin, "textFieldStyle");
        textFieldNickname.setWidth(300.0f);
        textFieldNickname.setAlignment(1);
        buttonPlay.setPosition(520,150);
        buttonExitGame.setPosition(520,50);
        textFieldNickname.setPosition(490,600);

        stage.addActor(buttonPlay);
        stage.addActor(buttonExitGame);
        stage.addActor(textFieldNickname);
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
                runnerGame.setPlayerName(textFieldNickname.getText());
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        runnerGame.getViewport().update(width, height, true);
        runnerGame.getViewport().apply();
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

    }
}
