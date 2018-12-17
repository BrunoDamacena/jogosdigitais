package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import game.YouRock;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "You Rock!";
        config.width = YouRock.SCREEN_WIDTH;
        config.height = YouRock.SCREEN_HEIGHT;
        config.resizable = false;
        new LwjglApplication(new YouRock(), config);
    }
}
