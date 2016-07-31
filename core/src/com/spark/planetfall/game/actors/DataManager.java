package com.spark.planetfall.game.actors;

import com.artemis.Entity;
import com.artemis.Manager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.spark.planetfall.game.map.components.Facility;
import com.spark.planetfall.utils.Log;

public class DataManager extends Manager implements AfterSceneInit {

    private VisIDManager idManager;
    private Array<Entity> entities;
    public final Array<Vector2> spawnPoints;
    public final Array<Vector2> capturePoints;
    public final Vector2[] mapBounds = new Vector2[2];


    public DataManager() {

        spawnPoints = new Array<Vector2>();
        capturePoints = new Array<Vector2>();

    }

    @Override
    public void afterSceneInit() {

        Array<Facility> facilities = new Array<Facility>();

        entities = idManager.getMultiple("spawn");

        // Load Spawn Points
        for (int i = 0; i < entities.size; i++) {
            Log.logInfo("ADDING POINT: " + i);
            Log.logInfo("" + entities.get(i).getComponent(Transform.class).getX());
            Log.logInfo("" + entities.get(i).getComponent(Transform.class).getY());
            Vector2 temp = new Vector2(entities.get(i).getComponent(Transform.class).getX(), entities.get(i).getComponent(Transform.class).getY());
            spawnPoints.add(temp);
            Log.logInfo("______________________");
        }

        Log.logInfo("______________________");

        entities = idManager.getMultiple("bottomLeft");

        //  Load Bottom Left Map Marker
        for (int i = 0; i < entities.size; i++) {
            Log.logInfo("ADDING POINT: " + i);
            Log.logInfo("" + entities.get(i).getComponent(Transform.class).getX());
            Log.logInfo("" + entities.get(i).getComponent(Transform.class).getY());
            Vector2 temp = new Vector2(entities.get(i).getComponent(Transform.class).getX(), entities.get(i).getComponent(Transform.class).getY());
            mapBounds[0] = temp;
            Log.logInfo("______________________");
        }

        Log.logInfo("______________________");

        entities = idManager.getMultiple("topRight");

        // Load Top Left Map Marker
        for (int i = 0; i < entities.size; i++) {
            Log.logInfo("ADDING POINT: " + i);
            Log.logInfo("" + entities.get(i).getComponent(Transform.class).getX());
            Log.logInfo("" + entities.get(i).getComponent(Transform.class).getY());
            Vector2 temp = new Vector2(entities.get(i).getComponent(Transform.class).getX(), entities.get(i).getComponent(Transform.class).getY());
            mapBounds[1] = temp;
            Log.logInfo("______________________");
        }

        Log.logInfo("______________________");

        entities = idManager.getMultiple("capturePoint");

    }
}