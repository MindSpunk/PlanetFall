package com.spark.planetfall.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.spark.planetfall.game.PlanetFallClient;
import com.spark.planetfall.game.PlanetFallServer;
import com.spark.planetfall.utils.Log;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.samples = 2;
        config.vSyncEnabled = true;


        try {
            //CHECK FOR TYPE ARGUMENTS
            if (arg[0].equals("client")) {
                Log.logInfo("Staring client");
                config.title = "PlanetFall - Client";
                try {
                    if (arg[1] != null && arg[2] != null) {
                        //CHECK FOR RESOLUTION ARGUMENTS
                        Log.logInfo("Found resolution arguments");
                        int a = Integer.parseInt(arg[1]);
                        int b = Integer.parseInt(arg[2]);
                        Log.logInfo("Resolution: " + a + "x" + b);
                        config.width = a;
                        config.height = b;
                        config.vSyncEnabled = true;

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //HANDLE NO RESOLUTION ARGUMENTS
                    Log.logInfo("No resolution arguments, defaulting to native");
                    config.width = 1280;
                    config.height = 720;
                    config.vSyncEnabled = true;
                }

                try {

                    if (arg[3] != null) {
                        //CHECK FOR FULLSCREEN ARGUMENTS
                        Log.logInfo("Fullscreen argument found");
                        boolean c = Boolean.parseBoolean(arg[3]);
                        Log.logInfo("Fullscreen: " + arg[3]);
                        config.fullscreen = c;

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //HANDLE NO FULLSCREEN ARGUMENTS
                    Log.logInfo("No fullscreen argument found");
                    config.fullscreen = false;
                }
                Log.logInfo("Instancing PlanetFall()");
                new LwjglApplication(new PlanetFallClient(), config);
            } else if (arg[0].equals("server")) {
                Log.logInfo("Starting Server");
                config.title = "PlanetFall - Server";
                config.width = 600;
                config.height = 400;
                config.fullscreen = false;
                new LwjglApplication(new PlanetFallServer(), config);
            } else {
                for (int i = 0; i <= arg.length; i++) {
                    Log.logWarn("ILLEGAL ARGUMENT AT INDEX: " + i + " " + arg[i]);
                }
                throw new ArrayIndexOutOfBoundsException();
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            //HANDLE NO ARGUMENTS FOUND
            new LwjglApplication(new PlanetFallClient(), config);
            config.width = 0;
            config.height = 0;
            config.fullscreen = true;
            config.vSyncEnabled = true;
        }
    }
}
