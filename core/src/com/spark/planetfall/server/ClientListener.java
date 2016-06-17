package com.spark.planetfall.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.spark.planetfall.game.actors.remote.*;
import com.spark.planetfall.game.screens.SparkGame;
import com.spark.planetfall.server.packets.*;
import com.spark.planetfall.utils.Log;

public class ClientListener extends Listener {

    public final SparkGame game;
    public final ClientHandler handler;

    public ClientListener(SparkGame game, ClientHandler clientHandler) {
        this.game = game;
        this.handler = clientHandler;
    }

    @Override
    public void connected(Connection connection) {

    }

    @Override
    public void disconnected(Connection connection) {

    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof LeavePacket) {
            LeavePacket packet = (LeavePacket) object;
            for (int i = 0; i < handler.players.length; i++) {
                if (handler.players[i].id == packet.id) {
                    handler.players[i].remove = true;
                }
            }
        }

        if (object instanceof JoinedPacket) {
            JoinedPacket packet = (JoinedPacket) object;
            if (packet.id != handler.id) {
                Log.logInfo("New Player Connected");
                for (int i = 0; i < handler.players.length; i++) {
                    if (handler.players[i].empty) {
                        handler.players[i].id = packet.id;
                        handler.players[i].empty = false;
                        Remote remote = new Remote(handler.players[i], game.world, handler);
                        handler.playerActors[i] = remote;
                        game.stage.addActor(remote);
                        if (handler.game.player.isCaptured()) {
                            HidePacket response = new HidePacket();
                            handler.client.sendTCP(response);
                        }
                        break;
                    }
                }
            }
        }

        if (object instanceof PingPacket) {
            ConnectPacket packet = new ConnectPacket();
            packet.position = new Vector2(21, 21);
            packet.angle = 20f;
            packet.name = "meme";
            handler.client.sendTCP(packet);
        }

        if (object instanceof AllowedPacket) {
            AllowedPacket packet = (AllowedPacket) object;
            Log.logInfo("Connection Established");
            handler.id = packet.id;
            for (int i = 0; i < packet.players.length; i++) {
                if (packet.players[i].id != handler.id) {
                    Log.logInfo("Adding Player" + i);
                    handler.players[i] = packet.players[i];
                    if (packet.players[i].id != -1) {
                        Remote remote = new Remote(handler.players[i], game.world, handler);
                        handler.playerActors[i] = remote;
                        game.stage.addActor(remote);
                    }
                } else {
                    handler.players[i] = packet.players[i];
                }
            }
            handler.vehicles.addAll(packet.vehicles);
            for (int i = 0; i < handler.vehicles.size; i++) {
                RemoteVehicle temp = handler.vehicles.get(i);
                if (temp != null) {
                    if (!temp.empty) {
                        Log.logInfo("ADDING VEHICLE");
                        game.stage.addActor(new RemoteVehicleActor(temp, game.world, game.handler));
                    }
                }
            }
        }

        if (object instanceof RefusedPacket) {
            RefusedPacket packet = (RefusedPacket) object;
            Log.logInfo("Connection Refused");
            Log.logInfo(packet.reason);
            handler.client.close();
            Gdx.app.exit();
        }

        if (object instanceof UpdatePacket) {
            UpdatePacket packet = (UpdatePacket) object;
            for (int i = 0; i < handler.players.length; i++) {
                if (!handler.players[i].empty) {
                    if (handler.players[i].id == packet.id) {
                        handler.players[i].position = packet.position;
                        handler.players[i].angle = packet.angle;
                        break;
                    }
                }
            }
        }

        if (object instanceof BulletPacket) {
            BulletPacket packet = (BulletPacket) object;
            game.stage.addActor(new RemoteBullet(packet.position, packet.velocity, game.world, packet.rgba8888, game.stage, game.player));
        }

        if (object instanceof HitPacket) {
            HitPacket packet = (HitPacket) object;
            Log.logCrit("HIT for: " + packet.damage);
            game.player.hit(packet.damage);
        }
        if (object instanceof VehicleHitPacket) {
            VehicleHitPacket packet = (VehicleHitPacket) object;
            game.vehicle.hit(packet.damage);
        }

        if (object instanceof VehicleAddPacket) {
            VehicleAddPacket packet = (VehicleAddPacket) object;
            if (packet.name.equals("Harasser")) {
                RemoteVehicle vehicle = new RemoteVehicle();
                vehicle.id = packet.id;
                vehicle.empty = false;
                handler.vehicles.add(vehicle);
                Log.logInfo("Adding Vehicle");
                game.stage.addActor(new RemoteHarasser(vehicle, game.world, game.handler));
            }
            if (packet.name.equals("Lightning")) {
                RemoteVehicle vehicle = new RemoteVehicle();
                vehicle.id = packet.id;
                vehicle.empty = false;
                handler.vehicles.add(vehicle);
                Log.logInfo("Adding Vehicle");
                game.stage.addActor(new RemoteLightning(vehicle, game.world, game.handler));
            }
        }

        if (object instanceof VehicleUpdatePacket) {
            VehicleUpdatePacket packet = (VehicleUpdatePacket) object;

            for (int i = 0; i < handler.vehicles.size; i++) {
                if (handler.vehicles.get(i) != null) {
                    if (!handler.vehicles.get(i).empty) {
                        if (handler.vehicles.get(i).id == packet.id) {
                            handler.vehicles.get(i).position = packet.position;
                            handler.vehicles.get(i).angle = packet.angle;
                            break;
                        }
                    }
                }
            }
        }

        if (object instanceof HidePacket) {
            HidePacket packet = (HidePacket) object;

            for (int i = 0; i < handler.playerActors.length; i++) {
                if (handler.playerActors[i] != null) {
                    if (packet.id == handler.playerActors[i].remote.id) {
                        handler.playerActors[i].remove();
                        handler.playerActors[i].delete();
                        break;
                    }
                }
            }
        }

        if (object instanceof ShowPacket) {
            ShowPacket packet = (ShowPacket) object;

            for (int i = 0; i < handler.playerActors.length; i++) {
                if (handler.playerActors[i] != null) {
                    if (packet.id == handler.playerActors[i].remote.id) {
                        game.stage.addActor(handler.playerActors[i]);
                        handler.playerActors[i].add();
                        break;
                    }
                }
            }
        }

        if (object instanceof VehicleHitPacket) {
            VehicleHitPacket packet = (VehicleHitPacket) object;
            game.vehicle.hit(packet.damage);
        }

        if (object instanceof TeleportPacket) {
            TeleportPacket packet = (TeleportPacket) object;
            for (Remote remote: handler.playerActors) {
                if (remote.remote.id == packet.id) {
                    remote.position.position = packet.location;
                    break;
                }
            }
        }
    }
}
