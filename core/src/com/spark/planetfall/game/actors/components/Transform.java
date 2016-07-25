package com.spark.planetfall.game.actors.components;

import com.badlogic.gdx.math.Vector2;

public class Transform {

    public Vector2 position;
    public float angle;

    public Transform() {

        position = new Vector2(0, 0);
        angle = 0;

    }

    public Transform(Vector2 position, float angle) {

        this.position = position;
        this.angle = angle;

    }

    public Transform(float x, float y, float angle) {

        this.position = new Vector2(x, y);
        this.angle = angle;

    }

}
