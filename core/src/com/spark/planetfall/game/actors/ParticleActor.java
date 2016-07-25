package com.spark.planetfall.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.utils.Log;

public class ParticleActor extends Actor {

    ParticleEffect effect;
    Transform transform;

    public ParticleActor(String dir, Vector2 position) {

        Log.logInfo("RIP");
        this.effect = new ParticleEffect();
        this.effect.load(Gdx.files.internal(dir), Gdx.files.internal(""));
        this.effect.start();
        this.transform = new Transform(position,0);

    }

    @Override
    public void act(float delta) {

        this.effect.setPosition(transform.position.x,transform.position.y);
        this.effect.update(delta);

        if (this.effect.isComplete()) {
            this.effect.dispose();
            this.remove();
        }

    }

    @Override
    public void draw(Batch batch, float alpha) {

        this.effect.draw(batch);

    }
}
