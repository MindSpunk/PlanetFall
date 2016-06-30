package com.spark.planetfall.game.map.components;


import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.server.RemotePlayer;
import com.spark.planetfall.server.ServerHandler;
import com.spark.planetfall.utils.Log;

public class CapturePoint implements BaseComponent {

    public Transform transform;
    public Facility facility;
    public byte team;
    public boolean active;
    public boolean changed;
    public float captureTime;

    private byte attackingTeam;
    private boolean capturing;
    private float timer;

    public CapturePoint(Transform transform, byte team, float captureTime) {

        this.transform = transform;
        this.team = team;
        this.changed = true;
        this.captureTime = captureTime;
        this.timer = captureTime;
        this.capturing = false;
        this.attackingTeam = -1;

    }

    public CapturePoint(Vector2 position, float angle, byte team, float captureTime) {

        this.transform = new Transform(position, angle);
        this.team = team;
        this.changed = true;
        this.captureTime = captureTime;
        this.timer = captureTime;
        this.capturing = false;
        this.attackingTeam = -1;

    }

    @Override
    public void update(float delta) {

        this.changed = false;
        this.capturing = false;

    }

    @Override
    public void update(float delta, ServerHandler handler) {

        update(delta);

        for (RemotePlayer player: handler.players) {
            Vector2 dist = player.position.sub(transform.position).cpy();
            if (dist.len() <= 10) {
                if (player.team != this.team) {
                    this.capturing = true;
                    this.changed = true;
                    Log.logInfo("erm bein attarcked");
                }
            }
        }

        if (capturing) {
            this.timer -= delta;
        } else {
            this.timer += delta;
            if (this.timer > this.captureTime) {
                this.timer = this.captureTime;
            }
        }

        if (timer <= 0) {
            this.team = this.attackingTeam;
        }

    }

    @Override
    public Transform getTransform() {return this.transform;}

    @Override
    public byte getTeam() {return this.team;}

    @Override
    public void setTeam(byte team) {this.team = team;}

    @Override
    public void setActive(boolean active) {this.active = active;}

    @Override
    public Facility getFacility() {return this.facility;}

    @Override
    public void setFacility(Facility facility) {this.facility = facility;}

    @Override
    public boolean changed() {
        return changed;
    }
}
