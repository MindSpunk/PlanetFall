package com.spark.planetfall.game.actors.weapons;

import com.spark.planetfall.utils.Log;

public class DamageModel {

    public float maxRange;
    public float minRange;
    public float maxDamage;
    public float minDamage;

    public DamageModel(float maxDamage, float maxRange, float minDamage, float minRange) {

        this.maxDamage = maxDamage;
        this.maxRange = maxRange;
        this.minDamage = minDamage;
        this.minRange = minRange;
        Log.logInfo("Damage Range: " + (maxDamage-minDamage));

    }

    public float calcDamage(float distance) {

        if (distance < maxRange) {
            return maxDamage;
        }

        if (distance > minRange) {
            return minDamage;
        }

        float damageRange = maxDamage - minDamage;
        float range = maxRange - minRange;

        float damagePercent = 1+((-(maxRange - distance)/range));
        Log.logInfo("damagePercent" + damagePercent);
        float damage = minDamage + damageRange*damagePercent;
        return damage;



    }

}
