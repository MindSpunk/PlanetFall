package com.spark.planetfall.server.packets;

import com.badlogic.gdx.math.Vector2;

public class UpdatePacket {

    public Vector2 position;
    public float angle;
    public int id;
    public int score = 0;
    public int kills = 0;
    public int deaths = 0;

}
