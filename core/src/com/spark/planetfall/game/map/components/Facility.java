package com.spark.planetfall.game.map.components;

import com.spark.planetfall.game.map.Map;

public class Facility {

    public BaseComponent[] components;
    public byte team;
    public int maxCapturePoints;
    public int capturePoints;
    public boolean active;
    public boolean remote;
    public Map map;

    public Facility(BaseComponent[] components, byte team, int capturePoints, boolean remote) {
        this.components = components;
        this.team = team;
        this.maxCapturePoints = capturePoints;
        this.capturePoints = capturePoints;
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

    public void update(float delta) {

        //IF THIS IS A CLIENT DATA HOLDER AS OPPOSED TO THE SERVER'S MAP
        if (!this.remote) {

            //UPDATE COMPONENTS -- SEE COMPONENT TYPE UPDATE METHOD FOR MORE INFORMATION
            for (BaseComponent component : components) {
                component.update(delta);
            }

            //TICK BASE CAPTURE TIMER BASED ON CAPTURE POINT OWNERSHIP
            for (BaseComponent component : components) {
                if (component.getClass() == SpawnPoint.class) {
                    if (component.getTeam() != this.team && component.getTeam() != -1) {
                        this.capturePoints -= 1;
                    } else if (component.getTeam() == this.team) {
                        this.capturePoints += 1;
                    }
                }
            }
        }
    }
}
