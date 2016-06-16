package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {

    public Sound hitmarker;

    public Sounds() {

        this.hitmarker = Gdx.audio.newSound(Gdx.files.internal("sound/hitmarker.wav"));

    }

}
