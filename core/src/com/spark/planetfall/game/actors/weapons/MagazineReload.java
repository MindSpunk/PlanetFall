package com.spark.planetfall.game.actors.weapons;

import com.spark.planetfall.game.actors.components.weapons.ReloadMechanism;
import com.spark.planetfall.game.actors.components.weapons.Weapon;
import com.spark.planetfall.utils.Log;

public class MagazineReload implements ReloadMechanism {

    private final float shortReload;
    private final float longReload;
    private Weapon weapon;
    private final int longTrigger;
    private boolean canFire;

    protected float timer;
    protected boolean reloading;

    public MagazineReload(float longReload, float shortReload, int longTrigger) {

        this.weapon = null;
        this.longReload = longReload;
        this.shortReload = shortReload;
        this.longTrigger = longTrigger;
        this.canFire = false;

        this.timer = 0;
        this.reloading = false;

    }

    @Override
    public void update(float delta) {

        if (timer > 0) {
            if (reloading) {
                timer -= delta;
            }
        }

        if (timer <= 0) {
            if (reloading) {
                Log.logInfo("reloaded");
                timer = 0;
                reloading = false;
                this.weapon.magazine().setAmount(this.weapon.magazine().capacity());
            }
        }

        canFire = !reloading;

    }

    @Override
    public void reload() {

        if (!reloading) {

            if (this.weapon.magazine().amount() < this.weapon.magazine().capacity()) {

                Log.logInfo("reloading");

                this.reloading = true;

                if (this.weapon.magazine().amount() <= longTrigger) {
                    this.timer = this.longReload;
                    this.weapon.effects().longReload.play();
                } else {
                    this.timer = this.shortReload;
                    this.weapon.effects().shortReload.play();
                }

            }

        }

    }

    @Override
    public void refill() {
        this.weapon.magazine().setAmount(this.weapon.magazine().capacity());
    }

    @Override
    public float shortReload() {
        return this.shortReload;
    }

    @Override
    public float longReload() {
        return this.longReload;
    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public ReloadMechanism copy() {
        return new MagazineReload(longReload, shortReload, longTrigger);
    }

    @Override
    public boolean canFire() {
        return canFire;
    }

    @Override
    public void cancel() {

        this.timer = 0;
        this.reloading = false;
        this.weapon.effects().shortReload.stop();
        this.weapon.effects().longReload.stop();

    }

}
