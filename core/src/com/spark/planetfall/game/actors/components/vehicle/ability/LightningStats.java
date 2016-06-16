package com.spark.planetfall.game.actors.components.vehicle.ability;

import com.spark.planetfall.game.actors.components.vehicle.VehicleStats;

/**
 * Created by godbo on 16/06/2016.
 */
public class LightningStats extends VehicleStats {

    public LightningStats() {
        this.maxSpeed = 5;
        this.maxReverse = 4;
        this.maxAcceleration = 5;
        this.turnRate = 2;
    }

}
