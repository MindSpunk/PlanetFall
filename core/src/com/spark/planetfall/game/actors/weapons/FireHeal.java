package com.spark.planetfall.game.actors.weapons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.actors.components.weapons.Action;
import com.spark.planetfall.game.actors.components.weapons.Weapon;

public class FireHeal implements Action {

    private Controlled player;
    private Weapon weapon;

    private final boolean rendersShapes;
    private final boolean hitMarkerUsed;

    public FireHeal(boolean rendersShapes, boolean hitMarkerUsed) {

        this.rendersShapes = rendersShapes;
        this.hitMarkerUsed = hitMarkerUsed;

    }

    @Override
    public boolean fire(float angle) {

        player.getHealth().healHealthOnly(weapon.magazine().damage());
        weapon.effects().shootEffect().start();
        Vector2 temp = new Vector2(0, 1f);
        temp.setAngle(player.getTransform().angle);
        temp.add(player.getTransform().position);
        weapon.effects().shootEffect().setPosition(temp.x, temp.y);

        return false;
    }

    @Override
    public boolean update(float delta) {
        return false;
    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void setPlayer(Controlled controlled) {
        this.player = controlled;
    }

    @Override
    public Action copy() {
        return new FireHeal(this.rendersShapes, this.hitMarkerUsed);
    }

    @Override
    public boolean fire(ShapeRenderer renderer, Sound sound, float angle) {

        if (!this.hitMarkerUsed) return fire(renderer, angle);

        return false;

    }

    @Override
    public boolean fire(ShapeRenderer renderer, float angle) {

        return !this.rendersShapes && fire(angle);

    }

    @Override
    public float getBarrel() {
        return 1f;
    }

}
