package com.spark.planetfall.game.actors.components.vehicle;

import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Vehicle;

public class MagriderMovement extends VehicleMovement {

    public boolean[] moving = new boolean[4];
    public Vehicle vehicle;

    public MagriderMovement(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public void update(float delta) {

        if (this.moving[0] == true) {
            this.vehicle.getPhysics().body.applyForceToCenter(new Vector2(0, this.vehicle.getStats().maxAcceleration), true);
        }
        if (this.moving[1] == true) {
            this.vehicle.getPhysics().body.applyForceToCenter(new Vector2(0, -this.vehicle.getStats().maxAcceleration), true);
        }
        if (this.moving[2] == true) {
            this.vehicle.getPhysics().body.applyForceToCenter(new Vector2(-this.vehicle.getStats().maxAcceleration, 0), true);
        }
        if (this.moving[3] == true) {
            this.vehicle.getPhysics().body.applyForceToCenter(new Vector2(this.vehicle.getStats().maxAcceleration, 0), true);
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
