package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.spark.planetfall.game.actors.Player;

public class Particles {

    public ParticleEffect shot;
    public Player player;

    public Particles(Player player) {

        this.player = player;

        this.shot = new ParticleEffect();
        shot.load(Gdx.files.internal("particle/shoot.p"), Gdx.files.internal(""));
        shot.start();
        shot.scaleEffect(1f);

    }

    public void draw(Batch batch) {
        this.shot.draw(batch);
    }

    public void update(float delta) {
        this.shot.update(delta);
    }

}
