package com.spark.planetfall.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SparkMath {

    public static float pointDirection(float fromX, float toX, float fromY, float toY) {

        double angle;

        angle = Math.atan2(toY - fromY, toX - fromX);
        angle = Math.toDegrees(angle);

        return (float) angle;

    }

    public static int randInt(int max) {

        float ret = max;

        ret *= Math.random();

        return (int) ret;

    }

    public static float randRange(float min, float max) {

        float range = max - min;

        range *= Math.random();

        range += min;

        return range;

    }

    public static Vector2 getMousePosition(Stage stage) {

        return new Vector2(stage.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x, stage.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y);

    }

    public static float clamp(float value, float min, float max) {

        if (value > max) {
            value = max;
        }

        if (value < min) {
            value = min;
        }

        return value;

    }

}

