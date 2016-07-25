package com.spark.planetfall.game.actors.components;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

public interface BodyBuild {

    BodyDef bodyDef();

    FixtureDef fixtureDef();

    Shape shape();

    float dampening();

    float angularDampening();

    void dispose();

}
