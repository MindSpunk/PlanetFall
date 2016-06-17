package com.spark.planetfall.game;

import com.badlogic.gdx.Game;
import com.spark.planetfall.game.screens.SparkSplash;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.game.ui.GUISkin;

public class PlanetFallClient extends Game {

    @Override
    public void create() {
        Atlas.load();
        GUISkin.load();
        this.setScreen(new SparkSplash(this));
    }

}
