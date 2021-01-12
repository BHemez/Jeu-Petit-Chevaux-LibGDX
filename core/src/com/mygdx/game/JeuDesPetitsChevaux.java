package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.loader.JdpcAssetManager;
import com.mygdx.game.view.EndScreen;
import com.mygdx.game.view.LoadingScreen;
import com.mygdx.game.view.MainScreen;
import com.mygdx.game.view.MenuScreen;
import com.mygdx.game.view.PreferencesScreen;

/**
 * The JeuDesPetitsChevaux class is the principal class
 * of the app. It load and manage every screens and assets.
 */
public class JeuDesPetitsChevaux extends Game {

	/**
	 * Preferences handler of the app (ex : SoundVolume)
	 */
	public AppPreferences preferences;
	/**
	 * JdpcAssetManager allow to load assets and manage memory.
	 */
    public JdpcAssetManager assetManager = new JdpcAssetManager();
    
	/**
	 * Every screens we will use in the app.
	 */
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    public MenuScreen menuScreen;
    public MainScreen mainScreen;
    private EndScreen endScreen;

	/**
	 * int to keep track of desired screen.
	 */
    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION_2P = 2;
    public final static int APPLICATION_3P = 3;
    public final static int APPLICATION_4P = 4;
    public final static int RESUME = 5;
    public final static int ENDGAME = 6;

	/**
	 * changeScreen(int) used to set the current screen to an other one
	 * wich is created if needed.
	 */
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
	
	/**
	 * create() is called when the Application is first created.
	 * Load the preferences and create a new loadingScreen before 
	 * setting it as the current screen.
	 */
    @Override
    public void create() {
    	preferences = new AppPreferences();
    	loadingScreen = new LoadingScreen(this);
    	this.setScreen(loadingScreen);
    }
	
}