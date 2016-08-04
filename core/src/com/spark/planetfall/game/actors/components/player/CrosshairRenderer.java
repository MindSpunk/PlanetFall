package com.spark.planetfall.game.actors.components.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.spark.planetfall.game.actors.Controlled;
import com.spark.planetfall.game.actors.Player;

public class CrosshairRenderer {

    private Controlled player;

    private Color CANT_FIRE;
    private Color CAN_FIRE;
    private Color HEALTH;
    private Color SHIELD;
    private Color STATUS_BAR;

    private boolean canFire;

    public final ShapeRenderer renderer;

    public CrosshairRenderer(Player player) {
        this.player = player;
        this.renderer = new ShapeRenderer();
        this.CANT_FIRE = new Color(1, 1, 1, 1f);
        this.CAN_FIRE = new Color(1, 0, 0, 1f);
        this.HEALTH = new Color(0,1,0,1f);
        this.SHIELD = new Color(0,0,1,1f);
        this.STATUS_BAR = new Color(1f,0.5f,1f,1f);
        this.canFire = true;
    }

    public void draw(float angle) {

        Vector2 place = new Vector2(0, player.getWeaponController().weapons.getSelected().action().getBarrel());
        place.setAngle(angle + 90);
        place.add(player.getTransform().position);

        renderer.setProjectionMatrix(player.getStage().getCamera().combined);
        renderer.begin(ShapeType.Line);

        if (this.player.getWeaponController().weapons.getSelected().mechanism().canFire() && this.player.getWeaponController().weapons.getSelected().reload().canFire() && this.player.getMovement().getShootCooldown() <= 0) {
            this.canFire = false;
        } else {
            this.canFire = true;
        }


        Vector2 temp = new Vector2(0, 30);
        temp.setAngle(angle + player.getWeaponController().weapons.getSelected().recoil().getCOF() + 90);
        temp.add(player.getTransform().position);
        if (this.canFire) {
            renderer.setColor(this.CAN_FIRE);
        } else {
            renderer.setColor(this.CANT_FIRE);
        }
        renderer.line(place, temp);

        //DRAW BACK
        temp.sub(player.getTransform().position);
        temp.clamp(0f, temp.len() - 10f);
        temp.add(player.getTransform().position);
        renderer.setColor(this.STATUS_BAR);
        renderer.line(place,temp);

        //DRAW BAR
        temp.sub(player.getTransform().position);
        temp.clamp(0f, temp.len()*(player.getHealth().health/player.getHealth().maxHealth));
        temp.add(player.getTransform().position);
        renderer.setColor(this.HEALTH);
        renderer.line(place,temp);


        temp = new Vector2(0, 30);
        temp.setAngle(angle - player.getWeaponController().weapons.getSelected().recoil().getCOF() + 90);
        temp.add(player.getTransform().position);
        if (this.canFire) {
            renderer.setColor(this.CAN_FIRE);
        } else {
            renderer.setColor(this.CANT_FIRE);
        }
        renderer.line(place, temp);

        //DRAW BACK
        temp.sub(player.getTransform().position);
        temp.clamp(0f, temp.len() - 10f);
        temp.add(player.getTransform().position);
        renderer.setColor(this.STATUS_BAR);
        renderer.line(place,temp);

        //DRAW BAR
        temp.sub(player.getTransform().position);
        temp.clamp(0f, temp.len()*(player.getHealth().shields/player.getHealth().maxShields));
        temp.add(player.getTransform().position);
        renderer.setColor(this.SHIELD);
        renderer.line(place,temp);


        renderer.end();


    }

    public void setControlled(Controlled set) {
        this.player = set;
    }

}
