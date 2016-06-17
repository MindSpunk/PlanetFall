package com.spark.planetfall.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.spark.planetfall.game.PlanetFallServer;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.server.packets.*;
import com.spark.planetfall.utils.Log;

import java.io.IOException;

public class ServerHandler {

    public Server server;
    public PlanetFallServer game;
    public float update;
    public RemotePlayer[] players = new RemotePlayer[Constant.SERVER_MAX_PLAYERS];
    public Array<RemoteVehicle> vehicles;

    public ServerHandler(PlanetFallServer game) {

        this.game = game;
        this.vehicles = new Array<RemoteVehicle>(false, 50, RemoteVehicle.class);
        this.vehicles.add(new RemoteVehicle());

        for (int i = 0; i < players.length; i++) {
            players[i] = new RemotePlayer();
            Log.logInfo("Index:" + i + " Length: " + players.length);
        }

        try {
            server = new Server();
            server.start();
            server.bind(Constant.SERVER_TCP_PORT, Constant.SERVER_UDP_PORT);
            Kryo kryo = server.getKryo();
            kryo.register(Vector2.class);
            kryo.register(RemotePlayer[].class);
            kryo.register(AllowedPacket.class);
            kryo.register(ConnectPacket.class);
            kryo.register(RefusedPacket.class);
            kryo.register(UpdatePacket.class);
            kryo.register(PingPacket.class);
            kryo.register(RemotePlayer.class);
            kryo.register(JoinedPacket.class);
            kryo.register(LeavePacket.class);
            kryo.register(BulletPacket.class);
            kryo.register(HitPacket.class);
            kryo.register(RemoteVehicle.class);
            kryo.register(RemoteVehicle[].class);
            kryo.register(VehicleAcceptedPacket.class);
            kryo.register(VehicleAddPacket.class);
            kryo.register(VehicleUpdatePacket.class);
            kryo.register(VehicleHitPacket.class);
            kryo.register(ShowPacket.class);
            kryo.register(HidePacket.class);
            server.addListener(new ServerListener(game, this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(float delta) {

        update += delta;
        if (update >= Constant.SERVER_UPDATE_RATE) {
            update = 0;
            for (int i = 0; i < players.length; i++) {
                if (!players[i].empty) {
                    UpdatePacket update = new UpdatePacket();
                    update.position = players[i].position;
                    update.angle = players[i].angle;
                    update.id = players[i].id;
                    server.sendToAllUDP(update);
                }
            }
            for (int i = 0; i < vehicles.size; i++) {
                if (!vehicles.get(i).empty) {
                    VehicleUpdatePacket update = new VehicleUpdatePacket();
                    update.position = vehicles.get(i).position;
                    update.angle = vehicles.get(i).angle;
                    update.id = vehicles.get(i).id;
                    server.sendToAllUDP(update);
                }
            }
        }

    }

    public int getPlayerCount() {
        int count = 0;
        for (int i = 0; i < players.length; i++) {
            if (!players[i].empty) {
                count++;
            }
        }
        return count;
    }

}
