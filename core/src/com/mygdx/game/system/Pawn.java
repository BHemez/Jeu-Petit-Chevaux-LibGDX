package com.mygdx.game.system;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.JeuDesPetitsChevaux;

/**
 * Pawn class, represent a single pawn
 */
public class Pawn {
    private TextureAtlas pionsAtlas; 	//Pawn's texture image
    public Sprite spritePion;	//Pawn's sprite using the texture
    public int team;   //Pawn's team
    public int id;  //Pawn's id
    protected int raceStartPosition; //Position of the pawn when entering race
    protected int raceEndPosition; //Position of the pawn before ladder
    protected int racePosition = -2; //Current position of the pawn in the race (-2 if not on racetrack)
    protected int ladderPosition = -2; //Current position of the pawn in the ladder (-2 of not in ladder)
    protected float[] stablePosition; //X and Y coordinate of the pawn's stable position
    protected float[][] ladderMatrix; //Matrix of X and Y position of the pawn ladder's steps
    protected Boolean passed55; //Keep track if the pawn has passed the end of teh track or not (used to prevent a pawn for entering the ladder early)
    protected Boolean isInStable; //Keep track if a ppawn is in Stable or not

    /**
     * Pawn's constructor
     */
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
        if(start == 0 ) {//If the pawn start at the begining of the board, consider it already passed55
        	passed55 = true;
        } else {
        	passed55 = false;
        }
    }
    
    /**
     * setPosition change pawn's sprite position
     */
    public void setPosition(float x, float y) {
    	this.spritePion.setPosition(x, y);
    }
    
    /**
     * setToStablePosition send a pawn back to it's stable and change it's
     * properties accordingly
     */
    public void setToStablePosition() {
    	this.racePosition = -2;
    	this.ladderPosition = -2;
    	this.isInStable = true;
    	this.passed55 = false;
    	this.setPosition(this.stablePosition[0]*16, this.stablePosition[1]*16);
    }
	
}
