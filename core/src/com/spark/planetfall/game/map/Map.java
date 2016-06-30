package com.spark.planetfall.game.map;

import com.badlogic.gdx.utils.Array;
import com.spark.planetfall.game.map.components.Facility;
import com.spark.planetfall.server.ServerHandler;

public class Map {

    public Array<Facility> facilities;

    public Map(Array<Facility> facilities) {
        this.facilities = facilities;
    }

    public void update(float delta, ServerHandler handler) {
        for (Facility facility: facilities) {
            facility.update(delta, handler);
        }

    }
}
