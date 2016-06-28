package com.spark.planetfall.game.actors.remote;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.spark.planetfall.game.actors.HitScan;
import com.spark.planetfall.game.actors.Player;
import com.spark.planetfall.game.actors.components.Transform;
import com.spark.planetfall.game.constants.Constant;

public class RemoteBullet extends Actor {

    private final Transform position;
    private final Vector2 velocity;
    private final Color color;

    protected final ShapeRenderer render;
    protected float timer;
    protected final World world;
    protected final Matrix4 matrix;
    protected Vector2 previousPosition;

    public RemoteBullet(Vector2 position, Vector2 velocity, World world, int color, Stage stage, Player player) {

        this.position = new Transform();
        this.position.position = position.cpy();
        this.previousPosition = position.cpy();
        this.velocity = velocity;
        this.color = new Color(color);
        this.world = world;
        this.matrix = stage.getCamera().combined;

        this.render = player.crosshair.renderer;
        this.timer = Constant.WEAPON_BULLET_LIFE_TIME;

    }

    @Override
    public void act(float delta) {

        this.previousPosition = this.position.position.cpy();

        Vector2 temp = velocity.cpy();
        temp.clamp(0, temp.len() * delta);

        temp.add(position.position);
        HitScan hitscan = new HitScan();
        this.world.rayCast(hitscan, position.position, temp);

        position.position = temp.cpy();


        if (hitscan.closestHit != null) {
            this.remove();
            this.position.position = hitscan.hitLocation;
        }

        this.timer -= delta;

        if (this.timer <= 0) {
            this.remove();
        }

    }

    @Override
    public void draw(Batch batch, float alpha) {

        if (render != null) {
            batch.end();
            render.setColor(color);
            render.setProjectionMatrix(matrix);
            render.begin(ShapeType.Line);
            Vector2 temp = this.position.position.cpy();
            temp.sub(this.previousPosition);
            temp.clamp(0, 3);
            render.line(this.previousPosition, this.position.position.cpy().sub(temp));
            render.end();
            batch.begin();
        }

    }

}
