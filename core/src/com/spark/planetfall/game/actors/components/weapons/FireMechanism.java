package com.spark.planetfall.game.actors.components.weapons;


public interface FireMechanism {

    public void update(float delta, boolean selected);

    public void fire();

    public void setWeapon(Weapon weapon);

    public FireMechanism copy();

    public boolean canFire();

    public float rateOfFire();

    public float timeToNextShot();

    public boolean remainTriggered();

}
