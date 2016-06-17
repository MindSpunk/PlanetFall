package com.spark.planetfall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.spark.planetfall.game.PlanetFallClient;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.game.ui.GUISkin;
import com.spark.planetfall.utils.Log;

public class MainMenu implements Screen {

    private final PlanetFallClient game;

    public final Stage stage;
    public final Window window;
    public final TextButton play_button;
    public final TextButton quit_button;
    public final Image background;
    public final TextField ipInput;

    public MainMenu(PlanetFallClient game) {

        this.game = game;
        ipInput = new TextField("localhost", GUISkin.get());
        ipInput.setPosition(0, 0);
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        window = new Window("", GUISkin.get());
        window.setPosition(100, Gdx.graphics.getHeight() - 300);
        window.align(Align.center);
        window.setMovable(false);
        play_button = new TextButton("Play", GUISkin.get());
        quit_button = new TextButton("Quit", GUISkin.get());
        window.add(play_button).width(200);
        window.row();
        window.add(quit_button).width(200);
        background = new Image(Atlas.get().createSprite("gfx/background_1"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        stage.addActor(window);
        stage.addActor(ipInput);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

        play_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SparkGame(game, ipInput.getText()));
                Log.logInfo(ipInput.getText());
            }
        });
        quit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.getViewport().apply();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();
    }


}
