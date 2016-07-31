package com.spark.planetfall.game.actors.components.player.abilities;

import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.components.player.Ability;

public class NaniteMeshGenerator implements Ability {

    private final float maxFuel;
    private final float regenRate;
    private final float minimumTrigger;
    private final Player player;

    private float fuel;
    private boolean active;
    private final float drainRate;
    private final boolean hold;

    public NaniteMeshGenerator(Player player, float maxFuel, float regenRate, float minimumTrigger, float drainRate) {

        this.maxFuel = maxFuel;
        this.regenRate = regenRate;
        this.minimumTrigger = minimumTrigger;
        this.drainRate = drainRate;
        this.player = player;

        this.active = false;
        this.fuel = maxFuel;
        this.hold = false;

        this.player.ui.abilityBar.setRange(0, this.maxFuel);
        this.player.ui.abilityBar.setValue(fuel);

    }

    @Override
    public void update(float delta) {

        if (this.active) {
            fuel -= this.drainRate * delta;
        } else {
            fuel += this.regenRate * delta;
        }

        if (fuel <= 0) {
            this.fuel = 0;
            this.deactivate();
        }

        if (fuel > this.maxFuel) {
            fuel = this.maxFuel;
        }

        this.player.ui.abilityBar.setValue(fuel);

    }

    @Override
    public void activate() {

        if (this.canActivate()) {
            this.active = true;
        }

    }

    @Override
    public void deactivate() {

        this.active = false;

    }

    @Override
    public boolean canActivate() {
        return fuel >= this.minimumTrigger;
    }

    @Override
    public boolean active() {
        return this.active;
    }

    @Override
    public float maxFuel() {
        return this.maxFuel;
    }

    @Override
    public float fuel() {
        return 0;
    }

    @Override
    public float hit(float damage) {

        if (this.active) {

            this.fuel -= damage;

            float temp = Math.abs(this.fuel);

            if (fuel <= 0) {
                this.fuel = 0;
                this.deactivate();
            }

            if (fuel > 0) {
                return 0;
            }

            return temp;

        }

        return damage;

    }

    @Override
    public boolean hold() {
        return hold;
    }

}
