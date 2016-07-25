package com.spark.planetfall.game.actors.components.player;

import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.utils.Log;

public class Health {

    public float shields;
    public float health;
    public float timer;

    public final float maxHealth;
    public final float maxShields;

    protected final Controlled player;
    protected final UIHandler ui;

    public Health(Controlled player, UIHandler ui) {

        this.health = Constant.PLAYER_HEALTH;
        this.shields = Constant.PLAYER_SHIELDS;
        this.maxHealth = Constant.PLAYER_HEALTH;
        this.maxShields = Constant.PLAYER_SHIELDS;
        this.timer = 0;

        this.ui = ui;

        this.player = player;

    }

    public Health(Controlled player, UIHandler ui, float maxHealth, float maxShields) {

        this.player = player;

        this.ui = ui;

        this.maxHealth = maxHealth;
        this.maxShields = maxShields;
        this.health = maxHealth;
        this.shields = maxShields;

    }

    public void update(float delta) {

        timer -= delta;

        if (timer <= 0) {
            shields += Constant.PLAYER_SHIELD_REGEN_PER_SECOND * delta;
        }

        if (shields > this.maxShields) {
            shields = this.maxShields;
        }

        if (health <= 0) {

            this.player.kill();

        }


        if (this.ui != null) {
            ui.healthBar.setValue(this.health);
            ui.shieldBar.setValue(this.shields);
        }


    }

    public void hit(float damage) {

        timer = Constant.PLAYER_HIT_REGEN_COOLDOWN;

        shields -= damage;
        if (shields <= 0) {
            health += shields;
            shields = 0;
        }
        Log.logCrit("shields: " + shields + " health: " + health);

    }

    public void heal(float amount) {
        float total = health + shields;

        if (total <= 0) {
            this.health = 0;
            this.shields = 0;
        }

        total += amount;

        if (total >= 1000) {
            total = 1000;
        }

        health += total;
        if (health >= this.maxHealth) {
            shields += health - this.maxHealth;
            health = this.maxHealth;
        }
        if (shields >= this.maxShields) {
            shields = this.maxShields;
        }
    }

    public void healHealthOnly(float amount) {

        health += amount;
        if (health >= this.maxHealth) {
            health = this.maxHealth;
        }

    }

    public void heal() {
        this.health = this.maxHealth;
        this.shields = this.maxShields;
    }

}
