package com.spark.planetfall.game.actors.components.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.runtime.scene.Scene;
import com.spark.planetfall.game.actors.DataManager;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.game.ui.GUISkin;
import com.spark.planetfall.game.ui.PlayerListEntry;
import com.spark.planetfall.server.RemotePlayer;

public class UIHandler {

    public final Stage stage;

    public final ProgressBar healthBar;
    public final ProgressBar shieldBar;
    public final ProgressBar abilityBar;
    public final Label healthLabel;
    public final Label shieldLabel;
    public final Label abilityLabel;
    public final Label ammoLabel;
    public final Label ammo;
    public final Minimap minimap;
    public final BitmapFont font;
    public final Window minimapBound;

    public Window playerList;
    public ScrollPane playerListPane;
    public Table playerListTable;
    public Array<PlayerListEntry> playerListEntries;

    public final Window table;

    public UIHandler(Viewport viewport, Scene scene, DataManager datamanager) {

        this.playerListEntries = new Array<PlayerListEntry>();

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

        this.setupPlayerListWindow();

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

    private void setupPlayerListWindow() {

        this.playerList = new Window("Players", GUISkin.get());
        this.playerList.setColor(1,1,1,0.5f);
        this.playerList.setWidth(650);
        this.playerList.setHeight(400);
        this.playerList.align(Align.center);
        this.playerList.setResizable(false);
        this.playerList.setMovable(false);

        Vector2 playerListPosition = new Vector2();
        playerListPosition.x = Gdx.graphics.getWidth()/2f - playerList.getWidth()/2f;
        playerListPosition.y = Gdx.graphics.getHeight()/2f - playerList.getHeight()/2f;
        this.playerList.setPosition(playerListPosition.x, playerListPosition.y);

        this.playerListTable = new Table();
        this.buildPlayerList();

        this.playerListPane = new ScrollPane(this.playerListTable,GUISkin.get());
        this.playerListPane.setHeight(200);
        this.playerListPane.setWidth(450);

        this.playerList.add(this.playerListPane);

        this.stage.addActor(this.playerList);

    }

    public void buildPlayerList() {
        Label labelName = new Label("Name",GUISkin.get());
        Label labelKills = new Label("Kills",GUISkin.get());
        Label labelDeaths = new Label("Deaths",GUISkin.get());
        Label labelScore = new Label("Score",GUISkin.get());
        labelName.setColor(Color.RED);
        labelKills.setColor(Color.RED);
        labelDeaths.setColor(Color.RED);
        labelScore.setColor(Color.RED);

        this.playerListTable.add(labelName).padLeft(30).padRight(30);
        this.playerListTable.add(labelKills).padLeft(20).padRight(20);
        this.playerListTable.add(labelDeaths).padLeft(20).padRight(20);
        this.playerListTable.add(labelScore).padLeft(20).padRight(20);
        this.playerListTable.row();
        /*
        for (int i = 0; i < 16; i++) {
            Label label = new Label("debug", GUISkin.get());
            this.playerListTable.add(label);
            Label label2 = new Label("debug", GUISkin.get());
            this.playerListTable.add(label2);
            Label label3 = new Label("debug", GUISkin.get());
            this.playerListTable.add(label3);
            Label label4 = new Label("debug", GUISkin.get());
            this.playerListTable.add(label4);
            this.playerListTable.row();
        }
        */

        /*
        for (int i = 0; i < 16; i++) {
            PlayerListEntry entry = new PlayerListEntry(new RemotePlayer());
            entry.addToTable(this.playerListTable);
            this.stage.addActor(entry);
        }
        */

    }
    public void rebuildPlayerList() {
        this.playerListPane.remove();
        this.playerListTable.reset();
        buildPlayerList();
        for (PlayerListEntry entry : this.playerListEntries) {
            entry.rebuild(this.playerListTable);
        }

        this.playerListPane = new ScrollPane(this.playerListTable,GUISkin.get());
        this.playerListPane.setHeight(200);
        this.playerListPane.setWidth(450);
        this.playerList.add(this.playerListPane);

    }

}
