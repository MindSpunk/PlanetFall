package com.spark.planetfall.game.map.components;

import com.badlogic.gdx.utils.Array;
import com.spark.planetfall.game.actors.components.Transform;

public class Generator implements BaseComponent {

    public Transform transform;
    public byte team;
    public Class type;
    public Array<BaseComponent> linked;
    public boolean active;
    public boolean overloading;
    public float overloadTime;
    public Facility facility;

    private float timer;

    public Generator(Transform transform, float time) {
        this.type = this.getClass();
        this.transform = transform;
        this.team = -1;
        this.linked = new Array<BaseComponent>();
        this.timer = time;
        this.overloading = false;
        this.overloadTime = time;
        this.active = true;
    }

    @Override
    public void update(float delta) {

        //ACTIVATE/DEACTIVATE ALL LINKED COMPONENTS IF GENERATOR IS ACTIVE/NOT ACTIVE
        for (BaseComponent linked: this.linked) {
            linked.setActive(this.active);
        }

    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public byte getTeam() {
        return team;
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

    public void setLinked(Array<BaseComponent> components) {
        this.linked = components;
    }
}
