package com.spark.planetfall.game.actors.components.vehicle.ability;

import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Vehicle;
import com.spark.planetfall.game.actors.components.player.Ability;

public class MagBurner implements Ability {

    private final float fuelCapacity;
    private final Vehicle vehicle;
    private final float regenRate;

    private final float maxSpeed;

    protected boolean active;
    protected float fuel;
    protected final boolean hold;

    public MagBurner(Vehicle player, float fuelCapacity, float regenRate) {

        this.fuelCapacity = fuelCapacity;
        this.regenRate = regenRate;
        this.vehicle = player;
        this.active = false;
        this.fuel = fuelCapacity;
        this.maxSpeed = player.getStats().maxSpeed;
        this.hold = true;


        if (this.vehicle.getUI() != null) {
            this.vehicle.getUI().abilityBar.setRange(0, this.fuelCapacity);
            this.vehicle.getUI().abilityBar.setValue(fuel);
        }

    }

    @Override
    public void update(float delta) {

        if (active) {
            Vector2 jump = new Vector2(0, 100);
            jump.setAngle(vehicle.getTransform().angle);
            vehicle.getPhysics().body.applyForceToCenter(jump, true);
            if (fuel > 0) {
                fuel -= delta;
                vehicle.getStats().maxSpeed = 100;
            } else {
                fuel = 0;
                deactivate();
            }
        } else {
            fuel += regenRate * delta;
            Vector2 temp = new Vector2(vehicle.getStats().maxSpeed, 0);
            temp.lerp(new Vector2(this.maxSpeed, 0), 0.1f);
            vehicle.getStats().maxSpeed = temp.x;
        }

        if (fuel > fuelCapacity) {
            fuel = fuelCapacity;
        }
        if (this.vehicle.getUI() != null) {
            this.vehicle.getUI().abilityBar.setValue(fuel);
        }

    }

    @Override
    public void activate() {

        if (canActivate()) {
            active = true;
        }

    }

    @Override
    public void deactivate() {

        active = false;

    }

    @Override
    public boolean canActivate() {

        return fuel >= fuelCapacity / 2f;

    }

    @Override
    public boolean active() {

        return active;

    }

    @Override
    public float maxFuel() {

        return fuelCapacity;

    }

    @Override
    public float fuel() {

        return fuel;

    }

    @Override
    public float hit(float damage) {

        return damage;

    }

    @Override
    public boolean hold() {
        return hold;
    }

}
