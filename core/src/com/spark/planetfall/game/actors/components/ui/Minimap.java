package com.spark.planetfall.game.actors.components.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.system.CameraManager;
import com.spark.planetfall.game.actors.DataManager;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.game.texture.Atlas;

public class Minimap extends Actor {

    private FrameBuffer map;
    private TextureRegion mapRegion;
    private Transform position;
    private DataManager datamanager;
    private OrthographicCamera camera;
    private FrameBuffer minimap;
    private SpriteBatch batch;
    private Sprite[] capturePoints;

    public Minimap(Scene scene, DataManager datamanager) {

        this.datamanager = datamanager;

        this.camera = new OrthographicCamera(100, 100);

        this.capturePoints = new Sprite[this.datamanager.capturePoints.size];

        for (int i = 0; i < this.capturePoints.length; i++) {
            this.capturePoints[i] = Atlas.get().createSprite("gfx/ui/capture_point_icon");
            this.capturePoints[i].setOrigin(0, 0);
            this.capturePoints[i].setScale(0.1f);
            this.capturePoints[i].setPosition(this.datamanager.capturePoints.get(i).x, this.datamanager.capturePoints.get(i).y);
        }

        int mapWidth = (int) Math.abs(this.datamanager.mapBounds[0].x - this.datamanager.mapBounds[1].x);
        int mapHeight = (int) Math.abs(this.datamanager.mapBounds[0].y - this.datamanager.mapBounds[1].y);

        this.minimap = new FrameBuffer(Format.RGBA8888, 1024, 1024, false);

        this.batch = new SpriteBatch();


        map = new FrameBuffer(Format.RGBA8888, mapWidth, mapHeight, false);
        float Vwidth = scene.getEntityEngine().getSystem(CameraManager.class).getViewport().getCamera().viewportWidth;
        float Vheight = scene.getEntityEngine().getSystem(CameraManager.class).getViewport().getCamera().viewportHeight;
        scene.getEntityEngine().getSystem(CameraManager.class).getViewport().getCamera().viewportWidth = mapWidth;
        scene.getEntityEngine().getSystem(CameraManager.class).getViewport().getCamera().viewportHeight = mapHeight;
        scene.getEntityEngine().getSystem(CameraManager.class).getViewport().getCamera().position.x = this.datamanager.mapBounds[0].x + mapWidth / 2f;
        scene.getEntityEngine().getSystem(CameraManager.class).getViewport().getCamera().position.y = this.datamanager.mapBounds[0].y + mapHeight / 2f;
        map.begin();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        scene.render();
        map.end();

        this.position = new Transform();
        this.mapRegion = new TextureRegion(map.getColorBufferTexture());
        this.mapRegion.flip(false, true);

        scene.getEntityEngine().getSystem(CameraManager.class).getViewport().getCamera().viewportWidth = Vwidth;
        scene.getEntityEngine().getSystem(CameraManager.class).getViewport().getCamera().viewportHeight = Vheight;


    }

    @Override
    public void act(float delta) {

        this.camera.position.set(this.position.position, 0);


    }

    @Override
    public void draw(Batch batch, float alpha) {

        this.minimap.begin();
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();
        this.batch.draw(this.mapRegion, datamanager.mapBounds[0].x, datamanager.mapBounds[0].y);
        for (int i = 0; i < this.capturePoints.length; i++) {
            this.capturePoints[i].draw(this.batch);
        }
        this.batch.end();
        this.minimap.end();


    }

    public void setPosition(Vector2 position) {
        this.position.position = position.cpy();
    }

    public TextureRegion region() {

        TextureRegion region = new TextureRegion(this.minimap.getColorBufferTexture());
        region.flip(false, true);
        return region;

    }

}
