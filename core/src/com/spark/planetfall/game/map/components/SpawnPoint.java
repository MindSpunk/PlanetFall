package com.spark.planetfall.game.map.components;

import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.server.ServerHandler;
import com.spark.planetfall.utils.Log;

public class SpawnPoint extends BaseComponent {


    public boolean changed;

    public SpawnPoint(Transform transform, byte team) {
        this.transform = transform;
        this.team = team;
        this.active = true;
        this.changed = true;
        this.remote = false;
    }

    public SpawnPoint() {}

    @Override
    public void update(float delta) {

        this.changed = false;

    }

    @Override
    public void update(float delta, ServerHandler handler) {

        this.changed = false;

    }

    @Override
    public void printInfo() {

        Log.logInfo("Spawn Point");
        Log.logInfo("Parent Facility: " + this.parent.name);
        Log.logInfo("Team: " + this.team);
        Log.logInfo("Active: " + this.active);
        Log.logInfo("==============================");

    }
}
