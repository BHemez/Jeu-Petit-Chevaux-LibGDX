package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JeuDesPetitsChevaux;
import com.mygdx.game.OrthogonalTiledMapRendererWithSprites;
import com.mygdx.game.controller.MouseKeyboardController;

public class MainScreen extends ScreenAdapter/* implements InputProcessor*/{
	
	private JeuDesPetitsChevaux parent;
	private MouseKeyboardController controller;
	
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
	
	public MainScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
		
    	//float w = Gdx.graphics.getWidth();
        //float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        
        viewport = new FitViewport(272,272,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        
        //camera.setToOrtho(false,272,272);
        //camera.update();
        
        //AJOUT DU CONTROLLEUR
        controller = new MouseKeyboardController();
        
        //=== CHARGEMENT DES TEXTURES ===
		parent.assetManager.queueAddPions();
		// tells the asset manager to load the images and wait until finsihed loading.
		parent.assetManager.manager.finishLoading();
		// gets the images as a texture
		texturePionRouge1 = parent.assetManager.manager.get("pions/Rouge1.png");
        texturePionRouge2 = parent.assetManager.manager.get("pions/Rouge2.png");
        texturePionVert1 = parent.assetManager.manager.get("pions/Vert1.png");
        texturePionVert2 = parent.assetManager.manager.get("pions/Vert2.png");
        texturePionPourpre1 = parent.assetManager.manager.get("pions/Pourpre1.png");
        texturePionPourpre2 = parent.assetManager.manager.get("pions/Pourpre2.png");
        texturePionBleu1 = parent.assetManager.manager.get("pions/Bleu1.png");
        texturePionBleu2 = parent.assetManager.manager.get("pions/Bleu2.png");
		
		
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
        
        
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);	
	}
	
	@Override
    public void render(float delta) {

        //Gdx.input.setInputProcessor(this);
		
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
    	if(controller.left){
    		camera.translate(-3,0);
    	}else if(controller.right){
    		camera.translate(3,0);
    	}else if(controller.up){
    		camera.translate(0,3);
    	}else if(controller.down){
    		camera.translate(0,-3);
    	}else if(controller.escape){
    		controller.escape = false;
            parent.changeScreen(0);
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
 
}
