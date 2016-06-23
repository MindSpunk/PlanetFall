package com.spark.planetfall.game.actors.components.player.abilities;

import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.components.player.Ability;
import com.spark.planetfall.game.constants.Constant;

public class JumpJet implements Ability {

    private final float fuelCapacity;
    private final Player player;
    private final float regenRate;

    protected boolean active;
    protected float fuel;
    protected final boolean hold;

    public JumpJet(Player player, float fuelCapacity, float regenRate) {

        this.fuelCapacity = fuelCapacity;
        this.regenRate = regenRate;
        this.player = player;
        this.active = false;
        this.fuel = fuelCapacity;
        this.hold = true;

        this.player.ui.abilityBar.setRange(0, this.fuelCapacity);
        this.player.ui.abilityBar.setValue(fuel);

    }

    @Override
    public void update(float delta) {

        if (active) {
            Vector2 jump = new Vector2(0, 100);
            jump.setAngle(player.position.angle);
            player.physics.body.applyForceToCenter(jump, true);
            if (fuel > 0) {
                fuel -= delta;
                player.stats.maxMoveSpeed = 100;
                player.stats.maxSprintSpeed = 100;
            } else {
                fuel = 0;
                deactivate();
            }
        } else {
            fuel += regenRate * delta;
            Vector2 temp = new Vector2(player.stats.maxMoveSpeed, 0);
            temp.lerp(new Vector2(Constant.PLAYER_MAX_VELOCITY, 0), 0.1f);
            player.stats.maxMoveSpeed = temp.x;
            temp = new Vector2(player.stats.maxSprintSpeed, 0);
            temp.lerp(new Vector2(Constant.PLAYER_MAX_SPRINT_VELOCITY, 0), 0.1f);
            player.stats.maxSprintSpeed = temp.x;
        }

        if (fuel > fuelCapacity) {
            fuel = fuelCapacity;
        }

        this.player.ui.abilityBar.setValue(fuel);

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
