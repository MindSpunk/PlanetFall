package com.spark.planetfall.game.map.components;

import com.spark.planetfall.game.actors.components.Transform;

public class SpawnPoint implements BaseComponent {

    public Transform transform;
    public byte team;
    public Class type;
    public boolean active;
    public Facility facility;

    public SpawnPoint(Transform transform, byte team) {
        this.transform = transform;
        this.team = team;
        this.active = true;
        this.type = this.getClass();
    }


    @Override
    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public byte getTeam() {
        return this.team;
    }

    @Override
    public void setTeam(byte team) {
        this.team = team;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public Facility getFacility() {
        return this.facility;
    }

    @Override
    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Override
    public void update(float delta) {

    }
}
