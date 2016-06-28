package com.spark.planetfall.game.map.components;


import com.spark.planetfall.game.actors.components.Transform;

public interface BaseComponent {

    Transform getTransform();
    byte getTeam();
    void setTeam(byte team);
    void setActive(boolean active);
    Facility getFacility();
    void setFacility(Facility facility);
    void update(float delta);

}
