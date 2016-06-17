package com.spark.planetfall.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.spark.planetfall.game.actors.remote.Remote;
import com.spark.planetfall.game.constants.Constant;
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

    public ClientHandler(SparkGame game, String ip) {

        for (int i = 0; i < players.length; i++) {
            players[i] = new RemotePlayer();
        }

        this.game = game;

        client = new Client();
        client.start();
        Kryo kryo = client.getKryo();
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
}
