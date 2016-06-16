package com.spark.planetfall.game.actors.components;

public interface Movement {

    public void update(float delta);

    public boolean[] getMove();

    public boolean getMove(int i);

    public void setMove(int i, boolean value);

    public float getShootCooldown();

}
