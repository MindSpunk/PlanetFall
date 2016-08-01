package com.spark.planetfall.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.spark.planetfall.game.PlanetFallClient;
import com.spark.planetfall.game.actors.components.weapons.Weapon;
import com.spark.planetfall.game.actors.components.weapons.WeaponTypes;
import com.spark.planetfall.game.actors.components.weapons.WeaponUtils;
import com.spark.planetfall.game.actors.weapons.Weapons;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.game.ui.GUISkin;
import com.spark.planetfall.utils.Log;

public class MainMenu implements Screen {

    private final PlanetFallClient game;

    public final Stage stage;
    public final Window window;
    public final TextButton play_button;
    public final TextButton quit_button;
    public final Image background;
    public final TextField ipInput;
    public final TextField nameInput;

    public Window play;
    public Table playTable;
    public Label playPrimary;
    public Label playSecondary;
    public SelectBox<String> playPrimaryList;
    public SelectBox<String> playSecondaryList;
    public Image primaryImage;
    public Image secondaryImage;

    public MainMenu(PlanetFallClient game) {

        nameInput = new TextField("Input Name", GUISkin.get());
        this.game = game;
        ipInput = new TextField("Enter IP", GUISkin.get());
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        window = new Window("", GUISkin.get());
        window.setPosition(100, Gdx.graphics.getHeight() - 300);
        window.align(Align.center);
        window.setMovable(false);
        play_button = new TextButton("Play", GUISkin.get());
        ipInput.setPosition(0, 0);
        quit_button = new TextButton("Quit", GUISkin.get());
        window.add(nameInput);
        window.row();
        window.add(ipInput);
        window.row();
        window.add(play_button).width(200);
        window.row();
        window.add(quit_button).width(200);
        background = new Image(Atlas.get().createSprite("gfx/background_1"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        stage.addActor(window);
        weapons();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

        play_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                Array<Weapon> weapons = new Array<Weapon>();

                for (Weapons weapon : Weapons.values()) {
                    if (playPrimaryList.getSelected() == weapon.name) {
                        weapons.add(weapon.copy());
                        Log.logInfo("Adding " + weapon.name + " to inventory");
                        break;
                    }
                }

                for (Weapons weapon : Weapons.values()) {
                    if (playSecondaryList.getSelected().equals(weapon.name)) {
                        weapons.add(weapon.copy());
                        Log.logInfo("Adding " + weapon.name + " to inventory");
                        break;
                    }
                }

                Weapon[] weaponsArray = weapons.toArray(Weapon.class);

                game.setScreen(new SparkGame(game, ipInput.getText(), nameInput.getText(), weaponsArray));
                Log.logInfo(ipInput.getText());
            }
        });
        quit_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.getViewport().apply();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void weapons() {
        play = new Window("Weapon Selection", GUISkin.get());
        play.setMovable(false);
        play.setWidth(800);
        play.setHeight(500);
        play.setPosition((Gdx.graphics.getWidth()/2f)-play.getWidth()/2f, (Gdx.graphics.getHeight()/2f)-play.getHeight()/2f);
        playTable = new Table();
        playPrimary = new Label("PRIMARY", GUISkin.get());
        playSecondary = new Label("SECONDARY", GUISkin.get());
        playPrimaryList = new SelectBox<String>(GUISkin.get());
        primaryImage = new Image(Weapons.values()[0].effects.sprite);
        primaryImage.setScaling(Scaling.fit);
        secondaryImage = new Image(Weapons.values()[1].effects.sprite);
        secondaryImage.setScaling(Scaling.fit);
        Array<String> primaryWeapons = new Array<String>();
        for (Weapons weapon : WeaponUtils.getType(WeaponTypes.PRIMARY_WEAPON)) {
            primaryWeapons.add(weapon.name);
        }


        playPrimaryList.setItems(primaryWeapons);
        playPrimaryList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (Weapons weapon : WeaponUtils.getType(WeaponTypes.PRIMARY_WEAPON)) {
                    if (playPrimaryList.getSelected().equals(weapon.name)) {
                        Image img = new Image(weapon.effects.sprite);
                        img.setScaling(Scaling.fit);
                        primaryImage.setDrawable(img.getDrawable());
                    }
                }
            }
        });


        playSecondaryList = new SelectBox<String>(GUISkin.get());
        Array<String> secondaryWeapons = new Array<String>();
        for (Weapons weapon : WeaponUtils.getType(WeaponTypes.SECONDARY_WEAPON)) {
            secondaryWeapons.add(weapon.name);
        }


        playSecondaryList.setItems(secondaryWeapons);
        playSecondaryList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (Weapons weapon : WeaponUtils.getType(WeaponTypes.SECONDARY_WEAPON)) {
                    if (playSecondaryList.getSelected().equals(weapon.name)) {
                        Image img = new Image(weapon.effects.sprite);
                        img.setScaling(Scaling.fit);
                        secondaryImage.setDrawable(img.getDrawable());
                    }
                }
            }
        });

        playTable.add(playPrimary);
        playTable.add(playPrimaryList);
        playTable.add(primaryImage).size(450, 150);
        playTable.row();
        playTable.add(playSecondary);
        playTable.add(playSecondaryList);
        playTable.add(secondaryImage).size(250, 250);

        play.add(playTable);
        this.stage.addActor(play);
    }


}
