package com.spark.planetfall.game.actors.components.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Effects {

    private ParticleEffect shootParticle;
    private Color bulletParticle;

    public Effects(String shootParticleName, Color bulletParticleName) {

        this.shootParticle = new ParticleEffect();
        this.shootParticle.load(Gdx.files.internal(shootParticleName), Gdx.files.internal(""));

        this.bulletParticle = bulletParticleName;

    }

    public Effects(ParticleEffect shootParticle, Color bulletParticle) {

        this.shootParticle = shootParticle;
        this.bulletParticle = bulletParticle;

    }

    public ParticleEffect shootEffect() {
        return this.shootParticle;
    }

    public Color bulletColor() {
        return this.bulletParticle;
    }

    public void setup() {
        this.shootParticle.setPosition(0, 0);
        this.shootParticle.start();
    }

    public Effects copy() {
        return new Effects(this.shootParticle, this.bulletParticle);
    }

}