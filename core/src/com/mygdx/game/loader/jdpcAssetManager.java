package com.mygdx.game.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class jdpcAssetManager {

	public final AssetManager manager = new AssetManager();
	
	//Textures - Pions
	public final String pionRouge1 = "pions/Rouge1.png";
	public final String pionRouge2 = "pions/Rouge2.png";
	public final String pionBleu1 = "pions/Bleu1.png";
	public final String pionBleu2 = "pions/Bleu2.png";
	public final String pionVert1 = "pions/Vert1.png";
	public final String pionVert2 = "pions/Vert2.png";
	public final String pionPourpre1 = "pions/Pourpre1.png";
	public final String pionPourpre2 = "pions/Pourpre2.png";
	
	//Sounds
	public final String clickSound = "sounds/click.mp3";
	
	
	public void queueAddPions(){
		manager.load(pionRouge1, Texture.class);
		manager.load(pionRouge2, Texture.class);
		manager.load(pionBleu1, Texture.class);
		manager.load(pionBleu2, Texture.class);
		manager.load(pionVert1, Texture.class);
		manager.load(pionVert2, Texture.class);
		manager.load(pionPourpre1, Texture.class);
		manager.load(pionPourpre2, Texture.class);
	}
	
	public void queueAddSounds(){
		manager.load(clickSound, Sound.class);
	}

}


