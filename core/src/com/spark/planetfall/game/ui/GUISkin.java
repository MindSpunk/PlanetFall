package com.spark.planetfall.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GUISkin {

    private static Skin skin;

    public static void load() {

        skin = new Skin(Gdx.files.internal("gui/skin.json"));

    }

    public static Skin get() {
        return skin;
    }

}
