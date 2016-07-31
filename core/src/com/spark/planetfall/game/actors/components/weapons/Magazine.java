package com.spark.planetfall.game.actors.components.weapons;


public class Magazine {

    private final float damage;
    private final float velocity;
    private final int pellets;
    private final float pelletSpread;
    private final int capacity;

    protected int amount;


    public Magazine(float damage, float velocity, int pellets, float pelletSpread, int capacity) {

        this.damage = damage;
        this.velocity = velocity; //PLAYER IS ALWAYS 1M x 1M
        this.pellets = pellets;
        this.pelletSpread = pelletSpread;
        this.capacity = capacity;

        this.amount = capacity;

    }


    public float damage() {

        return damage;

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

        return new Magazine(damage, velocity, pellets, pelletSpread, capacity);

    }

}
