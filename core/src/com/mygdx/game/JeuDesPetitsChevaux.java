package com.mygdx.game;

import java.awt.Dimension;

import com.badlogic.gdx.Game;
import com.mygdx.game.loader.JdpcAssetManager;
import com.mygdx.game.view.EndScreen;
import com.mygdx.game.view.LoadingScreen;
import com.mygdx.game.view.MainScreen;
import com.mygdx.game.view.MenuScreen;
import com.mygdx.game.view.PreferencesScreen;

public class JeuDesPetitsChevaux extends Game {

    private AppPreferences preferences;
    public JdpcAssetManager assetManager = new JdpcAssetManager();
    
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    public MainScreen mainScreen;
    private EndScreen endScreen;
    public Dimension dimension;

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION_2P = 2;
    public final static int APPLICATION_3P = 3;
    public final static int APPLICATION_4P = 4;
    public final static int RESUME = 5;
    public final static int ENDGAME = 6;

public JeuDesPetitsChevaux(Dimension dimension) {
		this.dimension = dimension;
	}

public void changeScreen(int screen){
	switch(screen){
		case MENU:
			if(menuScreen == null) menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
			break;
		case PREFERENCES:
			if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
			this.setScreen(preferencesScreen);
			break;
		case APPLICATION_2P:
			mainScreen = new MainScreen(this,2);
			this.setScreen(mainScreen);
			break;
		case APPLICATION_3P:
			mainScreen = new MainScreen(this,3);
			this.setScreen(mainScreen);
			break;
		case APPLICATION_4P:
			mainScreen = new MainScreen(this,4);
			this.setScreen(mainScreen);
			break;
		case RESUME:
			this.setScreen(mainScreen);
			break;
		case ENDGAME:
			if(endScreen == null) endScreen = new EndScreen(this);
			this.setScreen(endScreen);
			break;
	}
}
    
    @Override
    public void create() {
    	preferences = new AppPreferences();
    	loadingScreen = new LoadingScreen(this);
    	System.out.println("LOADING-SCREEN");
    	this.setScreen(loadingScreen);
    }

    public AppPreferences getPreferences() {
    	return this.preferences;
    	}
    
}