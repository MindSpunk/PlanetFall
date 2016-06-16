package com.spark.planetfall.game.actors;

import box2dLight.ConeLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.*;
import com.spark.planetfall.game.actors.components.player.*;
import com.spark.planetfall.game.actors.components.player.abilities.NaniteMeshGenerator;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.components.weapons.Weapon;
import com.spark.planetfall.game.actors.weapons.WeaponController;
import com.spark.planetfall.game.actors.weapons.Weapons;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.server.packets.HidePacket;
import com.spark.planetfall.server.packets.ShowPacket;
import com.spark.planetfall.utils.SparkMath;

public class Player extends Actor implements Controlled {

    public Transform position;
    public Physics physics;
    public PlayerInput input;
    public Stage stage;
    public Render render;
    public Network network;
    public PlayerStats stats;
    public Ability ability;
    public WeaponController controller;
    public CrosshairRenderer crosshair;
    public Health health;
    public PlayerMovement movement;
    public UIHandler ui;
    public CameraHandler camera;
    public Sounds sounds;

    public InputMultiplexer processor;
    public DataManager spawns;
    public RayHandler lightHandler;
    public ConeLight coneOfView;
    public Stage elevatedStage;
    public boolean captured;

    public Player(World world, Stage stage, Stage elevatedStage, InputMultiplexer input, ClientHandler handler, RayHandler lightHandler, DataManager manager, UIHandler ui) {

        this.ui = ui;

        this.elevatedStage = elevatedStage;

        Weapon[] weapons = {Weapons.AK_47.copy(), Weapons.MED_KITS.copy()};

        this.controller = new WeaponController(weapons, this.ui, this);

        this.movement = new PlayerMovement(this);

        this.spawns = manager;

        this.sounds = new Sounds();

        this.health = new Health(this, ui);

        this.camera = new CameraHandler(this, (OrthographicCamera) stage.getCamera());

        this.render = new Render(Atlas.get().createSprite("gfx/player_base"));
        this.stats = new PlayerStats();

        this.ability = new NaniteMeshGenerator(this, 750, 50, 100, 50);

        render.sprite.setSize(Constant.PLAYER_SIZE, Constant.PLAYER_SIZE);
        render.sprite.setOriginCenter();

        position = new Transform(this.spawns.spawnPoints.get(SparkMath.randInd(spawns.spawnPoints.size)), 0);
        PlayerBodyDef body = new PlayerBodyDef((render.sprite.getWidth() / 2));
        physics = new Physics(world, body, position, this);

        this.stage = stage;
        this.lightHandler = lightHandler;
        this.lightHandler.resizeFBO((Gdx.graphics.getWidth() / 2), Gdx.graphics.getHeight() / 2);

        coneOfView = new ConeLight(lightHandler, 1000, new Color(0, 0, 0, 1), 1000, position.position.x, position.position.y, position.angle, Constant.CAMERA_WIDE_VIEW);

        this.input = new PlayerInput(this);
        input.addProcessor(this.input);
        this.processor = input;

        network = new Network(handler, position);

        this.crosshair = new CrosshairRenderer(this);
        this.captured = false;

    }

    @Override
    public void act(float delta) {

        movement.update(delta);
        physics.update(delta);

        if (controller.aiming) {
            physics.body.setLinearVelocity(physics.body.getLinearVelocity().clamp(0, stats.maxMoveSpeed * controller.weapons.getSelected().modifiers().adsSpeed()));
            camera.zoom = 2;
        } else {
            camera.zoom = 1;
        }

        controller.update(delta);

        //UPDATE HEALTH SYSTEM
        health.update(delta);

        //SET SPRITE POSITION
        render.sprite.setPosition(position.position.x - render.sprite.getWidth() / 2f, position.position.y - render.sprite.getHeight() / 2f);

        //UPDATE CAMERA
        camera.update(delta);

        //SET ANGLE
        Vector2 angle = new Vector2(0, 1);
        angle.setAngle(position.angle);
        float direction = SparkMath.pointDirection(position.position.x, stage.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, position.position.y, stage.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y);
        Vector2 temp = new Vector2(0, 1);
        temp.setAngle(direction);
        angle.lerp(temp, 1f);
        this.position.angle = angle.angle();

        //CHECK IF WEAPON FIRED
        if (controller.fire()) {
            this.controller.weapons.getSelected().action().fire(this.crosshair.renderer, this.sounds.hitmarker, this.position.angle);
        }

        //SET LIGHT POSITION AND ANGLE
        coneOfView.setDirection(position.angle);
        coneOfView.setPosition(position.position);

        //UPDATE ABILITY
        ability.update(delta);

        //UPDATE EFFECT
        controller.weapons.getSelected().effects().shootEffect().update(delta);

        //SET SPRITE ANGLE
        position.angle -= 90;
        render.sprite.setRotation(position.angle);

        //SEND DATA OVER NETWORK
        network.update(delta);

        //MINIMAP
        this.ui.minimap.setPosition(this.position.position.cpy());

    }

    @Override
    public void draw(Batch batch, float alpha) {

        crosshair.draw(this.position.angle);

        batch.end();
        batch.begin();
        controller.weapons.getSelected().effects().shootEffect().draw(batch);
        render.sprite.draw(batch);
        batch.end();
        batch.begin();

    }

    public void hit(float damage) {

        this.health.hit(this.ability.hit(damage));

    }

    public void kill() {

        this.physics.body.setTransform(this.spawns.spawnPoints.get(SparkMath.randInd(spawns.spawnPoints.size)), 0f);
        this.health.heal();

    }

    public Player capture() {
        this.processor.removeProcessor(this.input);
        this.physics.body.destroyFixture(this.physics.fixture);
        this.remove();
        this.captured = true;
        this.network.handler.client.sendTCP(new HidePacket());
        return this;
    }

    public void release() {
        this.processor.addProcessor(this.input);
        this.physics.body.createFixture(this.physics.build.fixtureDef());
        this.ui.abilityBar.setRange(0, this.ability.maxFuel());
        this.ui.abilityBar.setValue(this.ability.fuel());
        this.crosshair.setControlled(this);
        this.elevatedStage.addActor(this);
        for (int i = 0; i < this.movement.moving.length; i++) {
            this.movement.moving[i] = false;
        }
        this.movement.sprint = false;
        this.captured = false;
        this.network.handler.client.sendTCP(new ShowPacket());
    }

    @Override
    public Transform getTransform() {
        // TODO Auto-generated method stub
        return this.position;
    }

    @Override
    public Physics getPhysics() {
        // TODO Auto-generated method stub
        return this.physics;
    }

    @Override
    public Network getNetwork() {
        // TODO Auto-generated method stub
        return this.network;
    }

    @Override
    public Health getHealth() {
        // TODO Auto-generated method stub
        return this.health;
    }

    @Override
    public void newActor(Actor actor) {
        this.stage.addActor(actor);

    }

    @Override
    public WeaponController getWeaponController() {
        // TODO Auto-generated method stub
        return this.controller;
    }

    @Override
    public Movement getMovement() {
        // TODO Auto-generated method stub
        return this.movement;
    }

    @Override
    public Stage getStage() {
        return this.stage;
    }

    public void addElevated(Actor actor) {
        this.elevatedStage.addActor(actor);
    }

    @Override
    public boolean isCaptured() {
        return this.captured;
    }

}
