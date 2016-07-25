package com.spark.planetfall.game.actors.components.vehicle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.actors.components.Effects;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.game.actors.components.player.Health;

public class VehicleEffects extends Effects {

    public ParticleEffect smokeEffect;
    public ParticleEffect fireEffect;

    public VehicleEffects(Controlled controlled) {

        super(controlled);

        this.fireEffect = new ParticleEffect();
        this.fireEffect.load(Gdx.files.internal("particle/VEHICLE_FIRE.p"), Gdx.files.internal(""));
        this.smokeEffect = new ParticleEffect();
        this.smokeEffect.load(Gdx.files.internal("particle/VEHICLE_SMOKE.p"), Gdx.files.internal(""));

    }

    public VehicleEffects(Transform transform, Health health) {

        super(transform,health);

        this.fireEffect = new ParticleEffect();
        this.fireEffect.load(Gdx.files.internal("particle/VEHICLE_FIRE.p"), Gdx.files.internal(""));
        this.smokeEffect = new ParticleEffect();
        this.smokeEffect.load(Gdx.files.internal("particle/VEHICLE_SMOKE.p"), Gdx.files.internal(""));

    }

    @Override
    public void update(float delta) {

        if (this.controlled != null) {

            if (this.controlled.getHealth().health <= this.controlled.getHealth().maxHealth / 2f) {
                if (this.smokeEffect.isComplete()) {
                    this.smokeEffect.start();
                }
                this.smokeEffect.setPosition(this.controlled.getTransform().position.x, this.controlled.getTransform().position.y);
            }
            if (this.controlled.getHealth().health <= this.controlled.getHealth().maxHealth / 4f) {
                if (this.fireEffect.isComplete()) {
                    this.fireEffect.start();
                }
                this.fireEffect.setPosition(this.controlled.getTransform().position.x, this.controlled.getTransform().position.y);
            }

        } else {

            if (this.health.health <= this.health.maxHealth / 2f) {
                if (this.smokeEffect.isComplete()) {
                    this.smokeEffect.start();
                }
                this.smokeEffect.setPosition(this.transform.position.x, this.transform.position.y);
            }
            if (this.health.health <= this.health.maxHealth / 4f) {
                if (this.fireEffect.isComplete()) {
                    this.fireEffect.start();
                }
                this.fireEffect.setPosition(this.transform.position.x, this.transform.position.y);
            }

        }

        this.fireEffect.update(delta);
        this.smokeEffect.update(delta);

    }

    @Override
    public void draw(Batch batch, float alpha) {

        this.fireEffect.draw(batch);
        this.smokeEffect.draw(batch);

    }
}
