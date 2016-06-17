package com.spark.planetfall.game.actors;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.*;
import com.spark.planetfall.game.actors.components.player.*;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.components.vehicle.*;
import com.spark.planetfall.game.actors.components.vehicle.ability.Afterburner;
import com.spark.planetfall.game.actors.weapons.WeaponController;
import com.spark.planetfall.game.actors.weapons.Weapons;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.server.packets.VehicleAddPacket;
import com.spark.planetfall.utils.Log;
import com.spark.planetfall.utils.SparkMath;

public class Harasser extends Actor implements Vehicle {

    public Transform transform;
    public VehicleMovement movement;
    public VehicleInput input;
    public VehicleNetwork network;
    public Physics physics;
    public Render render;
    public Ability ability;
    public UIHandler ui;
    public VehicleStats stats;
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
    public float turretAngle;


    public Harasser(World world, Stage stage, InputMultiplexer input, ClientHandler clienthandler, RayHandler rayhandler) {

        this.rayhandler = rayhandler;
        this.stage = stage;
        this.stats = new HarasserStats();
        this.transform = new Transform(40, 40, 0);
        this.movement = new HarasserMovement(this);
        this.network = new VehicleNetwork(clienthandler, transform);
        this.physics = new Physics(world, new HarasserBodyDef(), transform, this);
        this.render = new Render(Atlas.get().createSprite("gfx/box"));
        this.render.sprite.setSize(1.25f * 2f, 4f);
        this.render.sprite.setOriginCenter();
        this.ability = new Afterburner(this, 1f, 0.1f);
        this.input = new VehicleInput(this);
        this.camera = new CameraHandler(this, (OrthographicCamera) stage.getCamera());
        this.multiplexer = input;
        this.health = new Health(this, this.ui, 2000, 0);
        this.sounds = new Sounds();
        this.crosshair = null;
        this.turretAngle = 0;


        this.weapon = new WeaponController(Weapons.BASILISK.copy(), null, this);
        this.ui = null;

        this.active = false;

        VehicleAddPacket packet = new VehicleAddPacket();
        packet.angle = transform.angle;
        packet.position = transform.position;
        packet.name = "Harasser";

        this.network.handler.client.sendTCP(packet);

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
            this.player.camera.update(delta);

            Vector2 angle = new Vector2(0, 1);
            angle.setAngle(this.turretAngle);
            float direction = SparkMath.pointDirection(transform.position.x, stage.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, transform.position.y, stage.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y);
            Vector2 temp = new Vector2(0, 1);
            temp.setAngle(direction);
            angle.lerp(temp, 1f);
            this.turretAngle = angle.angle();

            this.view.setDirection(this.turretAngle);
            this.view.setPosition(this.transform.position);
            if (ui != null) {
                this.ui.minimap.setPosition(this.transform.position.cpy());
            }

            if (this.weapon.fire()) {
                this.weapon.weapons.getSelected().action().fire(this.crosshair.renderer, this.sounds.hitmarker, this.turretAngle);
            }

        }
        this.transform.angle += 90;
        this.ability.update(delta);
        this.transform.angle -= 90;

        this.network.update(delta);

    }

    @Override
    public void draw(Batch batch, float alpha) {

        if (this.crosshair != null) {
            this.crosshair.draw(this.turretAngle - 90);
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
    public void hit(float damage) {

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
        this.remove();
        this.player.addElevated(this);

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
        this.player.getPhysics().body.setTransform(this.transform.position.cpy(), 0f);
        this.weapon.ui = null;
        for (int i = 0; i < this.movement.moving.length; i++) {
            this.movement.moving[i] = false;
        }
        this.remove();
        this.stage.addActor(this);

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
        return this.network;
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
