package com.spark.planetfall.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.spark.planetfall.game.ui.GUISkin;
import com.spark.planetfall.server.ServerHandler;

import java.io.IOException;

public class PlanetFallServer extends Game {

    private Window window;
    private TextButton quit_button;
    private Stage stage;
    public ServerHandler handler;
    public Label players;

    @Override
    public void create() {

        GUISkin.load();

        handler = new ServerHandler(this);

        stage = new Stage();
        window = new Window("SERVER", GUISkin.get());
        window.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        quit_button = new TextButton("QUIT", GUISkin.get());
        players = new Label("Players: ", GUISkin.get());
        window.add(players);
        window.row();
        window.add(quit_button);
        stage.addActor(window);
        quit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                handler.server.stop();
                Gdx.app.exit();
            }
        });

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render() {

        players.setText("Players: " + handler.getPlayerCount());

        stage.act();
        stage.draw();

        handler.update(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void dispose() {
        try {
            handler.server.dispose();
            stage.dispose();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.dispose();
    }

}
