package com.mygdx.game.view;

import java.util.ArrayList;

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
	
	public JeuDesPetitsChevaux parent;
	private MouseKeyboardController controller;
	
    public OrthographicCamera camera;
    public GameMap gameMap;
    public ArrayList<Pawn> pawnList = new ArrayList<Pawn>();
    
    public Sprite spriteDice;
    
    public Viewport viewport;
    
    public JdpcSystem system;
    
    public Actor possibleMove;
    public Actor selectedPawn;
    public Label playerLabel;
    public Sprite playerIcon;
    public Label diceLabel;
    public Sprite nextTurnButton;
   
    public TextureAtlas diceAtlas;
    public TextureAtlas playerIconAtlas;
    public TextureAtlas buttonAtlas;
    
    public Stage stage;
    public Skin skin;
    
    private int draggedID = 0;
    private int downOnID = 0;
	
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
		playerIconAtlas = parent.assetManager.manager.get("playerIcon/playerIcon.pack");
		buttonAtlas = parent.assetManager.manager.get("button/button.pack");
		
        //=== CREATION DES SPRITES ===
		pawnList.add(new Pawn(this.parent, 1,1,"Rouge1",0,55, new float[]{5,5}, GameMap.REDLADDERPOSITIONMATRIX));
		pawnList.add(new Pawn(this.parent, 1,2,"Rouge2",0,55, new float[]{5,3}, GameMap.REDLADDERPOSITIONMATRIX));
		pawnList.add(new Pawn(this.parent, 2,1,"Bleu1",14,13, new float[]{5,13}, GameMap.BLUELADDERPOSITIONMATRIX));
		pawnList.add(new Pawn(this.parent, 2,2,"Bleu2",14,13, new float[]{5,15}, GameMap.BLUELADDERPOSITIONMATRIX));
		pawnList.add(new Pawn(this.parent, 3,1,"Pourpre1",28,27, new float[]{13,13}, GameMap.PURPLELADDERPOSITIONMATRIX));
		pawnList.add(new Pawn(this.parent, 3,2,"Pourpre2",28,27, new float[]{13,15}, GameMap.PURPLELADDERPOSITIONMATRIX));
		pawnList.add(new Pawn(this.parent, 4,1,"Vert1",42,41, new float[]{13,5}, GameMap.GREENLADDERPOSITIONMATRIX));
		pawnList.add(new Pawn(this.parent, 4,2,"Vert2",42,41, new float[]{13,3}, GameMap.GREENLADDERPOSITIONMATRIX));

        spriteDice = new Sprite(diceAtlas.findRegion("Dice6"));
        playerIcon = new Sprite(playerIconAtlas.findRegion("RedPlayer"));
        nextTurnButton = new Sprite(buttonAtlas.findRegion("NextTurnButton"));
        
        //=== AJOUT DES SPRITES SUR LA CARTE ===
        for(Pawn p : pawnList) {
        	gameMap.tiledMapRenderer.addSprite(p.spritePion);
        }
        gameMap.tiledMapRenderer.addSprite(spriteDice);
        gameMap.tiledMapRenderer.addSprite(playerIcon);
        gameMap.tiledMapRenderer.addSprite(nextTurnButton);
        
        //=== POSITIONNEMENT DES SPRITES SUR LA CARTE ===
        for(Pawn p : pawnList) {
        	p.setToStablePosition();
        }
        spriteDice.setPosition(320,176);
        playerIcon.setPosition(304, 240);
        nextTurnButton.setPosition(304, 112);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
		
        stage = new Stage(this.viewport);
        
        playerLabel = new Label("Player", skin);
        diceLabel = new Label("Throw", skin);
	
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
        
        playerLabel.setPosition(312, 224);
        playerLabel.setSize(32, 16);
        playerLabel.setFontScale(0.6f);
        playerLabel.setAlignment(Align.center);
        
        diceLabel.setPosition(302, 158);
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
        
        //KEYBOARD
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
        
    	//MOUSE-DOWN
    	if(controller.isMouse1Down){
            Vector3 clickCoordinates = new Vector3(controller.mouseLocation,0);
            Vector3 position = viewport.unproject(clickCoordinates);

            if(spriteDice.getBoundingRectangle().contains(position.x, position.y)) {
                this.system.throwDice();
            }
            
            if(nextTurnButton.getBoundingRectangle().contains(position.x, position.y)) {
            	this.system.changeTurn();
            }

            if(this.system.moveDone == false && this.system.diceThrown) {
            	boolean pFound = false;
            	
            	for(Pawn p : pawnList) {
            		if(p.spritePion.getBoundingRectangle().contains(position.x, position.y) && p.team == system.playerTurn && !pFound && (this.downOnID == p.id || this.downOnID == 0) ) {
            			system.findPossibleMove(p, true, false);
            			pFound = true;
            			downOnID = p.id;
            		}
            	}
            	
            	if(!pFound) {
            		system.unShowPossibleMove();
            	}
            	
            }
            
    	} else {
    		downOnID = 0;
    	}
    	
    	//IS DRAGGED
    	if(controller.isDragged) {
    		 Vector3 dragCoordinates = new Vector3(controller.mouseLocation,0);
             Vector3 position = viewport.unproject(dragCoordinates);
             if(this.system.moveDone == false && this.system.diceThrown) {
             	for(Pawn p : pawnList) {
             		if(p.spritePion.getBoundingRectangle().contains(position.x, position.y) && p.team == system.playerTurn && (this.draggedID == p.id || this.draggedID == 0)) {
             			p.setPosition(position.x-(GameMap.TILESIZE/2), position.y-(GameMap.TILESIZE/2));
             			this.draggedID = p.id;
             		}
             	}   
             }

    	} else {
    	//IS NOT DRAGGED
    		this.draggedID = 0;
         	for(Pawn p : pawnList) { 
         		this.system.findPossibleMove(p, false, true);
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
    	for(Pawn p : pawnList) { p.dispose(); }
    	nextTurnButton.getTexture().dispose();
    	spriteDice.getTexture().dispose();
    	playerIcon.getTexture().dispose();
    	parent.assetManager.manager.dispose();
    }
    
    @Override
    public void resize(int width, int height){
       viewport.update(width,height);
       camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }
    
}
