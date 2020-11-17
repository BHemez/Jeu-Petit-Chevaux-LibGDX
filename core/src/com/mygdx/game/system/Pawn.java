package com.mygdx.game.system;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.JeuDesPetitsChevaux;

public class Pawn {

    private TextureAtlas pionsAtlas;
    public Sprite spritePion;
    
    public int raceStartPosition;
    public int raceEndPosition;
    public int racePosition;
    public float[] stablePosition;
    public Boolean isInStable;

    
    public Pawn(JeuDesPetitsChevaux parent, String pion, int start, int end, float[] stable) {
    	this.pionsAtlas = parent.assetManager.manager.get("pions/pions.pack", TextureAtlas.class);
        this.spritePion = new Sprite(pionsAtlas.findRegion(pion));
        this.raceStartPosition = start;
        this.raceEndPosition = end;
        this.stablePosition = stable;
        this.isInStable = true;
    }
    
    public void setPosition(float x, float y) {
    	this.spritePion.setPosition(x, y);
    }
    
    public void setToStablePosition() {
    	this.setPosition(this.stablePosition[0]*16, this.stablePosition[1]*16);
    }
    
    public void dispose() {
    	this.spritePion.getTexture().dispose();
    }

	public void setToCorrectPosition() {
		// TODO Replace to initial position if not draged on possible spot
		
	}

	
}
