package com.spark.planetfall.game.actors.components.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.utils.Log;

public class Effects {

    private final ParticleEffect shootParticle;
    private final Color bulletParticle;
    public Sound fire;
    public Sound longReload;
    public Sound shortReload;
    public Sound fireEnd;
    private String resDir;
    public Sprite sprite;
    private String name;

    public Effects(String shootParticleName, Color bulletParticleName, String resDir, String name) {

        this.shootParticle = new ParticleEffect();
        this.shootParticle.load(Gdx.files.internal(shootParticleName), Gdx.files.internal(""));
        this.fire = Gdx.audio.newSound(Gdx.files.internal("weapons/" + resDir + "/fire.wav"));
        this.longReload = Gdx.audio.newSound(Gdx.files.internal("weapons/" + resDir + "/long.wav"));
        this.shortReload = Gdx.audio.newSound(Gdx.files.internal("weapons/" + resDir + "/short.wav"));
        this.fireEnd = Gdx.audio.newSound(Gdx.files.internal("weapons/" + resDir + "/fire_end.wav"));
        this.sprite = Atlas.get().createSprite("weapons/" + resDir + "/" + name);
        if (this.sprite == null) {
            Log.logWarn(name + " Sprite is Null");
        }
        this.resDir = resDir;
        this.name = name;

        this.bulletParticle = bulletParticleName;

    }

    public Effects(ParticleEffect shootParticle, Color bulletParticle, String resDir, String name) {

        this.shootParticle = shootParticle;
        this.bulletParticle = bulletParticle;
        this.fire = Gdx.audio.newSound(Gdx.files.internal("weapons/" + resDir + "/fire.wav"));
        this.longReload = Gdx.audio.newSound(Gdx.files.internal("weapons/" + resDir + "/long.wav"));
        this.shortReload = Gdx.audio.newSound(Gdx.files.internal("weapons/" + resDir + "/short.wav"));
        this.fireEnd = Gdx.audio.newSound(Gdx.files.internal("weapons/" + resDir + "/fire_end.wav"));
        this.sprite = Atlas.get().createSprite("weapons/" + resDir + "/" + name);
        if (this.sprite == null) {
            Log.logWarn(name + " Sprite is Null");
        }
        this.resDir = resDir;
        this.name = name;

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
        return new Effects(this.shootParticle, this.bulletParticle, this.resDir, this.name);
    }

}