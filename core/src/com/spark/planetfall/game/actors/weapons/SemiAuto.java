package com.spark.planetfall.game.actors.weapons;

import com.spark.planetfall.game.actors.components.weapons.FireMechanism;
import com.spark.planetfall.game.actors.components.weapons.Weapon;

public class SemiAuto implements FireMechanism {

    private final float rateOfFire;
    private Weapon weapon;
    private final boolean remainsTriggered;

    protected float timer;
    protected final float shotsPerSecond;
    protected final boolean firing;
    protected boolean canFire;


    public SemiAuto(float rateOfFire) {

        this.rateOfFire = rateOfFire;
        this.weapon = null;
        this.remainsTriggered = false;

        this.timer = 0;
        this.shotsPerSecond = this.rateOfFire / 60f;
        this.firing = false;
        this.canFire = false;

    }

    @Override
    public float rateOfFire() {
        return rateOfFire;
    }

    @Override
    public void update(float delta, boolean selected) {

        this.timer -= delta;

        if (timer <= 0) {
            if (this.weapon.magazine().amount() > 0) {
                this.canFire = true;
            }
        } else {
            this.canFire = false;
        }

    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public FireMechanism copy() {
        return new SemiAuto(rateOfFire);
    }

    @Override
    public boolean canFire() {
        return this.canFire;
    }

    @Override
    public void fire() {

        if (canFire) {
            timer = 1f / shotsPerSecond;
            this.canFire = false;
            weapon.magazine().setAmount(weapon.magazine().amount() - 1);
        }

    }

    @Override
    public float timeToNextShot() {
        return timer;
    }

    @Override
    public boolean remainTriggered() {
        return remainsTriggered;
    }

}
