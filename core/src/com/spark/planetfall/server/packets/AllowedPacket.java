package com.spark.planetfall.server.packets;

import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.game.map.Map;
import com.spark.planetfall.server.RemotePlayer;
import com.spark.planetfall.server.RemoteVehicle;

public class AllowedPacket {

    public int id;
    public RemotePlayer[] players = new RemotePlayer[Constant.SERVER_MAX_PLAYERS];
    public RemoteVehicle[] vehicles;
    public Map map;

}
