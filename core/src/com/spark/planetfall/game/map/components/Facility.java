package com.spark.planetfall.game.map.components;

import com.spark.planetfall.game.map.Map;

public class Facility {

    public BaseComponent[] components;
    public byte team;
    public int maxCapturePoints;
    public int capturePoints;
    public Map map;

    public Facility(BaseComponent[] components, byte team, int capturePoints) {
        this.components = components;
        this.team = team;
        this.maxCapturePoints = capturePoints;
        this.capturePoints = capturePoints;
        for (BaseComponent component: components) {
            component.setFacility(this);
        }

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

        //UPDATE COMPONENTS -- SEE COMPONENT TYPE UPDATE METHOD FOR MORE INFORMATION
        for (BaseComponent component: components) {
            component.update(delta);
        }

        //TICK BASE CAPTURE TIMER BASED ON CAPTURE POINT OWNERSHIP
        for (BaseComponent component: components) {
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