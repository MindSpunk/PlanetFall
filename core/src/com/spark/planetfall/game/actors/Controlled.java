package com.spark.planetfall.game.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.Movement;
import com.spark.planetfall.game.actors.components.Network;
import com.spark.planetfall.game.actors.components.Physics;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.game.actors.components.player.Health;
import com.spark.planetfall.game.actors.weapons.WeaponController;

public interface Controlled {

    void kill();

    Transform getTransform();

    Physics getPhysics();

    Network getNetwork();

    Health getHealth();

    WeaponController getWeaponController();

    Movement getMovement();

    Stage getStage();

    boolean isCaptured();

    void newActor(Actor actor);

}
