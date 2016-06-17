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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.*;
import com.spark.planetfall.game.actors.components.player.*;
import com.spark.planetfall.game.actors.components.player.Class.LightAssault;
import com.spark.planetfall.game.actors.components.player.Class.PlayerClass;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.weapons.WeaponController;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.game.screens.SparkGame;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.packets.HidePacket;
import com.spark.planetfall.server.packets.ShowPacket;
import com.spark.planetfall.utils.SparkMath;

public class Player extends Actor implements Controlled {

    public final Transform position;
    public final Physics physics;
    public final PlayerInput input;
    public final Stage stage;
    public final Render render;
    public final Network network;
    public final PlayerStats stats;
    public final Ability ability;
    public final WeaponController controller;
    public final CrosshairRenderer crosshair;
    public final Health health;
    public final PlayerMovement movement;
    public final UIHandler ui;
    public final CameraHandler camera;
    public final Sounds sounds;
    public PlayerClass playerClass;

    public final InputMultiplexer processor;
    public final DataManager spawns;
    public final RayHandler lightHandler;
    public final ConeLight coneOfView;
    public final Stage elevatedStage;
    public boolean captured;
    public final SparkGame game;

    public Player(SparkGame game) {

        this.game = game;

        this.ui = game.ui;

        this.elevatedStage = game.elevatedStage;
        this.stage = game.stage;

        this.movement = new PlayerMovement(this);

        this.spawns = game.manager;

        this.sounds = new Sounds();

        this.health = new Health(this, ui);

        this.camera = new CameraHandler(this, (OrthographicCamera) stage.getCamera());

        this.stats = new PlayerStats();

        //SET EQUIPMENT AND CLASS
        this.playerClass = new LightAssault(this);
        this.ability = this.playerClass.ability;
        this.controller = new WeaponController(this.playerClass.equipment, this.ui, this);

        //SIZING AND CENTERING SPRITE
        this.render = new Render(Atlas.get().createSprite("gfx/player_base"));
        render.sprite.setSize(Constant.PLAYER_SIZE, Constant.PLAYER_SIZE);
        render.sprite.setOriginCenter();

        //CREATING TRANSFORM AND PHYSICS BODY
        position = new Transform(this.spawns.spawnPoints.get(SparkMath.randInd(spawns.spawnPoints.size)), 0);
        PlayerBodyDef body = new PlayerBodyDef((render.sprite.getWidth() / 2));
        physics = new Physics(game.world, body, position, this);

        //SETTING LIGHT HANDLER
        this.lightHandler = game.lightHandler;
        this.lightHandler.resizeFBO((Gdx.graphics.getWidth() / 2), Gdx.graphics.getHeight() / 2);

        //SETTING LIGHT
        coneOfView = new ConeLight(lightHandler, 1000, new Color(0, 0, 0, 1), 1000, position.position.x, position.position.y, position.angle, Constant.CAMERA_WIDE_VIEW);

        //SETTING INPUT
        this.input = new PlayerInput(this);
        game.input.addProcessor(this.input);
        this.processor = game.input;

        //SETTING NETWORK
        network = new Network(game.handler, position);

        //SETTING CROSSHAIR
        this.crosshair = new CrosshairRenderer(this);

        //MISC
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
        HidePacket packet = new HidePacket();
        packet.id = -1;
        this.network.handler.client.sendTCP(packet);
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
        ShowPacket packet = new ShowPacket();
        packet.id = -1;
        this.network.handler.client.sendTCP(packet);
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
