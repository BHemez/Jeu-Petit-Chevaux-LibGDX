package com.mygdx.game.system;

import java.util.Random;

import com.mygdx.game.view.MainScreen;

public class JdpcSystem {
	
	public int diceValue;
	
	public int numberOfPlayer = 4;
	public int playerTurn = 1;
	public Boolean diceThrown = false;

	private MainScreen screen;
	
	private static final float[][] POSITIONMATRIX = new float[][]
		{
			{7,7,7,7,7,7,7,6,5,4,3,2,1,1,1,2,3,4,5,6,7,7,7,7,7,7,7,9,11,11,11,11,11,11,11,12,13,14,15,16,17,17,17,16,15,14,13,12,11,11,11,11,11,11,11,9},	//Position X des cases
			{1,2,3,4,5,6,7,7,7,7,7,7,7,9,11,11,11,11,11,11,11,12,13,14,15,16,17,17,17,16,15,14,13,12,11,11,11,11,11,11,11,9,7,7,7,7,7,7,7,6,5,4,3,2,1}	//position Y des cases
		};

	

	public JdpcSystem(MainScreen scrn) {
		this.screen = scrn;
	}
	
	public void changeTurn() {
		if (checkForEndTurnCondition()){
			if(this.playerTurn >= this.numberOfPlayer ) {
				this.playerTurn = 1;
			} else {
				this.playerTurn += 1;
			}
			diceThrown = false;
			System.out.println("TURN : Player " + this.playerTurn);
		}
	}
	
	public int throwDice() {
		if(!diceThrown) {
			this.diceValue = new Random().nextInt(6)+1;
			//AJOUTER ANIMATION / FEEDBACK SONORE
			this.screen.getSpriteDice().setRegion(this.screen.getDiceAtlas().findRegion("Dice"+Integer.toString(diceValue)));
			this.diceThrown = true;
		}
		return diceValue;
	}
	
	public void movePawn(Pawn pawn) {
		
		if(pawn.racePosition + this.diceValue >= pawn.raceEndPosition ) {
			pawn.racePosition = pawn.raceEndPosition;
			pawn.setPosition(POSITIONMATRIX[0][pawn.raceEndPosition]*16,POSITIONMATRIX[1][pawn.raceEndPosition]*16);
		} else if(pawn.racePosition + this.diceValue > 55) {
			pawn.racePosition += this.diceValue -55;
			pawn.setPosition(POSITIONMATRIX[0][pawn.racePosition]*16,POSITIONMATRIX[1][pawn.racePosition]*16);
		} else {
			pawn.racePosition += this.diceValue;
			pawn.setPosition(POSITIONMATRIX[0][pawn.racePosition]*16,POSITIONMATRIX[1][pawn.racePosition]*16);
		}
		
		checkForCollision();
	}
	
	public void movePawnToStart(Pawn pawn) {
		
		pawn.setPosition(POSITIONMATRIX[0][pawn.raceStartPosition]*16, POSITIONMATRIX[1][pawn.raceStartPosition]*16);
		
		checkForCollision();
	}
	
	public void showPossibleMove(Pawn pawn) {
		screen.selectedPawn.setPosition(pawn.spritePion.getOriginX(),pawn.spritePion.getOriginY());
		screen.selectedPawn.setVisible(true);
		screen.possibleMove.setVisible(true);
		if(pawn.isInStable) {
			if(this.diceValue == 6 && this.diceThrown) {
				screen.possibleMove.setPosition(POSITIONMATRIX[0][pawn.raceStartPosition]*16, POSITIONMATRIX[1][pawn.raceStartPosition]*16);
			}
		} else if(pawn.racePosition + this.diceValue >= pawn.raceEndPosition ) {
			screen.possibleMove.setPosition(POSITIONMATRIX[0][pawn.raceEndPosition]*16, POSITIONMATRIX[1][pawn.raceEndPosition]*16);
		} else if(pawn.racePosition + this.diceValue > 55) {
			screen.possibleMove.setPosition(POSITIONMATRIX[0][pawn.racePosition + this.diceValue -55]*16,POSITIONMATRIX[1][pawn.racePosition + this.diceValue -55]*16);
		} else {
			screen.possibleMove.setPosition(POSITIONMATRIX[0][pawn.racePosition + this.diceValue]*16,POSITIONMATRIX[1][pawn.racePosition + this.diceValue]*16);
		} 
	}
	
	public void unShowPossibleMove() {
		screen.possibleMove.setVisible(false);
		screen.selectedPawn.setVisible(true);
	}

	private boolean checkForEndTurnCondition() {
		// CHECK LIST : (Dice throw && dice != 6 (replay)) && (Move done || no possible move) 
		return false;
	}
	
	private boolean checkForCollision() {
		// TODO Auto-generated method stub
		return false;
	}

}
