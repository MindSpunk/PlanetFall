package com.spark.planetfall.game.actors.components.weapons;

import com.badlogic.gdx.utils.Array;
import com.spark.planetfall.game.actors.weapons.Weapons;

public class WeaponUtils {

    public static Weapons[] getType(byte type) {
        Array<Weapons> weaponArray = new Array<Weapons>();

        for (int i = 0; i < Weapons.values().length; i++) {
            if (Weapons.values()[i].type == type) {
                weaponArray.add(Weapons.values()[i]);
            }
        }

        Weapons[] ret;

        ret = weaponArray.toArray(Weapons.class);

        return ret;

    }

}
