package com.mygdx.game.desktop;

import java.awt.Dimension;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.JeuDesPetitsChevaux;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Dimension screenDimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Jeu des petits chevaux libGDX";
		config.height = (int) (screenDimension.height*0.8f);
		config.width = (int) (screenDimension.height*0.97f);
		config.addIcon("icon/icon.png", Files.FileType.Internal);
		new LwjglApplication(new JeuDesPetitsChevaux(), config);
	}
}
