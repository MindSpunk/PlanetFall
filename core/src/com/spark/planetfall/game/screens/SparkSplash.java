package com.spark.planetfall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spark.planetfall.game.PlanetFallClient;
import com.spark.planetfall.game.texture.Atlas;

public class SparkSplash implements Screen {

    private Sprite splash;
    private Sprite spark;
    private Sprite lib;
    private SpriteBatch batch;
    final PlanetFallClient game;
    private float timer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Sound sound;
    private Sound sound2;
    private boolean secondsound;

    public SparkSplash(final PlanetFallClient in) {
        game = in;
    }

    @Override
    public void show() {

        Gdx.input.setCursorCatched(true);

        camera = new OrthographicCamera();

        viewport = new StretchViewport(500, 500, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        timer = 0f;
        batch = new SpriteBatch();

        splash = new Sprite(Atlas.get().createSprite("gfx/background_1"));
        splash.setSize(500, 500);

        lib = new Sprite(Atlas.get().createSprite("gfx/libgdx_powered"));
        lib.setPosition(125, 250 - (250 * lib.getHeight() / lib.getWidth()) / 2);
        lib.setSize(250, 250 * lib.getHeight() / lib.getWidth());

        spark = new Sprite(Atlas.get().createSprite("gfx/spark_industries"));
        spark.setPosition(125, 250 - (250 * spark.getHeight() / spark.getWidth()) / 2);
        spark.setSize(250, 250 * spark.getHeight() / spark.getWidth());
        lib.setAlpha(0f);

        sound = Gdx.audio.newSound(Gdx.files.internal("sound/splash_high.wav"));
        sound.play();
        sound2 = Gdx.audio.newSound(Gdx.files.internal("sound/splash_low.wav"));

        secondsound = false;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        timer += Gdx.graphics.getDeltaTime();

        if (timer >= 3f) {
            if (!secondsound) {
                sound2.play();
                secondsound = true;
            }
            if (timer <= 4f) {
                lib.setAlpha(timer - 3f);
            }
        }

        if (timer >= 6) {
            game.setScreen(new MainMenu(this.game));
            Gdx.input.setCursorCatched(false);
        }

        if (timer <= 1) {
            spark.setAlpha(timer);
        } else {
            splash.setAlpha(1);
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        splash.draw(batch);
        if (timer <= 3) {
            spark.draw(batch);
        }
        lib.draw(batch);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            sound.stop();
            sound2.stop();
            game.setScreen(new MainMenu(this.game));
            Gdx.input.setCursorCatched(false);
        }

    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);

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
