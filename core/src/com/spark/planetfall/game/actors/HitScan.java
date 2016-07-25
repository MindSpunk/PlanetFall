package com.spark.planetfall.game.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class HitScan implements RayCastCallback {

    public Fixture closestHit;
    public Vector2 hitLocation = new Vector2();
    public boolean hit = false;
    public Vector2 normal = new Vector2();

    @Override
    public float reportRayFixture(Fixture fixture, Vector2 point,
                                  Vector2 normal, float fraction) {
        closestHit = fixture;
        hitLocation = point.cpy();
        hit = true;
        this.normal = normal;
        return fraction;
    }

}
