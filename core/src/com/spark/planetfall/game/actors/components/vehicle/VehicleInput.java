package com.spark.planetfall.game.actors.components.vehicle;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.spark.planetfall.game.actors.Vehicle;

public class VehicleInput implements InputProcessor {

    public final Vehicle vehicle;

    public VehicleInput(Vehicle vehicle) {

        this.vehicle = vehicle;

    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Keys.W) {
            vehicle.getMovement().setMove(0, true);
        }
        if (keycode == Keys.S) {
            vehicle.getMovement().setMove(1, true);
        }
        if (keycode == Keys.A) {
            vehicle.getMovement().setMove(2, true);
        }
        if (keycode == Keys.D) {
            vehicle.getMovement().setMove(3, true);
        }
        if (keycode == Keys.F) {
            if (vehicle.getAbility().active()) {
                vehicle.getAbility().deactivate();
            } else {
                vehicle.getAbility().activate();
            }
        }
        if (keycode == Keys.E) {
            vehicle.exit();
        }
        if (keycode == Keys.R) {
            vehicle.getWeaponController().reload(vehicle.getWeaponController().weapons.getIndex());
        }

        if (keycode == Keys.L) {
            vehicle.hit(100);
        }

        if (keycode == Keys.K) {
            vehicle.kill();
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Keys.W) {
            vehicle.getMovement().setMove(0, false);
        }
        if (keycode == Keys.S) {
            vehicle.getMovement().setMove(1, false);
        }
        if (keycode == Keys.A) {
            vehicle.getMovement().setMove(2, false);
        }
        if (keycode == Keys.D) {
            vehicle.getMovement().setMove(3, false);
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
            vehicle.getWeaponController().triggered = true;
        }
        if (button == Buttons.RIGHT) {
            vehicle.getWeaponController().aiming = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            vehicle.getWeaponController().triggered = false;
        }
        if (button == Buttons.RIGHT) {
            vehicle.getWeaponController().aiming = false;
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
