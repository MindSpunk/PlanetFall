package com.spark.planetfall.game.actors;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.*;
import com.spark.planetfall.game.actors.components.player.*;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.components.vehicle.*;
import com.spark.planetfall.game.actors.components.vehicle.ability.MagBurner;
import com.spark.planetfall.game.actors.weapons.WeaponController;
import com.spark.planetfall.game.actors.weapons.Weapons;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.utils.Log;

public class Magrider extends Actor implements Vehicle {

    public Transform transform;
    public VehicleMovement movement;
    public VehicleInput input;
    public Network network;
    public Physics physics;
    public Render render;
    public Ability ability;
    public UIHandler ui;
    public MagriderStats stats;
    public WeaponController weapon;
    public Health health;
    public Stage stage;
    public CameraHandler camera;
    public ConeLight view;
    public CrosshairRenderer crosshair;
    public Sounds sounds;
    public int id;

    public boolean active;

    public Player player;
    public InputMultiplexer multiplexer;
    public RayHandler rayhandler;


    public Magrider(World world, Stage stage, InputMultiplexer input, ClientHandler clienthandler, RayHandler rayhandler) {

        this.rayhandler = rayhandler;
        this.stage = stage;
        this.transform = new Transform(40, 40, 0);
        this.movement = new MagriderMovement(this);
        this.network = new Network(clienthandler, transform);
        this.physics = new Physics(world, new MagriderBodyDef(), transform, this);
        this.render = new Render(Atlas.get().createSprite("gfx/box"));
        this.render.sprite.setSize(3, 6);
        this.render.sprite.setOriginCenter();
        this.ability = new MagBurner(this, 0.1f, 0.02f);
        this.input = new VehicleInput(this);
        this.camera = new CameraHandler(this, (OrthographicCamera) stage.getCamera());
        this.multiplexer = input;
        this.stats = new MagriderStats();
        this.health = new Health(this, this.ui, 2000, 0);
        this.sounds = new Sounds();
        this.crosshair = null;


        this.weapon = new WeaponController(Weapons.PYTHON_AP.copy(), null, this);
        this.ui = null;

        this.active = false;

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

    }

    @Override
    public void draw(Batch batch, float alpha) {

        if (this.crosshair != null) {
            this.crosshair.draw(this.transform.angle);
        }

        batch.end();
        batch.begin();
        this.weapon.weapons.getSelected().effects().shootEffect().draw(batch);
        this.render.sprite.draw(batch);
        batch.end();
        batch.begin();

    }

    @Override
    public void kill() {

    }

    @Override
    public void board(Player player, ConeLight light) {

        Log.logInfo("Boarding");
        this.player = player;
        this.view = light;
        this.active = true;
        this.ui = player.ui;
        this.getUI().abilityBar.setRange(0, this.ability.maxFuel());
        this.getUI().abilityBar.setValue(this.ability.fuel());
        this.crosshair = this.player.crosshair;
        this.crosshair.setControlled(this);
        this.weapon.setUI(this.ui);

        this.multiplexer.addProcessor(this.input);

    }

    @Override
    public Player exit() {

        this.ui = null;
        this.active = false;
        this.player.getTransform().position = this.transform.position.cpy();
        this.newActor(this.player);
        this.multiplexer.removeProcessor(this.input);
        this.crosshair = null;
        this.player.release();
        this.weapon.ui = null;

        return player;
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

        this.stage.addActor(actor);

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
