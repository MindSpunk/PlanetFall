package com.spark.planetfall.game.screens;

import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.scene.VisAssetManager;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.kotcrab.vis.runtime.system.physics.PhysicsSystem;
import com.spark.planetfall.game.PlanetFallClient;
import com.spark.planetfall.game.actors.DataManager;
import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.Vehicle;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.server.ClientHandler;

public class SparkGame implements Screen {

    public PlanetFallClient game;
    public Stage stage;
    public Stage elevatedStage;
    public Player player;
    public ClientHandler handler;
    public String ip;
    public World world;
    public InputMultiplexer input;
    public Box2DDebugRenderer debugRenderer;
    public VisAssetManager vis;
    public Scene scene;
    public RayHandler lightHandler;
    public UIHandler ui;
    public Vehicle vehicle;
    public DataManager manager;

    public SparkGame(PlanetFallClient in, String ip) {
        this.game = in;
        this.ip = ip;
    }

    @Override
    public void show() {

        input = new InputMultiplexer();
        vis = new VisAssetManager(new SpriteBatch());
        scene = vis.loadSceneNow("scene/test.scene");

        world = scene.getEntityEngine().getSystem(PhysicsSystem.class).getPhysicsWorld();
        stage = new Stage(scene.getEntityEngine().getSystem(CameraManager.class).getViewport());
        elevatedStage = new Stage(stage.getViewport());

        manager = new DataManager();

        scene.getEntityEngine().inject(manager);

        manager.afterSceneInit();

        input.addProcessor(stage);
        debugRenderer = new Box2DDebugRenderer();
        handler = new ClientHandler(this, ip);
        lightHandler = new RayHandler(world, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ui = new UIHandler(stage.getViewport(), scene, manager);
        player = new Player(this);
        elevatedStage.addActor(player);

        Gdx.input.setInputProcessor(input);


    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(delta, 6, 6);
        stage.act();
        elevatedStage.act();
        lightHandler.update();
        scene.render();
        stage.getViewport().apply();
        stage.draw();
        lightHandler.setCombinedMatrix((OrthographicCamera) stage.getCamera());
        lightHandler.render();
        elevatedStage.draw();
        ui.draw(delta);

        //debugRenderer.render(world, stage.getCamera().combined);

    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width, height);
        ui.stage.getViewport().update(width, height);

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
