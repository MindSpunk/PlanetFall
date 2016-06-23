package com.spark.planetfall.game.actors.remote;

import com.spark.planetfall.game.actors.components.player.Health;
import com.spark.planetfall.game.constants.Constant;

public class RemoteHealth extends Health {

    public RemoteHealth(float health) {

        super(null,null,health,0);

    }

    @Override
    public void update(float delta) {

        timer -= delta;

        if (timer <= 0) {
            shields += Constant.PLAYER_SHIELD_REGEN_PER_SECOND * delta;
        }

        if (shields > this.maxShields) {
            shields = this.maxShields;
        }

    }

}
