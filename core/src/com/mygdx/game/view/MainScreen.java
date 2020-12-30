package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JeuDesPetitsChevaux;
import com.mygdx.game.controller.MouseKeyboardController;
import com.mygdx.game.system.GameMap;
import com.mygdx.game.system.JdpcSystem;
import com.mygdx.game.system.Pawn;
import com.mygdx.game.system.PossibleMove;

public class MainScreen extends ScreenAdapter {
	
	private JeuDesPetitsChevaux parent;
	private MouseKeyboardController controller;
	
    OrthographicCamera camera;
    public GameMap gameMap;
    
    Pawn rouge1;
    Pawn rouge2;
    Pawn vert1;
    Pawn vert2;
    Pawn pourpre1;
    Pawn pourpre2;
    Pawn bleu1;
    Pawn bleu2;
    
    Sprite spriteDice;
    
    Viewport viewport;
    
    TextureAtlas diceAtlas;
    
    JdpcSystem system;
    
    public Actor possibleMove;
    public Actor selectedPawn;
    public Label playerLabel;
    public Label diceLabel;
    
    public Stage stage;
    public Skin skin;
	
	public MainScreen(JeuDesPetitsChevaux jdpc) {
		this.parent = jdpc;
		
		this.skin = jdpc.assetManager.manager.get("skin/glassy-ui.json", Skin.class);
		
		this.system = new JdpcSystem(this);

    	//float w = Gdx.graphics.getWidth();
        //float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        
        viewport = new FitViewport(368,304,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        
        //AJOUT DU CONTROLLEUR
        controller = new MouseKeyboardController();
        
        //=== CHARGEMENT DE LA CARTE ===
        gameMap = new GameMap(new TmxMapLoader().load("carte.tmx"), 16);
        
        //=== CHARGEMENT DES TEXTURES ===
        diceAtlas = parent.assetManager.manager.get("dice/dice.pack", TextureAtlas.class);
		
        //=== CREATION DES SPRITES ===
        rouge1 = new Pawn(this.parent,"Rouge1",0,55, new float[]{5,5}, GameMap.REDLADDERPOSITIONMATRIX);
        rouge2 = new Pawn(this.parent,"Rouge2",0,55, new float[]{5,3}, GameMap.REDLADDERPOSITIONMATRIX);
        bleu1 = new Pawn(this.parent,"Bleu1",14,13, new float[]{5,13}, GameMap.BLUELADDERPOSITIONMATRIX);
        bleu2 = new Pawn(this.parent,"Bleu2",14,13, new float[]{5,15}, GameMap.BLUELADDERPOSITIONMATRIX);
        pourpre1 = new Pawn(this.parent,"Pourpre1",28,27, new float[]{13,13}, GameMap.PURPLELADDERPOSITIONMATRIX);
        pourpre2 = new Pawn(this.parent,"Pourpre2",28,27, new float[]{13,15}, GameMap.PURPLELADDERPOSITIONMATRIX);
        vert1 = new Pawn(this.parent,"Vert1",42,41, new float[]{13,5}, GameMap.GREENLADDERPOSITIONMATRIX);
        vert2 = new Pawn(this.parent,"Vert2",42,41, new float[]{13,3}, GameMap.GREENLADDERPOSITIONMATRIX);
   
        spriteDice = new Sprite(diceAtlas.findRegion("Dice6"));
        
        //=== AJOUT DES SPRITES SUR LA CARTE ===
        gameMap.tiledMapRenderer.addSprite(rouge1.spritePion);
        gameMap.tiledMapRenderer.addSprite(rouge2.spritePion);
        gameMap.tiledMapRenderer.addSprite(vert1.spritePion);
        gameMap.tiledMapRenderer.addSprite(vert2.spritePion);
        gameMap.tiledMapRenderer.addSprite(bleu1.spritePion);
        gameMap.tiledMapRenderer.addSprite(bleu2.spritePion);
        gameMap.tiledMapRenderer.addSprite(pourpre1.spritePion);
        gameMap.tiledMapRenderer.addSprite(pourpre2.spritePion);
        gameMap.tiledMapRenderer.addSprite(spriteDice);
        
        //=== POSITIONNEMENT DES SPRITES SUR LA CARTE ===
        rouge1.setToStablePosition();
        rouge2.setToStablePosition();
        vert1.setToStablePosition();
        vert2.setToStablePosition();
        pourpre1.setToStablePosition();
        pourpre2.setToStablePosition();
        bleu1.setToStablePosition();
        bleu2.setToStablePosition();
        spriteDice.setPosition(320,176);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
		
        stage = new Stage(this.viewport);
        
        playerLabel = new Label("Joueur 1", skin);
        diceLabel = new Label("Jetez", skin);
	
		TextureAtlas atlas = parent.assetManager.manager.get("possibleMove/possibleMove.pack", TextureAtlas.class);
		
        Animation<AtlasRegion> anim1 = new Animation<AtlasRegion>(0.05f, atlas.findRegions("PossibleMove") );
        anim1.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        possibleMove = new PossibleMove(anim1);
        
        Animation<AtlasRegion> anim2 = new Animation<AtlasRegion>(0.05f, atlas.findRegions("PossibleMove") );
        anim2.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        selectedPawn = new PossibleMove(anim2);
        
        stage.addActor(playerLabel);
        stage.addActor(diceLabel);
        stage.addActor(possibleMove);
        stage.addActor(selectedPawn);
        
        
        
        playerLabel.setPosition(306, 256);
        playerLabel.setSize(32, 16);
        playerLabel.setFontScale(0.6f);
        
        diceLabel.setPosition(306, 158);
        diceLabel.setFontScale(0.6f);
        diceLabel.setAlignment(Align.center);
        

        
    	this.possibleMove.setVisible(false);
    	this.selectedPawn.setVisible(false);
        
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
            parent.changeScreen(JeuDesPetitsChevaux.MENU);
    	}else if(controller.spacebar) {
    		this.system.changeTurn();
    	}
        
    	if(controller.isMouse1Down){
            Vector3 clickCoordinates = new Vector3(controller.mouseLocation,0);
            Vector3 position = camera.unproject(clickCoordinates);
            // TO-DO : VERIFIER LA SELECTION, GARDER LE PION SUR LE CURSEUR, REPOSER LE PION SUR CASE VALIDE
            if(spriteDice.getBoundingRectangle().contains(position.x, position.y)) {
            	if(!this.system.diceThrown) {
                	this.system.throwDice();
            	}
            }

            if(this.system.moveDone == false) {
                if(rouge1.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 1) {
                	system.showPossibleMove(rouge1);
                }  else if(rouge2.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 1) {
                	system.showPossibleMove(rouge2);
                }  else if(bleu1.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 2) {
                	system.showPossibleMove(bleu1);
                }  else if(bleu2.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 2) {
                	system.showPossibleMove(bleu2);
                }  else if(pourpre1.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 3) {
                	system.showPossibleMove(pourpre1);
                }  else if(pourpre2.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 3) {
                	system.showPossibleMove(pourpre2);
                }  else if(vert1.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 4) {
                	system.showPossibleMove(vert1);
                }  else if(vert2.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 4) {
                	system.showPossibleMove(vert2);
                } else {
                	system.unShowPossibleMove();
                }
            }

    	}
    	
    	if(controller.isDragged) {
    		 Vector3 dragCoordinates = new Vector3(controller.mouseLocation,0);
             Vector3 position = camera.unproject(dragCoordinates);
             if(this.system.moveDone == false) {
                 if(rouge1.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 1) {
                 	rouge1.setPosition(position.x-8, position.y-8);//-8 pour centrer le sprite 16*16 sur la sourie
                  }  else if(rouge2.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 1) {
                 	rouge2.setPosition(position.x-8, position.y-8);
                  }  else if(bleu1.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 2) {
                 	bleu1.setPosition(position.x-8, position.y-8);
                  }  else if(bleu2.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 2) {
                 	bleu2.setPosition(position.x-8, position.y-8);
                  }  else if(pourpre1.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 3) {
                 	pourpre1.setPosition(position.x-8, position.y-8);
                  }  else if(pourpre2.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 3) {
                  	pourpre2.setPosition(position.x-8, position.y-8);
                  }  else if(vert1.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 4) {
                  	vert1.setPosition(position.x-8, position.y-8);
                  }  else if(vert2.spritePion.getBoundingRectangle().contains(position.x, position.y) && system.playerTurn == 4) {
                  	vert2.setPosition(position.x-8, position.y-8);
                  }
             }

    	} else {
    		switch(system.playerTurn) {
    		case 1:
        		this.system.setToCorrectPosition(rouge1);
        		this.system.setToCorrectPosition(rouge2);
        		break;
        		
    		case 2:
        		this.system.setToCorrectPosition(bleu1);
        		this.system.setToCorrectPosition(bleu2);
        		break;
    		
    		case 3:
	    		this.system.setToCorrectPosition(pourpre1);
	    		this.system.setToCorrectPosition(pourpre2);
	    		break;
	    		
    		case 4:
	    		this.system.setToCorrectPosition(vert1);
	    		this.system.setToCorrectPosition(vert2);
	    		break;
    		}
    	}
    	
        camera.update();
        gameMap.tiledMapRenderer.setView(camera);
        gameMap.tiledMapRenderer.render();
        
        stage.act();
        stage.draw();
    }
	
    @Override
    public void dispose(){
    	rouge1.dispose();
    	rouge2.dispose();
    	vert1.dispose();
    	vert2.dispose();
    	pourpre1.dispose();
    	pourpre2.dispose();
    	bleu1.dispose();
    	bleu2.dispose();
    	spriteDice.getTexture().dispose();
    	parent.assetManager.manager.dispose();
    }
    
    @Override
    public void resize(int width, int height){
       viewport.update(width,height);
       camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

	public JeuDesPetitsChevaux getParent() {
		return parent;
	}

	public void setParent(JeuDesPetitsChevaux parent) {
		this.parent = parent;
	}

	public MouseKeyboardController getController() {
		return controller;
	}

	public void setController(MouseKeyboardController controller) {
		this.controller = controller;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public Sprite getSpriteDice() {
		return spriteDice;
	}

	public void setSpriteDice(Sprite spriteDice) {
		this.spriteDice = spriteDice;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	public TextureAtlas getDiceAtlas() {
		return diceAtlas;
	}

	public void setDiceAtlas(TextureAtlas diceAtlas) {
		this.diceAtlas = diceAtlas;
	}

	public JdpcSystem getSystem() {
		return system;
	}

	public void setSystem(JdpcSystem system) {
		this.system = system;
	}
    
    
}
