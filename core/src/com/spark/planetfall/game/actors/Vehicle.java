package com.spark.planetfall.game.actors;

import box2dLight.ConeLight;
import com.spark.planetfall.game.actors.components.*;
import com.spark.planetfall.game.actors.components.player.Ability;
import com.spark.planetfall.game.actors.components.player.Health;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.components.vehicle.VehicleStats;
import com.spark.planetfall.game.actors.weapons.WeaponController;

public interface Vehicle extends Controlled {

    void board(Player player, ConeLight light);

    Player exit();

    Movement getMovement();

    Transform getTransform();

    Physics getPhysics();

    Network getNetwork();

    Render getRender();

    Health getHealth();

    Ability getAbility();

    WeaponController getWeaponController();

    UIHandler getUI();

    VehicleStats getStats();

    int getID();

    void hit(float damage);

    void kill();

}
