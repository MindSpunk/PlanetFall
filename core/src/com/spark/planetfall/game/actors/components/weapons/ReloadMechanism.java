package com.spark.planetfall.game.actors.components.weapons;

public interface ReloadMechanism {

    float shortReload();

    float longReload();

    void setWeapon(Weapon weapon);

    ReloadMechanism copy();

    boolean canFire();

    void update(float delta);

    void reload();

    void cancel();

}
