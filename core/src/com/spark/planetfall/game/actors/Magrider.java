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
import com.spark.planetfall.game.actors.components.vehicle.MagriderBodyDef;
import com.spark.planetfall.game.actors.components.vehicle.MagriderMovement;
import com.spark.planetfall.game.actors.components.vehicle.MagriderStats;
import com.spark.planetfall.game.actors.components.vehicle.VehicleStats;
import com.spark.planetfall.game.actors.components.vehicle.ability.MagBurner;
import com.spark.planetfall.game.actors.weapons.WeaponController;
import com.spark.planetfall.game.actors.weapons.Weapons;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;

public class Magrider extends VehicleActor {

    public Magrider(Transform transform, World world, Stage stage, InputMultiplexer input, ClientHandler clienthandler, RayHandler rayhandler) {

        super(transform, world, stage, input, clienthandler, rayhandler);

        this.physics = new Physics(world, new MagriderBodyDef(), transform, this);
        this.render = new Render(Atlas.get().createSprite("gfx/box"));
        this.render.sprite.setSize(3, 6);
        this.render.sprite.setOriginCenter();
        this.ability = new MagBurner(this, 0.1f, 0.02f);
        this.stats = new MagriderStats();
        this.movement = new MagriderMovement(this);
        this.health = new Health(this, this.ui, 2000, 0);
        this.sounds = new Sounds();
        this.weapon = new WeaponController(Weapons.PYTHON_AP.copy(), null, this);

    }

    @Override
    public void act(float delta) {

        this.physics.update(delta);
        this.health.update(delta);
        this.weapon.update(delta);
        this.render.sprite.setPosition(transform.position.x - this.render.sprite.getWidth() / 2f, transform.position.y - this.render.sprite.getHeight() / 2f);
        this.weapon.weapons.getSelected().effects().shootEffect().update(delta);
        if (player != null) this.player.getTransform().position = this.transform.position.cpy();
        this.render.sprite.setRotation(transform.angle);


        if (active) {
            this.movement.update(delta);
            this.network.update(delta);
            this.player.camera.update(delta);
            this.view.setDirection(this.transform.angle + 90);
            this.view.setPosition(this.transform.position);
            if (ui != null) {
                this.ui.minimap.setPosition(this.transform.position.cpy());
            }
            if (this.weapon.fire()) {
                this.transform.angle += 90;
                this.weapon.weapons.getSelected().action().fire(this.crosshair.renderer, this.sounds.hitmarker, this.transform.angle);
                this.transform.angle -= 90;
            }
        }
        this.transform.angle += 90;
        this.ability.update(delta);
        this.transform.angle -= 90;

        this.effects.update(delta);

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
        return network;
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
