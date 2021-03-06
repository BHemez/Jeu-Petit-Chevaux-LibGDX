package com.mygdx.game.view;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
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
import com.mygdx.game.JeuDesPetitsChevaux;
import com.mygdx.game.controller.MouseKeyboardController;
import com.mygdx.game.system.GameMap;
import com.mygdx.game.system.JdpcSystem;
import com.mygdx.game.system.Pawn;

/**
 * MainScreen is the screen where the game board is displayed.
 */
public class MainScreen extends ScreenAdapter {
	
	public JeuDesPetitsChevaux parent; //Parent class of the screen.
	
	private MouseKeyboardController controller;// Controller for interactions between user and the game.
	private OrthographicCamera camera; //Create a camera to show our gameBoard
    public FitViewport viewport; //Viewport to translate camera's world unit into pixels.
    
	private GameMap gameMap; //Map containing our gameboard
    public ArrayList<Pawn> pawnList = new ArrayList<Pawn>(); //List of pawn present on the board.

    public JdpcSystem system; //Game logic
    
    //Set of actor, label and sprites to create the UI
    public Actor possibleMove;
    public Actor selectedPawn;
    public Label playerLabel;
    public Sprite playerIcon;
    public Label diceLabel;
    public Sprite spriteDice;
    public Sprite nextTurnButton;
    public Sprite menuButton;
   
    //Textures Atlas containing some assets images
    public TextureAtlas diceAtlas;
    public TextureAtlas playerIconAtlas;
    private TextureAtlas buttonAtlas;
    
    private Skin skin; //Appearence assets of the screen's UI
    
    private Stage stage; //A 2D scene graph containing hierarchies of actors.

    //ID to keep track of the object the user interact with
    private int draggedID = 0; 
    private int downOnID = 0;
    
    //Sounds the screen will use
	private Sound click;
	private Sound diceRoll;
	//Int to call the sounds
	private static final int CLICK_SOUND = 0;
	private static final int DICE_SOUND = 1;
	
	/**
	 * MainScreen's constructor.
	 * Fetch the assets, create the pawns and set up the game.
	 */
	public MainScreen(JeuDesPetitsChevaux jdpc, int numberOfPlayer) {
		this.parent = jdpc;
		
		this.skin = jdpc.assetManager.manager.get("skin/glassy-ui.json", Skin.class);
		
		this.system = new JdpcSystem(this, numberOfPlayer);
		
        //=== LOADING MAP ===
        gameMap = new GameMap(new TmxMapLoader().load("carte.tmx"));

        //=== ADDING CAMERA ===
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameMap.MAP_WIDTH*GameMap.TILESIZE,GameMap.MAP_HEIGHT*GameMap.TILESIZE,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        
        //=== ADDING CONTROLLER ===
        controller = new MouseKeyboardController();
        
        //=== ADDING TEXTURES ===
        diceAtlas = parent.assetManager.manager.get("dice/dice.pack", TextureAtlas.class);
		playerIconAtlas = parent.assetManager.manager.get("playerIcon/playerIcon.pack");
		buttonAtlas = parent.assetManager.manager.get("button/button.pack");
		
        //=== SPRITES CREATION ===
		switch(numberOfPlayer) {
			case 4:
				pawnList.add(new Pawn(this.parent, 4,1,"Vert1",42,41, new float[]{13,5}, GameMap.GREENLADDERPOSITIONMATRIX));
				pawnList.add(new Pawn(this.parent, 4,2,"Vert2",42,41, new float[]{13,3}, GameMap.GREENLADDERPOSITIONMATRIX));
			case 3:
				pawnList.add(new Pawn(this.parent, 2,1,"Bleu1",14,13, new float[]{5,13}, GameMap.BLUELADDERPOSITIONMATRIX));
				pawnList.add(new Pawn(this.parent, 2,2,"Bleu2",14,13, new float[]{5,15}, GameMap.BLUELADDERPOSITIONMATRIX));
			case 2:
				pawnList.add(new Pawn(this.parent, 1,1,"Rouge1",0,55, new float[]{5,5}, GameMap.REDLADDERPOSITIONMATRIX));
				pawnList.add(new Pawn(this.parent, 1,2,"Rouge2",0,55, new float[]{5,3}, GameMap.REDLADDERPOSITIONMATRIX));
				pawnList.add(new Pawn(this.parent, 3,1,"Pourpre1",28,27, new float[]{13,13}, GameMap.PURPLELADDERPOSITIONMATRIX));
				pawnList.add(new Pawn(this.parent, 3,2,"Pourpre2",28,27, new float[]{13,15}, GameMap.PURPLELADDERPOSITIONMATRIX));
				break;
		}
        spriteDice = new Sprite(diceAtlas.findRegion("Dice6"));
        playerIcon = new Sprite(playerIconAtlas.findRegion("RedPlayer"));
        nextTurnButton = new Sprite(buttonAtlas.findRegion("NextTurnButton"));
        menuButton = new Sprite(buttonAtlas.findRegion("MenuButton"));
        
        //=== ADDING SPRITES ON MAP ===
        for(Pawn p : pawnList) {
        	gameMap.tiledMapRenderer.addSprite(p.spritePion);
        }
        gameMap.tiledMapRenderer.addSprite(spriteDice);
        gameMap.tiledMapRenderer.addSprite(playerIcon);
        gameMap.tiledMapRenderer.addSprite(nextTurnButton);
        gameMap.tiledMapRenderer.addSprite(menuButton);
        
        //=== PLACING SPRITES ON MAP ===
        for(Pawn p : pawnList) {
        	p.setToStablePosition();
        }
        spriteDice.setPosition(320,176);
        playerIcon.setPosition(304, 240);
        nextTurnButton.setPosition(304, 112);
        menuButton.setPosition(304, 48);
        
		//=== ADDING SOUNDS ===
		click = parent.assetManager.manager.get("sounds/click.mp3", Sound.class);
		diceRoll = parent.assetManager.manager.get("sounds/diceRoll.mp3", Sound.class);
	}
	
	/**
	 * show is called when this screen becomes the current screen
	 * Fetch the UI assets and set it up.
	 */
	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
		
        stage = new Stage(this.viewport);
        
        playerLabel = new Label("Player", skin);
        diceLabel = new Label("Throw", skin);
	
		TextureAtlas atlas = parent.assetManager.manager.get("possibleMove/possibleMove.pack", TextureAtlas.class);
		
        Animation<AtlasRegion> anim1 = new Animation<AtlasRegion>(0.05f, atlas.findRegions("PossibleMove") );
        anim1.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        possibleMove = new Animated(anim1);
        
        Animation<AtlasRegion> anim2 = new Animation<AtlasRegion>(0.05f, atlas.findRegions("PossibleMove") );
        anim2.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        selectedPawn = new Animated(anim2);
        
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
	
	/**
	 * render is called when the screen should render itself.
	 * Update the display and listen to user actions to trigger reactions
	 */
	@Override
    public void render(float delta) {		
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //KEYBOARD
        if(controller.escape) {
    		controller.escape = false;
            parent.changeScreen(JeuDesPetitsChevaux.MENU);
    	} else if(controller.spacebar) {
    		this.system.changeTurn();
    	}
        
    	//MOUSE-DOWN
    	if(controller.isMouse1Down){
            Vector3 clickCoordinates = new Vector3(controller.mouseLocation,0);
            Vector3 position = viewport.unproject(clickCoordinates);

            if(spriteDice.getBoundingRectangle().contains(position.x, position.y)) {
                if(this.system.throwDice()) playSound(DICE_SOUND);
            }
            
            if(nextTurnButton.getBoundingRectangle().contains(position.x, position.y)) {
            	if(this.system.changeTurn()) playSound(CLICK_SOUND);
            }
            
            if(menuButton.getBoundingRectangle().contains(position.x, position.y)) {
            	controller.isMouse1Down = false;
            	playSound(CLICK_SOUND);
            	parent.changeScreen(JeuDesPetitsChevaux.MENU);
            }

            if(this.system.moveDone == false && this.system.diceThrown) {
            	boolean pFound = false;
            	
            	for(Pawn p : pawnList) {
            		if(p.spritePion.getBoundingRectangle().contains(position.x, position.y) 
            				&& p.team == system.playerList.get(system.playerTurn-1) 
            				&& !pFound && (this.downOnID == p.id || this.downOnID == 0) ) {
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
             		if(p.spritePion.getBoundingRectangle().contains(position.x, position.y) 
             				&& p.team == system.playerList.get(system.playerTurn-1) 
             				&& (this.draggedID == p.id || this.draggedID == 0)) {
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
    	
    	//DISPLAY UPDATE
        camera.update();
        gameMap.tiledMapRenderer.setView(camera);
        gameMap.tiledMapRenderer.render();
        
        stage.act();
        stage.draw();
    }
    
	/**
	 * resize is called when the Application is resized.
	 */
    @Override
    public void resize(int width, int height){
       viewport.update(width,height);
       camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }
    
	/**
	 * playSound is called when a sound needs to be played
	 */
	public void playSound(int sound) {
		if(parent.preferences.isSoundEffectsEnabled()) {
			switch(sound){
			case CLICK_SOUND:
				click.play(parent.preferences.getSoundVolume());
				break;
			case DICE_SOUND:
				diceRoll.play(parent.preferences.getSoundVolume());
				break;
			}
		}
	}
    
}
