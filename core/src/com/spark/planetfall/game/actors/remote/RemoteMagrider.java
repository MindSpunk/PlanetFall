package com.spark.planetfall.game.actors.remote;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.server.RemoteVehicle;

public class RemoteMagrider extends RemoteVehicleActor {

    public RemoteMagrider(RemoteVehicle remote, World world, ClientHandler handler, Stage stage) {
        super(remote, world, handler, stage);
    }

}
