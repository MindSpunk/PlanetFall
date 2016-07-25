package com.spark.planetfall.game.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Atlas {

    private static TextureAtlas atlas;

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("textures.atlas"));
    }

    public static TextureAtlas get() {
        return atlas;
    }

}
