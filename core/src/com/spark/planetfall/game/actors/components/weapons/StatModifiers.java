package com.spark.planetfall.game.actors.components.weapons;

public class StatModifiers {

    private final float moveSpeedMultiplier;
    private final float sprintSpeedMultiplier;
    private final float adsSpeedMultiplier;


    public StatModifiers(float moveSpeedMultiplier, float sprintSpeedMultiplier, float adsSpeedMultiplier) {

        this.moveSpeedMultiplier = moveSpeedMultiplier;
        this.sprintSpeedMultiplier = sprintSpeedMultiplier;
        this.adsSpeedMultiplier = adsSpeedMultiplier;

    }

    public float moveSpeed() {
        return this.moveSpeedMultiplier;
    }

    public float sprintSpeed() {
        return this.sprintSpeedMultiplier;
    }

    public float adsSpeed() {
        return this.adsSpeedMultiplier;
    }

    public StatModifiers copy() {
        return new StatModifiers(this.moveSpeedMultiplier, this.sprintSpeedMultiplier, this.adsSpeedMultiplier);
    }

}
