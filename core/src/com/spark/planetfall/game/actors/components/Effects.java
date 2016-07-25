package com.spark.planetfall.game.actors.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.actors.components.player.Health;

public class Effects {

    public Controlled controlled;
    public Transform transform;
    public Health health;

    public Effects(Controlled controlled) {

        this.controlled = controlled;
        this.transform = this.controlled.getTransform();
        this.health = this.controlled.getHealth();

    }

    public Effects(Transform transform, Health health) {

        this.transform = transform;
        this.health = health;
        this.controlled = null;

    }

    public void update(float delta) {

    }

    public void draw(Batch batch, float alpha) {

    }
}
