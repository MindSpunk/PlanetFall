package com.spark.planetfall.game.actors.components.player.classes;

import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.components.player.abilities.JumpJet;
import com.spark.planetfall.game.actors.components.weapons.Weapon;
import com.spark.planetfall.game.actors.weapons.Weapons;

public class LightAssault extends PlayerClass {

    public LightAssault(Player player) {
        super(null);

        if (player != null) {
            this.equipment = new Weapon[3];
            this.equipment[0] = Weapons.values()[0].copy();
            this.equipment[1] = Weapons.values()[0].copy();
            this.equipment[2] = Weapons.values()[0].copy();
            this.ability = new JumpJet(player, 0.1f, 0.02f);
        }
    }

}
