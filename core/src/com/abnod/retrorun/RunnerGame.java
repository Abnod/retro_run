package com.abnod.retrorun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RunnerGame extends Game {

    public enum ScreenType {
        MENU, GAME, END_GAME
    }

    private SpriteBatch batch;
    private Viewport viewport;
    private MenuScreen menuScreen;
    private String playerName;
    private OrthographicCamera camera;
    private static int score;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        this.viewport = new FitViewport(12.8f, 7.2f, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        this.menuScreen = new MenuScreen(this, batch);
        changeScreen(ScreenType.MENU);
    }

    void changeScreen(ScreenType screenType) {
        Screen currentScreen = getScreen();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        switch (screenType) {
            case MENU:
                setScreen(menuScreen);
                break;
            case GAME:
                setScreen(new GameScreen(this));
                break;
            case END_GAME:
                setScreen(new EndgameScreen(this, batch));
                break;
        }
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        getScreen().render(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
        getScreen().dispose();
    }

    Viewport getViewport() {
        return viewport;
    }

    public String getPlayerName() {
        return playerName;
    }

    void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    OrthographicCamera getCamera() {
        return camera;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int newScore) {
        score = newScore;
    }
}
