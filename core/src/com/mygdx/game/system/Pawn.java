package com.mygdx.game.system;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.JeuDesPetitsChevaux;

public class Pawn {

    private TextureAtlas pionsAtlas;
    public Sprite spritePion;
    public int team;
    public int id;
    
    public int raceStartPosition;
    public int raceEndPosition;
    public int racePosition = -2;
    public int ladderPosition = -2;
    public float[] stablePosition;
    public float[][] ladderMatrix;
    public Boolean passed55;
    public Boolean isInStable;


    public Pawn(JeuDesPetitsChevaux parent, int team, int id, String pion, int start, int end, float[] stable, float[][] ladder) {
    	this.team = team;
    	this.id = id;
    	this.pionsAtlas = parent.assetManager.manager.get("pions/pions.pack", TextureAtlas.class);
        this.spritePion = new Sprite(pionsAtlas.findRegion(pion));
        this.raceStartPosition = start;
        this.raceEndPosition = end;
        this.stablePosition = stable;
        this.ladderMatrix = ladder;
        this.isInStable = true;
        if(start == 0 ) {
        	passed55 = true;
        } else {
        	passed55 = false;
        }
    }
    
    public void setPosition(float x, float y) {
    	this.spritePion.setPosition(x, y);
    }
    
    public void setToStablePosition() {
    	this.racePosition = -1;
    	this.isInStable = true;
    	this.passed55 = false;
    	this.setPosition(this.stablePosition[0]*16, this.stablePosition[1]*16);
    }
    
    public void dispose() {
    	this.spritePion.getTexture().dispose();
    }

	
}
