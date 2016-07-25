package com.spark.planetfall.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.spark.planetfall.server.RemotePlayer;


public class PlayerListEntry extends Actor {

    public String name;
    public int score;
    public int kills;
    public int deaths;

    public Label labelName;
    public Label labelKills;
    public Label labelDeaths;
    public Label labelScore;

    public RemotePlayer player;

    public PlayerListEntry(RemotePlayer player) {

        this.player = player;

        this.name = this.player.name;
        this.score = this.player.score;
        this.deaths = this.player.deaths;
        this.kills = this.player.kills;

        labelName = new Label(player.name,GUISkin.get());
        labelKills = new Label("" + player.kills,GUISkin.get());
        labelDeaths = new Label("" + player.deaths,GUISkin.get());
        labelScore = new Label("" + player.score,GUISkin.get());

    }

    @Override
    public void act(float delta) {
        this.score = this.player.score;
        this.deaths = this.player.deaths;
        this.kills = this.player.kills;
        this.labelKills.setText(""+this.kills);
        this.labelDeaths.setText(""+this.deaths);
        this.labelScore.setText(""+this.score);
    }

    public void addToTable(Table table) {
        table.add(labelName);
        table.add(labelKills);
        table.add(labelDeaths);
        table.add(labelScore);
        table.row();
    }

    public void removeItems(Table table) {
        this.labelName.remove();
        this.labelKills.remove();
        this.labelDeaths.remove();
        this.labelScore.remove();
        table.removeActor(labelName);
    }

    public void rebuild(Table table) {
        removeItems(table);
        addToTable(table);
    }

}
