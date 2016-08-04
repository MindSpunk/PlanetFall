package com.spark.planetfall.game.actors.components.weapons;


import com.spark.planetfall.game.actors.weapons.DamageModel;

public class Magazine {

    private final DamageModel damageModel;
    private final float velocity;
    private final int pellets;
    private final float pelletSpread;
    private final int capacity;

    protected int amount;


    public Magazine(DamageModel damageModel, float velocity, int pellets, float pelletSpread, int capacity) {

        this.damageModel = damageModel;
        this.velocity = velocity; //PLAYER IS ALWAYS 1M x 1M
        this.pellets = pellets;
        this.pelletSpread = pelletSpread;
        this.capacity = capacity;

        this.amount = capacity;

    }


    public DamageModel damage() {

        return damageModel;

    }

    public float velocity() {

        return velocity;

    }

    public int pellets() {

        return pellets;

    }

    public float pelletSpread() {

        return pelletSpread;

    }

    public int capacity() {

        return capacity;

    }

    public int amount() {

        return amount;

    }

    public void setAmount(int amount) {

        this.amount = amount;

    }

    public Magazine copy() {

        return new Magazine(damageModel, velocity, pellets, pelletSpread, capacity);

    }

}
