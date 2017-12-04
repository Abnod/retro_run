package com.abnod.retrorun;


import com.abnod.retrorun.listeners.PlayerListener;
import com.abnod.retrorun.objects.Background;
import com.abnod.retrorun.objects.DesertLevelGenerator;
import com.abnod.retrorun.objects.Player;
import com.abnod.retrorun.objects.GameInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    private RunnerGame runnerGame;
    private Batch batch;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Stage stage;
    private Stage pauseStage;
    private Box2DDebugRenderer debugRenderer;

    private World world;
    private Player player;
    private DesertLevelGenerator desertLevelGenerator;
    private Background background;
    private Stage gameInterface;

    private TextureAtlas atlas;
    private TextureRegion textureBackground;
    private TextureRegion textureBird;
    private TextureRegion textureRunner;
    private Texture bg;

    private boolean gameOver = false;
    private boolean paused = false;
    private float playerAnchor = 2.0f;
    private final float groundHeight = 2.0f;
    private float accumulator = 0;
    private static final float STEP_TIME = 1f/300f;


    public GameScreen(RunnerGame runnerGame) {
        this.runnerGame = runnerGame;
        this.batch = runnerGame.getBatch();
        this.camera = runnerGame.getCamera();
        this.viewport = runnerGame.getViewport();
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        world = new World(new Vector2(0.0f, -12f), true);

        atlas = new TextureAtlas("runner.pack");
        textureRunner = atlas.findRegion("runner");
        textureBird = atlas.findRegion("bird");
        bg = new Texture("bg.jpg");
        textureBackground = new TextureRegion(bg);

        background = new Background(this, textureBackground);
        desertLevelGenerator = new DesertLevelGenerator(this, world);
        player = new Player(this, world, textureRunner, textureBird, playerAnchor, groundHeight);

        gameInterface = new GameInterface(this, atlas, batch);

        stage = new Stage(viewport);
        stage.addActor(background);
        stage.addActor(desertLevelGenerator);
        stage.addActor(player);

        pauseStage = new PauseStage(this, new FitViewport(1280, 720), batch, atlas);

        world.setContactListener(new PlayerListener(this));
    }

    @Override
    public void render(float delta) {
        if (!gameOver) {
            camera.update();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            if (paused) {
                Gdx.input.setInputProcessor(pauseStage);
                pauseStage.act();
                pauseStage.draw();
            } else {
                Gdx.input.setInputProcessor(gameInterface);
                gameInterface.act();
                stage.act();
                stage.draw();
                gameInterface.draw();
//                debugRenderer.render(world, camera.combined);
                worldStep(delta);
            }
        } else {
            runnerGame.changeScreen(RunnerGame.ScreenType.END_GAME);
        }
    }

    private void worldStep(float delta) {
        float frameTime = Math.min(delta, 0.25f);
        accumulator += frameTime;
        while (accumulator >= STEP_TIME) {
            world.step(STEP_TIME, 6, 2);
            accumulator -= STEP_TIME;
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

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    RunnerGame getRunnerGame() {
        return runnerGame;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
