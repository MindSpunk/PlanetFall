package com.spark.planetfall.game.actors.weapons;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Bullet;
import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.actors.components.weapons.Action;
import com.spark.planetfall.game.actors.components.weapons.Weapon;
import com.spark.planetfall.server.packets.BulletPacket;
import com.spark.planetfall.utils.SparkMath;

public class FireBullet implements Action {

    private Controlled player;
    private Weapon weapon;
    private final float barrelLength;

    public FireBullet(float barrelLength) {

        this.barrelLength = barrelLength;

    }

    @Override
    public boolean fire(ShapeRenderer renderer, Sound sound, float angle) {

        if (weapon == null || player == null) {
            return false;
        }

        //APPLY RECOIL
        player.getPhysics().body.applyForceToCenter(weapon.recoil().recoil(angle)[0], true);
        player.getPhysics().body.applyForceToCenter(weapon.recoil().recoil(angle)[1], true);

        //MUZZLE FLASH
        weapon.effects().shootEffect().start();
        Vector2 temp = new Vector2(0, this.barrelLength);
        temp.setAngle(angle);
        temp.add(player.getTransform().position);
        weapon.effects().shootEffect().setPosition(temp.x, temp.y);

        //CREATE BULLET ACTORS

        float COF = weapon.recoil().getCOF();
        if (Math.random() <= 0.5f) {
            COF = (float) (-COF * Math.random());
        } else {
            COF = (float) (COF * Math.random());
        }

        for (int i = 0; i < weapon.magazine().pellets(); i++) {
            Vector2 bullet = new Vector2(0, weapon.magazine().velocity());

            float pellet = weapon.magazine().pelletSpread();
            if (Math.random() <= 0.5f) {
                pellet = (float) (-pellet * Math.random());
            } else {
                pellet = (float) (pellet * Math.random());
            }
            bullet.setAngle(angle + COF + pellet);
            player.newActor(new Bullet(temp, bullet, weapon.magazine().damage(), this.player.getPhysics().world, sound, renderer, weapon.effects().bulletColor()));
            BulletPacket packet = new BulletPacket();
            packet.position = temp.cpy();
            packet.velocity = bullet.cpy();
            packet.rgba8888 = Color.rgba8888(weapon.effects().bulletColor());
            player.getNetwork().handler.client.sendUDP(packet);
        }

        this.weapon.effects().fireEnd.stop();
        this.weapon.effects().fire.play(SparkMath.randRange(0.5f,1f), SparkMath.randRange(0.7f,1.2f),0);

        return true;

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
        return new FireBullet(this.barrelLength);
    }

    @Override
    public boolean update(float delta) {
        return false;
    }

    @Override
    public boolean fire(ShapeRenderer renderer, float angle) {
        return false;
    }

    @Override
    public boolean fire(float angle) {
        return false;
    }

    @Override
    public float getBarrel() {
        return this.barrelLength;
    }

}
