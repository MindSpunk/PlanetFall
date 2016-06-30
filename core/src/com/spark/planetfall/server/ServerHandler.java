package com.spark.planetfall.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.spark.planetfall.game.PlanetFallServer;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.game.map.Map;
import com.spark.planetfall.game.map.components.BaseComponent;
import com.spark.planetfall.game.map.components.CapturePoint;
import com.spark.planetfall.game.map.components.Facility;
import com.spark.planetfall.game.map.components.SpawnPoint;
import com.spark.planetfall.server.packets.*;
import com.spark.planetfall.utils.Log;

import java.io.IOException;

public class ServerHandler {

    public Server server;
    public final PlanetFallServer game;
    public float update;
    public final RemotePlayer[] players = new RemotePlayer[Constant.SERVER_MAX_PLAYERS];
    public final Array<RemoteVehicle> vehicles;
    public Map map;

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
            kryo.register(VehicleKillPacket.class);
            kryo.register(TeleportPacket.class);
            kryo.register(MapPacket.class);
            kryo.register(Map.class);
            kryo.register(Facility.class);
            kryo.register(CapturePoint.class);
            kryo.register(SpawnPoint.class);
            kryo.register(BaseComponent.class);
            server.addListener(new ServerListener(game, this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(float delta) {

        update += delta;
        if (update >= Constant.SERVER_UPDATE_RATE) {
            for (RemotePlayer player : players) {
                if (!player.empty) {
                    UpdatePacket update = new UpdatePacket();
                    update.position = player.position;
                    update.angle = player.angle;
                    update.id = player.id;
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
            map.update(update, this);
            update = 0;
        }

    }

    public int getPlayerCount() {
        int count = 0;
        for (RemotePlayer player : players) {
            if (!player.empty) {
                count++;
            }
        }
        return count;
    }

}
