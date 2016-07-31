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
import com.spark.planetfall.game.actors.components.vehicle.ability.MagBurner;
import com.spark.planetfall.game.actors.weapons.WeaponController;
import com.spark.planetfall.game.actors.weapons.Weapons;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.server.packets.VehicleKillPacket;
import com.spark.planetfall.utils.Log;
import com.spark.planetfall.utils.SparkMath;

public class VehicleActor extends Actor implements Vehicle {

    //ID
    public int id;

    //POSITION
    public final Transform transform;
    public Physics physics;

    //CONTROL
    public VehicleMovement movement;
    public VehicleInput input;
    public final InputMultiplexer multiplexer;

    //NETWORK
    public final VehicleNetwork network;

    //RENDER
    public Render render;
    public ConeLight view;
    public CrosshairRenderer crosshair;
    public final RayHandler rayhandler;
    public UIHandler ui;
    public final CameraHandler camera;
    public VehicleEffects effects;

    //AUDIO
    public Sounds sounds;

    //EQUIPMENT
    public Ability ability;
    public WeaponController weapon;

    //STATS
    public VehicleStats stats;
    public Health health;

    //MISC
    public final Stage stage;
    public boolean active;
    public Player player;
    public float turretAngle;
    public boolean alive;

    public VehicleActor(Transform transform, World world, Stage stage, InputMultiplexer input, ClientHandler clienthandler, RayHandler rayhandler) {

        //CRITICAL SETUP
        this.rayhandler = rayhandler;
        this.stage = stage;
        this.transform = transform;
        this.network = new VehicleNetwork(clienthandler, transform);
        this.input = new VehicleInput(this);
        this.camera = new CameraHandler(this, (OrthographicCamera) stage.getCamera());
        this.multiplexer = input;
        this.crosshair = null;
        this.ui = null;
        this.active = false;
        this.turretAngle = 0;
        this.alive = true;

        //CUSTOMISABLE
        this.physics = null;
        this.render = new Render(Atlas.get().createSprite("gfx/box"));
        this.render.sprite.setSize(3, 6);
        this.render.sprite.setOriginCenter();
        this.stats = new LightningStats();
        this.movement = new VehicleMovement(this);
        this.ability = new MagBurner(this, 0.1f, 0.02f);
        this.health = new Health(this, this.ui, 2000, 0);
        this.sounds = new Sounds();
        this.weapon = new WeaponController(Weapons.PYTHON_AP.copy(), null, this);
        this.effects = new VehicleEffects(this);

    }

    @Override
    public void act(float delta) {

        //UPDATE COMPONENTS
        this.physics.update(delta);
        this.health.update(delta);
        this.weapon.update(delta);
        this.render.sprite.setPosition(transform.position.x - this.render.sprite.getWidth() / 2f, transform.position.y - this.render.sprite.getHeight() / 2f);
        this.weapon.weapons.getSelected().effects().shootEffect().update(delta);
        if (player != null) this.player.getTransform().position = this.transform.position.cpy();
        this.render.sprite.setRotation(transform.angle);


        //IF BOARDED
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

        //FINAL UPDATES
        this.transform.angle += 90;
        this.ability.update(delta);
        this.transform.angle -= 90;

        this.network.update(delta);
        this.effects.update(delta);

    }

    @Override
    public void draw(Batch batch, float alpha) {

        //UPDATE CAPTURED CROSSHAIR RENDERER
        if (this.crosshair != null) {
            this.crosshair.draw(this.turretAngle - 90);
        }


        //RENDER EFFECTS AND SPRITE
        batch.end();
        batch.begin();
        this.weapon.weapons.getSelected().effects().shootEffect().draw(batch);
        this.render.sprite.draw(batch);
        this.effects.draw(batch, alpha);
        batch.end();
        batch.begin();

    }


    @Override
    public void board(Player player, ConeLight light) {

        //CAPTURE PLAYER AND OTHER INSTANCES
        this.player = player;
        this.view = light;
        this.ui = player.ui;
        this.crosshair = this.player.crosshair;

        //CONFIGURE NEW UI ELEMENTS
        this.getUI().abilityBar.setRange(0, this.ability.maxFuel());
        this.getUI().abilityBar.setValue(this.ability.fuel());

        //CONFIGURE CROSSHAIR AND WEAPON UI
        this.crosshair.setControlled(this);
        this.weapon.setUI(this.ui);

        //MOVE TO ELEVATED STAGE
        this.remove();
        this.player.addElevated(this);

        //SET AS ACTIVE INPUT PROCESSOR
        this.multiplexer.addProcessor(this.input);

        //MAKE ACTIVE
        this.active = true;

    }

    @Override
    public Player exit() {

        //SET PLAYER CAPTURED OBJECTS TO NULL
        this.ui = null;
        this.weapon.ui = null;
        this.crosshair = null;

        //RELEASE PLAYER
        this.player.getTransform().position = this.transform.position.cpy();

        //REMOVE INPUT PROCESSOR
        this.multiplexer.removeProcessor(this.input);

        //RELEASE PLAYER
        this.player.release();
        this.player.getPhysics().body.setTransform(this.transform.position.cpy(), 0f);


        //RESET MOVEMENT VALUES
        for (int i = 0; i < this.movement.moving.length; i++) {
            this.movement.moving[i] = false;
        }

        //RETURN TO MAIN STAGE
        this.remove();
        this.stage.addActor(this);

        //MAKE INACTIVE
        this.active = false;

        this.player = null;

        return player;

    }

    @Override
    public void hit(float damage) {
        this.health.hit(damage);
    }

    @Override
    public void kill() {

        this.stage.addActor(new ParticleActor("particle/VEHICLE_EXPLOSION.p", transform.position.cpy()));
        Log.logInfo("KILLED");
        this.physics.body.destroyFixture(this.physics.fixture);
        this.remove();
        if (this.player != null) {
            this.player.processor.removeProcessor(this.input);
            this.player.release();
            this.player.kill();
        }
        VehicleKillPacket packet = new VehicleKillPacket();
        this.network.handler.client.sendTCP(packet);

        this.alive = false;

    }


    @Override
    public Movement getMovement() {
        return this.movement;
    }

    @Override
    public Transform getTransform() {
        return this.transform;
    }

    @Override
    public Physics getPhysics() {
        return this.physics;
    }

    @Override
    public Network getNetwork() {
        return this.network;
    }

    @Override
    public Render getRender() {
        return this.render;
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
    public UIHandler getUI() {
        return this.ui;
    }

    @Override
    public VehicleStats getStats() {
        return this.stats;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public boolean isCaptured() {
        return false;
    }

    @Override
    public void newActor(Actor actor) {
        //ABSTRACTION METHOD
        this.stage.addActor(actor);
    }

    public void newElevated(Actor actor) {
        if (player != null) {
            this.player.addElevated(actor);
        }
    }

}
