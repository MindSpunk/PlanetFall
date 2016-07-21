package com.spark.planetfall.game.map.components;

import com.badlogic.gdx.utils.Array;
import com.spark.planetfall.game.map.Map;
import com.spark.planetfall.server.ServerHandler;
import com.spark.planetfall.utils.Log;

public class Facility {

    public BaseComponent[] components;
    public String name;
    public byte team;
    public float captureTime;
    public boolean active;
    public boolean remote;
    public Map map;
    public boolean changed;

    private float timer;

    public Facility(String name, byte team, float captureTime, boolean remote) {
        this.name = name;
        this.team = team;
        this.timer = this.captureTime;
        this.captureTime = captureTime;
        this.remote = remote;
        this.changed = false;

    }

    public Facility() {}

    public void setComponents(BaseComponent[] components) {
        this.components = components;
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

        this.changed = false;

        //UPDATE COMPONENTS -- SEE COMPONENT TYPE UPDATE METHOD FOR MORE INFORMATION
        for (BaseComponent component : components) {
            component.update(delta, handler);
            if (component.changed) {
                this.changed = true;
            }
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

        Array<BaseComponent> components;
        if (this.components == null) {
            components = new Array<BaseComponent>();
        } else {
            components = new Array<BaseComponent>(this.components);
        }

        component.setFacility(this);
        component.setTeam(this.team);
        component.setActive(this.active);
        components.add(component);
        this.components = components.toArray(BaseComponent.class);

    }

    public void setRemote(boolean remote) {
        this.remote = remote;
        for (BaseComponent component: components) {
            component.setRemote(remote);
        }
    }

    public void printAll() {

        Log.logInfo("Facility: " + this.name);
        Log.logInfo("Team: " + this.team);
        Log.logInfo("Active: " + this.active);

        Log.logInfo("");
        Log.logInfo("Components");
        Log.logInfo("______________________________");

        for (BaseComponent component : components) {
            component.printInfo();
        }

        Log.logInfo("______________________________");

    }

    public Facility getChanges() {

        Array<BaseComponent> componentsList = new Array<BaseComponent>();

        for (BaseComponent component : this.components) {
            if (component.changed) {
                componentsList.add(component);
            }
        }

        if (componentsList.size == 0) {
            return null;
        }

        Facility facility = new Facility(this.name, this.team, this.captureTime, this.remote);

        BaseComponent[] baseComponents = componentsList.toArray(BaseComponent.class);

        facility.setComponents(baseComponents);

        return facility;

    }
}
