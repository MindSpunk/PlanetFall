package com.spark.planetfall.game.map.components;

import com.badlogic.gdx.physics.box2d.Transform;

public interface BaseComponent {

    Transform getTransform();
    byte getTeam();
    void setTeam(byte team);
    Class getType();
    void setActive(boolean active);
    Facility getFacility();
    void setFacility(Facility facility);
    void update(float delta);

}
