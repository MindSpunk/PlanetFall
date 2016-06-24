package com.spark.planetfall.game.map;

import com.spark.planetfall.game.map.components.Facility;

public class Map {

    public Facility[] facilities;

    public Map(Facility[] facilities) {
        this.facilities = facilities;
    }

    public void update(float delta) {
        for (Facility facility: facilities) {
            facility.update(delta);
        }

    }
}
