package com.spark.planetfall.game.actors.remote;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.Physics;
import com.spark.planetfall.game.actors.components.Render;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.game.actors.components.player.Health;
import com.spark.planetfall.game.actors.components.player.PlayerBodyDef;
import com.spark.planetfall.game.actors.components.vehicle.VehicleEffects;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.server.RemoteVehicle;

public class RemoteVehicleActor extends Actor {

    public final ClientHandler handler;
    public final Transform position;
    public Physics physics;
    public Stage stage;
    public Render render;
    public final RemoteVehicle remote;
    public VehicleEffects effects;
    public Health health;

    public RemoteVehicleActor(RemoteVehicle remote, World world, ClientHandler handler, Stage stage) {

        this.handler = handler;
        this.position = new Transform();
        this.render = new Render(Atlas.get().createSprite("gfx/player_base"));
        this.render.sprite.setSize(Constant.PLAYER_SIZE, Constant.PLAYER_SIZE);
        this.render.sprite.setOriginCenter();
        this.physics = new Physics(world, new PlayerBodyDef(render.sprite.getWidth() / 2), position, this);
        this.physics.body.setUserData(this);
        this.health = new RemoteHealth(2000);
        this.remote = remote;
        this.stage = stage;
    }

    @Override
    public void act(float delta) {

        if (this.effects == null) {
            this.effects = new VehicleEffects(position,health);
        }

        if (remote.remove) {

            this.kill();
        }

        position.position.lerp(remote.position, Constant.SERVER_NETWORK_LERP);

        this.physics.body.setTransform(position.position, (float) Math.toRadians(position.angle));
        physics.update(delta);

        render.sprite.setPosition(position.position.x - render.sprite.getWidth() / 2f, position.position.y - render.sprite.getHeight() / 2f);

        Vector2 angle = new Vector2(0, 1);
        angle.setAngle(position.angle);
        float direction = remote.angle;
        Vector2 temp = new Vector2(0, 1);
        temp.setAngle(direction);
        angle.lerp(temp, 0.2f);
        this.position.angle = angle.angle();

        render.sprite.setRotation(remote.angle);

        if (this.effects != null) {
            this.effects.update(delta);
        }


    }

    @Override
    public void draw(Batch batch, float alpha) {

        batch.end();
        batch.begin();
        render.sprite.draw(batch);
        if (this.effects != null) {
            effects.draw(batch, alpha);
        }
        batch.end();
        batch.begin();

    }

    public void hit(float damage) {
        this.health.hit(damage);
    }

    public void kill() {
        this.physics.body.destroyFixture(this.physics.fixture);
        this.remove();
    }

}
