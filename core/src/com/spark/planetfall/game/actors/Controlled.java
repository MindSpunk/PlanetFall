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

    public void kill();

    public Transform getTransform();

    public Physics getPhysics();

    public Network getNetwork();

    public Health getHealth();

    public WeaponController getWeaponController();

    public Movement getMovement();

    public Stage getStage();

    public boolean isCaptured();

    public void newActor(Actor actor);

}
