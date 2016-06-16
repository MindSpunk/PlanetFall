package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.actors.Player;

public class CrosshairRenderer {

    private Controlled player;

    public ShapeRenderer renderer;

    public CrosshairRenderer(Player player) {
        this.player = player;
        this.renderer = new ShapeRenderer();
    }

    public void draw(float angle) {

        Vector2 place = new Vector2(0, player.getWeaponController().weapons.getSelected().action().getBarrel());
        place.setAngle(angle + 90);
        place.add(player.getTransform().position);

        renderer.setProjectionMatrix(player.getStage().getCamera().combined);
        renderer.begin(ShapeType.Line);

        if (this.player.getWeaponController().weapons.getSelected().mechanism().canFire() && this.player.getWeaponController().weapons.getSelected().reload().canFire() && this.player.getMovement().getShootCooldown() <= 0) {
            renderer.setColor(new Color(1, 1, 1, 0.5f));
        } else {
            renderer.setColor(new Color(1, 0, 0, 0.5f));
        }
        Vector2 temp = new Vector2(0, 30);
        temp.setAngle(angle + player.getWeaponController().weapons.getSelected().recoil().getCOF() + 90);
        temp.add(player.getTransform().position);
        renderer.line(place, temp);
        temp = new Vector2(0, 30);
        temp.setAngle(angle - player.getWeaponController().weapons.getSelected().recoil().getCOF() + 90);
        temp.add(player.getTransform().position);
        renderer.line(place, temp);
        renderer.end();


    }

    public void setControlled(Controlled set) {
        this.player = set;
    }

}
