package com.spark.planetfall.game.actors.components.weapons;


public interface FireMechanism {

    void update(float delta, boolean selected);

    void fire();

    void setWeapon(Weapon weapon);

    FireMechanism copy();

    boolean canFire();

    float rateOfFire();

    float timeToNextShot();

    boolean remainTriggered();

}
