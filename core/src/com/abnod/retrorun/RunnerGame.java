package com.abnod.retrorun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RunnerGame extends Game {

	public enum ScreenType{
		MENU, GAME
	}

	private SpriteBatch batch;
	private Viewport viewport;
	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	private String playerName;
	private OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		this.viewport = new FitViewport(12.8f, 7.2f, camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
		this.gameScreen = new GameScreen(this);
		this.menuScreen = new MenuScreen(this, batch);
		changeScreen(ScreenType.MENU);
	}

	public void changeScreen(ScreenType screenType){
		Screen currentScreen = getScreen();
		if (currentScreen != null){currentScreen.dispose();}
		switch (screenType){
			case MENU:
				setScreen(menuScreen);
				break;
			case GAME:
				setScreen(gameScreen);
				break;
		}

	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		getScreen().dispose();
	}

	Viewport getViewport() {
		return viewport;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
}
