package com.abnod.retrorun;


import com.abnod.retrorun.listeners.PlayerListener;
import com.abnod.retrorun.objects.Background;
import com.abnod.retrorun.objects.DesertLevelGenerator;
import com.abnod.retrorun.objects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Box2DDebugRenderer debugRenderer;

    private World world;
    private Player player;
    private DesertLevelGenerator desertLevelGenerator;
    private Background background;

    private TextureAtlas atlas;
    private TextureRegion textureBackground;
    private TextureRegion textureBird;
    private TextureRegion textureBird2;
    private TextureRegion textureRunner;
    Texture bg;

    private float playerAnchor = 2.0f;
    private final float groundHeight = 2.0f;
    private float accum = 0f;
    private final float step = 1f / 60f;
    private final float maxAccum = 1f / 20f;
    private boolean gameOver = false;


    public GameScreen(RunnerGame runnerGame) {
        this.batch = runnerGame.getBatch();
        this.camera = runnerGame.getCamera();
        this.viewport = runnerGame.getViewport();
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        world = new World(new Vector2(0.0f,-12f), true);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("runner.pack");

        textureRunner = atlas.findRegion("runner");
        textureBird = atlas.findRegion("bird");

        bg = new Texture("bg.jpg");
        bg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        textureBackground = new TextureRegion(bg);

        background = new Background(this, world, textureBackground);
        stage.addActor(background);
        desertLevelGenerator = new DesertLevelGenerator(this, world);
        stage.addActor(desertLevelGenerator);
        player = new Player(this, world, textureRunner, textureBird, playerAnchor, groundHeight);
        stage.addActor(player);

        world.setContactListener(new PlayerListener(this));
    }

    @Override
    public void render(float delta) {
        if (!gameOver){
            camera.update();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act();
            stage.draw();
//            debugRenderer.render(world, camera.combined);
            worldStep(delta);
        }
    }

    private void worldStep(float delta) {
        accum += delta;
        accum = Math.min(accum, maxAccum);
        while (accum > step) {
            world.step(step, 15, 10);
            accum -= step;
        }
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
        world.dispose();
        atlas.dispose();
        stage.dispose();
        debugRenderer.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public float getPlayerAnchor() {
        return playerAnchor;
    }

    public float getGroundHeight() {
        return groundHeight;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
