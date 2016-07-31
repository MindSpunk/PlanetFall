package com.spark.planetfall.game.actors.components.vehicle;

import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Vehicle;
import com.spark.planetfall.game.actors.components.Movement;

public class VehicleMovement implements Movement {

    public final boolean[] moving = new boolean[4];
    public final Vehicle vehicle;


    public VehicleMovement(Vehicle vehicle) {

        this.vehicle = vehicle;

        for (int i = 0; i < moving.length; i++) {
            moving[i] = false;
        }

    }


    public void update(float delta) {

        Vector2 temp = new Vector2(0, this.vehicle.getStats().maxAcceleration);
        temp.setAngle(this.vehicle.getTransform().angle + 90);

        if (this.moving[0]) {
            this.vehicle.getPhysics().body.applyForceToCenter(temp, true);
        }
        if (this.moving[1]) {
            this.vehicle.getPhysics().body.applyForceToCenter(temp.scl(-1), true);
        }
        if (this.moving[2]) {
            this.vehicle.getPhysics().body.applyTorque(5, true);
        }
        if (this.moving[3]) {
            this.vehicle.getPhysics().body.applyTorque(-5, true);
        }

        this.vehicle.getPhysics().body.getLinearVelocity().clamp(0, this.vehicle.getStats().maxSpeed);


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
