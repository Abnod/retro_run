package com.abnod.retrorun;


import com.abnod.retrorun.objects.Floor;
import com.abnod.retrorun.objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    RunnerGame runnerGame;
    SpriteBatch batch;
    private Stage stage;
    World world;
    Player player;
    Floor floor;
    private Box2DDebugRenderer debugRenderer;

    private TextureAtlas atlas;
    private TextureRegion textureBackground;
    private TextureRegion textureGround;
    private TextureRegion textureBird;
    private TextureRegion textureBird2;
    private TextureRegion textureRunner;

    public GameScreen(RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
        world = new World(new Vector2(0, -50), true);
        debugRenderer = new Box2DDebugRenderer();
        stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("runner.pack");
        textureRunner = atlas.findRegion("runner");
        player = new Player(this, world, textureRunner, new Vector2(300, 250));
        textureGround = atlas.findRegion("ground");
        floor = new Floor(this, world, textureGround, new Vector2(0,0));
        stage.addActor(floor);
        stage.addActor(player);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        for (int i = 0; i <50; i++) {
//            batch.draw(textureGround, i * 200, 0);
//        }
//        player.draw(batch);
//        batch.end();
        stage.act();
        stage.draw();
        debugRenderer.render(world, stage.getCamera().combined);
        world.step(delta, 6, 2);
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
        // Hey, I actually did some clean up in a code sample!
        world.dispose();
    }

//    public TextureAtlas getAtlas() {
//        return atlas;
//    }
//
//    public float getGroundHeight() {
//        return groundHeight;
//    }
//
//    public float getPlayerAnchor() {
//        return playerAnchor;
//    }
}
