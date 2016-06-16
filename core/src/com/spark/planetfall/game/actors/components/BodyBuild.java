package com.spark.planetfall.game.actors.components;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public interface BodyBuild {

    public BodyDef bodyDef();

    public FixtureDef fixtureDef();

    public Shape shape();

    public float dampening();

    public float angularDampening();

    public void dispose();

}
