package com.spark.planetfall.game.map.components;


import com.spark.planetfall.game.actors.components.Transform;

public class CapturePoint implements BaseComponent {

    public Transform transform;
    public Facility facility;
    public byte team;
    public boolean active;

    public CapturePoint(Transform transform, byte team) {

        this.transform = transform;
        this.team = team;

    }

    @Override
    public void update(float delta) {

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
}
