package com.mygdx.autofarm;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.autofarm.AutoFarm;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Automated Farming");
		config.setWindowedMode(1280, 720);
		config.useVsync(true);
		config.setResizable(false);
		new Lwjgl3Application(new AutoFarm(), config);
	}
}
