package com.abnod.retrorun;


import com.abnod.retrorun.listeners.PlayerListener;
import com.abnod.retrorun.objects.Background;
import com.abnod.retrorun.objects.DesertLevelGenerator;
import com.abnod.retrorun.objects.Player;
import com.abnod.retrorun.objects.Text;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Box2DDebugRenderer debugRenderer;

    private World world;
    private Player player;
    private DesertLevelGenerator desertLevelGenerator;
    private Background background;
    private Text gameScreenText;

    private TextureAtlas atlas;
    private TextureRegion textureBackground;
    private TextureRegion textureBird;
    private TextureRegion textureRunner;
    Texture bg;

    private boolean gameOver = false;
    private float playerAnchor = 2.0f;
    private float accum = 0f;
    private final float groundHeight = 2.0f;
    private final float step = 1f / 60f;
    private final float maxAccum = 1f / 20f;


    public GameScreen(RunnerGame runnerGame) {
        this.camera = runnerGame.getCamera();
        this.viewport = runnerGame.getViewport();
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        world = new World(new Vector2(0.0f,-12f), true);

        atlas = new TextureAtlas("runner.pack");
        textureRunner = atlas.findRegion("runner");
        textureBird = atlas.findRegion("bird");
        bg = new Texture("bg.jpg");
        textureBackground = new TextureRegion(bg);

        background = new Background(this, textureBackground);
        desertLevelGenerator = new DesertLevelGenerator(this, world);
        player = new Player(this, world, textureRunner, textureBird, playerAnchor, groundHeight);
        gameScreenText = new Text(this);

        stage = new Stage(viewport);
        stage.addActor(background);
        stage.addActor(desertLevelGenerator);
        stage.addActor(player);
        stage.addActor(gameScreenText);

        world.setContactListener(new PlayerListener(this));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if (!gameOver){
            camera.update();
//            Gdx.gl.glClearColor(0, 0, 0, 1);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            stage.act();
            stage.draw();
            debugRenderer.render(world, camera.combined);
            worldStep(delta);
        }
    }

    private void worldStep(float delta) {
        accum += delta;
        accum = Math.min(accum, maxAccum);
        while (accum > step) {
            world.step(step, 8, 3);
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
