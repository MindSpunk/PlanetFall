package com.spark.planetfall.game.actors.weapons;

import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.actors.components.ui.UIHandler;
import com.spark.planetfall.game.actors.components.weapons.Weapon;
import com.spark.planetfall.game.actors.components.weapons.WeaponInventory;


public class WeaponController {

    public final WeaponInventory weapons;
    public boolean aiming;
    public boolean triggered;

    public final Controlled controlled;

    public UIHandler ui;

    protected boolean firing;

    public WeaponController(Weapon[] weapons, UIHandler ui, Controlled controlled) {

        this.weapons = new WeaponInventory(weapons);
        this.aiming = false;
        this.triggered = false;

        this.controlled = controlled;

        this.ui = ui;

        this.firing = false;

        for (int i = 0; i < this.weapons.size(); i++) {

            this.weapons.getWeapons()[i].action().setPlayer(this.controlled);
            this.weapons.getWeapons()[i].effects().setup();

        }

    }

    public WeaponController(Weapon weapon, UIHandler ui, Controlled controlled) {

        Weapon[] weapons = {weapon};
        this.weapons = new WeaponInventory(weapons);

        this.aiming = false;
        this.triggered = false;

        this.controlled = controlled;

        this.ui = ui;

        this.firing = false;

        for (int i = 0; i < this.weapons.size(); i++) {

            this.weapons.getWeapons()[i].action().setPlayer(this.controlled);
            this.weapons.getWeapons()[i].effects().setup();

        }
    }

    public boolean fire() {

        if (triggered) {
            if (weapons.getSelected().reload().canFire()) {
                if (weapons.getSelected().mechanism().canFire()) {
                    weapons.getSelected().mechanism().fire();
                    weapons.getSelected().recoil().fire();
                    this.triggered = weapons.getSelected().mechanism().remainTriggered();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public void reload(int index) {

        weapons.getWeapons()[index].reload().reload();

    }

    public void update(float delta) {

        for (int i = 0; i < this.weapons.getWeapons().length; i++) {

            boolean selected = false;

            if (i == this.weapons.getIndex()) {
                selected = true;
            }

            firing = weapons.getWeapons()[i].mechanism().timeToNextShot() > 0;

            if (triggered && selected) {
                if (weapons.getWeapons()[i].magazine().amount() <= 0) {
                    reload(i);
                }
            }

            weapons.getWeapons()[i].mechanism().update(delta, selected);
            weapons.getWeapons()[i].recoil().update(delta, firing, aiming);
            weapons.getWeapons()[i].reload().update(delta);
            weapons.getWeapons()[i].action().update(delta);

            weapons.getSelected().effects().shootEffect().update(delta);
        }
        if (this.ui != null) {
            ui.ammo.setText("" + weapons.getSelected().magazine().capacity() + "/" + weapons.getSelected().magazine().amount());
        }

    }

    public void setUI(UIHandler ui) {
        this.ui = ui;
    }

}
