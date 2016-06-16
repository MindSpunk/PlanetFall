package com.spark.planetfall.game.actors.components.weapons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spark.planetfall.game.actors.Controlled;

public interface Action {

    public boolean fire(ShapeRenderer renderer, Sound sound, float angle);

    boolean fire(ShapeRenderer renderer, float angle);

    boolean fire(float angle);

    public boolean update(float delta);

    public float getBarrel();

    public void setWeapon(Weapon weapon);

    public void setPlayer(Controlled controlled);

    public Action copy();

}
