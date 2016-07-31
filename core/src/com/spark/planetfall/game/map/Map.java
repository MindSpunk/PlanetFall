package com.spark.planetfall.game.map;

import com.badlogic.gdx.utils.Array;
import com.spark.planetfall.game.map.components.Facility;
import com.spark.planetfall.server.ServerHandler;
import com.spark.planetfall.utils.Log;

public class Map {

    public Facility[] facilities;

    public Map(Facility[] facilities) {
        this.facilities = facilities;
    }

    public Map() {}

    public void update(float delta, ServerHandler handler) {
        for (Facility facility: facilities) {
            facility.update(delta, handler);
        }

    }

    public void printAll() {

        for (Facility facility : facilities) {
            facility.printAll();
        }

    }

    public void setRemote(boolean remote) {

        for (Facility facility : facilities) {
            facility.setRemote(remote);
        }

    }

    public Map getChanges() {

        Facility[] facilitiesChanged;

        Array<Facility> changes = new Array<Facility>();

        for (Facility facility : this.facilities) {

            if (facility.getChanges() != null) {
                changes.add(facility.getChanges());
            }

        }

        if (changes.size == 0) {
            return null;
        }

        facilitiesChanged = changes.toArray(Facility.class);

        return new Map(facilitiesChanged);

    }
}
