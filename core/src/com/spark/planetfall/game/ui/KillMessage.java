package com.spark.planetfall.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.spark.planetfall.utils.SparkUtils;

public class KillMessage extends Actor {

    public Label label;
    public Color alpha;

    public float slideRate;
    public float alphaDropRate;
    public Window window;

    public KillMessage(String name, Stage stage) {

        label = new Label("+100 " + name + " killed", GUISkin.get());

        float[] xy = SparkUtils.getScreenCenter();

        this.window = new Window("",GUISkin.get());
        window.setPosition(500,500);

        window.addActor(label);
        alpha = new Color(1,1,1,1);
        slideRate = 0.5f;
        alphaDropRate = 0.2f;
        stage.addActor(window);


    }

    @Override
    public void act(float delta) {

        //label.setPosition(label.getX(),label.getY()-(0.5f * delta));
        /*
        if (alpha.a <= 0) {
            this.remove();
            this.label.remove();
        }
        */

    }

}
