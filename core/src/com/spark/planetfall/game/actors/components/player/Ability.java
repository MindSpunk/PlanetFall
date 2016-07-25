package com.spark.planetfall.game.actors.components.player;

public interface Ability {

    void update(float delta);

    void activate();

    void deactivate();

    boolean canActivate();

    boolean active();

    float maxFuel();

    float fuel();

    float hit(float damage);

    boolean hold();

}
