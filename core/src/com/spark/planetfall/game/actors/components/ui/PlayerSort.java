package com.spark.planetfall.game.actors.components.ui;

import com.spark.planetfall.game.ui.PlayerListEntry;
import com.spark.planetfall.server.RemotePlayer;

import java.util.Comparator;

public class PlayerSort implements Comparator<PlayerListEntry> {
    @Override
    public int compare(PlayerListEntry o1, PlayerListEntry o2) {

        if (o1.score > o2.score) {
            return -1;
        }
        if (o1.score < o2.score) {
            return 1;
        }
        return 0;
    }
}
