package com.spark.planetfall.game.actors.components.vehicle;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.spark.planetfall.game.actors.components.BodyBuild;

public class MagriderBodyDef implements BodyBuild {

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private Shape shape;
    private float dampening;
    private float angularDampening;

    public MagriderBodyDef() {

        bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(1.5f, 3f);

        this.shape = shape;

        fixtureDef = new FixtureDef();
        fixtureDef.shape = this.shape;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;

        this.dampening = 2f;
        this.angularDampening = 10f;

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
        // TODO Auto-generated method stub
        return this.angularDampening;
    }

}
