package com.spark.planetfall.game.actors;

import box2dLight.RayHandler;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.*;
import com.spark.planetfall.game.actors.components.player.Ability;
import com.spark.planetfall.game.actors.components.player.Health;
import com.spark.planetfall.game.actors.components.player.Sounds;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.components.vehicle.LightningStats;
import com.spark.planetfall.game.actors.components.vehicle.MagriderBodyDef;
import com.spark.planetfall.game.actors.components.vehicle.VehicleStats;
import com.spark.planetfall.game.actors.components.vehicle.ability.MagBurner;
import com.spark.planetfall.game.actors.weapons.WeaponController;
import com.spark.planetfall.game.actors.weapons.Weapons;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;

public class Lightning extends VehicleActor {

    public Lightning(Transform transform, World world, Stage stage, InputMultiplexer input, ClientHandler clienthandler, RayHandler rayhandler) {

        super(transform, world, stage, input, clienthandler, rayhandler);

        this.physics = new Physics(world, new MagriderBodyDef(), transform, this);
        this.render = new Render(Atlas.get().createSprite("gfx/box"));
        this.render.sprite.setSize(3, 6);
        this.render.sprite.setOriginCenter();
        this.stats = new LightningStats();
        this.ability = new MagBurner(this, 0.1f, 0.02f);
        this.health = new Health(this, this.ui, 2000, 0);
        this.sounds = new Sounds();
        this.weapon = new WeaponController(Weapons.PYTHON_AP.copy(), null, this);

    }

    @Override
    public Movement getMovement() {
        return movement;
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public Physics getPhysics() {
        return physics;
    }

    @Override
    public Network getNetwork() {
        return network;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public Ability getAbility() {
        return this.ability;
    }

    @Override
    public WeaponController getWeaponController() {
        return this.weapon;
    }

    @Override
    public Render getRender() {
        return this.render;
    }

    @Override
    public UIHandler getUI() {
        return this.ui;
    }

    @Override
    public VehicleStats getStats() {
        return this.stats;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public boolean isCaptured() {
        return false;
    }

}
