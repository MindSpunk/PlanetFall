package com.spark.planetfall.game.constants;

public class Constant {

    //SERVER
    public static final int SERVER_MAX_PLAYERS = 16;
    public static final int SERVER_TCP_PORT = 42021;
    public static final int SERVER_UDP_PORT = 42021;
    public static final float SERVER_NETWORK_LERP = 0.2f;
    public static final float SERVER_UPDATE_RATE = 1 / 60f;

    //PLAYER
    public static final float PLAYER_MAX_VELOCITY = 3f;
    public static final float PLAYER_MAX_SPRINT_VELOCITY = 20f;
    public static final float PLAYER_MOVE_SPEED = 7f;
    public static final float PLAYER_SPRINT_SPEED = 10f;
    public static final float PLAYER_SIZE = 1.5f;
    public static final float PLAYER_SPRINT_FIRE_COOLDOWN = 0.5f;
    public static final float PLAYER_HEALTH = 500;
    public static final float PLAYER_SHIELDS = 500;
    public static final float PLAYER_SHIELD_REGEN_PER_SECOND = 100;
    public static final float PLAYER_HIT_REGEN_COOLDOWN = 5f;

    //CAMERA
    public static final float CAMERA_SPEED = 0.3f; // Used for linear interpolation
    public static final float CAMERA_WIDE_VIEW = 77;
    public static final float CAMERA_AIMED_VIEW = 20f;
    public static final float CAMERA_WIDE_LIGHT = 0f;
    public static final float CAMERA_AIMED_LIGHT = 0f;
    public static final float CAMERA_FOCUS_LERP = 0.2f;

    //WEAPON
    public static final float WEAPON_BLOOM_LERP = 0.3f;
    public static final float WEAPON_BULLET_LIFE_TIME = 5;


}
