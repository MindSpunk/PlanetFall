package com.spark.planetfall.game.actors;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.game.actors.remote.Remote;
import com.spark.planetfall.game.actors.remote.RemoteVehicleActor;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.server.packets.HitPacket;
import com.spark.planetfall.server.packets.VehicleHitPacket;

public class Bullet extends Actor {

    private final Transform position;
    private final Vector2 velocity;
    private final float damage;
    private final Color color;

    protected final ShapeRenderer render;
    protected float timer;
    protected final World world;
    protected final Sound hit;
    protected Vector2 previousPosition;
    protected boolean removed;

    public Bullet(Vector2 position, Vector2 velocity, float damage, World world, Sound sound, ShapeRenderer render, Color color) {

        this.position = new Transform();
        this.position.position = position.cpy();
        this.velocity = velocity;
        this.world = world;
        this.damage = damage;
        this.hit = sound;
        this.color = color;

        this.removed = false;

        this.render = render;
        this.previousPosition = position.cpy();
        this.timer = Constant.WEAPON_BULLET_LIFE_TIME;

    }

    @Override
    public void act(float delta) {

        this.previousPosition = this.position.position.cpy();

        Vector2 temp = velocity.cpy();
        temp.clamp(0, temp.len() * delta);

        temp.add(position.position);
        HitScan hitscan = new HitScan();
        this.world.rayCast(hitscan, position.position, temp);

        position.position = temp.cpy();

        if (hitscan.closestHit != null) {
            this.removed = true;
            this.position.position = hitscan.hitLocation;
            if (hitscan.closestHit.getBody().getUserData() instanceof Remote) {
                this.hit.play();
                Remote remote = (Remote) hitscan.closestHit.getBody().getUserData();
                HitPacket packet = new HitPacket();
                packet.damage = damage;
                packet.id = remote.remote.id;
                remote.handler.client.sendUDP(packet);
            }
            if (hitscan.closestHit.getBody().getUserData() instanceof RemoteVehicleActor) {
                this.hit.play();
                RemoteVehicleActor remote = (RemoteVehicleActor) hitscan.closestHit.getBody().getUserData();
                VehicleHitPacket packet = new VehicleHitPacket();
                packet.damage = damage;
                packet.id = remote.remote.id;
                remote.handler.client.sendUDP(packet);
            }

        }

        this.timer -= delta;

        if (this.timer <= 0) {
            this.removed = true;
        }

    }

    @Override
    public void draw(Batch batch, float alpha) {

        batch.end();
        render.setColor(color);
        render.begin(ShapeType.Line);

        Vector2 temp = this.position.position.cpy();
        temp.sub(this.previousPosition);
        temp.clamp(0, 3);
        render.line(this.position.position, this.position.position.cpy().sub(temp));
        render.end();
        batch.begin();

        if (this.removed) {
            this.remove();
        }

    }

}
