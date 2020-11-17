package com.mygdx.game.system;

import java.util.Random;

import com.mygdx.game.view.MainScreen;

public class JdpcSystem {
	
	public int diceValue;
	
	public int numberOfPlayer = 4;
	public int playerTurn = 1;
	public Boolean diceThrown = false;
	public Boolean moveDone = false;

	private MainScreen screen;
	
	private static final float[][] POSITIONMATRIX = new float[][]
		{
			{7,7,7,7,7,7,7,6,5,4,3,2,1,1,1,2,3,4,5,6,7,7,7,7,7,7,7,9,11,11,11,11,11,11,11,12,13,14,15,16,17,17,17,16,15,14,13,12,11,11,11,11,11,11,11,9,0,0,0,0,0,0,0},	//Position X des cases
			{1,2,3,4,5,6,7,7,7,7,7,7,7,9,11,11,11,11,11,11,11,12,13,14,15,16,17,17,17,16,15,14,13,12,11,11,11,11,11,11,11,9,7,7,7,7,7,7,7,6,5,4,3,2,1,0,0,0,0,0,0,0}	//position Y des cases
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
			this.diceThrown = false;
			this.moveDone = false;
			screen.playerLabel.setText("Joueur "+this.playerTurn);
			screen.diceLabel.setText("Jetez");

			System.out.println("TURN : Player " + this.playerTurn);
		}
	}
	
	public void replay() {
		this.diceThrown = false;
		this.moveDone = false;
		screen.diceLabel.setText("Jetez");
		System.out.println("Player " + this.playerTurn + " replay");
	}
	
	public int throwDice() {
		if(!diceThrown || (diceThrown && diceValue == 6 && moveDone)) {
			this.diceValue = new Random().nextInt(6)+1;
			//AJOUTER ANIMATION / FEEDBACK SONORE
			this.screen.getSpriteDice().setRegion(this.screen.getDiceAtlas().findRegion("Dice"+Integer.toString(diceValue)));
			this.diceThrown = true;
			this.moveDone = false;
			screen.diceLabel.setText(""+diceValue);
			System.out.println("VALEUR DU DE : " + this.diceValue);
		}
		return diceValue;
	}
	
	public void movePawn(Pawn pawn, int position) {		
		pawn.racePosition = position;
		pawn.setPosition(POSITIONMATRIX[0][pawn.racePosition]*16, POSITIONMATRIX[1][pawn.racePosition]*16);
		this.moveDone = true;
		checkForCollision();
		unShowPossibleMove();
	}
	
	public void movePawnToStart(Pawn pawn) {
		
		pawn.setPosition(POSITIONMATRIX[0][pawn.raceStartPosition]*16, POSITIONMATRIX[1][pawn.raceStartPosition]*16);
		pawn.isInStable = false;
		pawn.racePosition = pawn.raceStartPosition;
		this.moveDone = true;
		checkForCollision();
		unShowPossibleMove();
	}
	
	public void showPossibleMove(Pawn pawn) {
		screen.selectedPawn.setPosition(pawn.spritePion.getX(),pawn.spritePion.getY());
		screen.selectedPawn.setVisible(true);
		if(diceThrown == true) {
			if(pawn.isInStable) {
				if(this.diceValue == 6 && this.diceThrown) {
					screen.possibleMove.setVisible(true);
					screen.possibleMove.setPosition(POSITIONMATRIX[0][pawn.raceStartPosition]*16, POSITIONMATRIX[1][pawn.raceStartPosition]*16);
				}
			} else if((pawn.racePosition + this.diceValue >= pawn.raceEndPosition) && pawn.passed55 == true) {
				screen.possibleMove.setVisible(true);
				screen.possibleMove.setPosition(POSITIONMATRIX[0][pawn.raceEndPosition]*16, POSITIONMATRIX[1][pawn.raceEndPosition]*16);
			} else if(pawn.racePosition + this.diceValue > 55) {
				screen.possibleMove.setVisible(true);
				screen.possibleMove.setPosition(POSITIONMATRIX[0][pawn.racePosition + this.diceValue -55]*16,POSITIONMATRIX[1][pawn.racePosition + this.diceValue -55]*16);

			} else {
				screen.possibleMove.setVisible(true);
				screen.possibleMove.setPosition(POSITIONMATRIX[0][pawn.racePosition + this.diceValue]*16,POSITIONMATRIX[1][pawn.racePosition + this.diceValue]*16);
			} 
		}
		
	}
	
	public void unShowPossibleMove() {
		screen.possibleMove.setVisible(false);
		screen.selectedPawn.setVisible(false);
	}

	private boolean checkForEndTurnCondition() {
		if(diceThrown && (moveDone || !checkForPossibleMove())) {
			if(diceValue == 6) {
				replay();
				return false;
			}
			return true;
		}
		return false;
	}
	
	private boolean checkForPossibleMove() {
		//True if moves are possible
		return false;
	}
	
	private boolean checkForCollision() {
		//TO-DO ecrase chevaux si besoin
		return false;
	}

	public void setToCorrectPosition(Pawn pawn) {
		if(pawn.isInStable) {
			if(this.diceValue == 6 && this.diceThrown && pawn.spritePion.getBoundingRectangle().contains(POSITIONMATRIX[0][pawn.raceStartPosition]*16+8, POSITIONMATRIX[1][pawn.raceStartPosition]*16+8)) {
				movePawnToStart(pawn);
			} else {
				pawn.setPosition(pawn.stablePosition[0]*16,pawn.stablePosition[1]*16);
			}
		} else if((pawn.racePosition + this.diceValue >= pawn.raceEndPosition)&& pawn.passed55 == true && this.diceThrown && pawn.spritePion.getBoundingRectangle().contains(POSITIONMATRIX[0][pawn.raceEndPosition]*16+8, POSITIONMATRIX[1][pawn.raceEndPosition]*16+8)) {
			movePawn(pawn, pawn.raceEndPosition);
		} else if(( pawn.racePosition + this.diceValue > 55) && this.diceThrown && pawn.spritePion.getBoundingRectangle().contains(POSITIONMATRIX[0][pawn.racePosition + this.diceValue-55]*16+8, POSITIONMATRIX[1][pawn.racePosition + this.diceValue -55]*16+8)) {
			pawn.passed55 = true;
			movePawn(pawn, pawn.racePosition+this.diceValue-55);
		} else if(this.diceThrown && pawn.spritePion.getBoundingRectangle().contains(POSITIONMATRIX[0][pawn.racePosition + this.diceValue]*16+8, POSITIONMATRIX[1][pawn.racePosition + this.diceValue]*16+8)){
			movePawn(pawn, pawn.racePosition+this.diceValue);
		} else {
			pawn.setPosition(POSITIONMATRIX[0][pawn.racePosition]*16, POSITIONMATRIX[1][pawn.racePosition]*16);
		}
	}

}
