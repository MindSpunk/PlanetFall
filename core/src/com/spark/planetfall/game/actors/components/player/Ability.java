package com.spark.planetfall.game.actors.components.player;

public interface Ability {

    public void update(float delta);

    public void activate();

    public void deactivate();

    public boolean canActivate();

    public boolean active();

    public float maxFuel();

    public float fuel();

    public float hit(float damage);

    public boolean hold();

}
