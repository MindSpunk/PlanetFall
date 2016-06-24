package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.components.Movement;
import com.spark.planetfall.game.constants.Constant;

public class PlayerMovement implements Movement {

    public final boolean[] moving = new boolean[4];
    public boolean sprint;
    public final Player player;
    public float shootCooldown;


    public PlayerMovement(Player player) {

        this.player = player;

        for (int i = 0; i < moving.length; i++) {
            moving[i] = false;
        }

    }


    public void update(float delta) {

        if (!sprint) {
            if (moving[0]) {
                player.physics.body.applyForceToCenter(new Vector2(0, player.stats.maxMove * player.controller.weapons.getSelected().modifiers().moveSpeed()), true);
            }
            if (moving[1]) {
                player.physics.body.applyForceToCenter(new Vector2(0, -player.stats.maxMove * player.controller.weapons.getSelected().modifiers().moveSpeed()), true);
            }
            if (moving[2]) {
                player.physics.body.applyForceToCenter(new Vector2(-player.stats.maxMove * player.controller.weapons.getSelected().modifiers().moveSpeed(), 0), true);
            }
            if (moving[3]) {
                player.physics.body.applyForceToCenter(new Vector2(player.stats.maxMove * player.controller.weapons.getSelected().modifiers().moveSpeed(), 0), true);
            }
        } else {
            this.shootCooldown = Constant.PLAYER_SPRINT_FIRE_COOLDOWN;
            if (moving[0]) {
                player.physics.body.applyForceToCenter(new Vector2(0, player.stats.maxSprint * player.controller.weapons.getSelected().modifiers().sprintSpeed()), true);
            }
            if (moving[1]) {
                player.physics.body.applyForceToCenter(new Vector2(0, -player.stats.maxSprint * player.controller.weapons.getSelected().modifiers().sprintSpeed()), true);
            }
            if (moving[2]) {
                player.physics.body.applyForceToCenter(new Vector2(-player.stats.maxSprint * player.controller.weapons.getSelected().modifiers().sprintSpeed(), 0), true);
            }
            if (moving[3]) {
                player.physics.body.applyForceToCenter(new Vector2(player.stats.maxSprint * player.controller.weapons.getSelected().modifiers().sprintSpeed(), 0), true);
            }
        }

        if (!sprint) {
            player.physics.body.setLinearVelocity(player.physics.body.getLinearVelocity().clamp(0, player.stats.maxMoveSpeed));
        } else {
            player.physics.body.setLinearVelocity(player.physics.body.getLinearVelocity().clamp(0, player.stats.maxSprintSpeed));
        }

        this.shootCooldown -= delta;

        if (this.shootCooldown > 0) {
            player.controller.triggered = false;
        }

    }


    @Override
    public boolean[] getMove() {
        return null;
    }


    @Override
    public boolean getMove(int i) {
        return false;
    }


    @Override
    public void setMove(int i, boolean value) {


    }


    @Override
    public float getShootCooldown() {
        return this.shootCooldown;
    }

}
