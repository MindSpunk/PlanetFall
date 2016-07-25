package com.spark.planetfall.game.actors.components.vehicle;

import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Vehicle;
import com.spark.planetfall.utils.SparkMath;

public class HarasserMovement extends VehicleMovement {

    public HarasserMovement(Vehicle vehicle) {
        super(vehicle);
    }


    public void update(float delta) {

        Vector2 accelerate = new Vector2(0, this.vehicle.getStats().maxAcceleration);
        Vector2 reverse = new Vector2(0, this.vehicle.getStats().maxReverse);
        accelerate.setAngle(this.vehicle.getTransform().angle + 90);
        reverse.setAngle(this.vehicle.getTransform().angle + 90);

        if (this.moving[0]) {
            this.vehicle.getPhysics().body.applyForceToCenter(accelerate, true);
        }
        if (this.moving[1]) {
            this.vehicle.getPhysics().body.applyForceToCenter(reverse.scl(-1), true);
        }
        if (this.moving[2]) {
            this.vehicle.getPhysics().body.applyTorque(5 * (this.vehicle.getPhysics().body.getLinearVelocity().len() / vehicle.getStats().maxSpeed), true);
        }
        if (this.moving[3]) {
            this.vehicle.getPhysics().body.applyTorque(-5 * (this.vehicle.getPhysics().body.getLinearVelocity().len() / vehicle.getStats().maxSpeed), true);
        }

        float maxVelFrac = this.vehicle.getPhysics().body.getLinearVelocity().len() / vehicle.getStats().maxSpeed;

        this.vehicle.getPhysics().body.getLinearVelocity().clamp(0, this.vehicle.getStats().maxSpeed);
        this.vehicle.getPhysics().body.setAngularVelocity(SparkMath.clamp(this.vehicle.getPhysics().body.getAngularVelocity(), -1 * maxVelFrac, 1 * maxVelFrac));


    }


    @Override
    public boolean[] getMove() {
        return this.moving;
    }


    @Override
    public boolean getMove(int i) {
        return this.moving[i];
    }


    @Override
    public void setMove(int i, boolean value) {

        this.moving[i] = value;

    }


    @Override
    public float getShootCooldown() {
        return -1;
    }


}
