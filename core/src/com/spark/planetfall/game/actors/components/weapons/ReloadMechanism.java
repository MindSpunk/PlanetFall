package com.spark.planetfall.game.actors.components.weapons;

public interface ReloadMechanism {

    public float shortReload();

    public float longReload();

    public void setWeapon(Weapon weapon);

    public ReloadMechanism copy();

    public boolean canFire();

    public void update(float delta);

    public void reload();

    public void cancel();

}
