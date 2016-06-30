package com.spark.planetfall.game.map;

import com.artemis.Entity;
import com.artemis.Manager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.runtime.component.Transform;
import com.kotcrab.vis.runtime.component.Variables;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.spark.planetfall.game.map.components.CapturePoint;
import com.spark.planetfall.game.map.components.Facility;
import com.spark.planetfall.game.map.components.Generator;
import com.spark.planetfall.utils.Log;

public class MapConfigLoader extends Manager implements AfterSceneInit {

    private VisIDManager idManager;
    private Array<Entity> entities;
    public final Array<Vector2> spawnPoints;
    public final Array<Vector2> capturePoints;
    public final Vector2[] mapBounds = new Vector2[2];
    public Map map;


    public MapConfigLoader() {

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
            line();
        }

        line();

        entities = idManager.getMultiple("capturePoint");

        // Load Capture Points
        for (int i = 0; i < entities.size; i++) {
            Log.logInfo("ADDING POINT: " + i);
            Log.logInfo("" + entities.get(i).getComponent(Transform.class).getX());
            Log.logInfo("" + entities.get(i).getComponent(Transform.class).getY());
            Vector2 temp = new Vector2(entities.get(i).getComponent(Transform.class).getX(), entities.get(i).getComponent(Transform.class).getY());
            capturePoints.add(temp);
            line();
        }

        line();

        entities = idManager.getMultiple("Facility");

        // Load Facilities
        for (Entity entity: entities) {
            Variables vars = entity.getComponent(Variables.class);
            Log.logInfo("Found Facility: " + vars.get("Name"));
            byte team = Byte.parseByte(vars.get("Team"));
            Log.logInfo("Team ID: " + team);
            float captureTime = Float.parseFloat(vars.get("CapTime"));
            Log.logInfo("Capture Time: " + captureTime + " seconds");
            facilities.add(new Facility(vars.get("Name"),team, captureTime, false));
            line();
        }

        line();

        entities = idManager.getMultiple("BaseComponent");

        // Load Facilities
        for (Entity entity: entities) {
            Variables vars = entity.getComponent(Variables.class);
            Log.logInfo("Found a: " + vars.get("Type"));
            Log.logInfo("Linked to facility: " + vars.get("Owner"));

            //IF THE FOUND BASE COMPONENT IS A GENERATOR ASSIGN IT TO ITS PARENT FACILITY AND GIVE IT ITS CONFIG VALUES
            if (vars.get("Type").equals("Generator")) {

                //Look For Owner Facility
                for (Facility facility: facilities) {

                    if (facility.name.equals(vars.get("Owner"))) {

                        //Get The Components Values (Transform, and Statistics)
                        Vector2 position = new Vector2(entity.getComponent(Transform.class).getX(), entity.getComponent(Transform.class).getY());
                        float angle = entity.getComponent(Transform.class).getRotation();
                        float overloadTime = Float.parseFloat(vars.get("Time"));


                        Log.logInfo("Overload Time: " + overloadTime);

                        //Add generator to list
                        Generator generator = new Generator(position, angle, overloadTime);
                        facility.addComponent(generator);

                        break;
                    }
                }
            }
            if (vars.get("Type").equals("CapturePoint")) {

                //Look For Owner Facility
                for (Facility facility: facilities) {

                    if (facility.name.equals(vars.get("Owner"))) {

                        //Get The Components Values (Transform, and Statistics)
                        Vector2 position = new Vector2(entity.getComponent(Transform.class).getX(), entity.getComponent(Transform.class).getY());
                        float angle = entity.getComponent(Transform.class).getRotation();
                        float capTime = Float.parseFloat(vars.get("Time"));

                        CapturePoint capPoint = new CapturePoint(position,angle,facility.team,capTime);

                        facility.addComponent(capPoint);

                        break;
                    }
                }
            }
            line();
        }

        line();

        map = new Map(facilities);

    }

    private void line() {
        Log.logInfo("______________________");
    }
}