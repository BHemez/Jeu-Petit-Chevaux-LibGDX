package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    public void create () {
    	preferences = new AppPreferences();
    	loadingScreen = new LoadingScreen(this);
    	System.out.println("LOADING-SCREEN");
    	this.setScreen(loadingScreen);

    	/*
    	//float w = Gdx.graphics.getWidth();
        //float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        
        viewport = new FitViewport(272,272,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        
        //camera.setToOrtho(false,272,272);
        //camera.update();
        
        
        //=== CHARGEMENT DES TEXTURES ===
        //texture = new Texture(Gdx.files.internal("pikachu.png"));
        texturePionRouge1 = new Texture(Gdx.files.internal("Rouge1.png"));
        texturePionRouge2 = new Texture(Gdx.files.internal("Rouge2.png"));
        texturePionVert1 = new Texture(Gdx.files.internal("Vert1.png"));
        texturePionVert2 = new Texture(Gdx.files.internal("Vert2.png"));
        texturePionPourpre1 = new Texture(Gdx.files.internal("Pourpre1.png"));
        texturePionPourpre2 = new Texture(Gdx.files.internal("Pourpre2.png"));
        texturePionBleu1 = new Texture(Gdx.files.internal("Bleu1.png"));
        texturePionBleu2 = new Texture(Gdx.files.internal("Bleu2.png"));
        
        //=== CHARGEMENT DES SPRITES ===
        //sprite = new Sprite(texture);
        spritePionRouge1 = new Sprite(texturePionRouge1);
        spritePionRouge2 = new Sprite(texturePionRouge2);
        spritePionVert1 = new Sprite(texturePionVert1);
        spritePionVert2 = new Sprite(texturePionVert2);
        spritePionPourpre1 = new Sprite(texturePionPourpre1);
        spritePionPourpre2 = new Sprite(texturePionPourpre2);
        spritePionBleu1 = new Sprite(texturePionBleu1);
        spritePionBleu2 = new Sprite(texturePionBleu2);
        
        //== CHARGEMENT DE LA CARTE ===
        tiledMap = new TmxMapLoader().load("carte.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        
        //== AJOUT DES SPRITES SUR LA CARTE ===
        //tiledMapRenderer.addSprite(sprite);
        tiledMapRenderer.addSprite(spritePionRouge1);
        tiledMapRenderer.addSprite(spritePionRouge2);
        tiledMapRenderer.addSprite(spritePionVert1);
        tiledMapRenderer.addSprite(spritePionVert2);
        tiledMapRenderer.addSprite(spritePionPourpre1);
        tiledMapRenderer.addSprite(spritePionPourpre2);
        tiledMapRenderer.addSprite(spritePionBleu1);
        tiledMapRenderer.addSprite(spritePionBleu2);
        
        Gdx.input.setInputProcessor(this);
        */
    }
/*    
    @Override
    public void render () {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

    }
    
    @Override
    public void dispose(){

    	//sprite.getTexture().dispose();
    	spritePionRouge1.getTexture().dispose();
    	spritePionRouge2.getTexture().dispose();
    	spritePionVert1.getTexture().dispose();
    	spritePionVert2.getTexture().dispose();
    	spritePionPourpre1.getTexture().dispose();
    	spritePionPourpre2.getTexture().dispose();
    	spritePionBleu1.getTexture().dispose();
    	spritePionBleu2.getTexture().dispose();

    }
    @Override
    public void resize(int width, int height){

       viewport.update(width,height);
       camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

    }
    
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.LEFT)
            camera.translate(-32,0);
        if(keycode == Input.Keys.RIGHT)
            camera.translate(32,0);
        if(keycode == Input.Keys.UP)
            camera.translate(0,32);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0,-32);
        if(keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if(keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        if(keycode == Input.Keys.NUM_3)
            tiledMap.getLayers().get(2).setVisible(!tiledMap.getLayers().get(2).isVisible());
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        // TO-DO : VERIFIER LA SELECTION, GARDER LE PION SUR LE CURSEUR, REPOSER LE PION SUR CASE VALIDE
        //sprite.setPosition(position.x, position.y);
        return true;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    */

    public AppPreferences getPreferences() {
    	return this.preferences;
    	}
    
}