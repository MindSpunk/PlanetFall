package com.spark.planetfall.game.map.components;


import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.server.ServerHandler;

public class BaseComponent {

    public Transform transform;
    public byte team;
    public boolean active;
    public Facility parent;
    public boolean remote;
    public boolean changed;

    public Transform getTransform() {
        return this.transform;
    }
    public byte getTeam() {
        return this.team;
    }
    public void setTeam(byte team) {
        this.team = team;
    }
    public void setActive(boolean active) {
        this.active = true;
    }
    public Facility getFacility() {
        return this.parent;
    }
    public void setFacility(Facility facility) {
        this.parent = facility;
    }
    public void update(float delta, ServerHandler handler) {
        //OVERRIDE ME <<<<<<<<
    }
    public void update(float delta) {
        //OVERRIDE ME <<<<<<<<
    }
    public void setRemote(boolean remote) {
        this.remote = remote;
    }
    public boolean changed() {
        return this.changed;
    }
    public void printInfo() {
        //OVERRIDE ME <<<<<<<<
    }

}
