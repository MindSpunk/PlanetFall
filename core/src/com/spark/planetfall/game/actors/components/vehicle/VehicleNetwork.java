package com.spark.planetfall.game.actors.components.vehicle;

import com.spark.planetfall.game.actors.components.Network;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.server.packets.VehicleUpdatePacket;

public class VehicleNetwork extends Network {

    public VehicleNetwork(ClientHandler handler, Transform position) {

        super(handler, position);

    }

    @Override
    public void update(float delta) {
        this.update += delta;
        if (this.update >= Constant.SERVER_UPDATE_RATE) {
            update = 0;
            VehicleUpdatePacket packet = new VehicleUpdatePacket();
            packet.position = position.position;
            packet.angle = position.angle;
            if (handler.client.isConnected()) {
                handler.client.sendUDP(packet);
            }
        }
    }

}
