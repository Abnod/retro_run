package com.abnod.retrorun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.awt.Menu;

public class RunnerGame extends Game {

	public enum ScreenType{
		MENU, GAME
	}

	SpriteBatch batch;
	private Viewport viewport;
	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	private String playerName;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.gameScreen = new GameScreen(this, batch);
		this.menuScreen = new MenuScreen(this, batch);
		this.viewport = new FitViewport(1280.0F, 720.0F);
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
}
