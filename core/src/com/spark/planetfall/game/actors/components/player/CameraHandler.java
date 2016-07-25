package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.constants.Constant;

public class CameraHandler {

    public float zoom;
    public final OrthographicCamera camera;
    public final Controlled player;
    public float view;

    private final Vector2 lerp;
    private final Vector2 lerpView;

    public CameraHandler(Controlled player, OrthographicCamera camera) {
        this.camera = camera;
        this.zoom = 0;
        this.view = Constant.CAMERA_WIDE_VIEW;
        this.player = player;

        this.lerp = new Vector2(zoom, 0);
        this.lerpView = new Vector2(Constant.CAMERA_WIDE_VIEW, 0);
    }

    public void update(float delta) {

        lerp.lerp(new Vector2(zoom, 0), Constant.CAMERA_FOCUS_LERP);
        this.camera.zoom = lerp.x;

        this.camera.position.lerp(new Vector3(player.getTransform().position.x, player.getTransform().position.y, 0), Constant.CAMERA_SPEED);

        if (this.player.getWeaponController().aiming) {
            this.lerpView.lerp(new Vector2(Constant.CAMERA_AIMED_VIEW, 0), 0.2f);
        } else {
            this.lerpView.lerp(new Vector2(Constant.CAMERA_WIDE_VIEW, 0), 0.2f);
        }
        this.view = this.lerpView.x;

    }

}
