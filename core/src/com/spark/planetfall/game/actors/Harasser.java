package com.spark.planetfall.game.actors;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.*;
import com.spark.planetfall.game.actors.components.player.Ability;
import com.spark.planetfall.game.actors.components.player.Health;
import com.spark.planetfall.game.actors.components.player.Sounds;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.components.vehicle.HarasserBodyDef;
import com.spark.planetfall.game.actors.components.vehicle.HarasserMovement;
import com.spark.planetfall.game.actors.components.vehicle.HarasserStats;
import com.spark.planetfall.game.actors.components.vehicle.VehicleStats;
import com.spark.planetfall.game.actors.components.vehicle.ability.Afterburner;
import com.spark.planetfall.game.actors.weapons.WeaponController;
import com.spark.planetfall.game.actors.weapons.Weapons;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;

public class Harasser extends VehicleActor {

    public Harasser(Transform transform, World world, Stage stage, InputMultiplexer input, ClientHandler clienthandler, RayHandler rayhandler) {

        super(transform, world, stage, input, clienthandler, rayhandler);

        this.physics = new Physics(world, new HarasserBodyDef(), transform, this);
        this.render = new Render(Atlas.get().createSprite("gfx/box"));
        this.render.sprite.setSize(1.25f * 2f, 4f);
        this.render.sprite.setOriginCenter();
        this.ability = new Afterburner(this, 1f, 0.1f);
        this.stats = new HarasserStats();
        this.movement = new HarasserMovement(this);
        this.health = new Health(this, this.ui, 2000, 0);
        this.sounds = new Sounds();
        this.weapon = new WeaponController(Weapons.BASILISK.copy(), null, this);

    }

    @Override
    public void act(float delta) {

        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float alpha) {

        super.draw(batch, alpha);

    }

    @Override
    public void kill() {

        super.kill();

    }

    @Override
    public void hit(float damage) {

        super.hit(damage);

    }

    @Override
    public void board(Player player, ConeLight light) {

        super.board(player, light);

    }

    @Override
    public Player exit() {

        return super.exit();

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
        return this.network;
    }

    @Override
    public void newActor(Actor actor) {

        super.newActor(actor);

    }

    @Override
    public Health getHealth() {
        return null;
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
