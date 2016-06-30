package com.spark.planetfall.game.map.components;

import com.badlogic.gdx.utils.Array;
import com.spark.planetfall.game.map.Map;
import com.spark.planetfall.server.ServerHandler;

public class Facility {

    public Array<BaseComponent> components;
    public String name;
    public byte team;
    public float captureTime;
    public boolean active;
    public boolean remote;
    public Map map;

    private float timer;

    public Facility(String name, byte team, float captureTime, boolean remote) {
        this.name = name;
        this.components = new Array<BaseComponent>();
        this.team = team;
        this.timer = this.captureTime;
        this.captureTime = captureTime;
        for (BaseComponent component: components) {
            component.setFacility(this);
        }
        this.remote = remote;

    }

    public void setTeam(byte team) {
        for (BaseComponent component: components) {
            component.setTeam(team);
        }
        this.team = team;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void update(float delta, ServerHandler handler) {

        //UPDATE COMPONENTS -- SEE COMPONENT TYPE UPDATE METHOD FOR MORE INFORMATION
        for (BaseComponent component : components) {
            component.update(delta, handler);
        }

        //TICK BASE CAPTURE TIMER BASED ON CAPTURE POINT OWNERSHIP
        for (BaseComponent component : components) {
            if (component.getClass() == SpawnPoint.class) {
                if (component.getTeam() != this.team && component.getTeam() != -1) {
                    this.timer -= delta;
                    if (timer > this.captureTime) timer = captureTime;
                } else if (component.getTeam() == this.team) {
                    if (timer > this.captureTime) timer = captureTime;;
                }
            }
        }

    }

    public void addComponent(BaseComponent component) {
        this.components.add(component);
        component.setFacility(this);
        component.setTeam(this.team);
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }
}
