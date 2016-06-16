package com.spark.planetfall.game.actors.components.weapons;


public class WeaponInventory {

    private Weapon[] weapons;
    private int index;

    public WeaponInventory(Weapon[] weapons) {

        this.weapons = weapons;

        this.index = 0;

    }

    public int getIndex() {
        return index;
    }

    public int size() {
        return this.weapons.length;
    }

    public boolean select(int index) {

        if (index < this.size() && index >= 0) {
            this.weapons[this.index].reload().cancel();
            this.index = index;
        }

        return false;

    }

    public Weapon getSelected() {
        return this.weapons[index];
    }

    public Weapon[] getWeapons() {
        return this.weapons;
    }

}
