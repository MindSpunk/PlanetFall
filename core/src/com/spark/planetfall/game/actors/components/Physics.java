package com.spark.planetfall.game.actors.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Physics {

    public final Body body;
    public final World world;
    public final Transform position;
    public Fixture fixture;
    public final BodyBuild build;

    public Physics(World world, BodyBuild bodyBuild, Transform position, Object user) {

        this.position = position;
        this.build = bodyBuild;

        bodyBuild.bodyDef().position.set(position.position);
        this.body = world.createBody(bodyBuild.bodyDef());
        this.fixture = this.body.createFixture(bodyBuild.fixtureDef());
        this.fixture.setUserData(this);
        this.body.setUserData(user);
        this.body.setLinearDamping(bodyBuild.dampening());
        this.body.setAngularDamping(bodyBuild.angularDampening());

        this.world = world;

    }

    public void update(float delta) {

        this.position.position = body.getPosition();
        this.position.angle = (float) Math.toDegrees(body.getAngle());

    }

}
