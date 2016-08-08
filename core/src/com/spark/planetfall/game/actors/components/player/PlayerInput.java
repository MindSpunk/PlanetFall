package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spark.planetfall.game.PlanetFallClient;
import com.spark.planetfall.game.PlanetFallServer;
import com.spark.planetfall.game.actors.Lightning;
import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.Vehicle;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.server.packets.VehicleAddPacket;



public class PlayerInput implements InputProcessor {

    public final Player player;

    public boolean tabHeld;

    public PlayerInput(Player player) {

        this.player = player;
        this.tabHeld = false;

    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Keys.W) {
            player.movement.moving[0] = true;
        }
        if (keycode == Keys.S) {
            player.movement.moving[1] = true;
        }
        if (keycode == Keys.A) {
            player.movement.moving[2] = true;
        }
        if (keycode == Keys.D) {
            player.movement.moving[3] = true;
        }
        if (keycode == Keys.E) {
            for (int i = 0; i < player.stage.getActors().size; i++) {
                if (player.stage.getActors().get(i) instanceof Vehicle) {
                    Vehicle vehicle = (Vehicle) player.stage.getActors().get(i);
                    if (Math.abs(vehicle.getTransform().position.dst(this.player.getTransform().position)) < 10) {
                        vehicle.board(player, player.coneOfView);
                        this.player.capture();
                        break;
                    }
                }
            }
        }
        if (keycode == Keys.SHIFT_LEFT) {
            if (!player.controller.aiming) {
                player.movement.sprint = true;
            }
        }
        if (keycode == Keys.F) {
            if (player.ability.active()) {
                player.ability.deactivate();
            } else {
                player.ability.activate();
            }
        }
        if (keycode == Keys.R) {
            player.controller.reload(player.controller.weapons.getIndex());
        }
        if (keycode == Keys.NUM_1) {
            player.controller.weapons.select(0);
        }
        if (keycode == Keys.NUM_2) {
            player.controller.weapons.select(1);
        }
        if (keycode == Keys.NUM_3) {
            player.controller.weapons.select(2);
        }
        if (keycode == Keys.NUM_4) {
            player.controller.weapons.select(3);
        }

        /*
        if (keycode == Keys.NUM_5) {
            if (player.game.vehicle == null) {
                Transform transform = new Transform(player.getTransform().position.cpy(), player.getTransform().angle);
                player.game.vehicle = new Lightning(transform, player.physics.world, player.stage, player.processor, player.network.handler, player.lightHandler);
                player.game.vehicle.getPhysics().body.setTransform(player.getTransform().position.cpy(), player.game.vehicle.getPhysics().body.getAngle());

                player.stage.addActor(player.game.vehicle);

                VehicleAddPacket packet = new VehicleAddPacket();
                packet.angle = player.game.vehicle.transform.angle;
                packet.position = player.game.vehicle.transform.position;
                packet.name = "Lightning";

                this.player.network.handler.client.sendTCP(packet);

            } else {
                if (player.game.vehicle.alive) {
                    player.game.vehicle.remove();
                    Transform transform = new Transform(player.getTransform().position.cpy(), player.getTransform().angle);
                    player.game.vehicle.getPhysics().body.destroyFixture(player.game.vehicle.getPhysics().fixture);
                    player.game.vehicle = new Lightning(transform, player.physics.world, player.stage, player.processor, player.network.handler, player.lightHandler);
                    player.game.vehicle.getPhysics().body.setTransform(player.getTransform().position.cpy(), player.game.vehicle.getPhysics().body.getAngle());
                    player.stage.addActor(player.game.vehicle);
                } else {
                    Transform transform = new Transform(player.getTransform().position.cpy(), player.getTransform().angle);
                    player.game.vehicle = new Lightning(transform, player.physics.world, player.stage, player.processor, player.network.handler, player.lightHandler);
                    player.game.vehicle.getPhysics().body.setTransform(player.getTransform().position.cpy(), player.game.vehicle.getPhysics().body.getAngle());
                    player.stage.addActor(player.game.vehicle);
                    VehicleAddPacket packet = new VehicleAddPacket();
                    packet.angle = player.game.vehicle.transform.angle;
                    packet.position = player.game.vehicle.transform.position;
                    packet.name = "Lightning";

                    this.player.network.handler.client.sendTCP(packet);
                }
            }
        }
        */

        if (keycode == Keys.TAB) {
            this.tabHeld = true;
        }

        if (keycode == Keys.ESCAPE) {
            player.network.handler.client.close();
            Gdx.app.exit();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Keys.W) {
            player.movement.moving[0] = false;
        }
        if (keycode == Keys.S) {
            player.movement.moving[1] = false;
        }
        if (keycode == Keys.A) {
            player.movement.moving[2] = false;
        }
        if (keycode == Keys.D) {
            player.movement.moving[3] = false;
        }
        if (keycode == Keys.SHIFT_LEFT) {
            player.movement.sprint = false;
        }
        if (keycode == Keys.F) {
            if (player.ability.hold()) {
                player.ability.deactivate();
            }
        }
        if (keycode == Keys.TAB) {
            this.tabHeld = false;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            player.movement.sprint = false;
            player.controller.triggered = true;
        }
        if (button == Buttons.RIGHT) {
            player.controller.aiming = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            player.controller.triggered = false;
        }
        if (button == Buttons.RIGHT) {
            player.controller.aiming = false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
