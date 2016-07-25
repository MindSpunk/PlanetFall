package com.spark.planetfall.game.actors.components.player;

import com.spark.planetfall.game.constants.Constant;

public class PlayerStats {

    public float maxMoveSpeed;
    public float maxSprintSpeed;
    public final float maxMove;
    public final float maxSprint;

    public PlayerStats(float maxMoveSpeed, float maxSprintSpeed, float maxMoveAcceleration, float maxSprintAcceleration) {

        this.maxMoveSpeed = maxMoveSpeed;
        this.maxSprintSpeed = maxSprintSpeed;
        this.maxMove = maxMoveAcceleration;
        this.maxSprint = maxSprintAcceleration;

    }

    public PlayerStats() {

        this.maxMoveSpeed = Constant.PLAYER_MAX_VELOCITY;
        this.maxSprintSpeed = Constant.PLAYER_MAX_SPRINT_VELOCITY;
        this.maxMove = Constant.PLAYER_MOVE_SPEED;
        this.maxSprint = Constant.PLAYER_SPRINT_SPEED;

    }

}
