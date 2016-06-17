package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.spark.planetfall.game.actors.Lightning;
import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.Vehicle;
import com.spark.planetfall.server.packets.VehicleAddPacket;

public class PlayerInput implements InputProcessor {

    public Player player;

    public PlayerInput(Player player) {

        this.player = player;

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
        if (keycode == Keys.NUM_5) {
            if (player.game.vehicle == null) {
                Lightning harasser = new Lightning(player.physics.world, player.stage, player.processor, player.network.handler, player.lightHandler);
                harasser.getPhysics().body.setTransform(player.getTransform().position.cpy(), harasser.getPhysics().body.getAngle());
                player.game.vehicle = harasser;
                player.stage.addActor(harasser);

                VehicleAddPacket packet = new VehicleAddPacket();
                packet.angle = harasser.transform.angle;
                packet.position = harasser.transform.position;
                packet.name = "Lightning";

                this.player.network.handler.client.sendTCP(packet);

            } else {
                player.game.vehicle = new Lightning(player.physics.world, player.stage, player.processor, player.network.handler, player.lightHandler);
            }
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

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

}
