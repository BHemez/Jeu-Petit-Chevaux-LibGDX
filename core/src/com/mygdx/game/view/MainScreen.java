package com.mygdx.game.view;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JeuDesPetitsChevaux;
import com.mygdx.game.OrthogonalTiledMapRendererWithSprites;
import com.mygdx.game.controller.MouseKeyboardController;

public class MainScreen extends ScreenAdapter {
	
	private JeuDesPetitsChevaux parent;
	private MouseKeyboardController controller;
	
    TiledMap tiledMap;
    OrthographicCamera camera;
    OrthogonalTiledMapRendererWithSprites tiledMapRenderer;
    
    Sprite spritePionRouge1;
    Sprite spritePionRouge2;
    Sprite spritePionVert1;
    Sprite spritePionVert2;
    Sprite spritePionPourpre1;
    Sprite spritePionPourpre2;
    Sprite spritePionBleu1;
    Sprite spritePionBleu2;
    Sprite spriteDice;
    
    Viewport viewport;
    
    TextureAtlas pionsAtlas;
    TextureAtlas diceAtlas;
    
    int diceValue = 6;
	
	public MainScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
		
    	//float w = Gdx.graphics.getWidth();
        //float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        
        viewport = new FitViewport(368,304,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        
        //AJOUT DU CONTROLLEUR
        controller = new MouseKeyboardController();
        
        //=== CHARGEMENT DES TEXTURES ===
        pionsAtlas = parent.assetManager.manager.get("pions/pions.pack", TextureAtlas.class);
        diceAtlas = parent.assetManager.manager.get("dice/dice.pack", TextureAtlas.class);
		
        //=== CREATION DES SPRITES ===
        spritePionRouge1 = new Sprite(pionsAtlas.findRegion("Rouge1"));
        spritePionRouge2 = new Sprite(pionsAtlas.findRegion("Rouge2"));
        spritePionVert1 = new Sprite(pionsAtlas.findRegion("Vert1"));
        spritePionVert2 = new Sprite(pionsAtlas.findRegion("Vert2"));
        spritePionPourpre1 = new Sprite(pionsAtlas.findRegion("Pourpre1"));
        spritePionPourpre2 = new Sprite(pionsAtlas.findRegion("Pourpre2"));
        spritePionBleu1 = new Sprite(pionsAtlas.findRegion("Bleu1"));
        spritePionBleu2 = new Sprite(pionsAtlas.findRegion("Bleu2"));
        spriteDice = new Sprite(diceAtlas.findRegion("Dice6"));
        
        //=== CHARGEMENT DE LA CARTE ===
        tiledMap = new TmxMapLoader().load("carte.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);
        
        //=== AJOUT DES SPRITES SUR LA CARTE ===
        tiledMapRenderer.addSprite(spritePionRouge1);
        tiledMapRenderer.addSprite(spritePionRouge2);
        tiledMapRenderer.addSprite(spritePionVert1);
        tiledMapRenderer.addSprite(spritePionVert2);
        tiledMapRenderer.addSprite(spritePionPourpre1);
        tiledMapRenderer.addSprite(spritePionPourpre2);
        tiledMapRenderer.addSprite(spritePionBleu1);
        tiledMapRenderer.addSprite(spritePionBleu2);
        tiledMapRenderer.addSprite(spriteDice);
        
        //=== POSITIONNEMENT DES SPRITES SUR LA CARTE ===
        spritePionRouge1.setPosition(80,80);
        spritePionRouge2.setPosition(80,48);
        spritePionVert1.setPosition(208,80);
        spritePionVert2.setPosition(208,48);
        spritePionPourpre1.setPosition(208,208);
        spritePionPourpre2.setPosition(208,240);
        spritePionBleu1.setPosition(80,208);
        spritePionBleu2.setPosition(80,240);
        spriteDice.setPosition(320,176);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);	
	}
	
	@Override
    public void render(float delta) {		
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
    	if(controller.left){
    		camera.translate(-3,0);
    	}else if(controller.right) {
    		camera.translate(3,0);
    	}else if(controller.up) {
    		camera.translate(0,3);
    	}else if(controller.down) {
    		camera.translate(0,-3);
    	}else if(controller.escape) {
    		controller.escape = false;
            parent.changeScreen(0);
    	}else if(controller.spacebar) {
    		this.throwDice();
    	}
        
    	if(controller.isMouse1Down){
            Vector3 clickCoordinates = new Vector3(controller.mouseLocation,0);
            Vector3 position = camera.unproject(clickCoordinates);
            // TO-DO : VERIFIER LA SELECTION, GARDER LE PION SUR LE CURSEUR, REPOSER LE PION SUR CASE VALIDE
            spritePionRouge1.setPosition(position.x-8, position.y-8);	//-8 pour centrer le sprite 16*16 sur la sourie	//DEMO
    	}
    	
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }
    
	public int throwDice() {
		this.diceValue = new Random().nextInt(6)+1;
		spriteDice.setRegion(diceAtlas.findRegion("Dice"+Integer.toString(diceValue)));
		return diceValue;
	}
	
    @Override
    public void dispose(){
    	spritePionRouge1.getTexture().dispose();
    	spritePionRouge2.getTexture().dispose();
    	spritePionVert1.getTexture().dispose();
    	spritePionVert2.getTexture().dispose();
    	spritePionPourpre1.getTexture().dispose();
    	spritePionPourpre2.getTexture().dispose();
    	spritePionBleu1.getTexture().dispose();
    	spritePionBleu2.getTexture().dispose();
    	spriteDice.getTexture().dispose();
    	parent.assetManager.manager.dispose();
    }
    
    @Override
    public void resize(int width, int height){
       viewport.update(width,height);
       camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }
 
}
