package com.spark.planetfall.game.actors.components.player.classes;

import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.components.player.Ability;
import com.spark.planetfall.game.actors.components.player.abilities.JumpJet;
import com.spark.planetfall.game.actors.components.weapons.Weapon;
import com.spark.planetfall.game.actors.weapons.Weapons;

public class PlayerClass {

    public Weapon[] equipment;
    public Ability ability;

    public PlayerClass(Player player) {

        if (player != null) {
            equipment = new Weapon[1];
            equipment[0] = Weapons.AK_47.copy();
            this.ability = new JumpJet(player, 0.1f, 0.2f);
        }

    }

}
