package com.spark.planetfall.game.actors.remote;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.components.Physics;
import com.spark.planetfall.game.actors.components.Render;
import com.spark.planetfall.game.actors.components.vehicle.MagriderBodyDef;
import com.spark.planetfall.game.texture.Atlas;
import com.spark.planetfall.server.ClientHandler;
import com.spark.planetfall.server.RemoteVehicle;

public class RemoteLightning extends RemoteVehicleActor {

    public RemoteLightning(RemoteVehicle remote, World world, ClientHandler handler, Stage stage) {
        super(remote, world, handler, stage);
        this.physics = new Physics(world, new MagriderBodyDef(), position, this);
        this.render = new Render(Atlas.get().createSprite("gfx/box"));
        this.render.sprite.setSize(3, 6);
        this.render.sprite.setOriginCenter();
    }

}
