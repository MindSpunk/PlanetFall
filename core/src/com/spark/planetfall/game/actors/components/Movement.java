package com.spark.planetfall.game.actors.components;

public interface Movement {

    void update(float delta);

    boolean[] getMove();

    boolean getMove(int i);

    void setMove(int i, boolean value);

    float getShootCooldown();

}
