package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.loader.jdpcAssetManager;
import com.mygdx.game.view.EndScreen;
import com.mygdx.game.view.MainScreen;
import com.mygdx.game.view.MenuScreen;
import com.mygdx.game.view.PreferencesScreen;

public class JeuDesPetitsChevaux extends Game /*implements InputProcessor*/ {
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    Texture texturePionRouge1;
    Texture texturePionRouge2;
    Texture texturePionVert1;
    Texture texturePionVert2;
    Texture texturePionPourpre1;
    Texture texturePionPourpre2;
    Texture texturePionBleu1;
    Texture texturePionBleu2;
    Sprite spritePionRouge1;
    Sprite spritePionRouge2;
    Sprite spritePionVert1;
    Sprite spritePionVert2;
    Sprite spritePionPourpre1;
    Sprite spritePionPourpre2;
    Sprite spritePionBleu1;
    Sprite spritePionBleu2;
    
    Viewport viewport;
    
    private AppPreferences preferences;
    public jdpcAssetManager assetManager = new jdpcAssetManager();
    
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private EndScreen endScreen;

    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;

public void changeScreen(int screen){
	System.out.print("CHANGEMENT D'ECRAN : ");
	switch(screen){
		case MENU:
			if(menuScreen == null) menuScreen = new MenuScreen(this);
			this.setScreen(menuScreen);
	    	System.out.println("MENU-SCREEN");
			break;
		case PREFERENCES:
			if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
			this.setScreen(preferencesScreen);
			System.out.println("PREFERENCE-SCREEN");
			break;
		case APPLICATION:
			if(mainScreen == null) mainScreen = new MainScreen(this);
			this.setScreen(mainScreen);
			System.out.println("MAIN-SCREEN");
			break;
		case ENDGAME:
			if(endScreen == null) endScreen = new EndScreen(this);
			this.setScreen(endScreen);
			System.out.println("END-SCREEN");
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