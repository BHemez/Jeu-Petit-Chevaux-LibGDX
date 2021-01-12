package com.mygdx.game.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * JdpcAssetManager contain the app AssetManager
 * gather every actions regarding the assets' loading
 */
public class JdpcAssetManager {
	/**
	 * Our app AssetManager is created here.
	 * Default libGDX assetManager.
	 */
	public final AssetManager manager = new AssetManager();
	
	/**
	 * List of string of where the files are in the assets folder.
	 */
	//Textures
	private final String loadingAtlas = "loading/loading.pack";
	private final String pionsAtlas = "pions/pions.pack";
	private final String playerIconAtlas = "playerIcon/playerIcon.pack";
	private final String diceAtlas = "dice/dice.pack";
	private final String possibleMoveAtlas = "possibleMove/possibleMove.pack";
	private final String button = "button/button.pack";
	//Sounds
	private final String clickSound = "sounds/click.mp3";
	private final String diceSound = "sounds/diceRoll.mp3";
	private final String fanfare = "sounds/triumphalFanfare.mp3";
	//Music
	private final String jazzMusic = "music/Jazz.mp3";
	//Skin
	private final String skin = "skin/glassy-ui.json";
	
	/**
	 * Methods called to load assets into the manager.
	 */
	public void queueAddPions(){
		manager.load(pionsAtlas, TextureAtlas.class);
	}
	
	public void queueAddPlayerIcon(){
		manager.load(playerIconAtlas, TextureAtlas.class);
	}
	
	public void queueAddButtons(){
		manager.load(button, TextureAtlas.class);
	}
	
	public void queueAddDice(){
		manager.load(diceAtlas, TextureAtlas.class);
	}
	
	public void queueAddSounds(){
		manager.load(clickSound, Sound.class);
		manager.load(diceSound, Sound.class);
		manager.load(fanfare, Sound.class);
	}

	public void queueAddMusic(){
		manager.load(jazzMusic, Music.class);
	}
	
	public void queueAddSkin(){
		SkinParameter params = new SkinParameter("skin/glassy-ui.atlas");
		manager.load(skin, Skin.class, params);
	}
	
	public void queueAddLoading(){
		manager.load(loadingAtlas, TextureAtlas.class);
	}
	
	public void queueAddPossibleMove(){
		manager.load(possibleMoveAtlas, TextureAtlas.class);
	}
	
}


