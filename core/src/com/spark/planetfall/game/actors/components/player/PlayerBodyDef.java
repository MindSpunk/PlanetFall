package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.spark.planetfall.game.actors.components.BodyBuild;

public class PlayerBodyDef implements BodyBuild {

    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private final Shape shape;
    private final float dampening;
    private final float angularDampening;

    public PlayerBodyDef(float width) {

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;

        CircleShape circle = new CircleShape();
        circle.setRadius(width);

        shape = circle;

        fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;

        this.dampening = 4f;
        this.angularDampening = 2f;

    }

    @Override
    public BodyDef bodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef fixtureDef() {
        return fixtureDef;
    }

    @Override
    public Shape shape() {
        return shape;
    }

    @Override
    public void dispose() {
        shape.dispose();
    }

    @Override
    public float dampening() {
        return dampening;
    }

    @Override
    public float angularDampening() {
        return this.angularDampening;
    }

}
