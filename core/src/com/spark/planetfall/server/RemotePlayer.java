package com.spark.planetfall.server;

import com.badlogic.gdx.math.Vector2;

public class RemotePlayer {

    public boolean empty = true;
    public boolean remove = false;

    public Vector2 position = new Vector2(0, 0);
    public float angle = 0;
    public int id = -1;
    public String name = "new";
    public byte team = -1;
    public int score = 0;
    public int kills = 0;
    public int deaths = 0;
    public int lastHitID = -1;

}
