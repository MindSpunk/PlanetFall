package com.spark.planetfall.game.actors.components.weapons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spark.planetfall.game.actors.Controlled;

public interface Action {

    boolean fire(ShapeRenderer renderer, Sound sound, float angle);

    boolean fire(ShapeRenderer renderer, float angle);

    boolean fire(float angle);

    boolean update(float delta);

    float getBarrel();

    void setWeapon(Weapon weapon);

    void setPlayer(Controlled controlled);

    Action copy();

}
