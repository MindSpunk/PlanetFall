package com.spark.planetfall.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.spark.planetfall.game.PlanetFallServer;
import com.spark.planetfall.server.packets.*;
import com.spark.planetfall.utils.Log;

public class ServerListener extends Listener {

    final PlanetFallServer serverLoop;
    final ServerHandler handler;

    public ServerListener(PlanetFallServer serverLoop, ServerHandler handler) {
        this.serverLoop = serverLoop;
        this.handler = handler;
    }

    @Override
    public void connected(Connection connection) {

        Log.logInfo("SENDING PING");
        connection.sendTCP(new PingPacket());

    }

    @Override
    public void disconnected(Connection connection) {

        for (int i = 0; i < handler.players.length; i++) {
            if (handler.players[i].id == connection.getID()) {
                handler.players[i] = new RemotePlayer();
                LeavePacket packet = new LeavePacket();
                packet.id = connection.getID();
                handler.server.sendToAllTCP(packet);
                VehicleKillPacket packet2 = new VehicleKillPacket();
                packet2.id = connection.getID();
                handler.server.sendToAllTCP(packet2);
                for (int a = 0; i < handler.vehicles.size; i++) {
                    if (handler.vehicles.get(a).id == connection.getID() && !handler.vehicles.get(a).empty) {
                        handler.vehicles.set(i, new RemoteVehicle());
                    }
                }

            }
        }

    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof ConnectPacket) {
            ConnectPacket packet = (ConnectPacket) object;
            boolean slotFound = false;
            Log.logInfo("Connection");
            for (int i = 0; i < handler.players.length; i++) {
                if (handler.players[i].empty) {
                    slotFound = true;
                    handler.players[i].position = packet.position;
                    handler.players[i].angle = packet.angle;
                    handler.players[i].id = connection.getID();
                    handler.players[i].name = packet.name;
                    handler.players[i].empty = false;
                    AllowedPacket response = new AllowedPacket();
                    response.map = handler.map;
                    response.id = connection.getID();
                    response.players = handler.players;
                    response.vehicles = handler.vehicles.items;
                    connection.sendTCP(response);
                    break;
                }
            }
            if (!slotFound) {
                Log.logInfo("Player Refused");
                RefusedPacket refuse = new RefusedPacket();
                refuse.reason = "ServerFull";
                handler.server.sendToTCP(connection.getID(), refuse);
            } else {
                Log.logInfo("New Player");
                JoinedPacket join = new JoinedPacket();
                join.id = connection.getID();
                handler.server.sendToAllExceptTCP(connection.getID(), join);
            }
        }

        if (object instanceof VehicleKillPacket) {
            VehicleKillPacket packet = (VehicleKillPacket) object;

            for (int i = 0; i < handler.vehicles.size; i++) {
                if (handler.vehicles.get(i).id == connection.getID() && !handler.vehicles.get(i).empty) {
                    handler.vehicles.set(i, new RemoteVehicle());
                    packet.id = connection.getID();
                    handler.server.sendToAllExceptTCP(connection.getID(), packet);
                }
            }

        }

        if (object instanceof UpdatePacket) {
            UpdatePacket packet = (UpdatePacket) object;

            for (int i = 0; i < handler.players.length; i++) {
                if (!handler.players[i].empty) {
                    if (handler.players[i].id == connection.getID()) {
                        handler.players[i].position = packet.position;
                        handler.players[i].angle = packet.angle;
                        break;
                    }
                }
            }
        }

        if (object instanceof BulletPacket) {
            BulletPacket packet = (BulletPacket) object;
            handler.server.sendToAllExceptUDP(connection.getID(), packet);
        }
        if (object instanceof HitPacket) {
            HitPacket packet = (HitPacket) object;
            handler.server.sendToTCP(packet.id, packet);
        }
        if (object instanceof VehicleHitPacket) {
            VehicleHitPacket packet = (VehicleHitPacket) object;
            handler.server.sendToTCP(packet.id, packet);
        }
        if (object instanceof VehicleAddPacket) {
            Log.logInfo("ADDING VEHICLE");
            VehicleAddPacket packet = (VehicleAddPacket) object;
            packet.id = connection.getID();
            handler.server.sendToAllExceptTCP(connection.getID(), packet);
            RemoteVehicle vehicle = new RemoteVehicle();
            vehicle.id = connection.getID();
            vehicle.position = packet.position;
            vehicle.angle = packet.angle;
            vehicle.name = packet.name;
            vehicle.empty = false;
            handler.vehicles.add(vehicle);
        }
        if (object instanceof VehicleUpdatePacket) {
            VehicleUpdatePacket packet = (VehicleUpdatePacket) object;

            for (int i = 0; i < handler.vehicles.size; i++) {
                if (!handler.vehicles.get(i).empty) {
                    if (handler.vehicles.get(i).id == connection.getID()) {
                        handler.vehicles.get(i).position = packet.position;
                        handler.vehicles.get(i).angle = packet.angle;
                        break;
                    }
                }
            }
        }

        if (object instanceof HidePacket) {
            HidePacket packet = (HidePacket) object;
            packet.id = connection.getID();
            handler.server.sendToAllExceptTCP(connection.getID(), packet);
        }
        if (object instanceof ShowPacket) {
            ShowPacket packet = (ShowPacket) object;
            packet.id = connection.getID();
            handler.server.sendToAllExceptTCP(connection.getID(), packet);
        }
        if (object instanceof TeleportPacket) {
            TeleportPacket packet = (TeleportPacket) object;
            packet.id = connection.getID();
            handler.server.sendToAllExceptTCP(connection.getID(),packet);
        }
    }
}
