package com.spark.planetfall.game.actors;

import box2dLight.ConeLight;
import com.spark.planetfall.game.actors.components.*;
import com.spark.planetfall.game.actors.components.player.Ability;
import com.spark.planetfall.game.actors.components.player.Health;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.components.vehicle.VehicleStats;
import com.spark.planetfall.game.actors.weapons.WeaponController;

public interface Vehicle extends Controlled {

    public void board(Player player, ConeLight light);

    public Player exit();

    public Movement getMovement();

    public Transform getTransform();

    public Physics getPhysics();

    public Network getNetwork();

    public Render getRender();

    public Health getHealth();

    public Ability getAbility();

    public WeaponController getWeaponController();

    public UIHandler getUI();

    public VehicleStats getStats();

    public int getID();

}
