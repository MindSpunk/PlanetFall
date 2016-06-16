package com.spark.planetfall.game.actors.components.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.runtime.scene.Scene;
import com.spark.planetfall.game.actors.DataManager;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.game.ui.GUISkin;

public class UIHandler {

    public Stage stage;

    public ProgressBar healthBar;
    public ProgressBar shieldBar;
    public ProgressBar abilityBar;
    public Label healthLabel;
    public Label shieldLabel;
    public Label abilityLabel;
    public Label ammoLabel;
    public Label ammo;
    public Minimap minimap;
    public BitmapFont font;
    public Window minimapBound;

    public Window table;

    public UIHandler(Viewport viewport, Scene scene, DataManager datamanager) {

        this.minimap = new Minimap(scene, datamanager);

        this.font = new BitmapFont();

        this.minimapBound = new Window("MAP", GUISkin.get());

        minimapBound.setSize(183, 204);
        this.minimapBound.setPosition(Gdx.graphics.getWidth() - 128, 0);

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        table = new Window("STATS", GUISkin.get());

        table.setWidth(200);
        table.setHeight(100);
        table.setPosition(0, 0);
        table.align(Align.bottomLeft);

        shieldLabel = new Label("Shield ", GUISkin.get());
        table.add(shieldLabel);

        shieldBar = new ProgressBar(0, Constant.PLAYER_SHIELDS, 0.1f, false, GUISkin.get());
        table.add(shieldBar);

        table.row();

        healthLabel = new Label("Health ", GUISkin.get());
        table.add(healthLabel);

        healthBar = new ProgressBar(0, Constant.PLAYER_HEALTH, 0.1f, false, GUISkin.get());
        table.add(healthBar);

        table.row();

        abilityLabel = new Label("Ability ", GUISkin.get());
        table.add(abilityLabel);

        abilityBar = new ProgressBar(0, 1, 0.001f, false, GUISkin.get());
        table.add(abilityBar);

        table.row();

        ammoLabel = new Label("Ammo: ", GUISkin.get());
        table.add(ammoLabel);

        ammo = new Label("0/0", GUISkin.get());
        table.add(ammo);

        stage.addActor(table);
        stage.addActor(this.minimapBound);
        stage.addActor(minimap);

    }

    public void draw(float delta) {

        stage.getViewport().apply();
        table.setPosition(stage.getCamera().position.x - (stage.getWidth() / 2f), stage.getCamera().position.y - (stage.getHeight() / 2f));
        stage.act(delta);
        stage.draw();
        stage.getBatch().begin();
        stage.getBatch().draw(minimap.region(), Gdx.graphics.getWidth() - 180, 0, 180, 180);
        stage.getBatch().end();

    }

}
