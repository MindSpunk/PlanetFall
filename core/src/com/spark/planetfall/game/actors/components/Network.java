package com.spark.planetfall.game.actors.components;

import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.server.packets.UpdatePacket;

public class Network {

    public final Transform position;
    protected float update;
    public final ClientHandler handler;

    public Network(ClientHandler handler, Transform position) {

        this.position = position;
        this.handler = handler;
        this.update = 0;

    }

    public void update(float delta) {
        update += delta;
        if (update >= Constant.SERVER_UPDATE_RATE) {
            update = 0;
            UpdatePacket packet = new UpdatePacket();
            packet.position = position.position;
            packet.angle = position.angle;
            if (handler.client.isConnected()) {
                handler.client.sendUDP(packet);
            }
        }
    }

}
