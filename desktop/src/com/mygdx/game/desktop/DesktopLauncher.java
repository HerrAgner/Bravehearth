package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.BravehearthGame;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.entities.monsters.DummyMonster;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) GameConfig.WIDTH;
		config.height = (int) GameConfig.HEIGHT;
	//	config.fullscreen = GameConfig.FULLSCREEN;
		config.foregroundFPS = GameConfig.FPS_30;
		config.forceExit = GameConfig.FORCE_EXIT_FALSE;
		new LwjglApplication(new BravehearthGame(), config);
	}
}
