package com.spark.planetfall.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.game.actors.remote.Remote;
import com.spark.planetfall.game.constants.Constant;
import com.spark.planetfall.game.map.Map;
import com.spark.planetfall.game.map.components.*;
import com.spark.planetfall.game.screens.SparkGame;
import com.spark.planetfall.server.packets.*;

import java.io.IOException;

public class ClientHandler {

    public final Client client;
    public final SparkGame game;
    public int id;
    public final RemotePlayer[] players = new RemotePlayer[Constant.SERVER_MAX_PLAYERS];
    public final Remote[] playerActors = new Remote[Constant.SERVER_MAX_PLAYERS];
    public final Array<RemoteVehicle> vehicles = new Array<RemoteVehicle>();
    public RemotePlayer player;

    public String name;

    public ClientHandler(SparkGame game, String ip) {

        for (int i = 0; i < players.length; i++) {
            players[i] = new RemotePlayer();
        }

        this.game = game;

        client = new Client(16384*4, 2048*4 , new KryoSerialization());
        client.start();
        Kryo kryo = client.getKryo();
        kryo.setReferences(true);
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
        kryo.register(SpawnPoint.class);
        kryo.register(BaseComponent.class);
        kryo.register(BaseComponent[].class);
        kryo.register(Facility[].class);
        kryo.register(SpawnPoint[].class);
        kryo.register(Transform.class);
        kryo.register(KilledPacket.class);
        kryo.register(ClientUpdatePacket.class);
        kryo.register(Class.class);
        kryo.register(RemotePlayerRequestPacket.class);
        kryo.register(SortPacket.class);
        client.addListener(new ClientListener(game, this));
        new Thread(new ConnectThread(ip) {

            @Override
            public void run() {

                try {
                    client.connect(5000, this.ip, Constant.SERVER_TCP_PORT, Constant.SERVER_UDP_PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }, "Client Connect").start();
    }

    public RemotePlayer getPlayerFromID(int id) {
        for (RemotePlayer player : players) {
            if (player.id == id) {
                return player;
            }
        }
        return null;
    }
}
