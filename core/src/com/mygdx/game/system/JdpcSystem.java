package com.mygdx.game.system;

import java.util.Random;

import com.mygdx.game.JeuDesPetitsChevaux;
import com.mygdx.game.view.MainScreen;

public class JdpcSystem {
	
	public int diceValue;
	
	public int numberOfPlayer = 4;
	public int playerTurn = 1;
	public Boolean diceThrown = false;
	public Boolean moveDone = false;

	private MainScreen screen;
		
	
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
			screen.diceLabel.setText("Throw");
			
	        switch(playerTurn) {
        	case 1:
        		this.screen.playerIcon.setRegion(this.screen.playerIconAtlas.findRegion("RedPlayer"));
        		break;
        	case 2:
        		this.screen.playerIcon.setRegion(this.screen.playerIconAtlas.findRegion("BluePlayer"));
        		break;
        	case 3:
        		this.screen.playerIcon.setRegion(this.screen.playerIconAtlas.findRegion("PurplePlayer"));
        		break;
        	case 4:
        		this.screen.playerIcon.setRegion(this.screen.playerIconAtlas.findRegion("GreenPlayer"));
        		break;
        }
			
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
			this.screen.spriteDice.setRegion(this.screen.diceAtlas.findRegion("Dice"+Integer.toString(diceValue)));
			this.diceThrown = true;
			this.moveDone = false;
			screen.diceLabel.setText(""+diceValue);
			System.out.println("VALEUR DU DE : " + this.diceValue);
		}
		return diceValue;
	}
	
	public void movePawn(Pawn pawn, int position, int ladderPosition) {		
		pawn.racePosition = position;
		pawn.ladderPosition = ladderPosition+1;
		if(position != -1) {
			pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.racePosition]*GameMap.TILESIZE);
		} else {
			pawn.setPosition(pawn.ladderMatrix[0][ladderPosition]*GameMap.TILESIZE, pawn.ladderMatrix[1][ladderPosition]*GameMap.TILESIZE);
		}
		this.moveDone = true;
		checkForCollision();
		unShowPossibleMove();
	}
	
	public void movePawnToStart(Pawn pawn) {
		pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.raceStartPosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.raceStartPosition]*GameMap.TILESIZE);
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
			if(pawn.racePosition == pawn.raceEndPosition) {
				if(this.diceValue == 1) {
					screen.possibleMove.setVisible(true);
					screen.possibleMove.setPosition(pawn.ladderMatrix[0][0]*GameMap.TILESIZE, pawn.ladderMatrix[1][0]*GameMap.TILESIZE);
				}
			} else if(pawn.ladderPosition >= 1 && pawn.ladderPosition <= 5) {
				if(this.diceValue == pawn.ladderPosition+1) {
					screen.possibleMove.setVisible(true);
					screen.possibleMove.setPosition(pawn.ladderMatrix[0][this.diceValue-1]*GameMap.TILESIZE, pawn.ladderMatrix[1][this.diceValue-1]*GameMap.TILESIZE);
				}
		    } else if(pawn.ladderPosition == 6 && this.diceValue == 6) {
		    	screen.possibleMove.setVisible(true);
		    	screen.possibleMove.setPosition(pawn.ladderMatrix[0][6]*GameMap.TILESIZE, pawn.ladderMatrix[1][6]*GameMap.TILESIZE);
			} else if(pawn.isInStable) {
				if(this.diceValue == 6 && this.diceThrown) {
					screen.possibleMove.setVisible(true);
					screen.possibleMove.setPosition(GameMap.POSITIONMATRIX[0][pawn.raceStartPosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.raceStartPosition]*GameMap.TILESIZE);
				}
			} else if((pawn.racePosition + this.diceValue >= pawn.raceEndPosition) && pawn.passed55 == true) {
				screen.possibleMove.setVisible(true);
				screen.possibleMove.setPosition(GameMap.POSITIONMATRIX[0][pawn.raceEndPosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.raceEndPosition]*GameMap.TILESIZE);
			} else if(pawn.racePosition + this.diceValue > 55) {
				screen.possibleMove.setVisible(true);
				screen.possibleMove.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition + this.diceValue -55]*GameMap.TILESIZE,GameMap.POSITIONMATRIX[1][pawn.racePosition + this.diceValue -55]*GameMap.TILESIZE);
			} else {
				screen.possibleMove.setVisible(true);
				screen.possibleMove.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition + this.diceValue]*GameMap.TILESIZE,GameMap.POSITIONMATRIX[1][pawn.racePosition + this.diceValue]*GameMap.TILESIZE);
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
		boolean positionned = false;
		
		if(pawn.racePosition == pawn.raceEndPosition && !positionned) {
			if(this.diceValue == 1 && pawn.spritePion.getBoundingRectangle().contains(pawn.ladderMatrix[0][0]*GameMap.TILESIZE+(GameMap.TILESIZE/2), pawn.ladderMatrix[1][0]*GameMap.TILESIZE+(GameMap.TILESIZE/2))) {
				movePawn(pawn, -1, 0);
			} else {
				pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.raceEndPosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.raceEndPosition]*GameMap.TILESIZE);
			}
			positionned = true;
		} else if(pawn.ladderPosition >= 1 && pawn.ladderPosition <= 5 && !positionned) {
			if(this.diceValue == pawn.ladderPosition+1 && pawn.spritePion.getBoundingRectangle().contains(pawn.ladderMatrix[0][this.diceValue-1]*GameMap.TILESIZE+(GameMap.TILESIZE/2), pawn.ladderMatrix[1][this.diceValue-1]*GameMap.TILESIZE+(GameMap.TILESIZE/2))) {
				movePawn(pawn, -1, this.diceValue-1);
			} else {
				pawn.setPosition(pawn.ladderMatrix[0][pawn.ladderPosition-1]*GameMap.TILESIZE, pawn.ladderMatrix[1][pawn.ladderPosition-1]*GameMap.TILESIZE );
			}
			positionned = true;
		} else if(pawn.ladderPosition == 6 && this.diceValue == 6 && pawn.spritePion.getBoundingRectangle().contains(pawn.ladderMatrix[0][6]*GameMap.TILESIZE+(GameMap.TILESIZE/2), pawn.ladderMatrix[1][6]*GameMap.TILESIZE+(GameMap.TILESIZE/2)) && !positionned) {
				movePawn(pawn, -1, 6);
				triggerVictory();
				positionned = true;
	    } else if(pawn.isInStable && !positionned) {
			if(this.diceValue == 6 && this.diceThrown && pawn.spritePion.getBoundingRectangle().contains(GameMap.POSITIONMATRIX[0][pawn.raceStartPosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2), GameMap.POSITIONMATRIX[1][pawn.raceStartPosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2))) {
				movePawnToStart(pawn);
			} else {
				pawn.setPosition(pawn.stablePosition[0]*16,pawn.stablePosition[1]*16);
			}
			positionned = true;
		} else if((pawn.racePosition + this.diceValue >= pawn.raceEndPosition)&& pawn.passed55 == true && this.diceThrown && pawn.spritePion.getBoundingRectangle().contains(GameMap.POSITIONMATRIX[0][pawn.raceEndPosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2), GameMap.POSITIONMATRIX[1][pawn.raceEndPosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2)) && !positionned) {
			movePawn(pawn, pawn.raceEndPosition, -1);
			positionned = true;
		} else if(( pawn.racePosition + this.diceValue > 55) && this.diceThrown && pawn.spritePion.getBoundingRectangle().contains(GameMap.POSITIONMATRIX[0][pawn.racePosition + this.diceValue-55]*GameMap.TILESIZE+(GameMap.TILESIZE/2), GameMap.POSITIONMATRIX[1][pawn.racePosition + this.diceValue -55]*GameMap.TILESIZE+(GameMap.TILESIZE/2)) && !positionned) {
			pawn.passed55 = true;
			movePawn(pawn, pawn.racePosition+this.diceValue-55, -1);
			positionned = true;
		} else if(this.diceThrown && pawn.spritePion.getBoundingRectangle().contains(GameMap.POSITIONMATRIX[0][pawn.racePosition + this.diceValue]*GameMap.TILESIZE+(GameMap.TILESIZE/2), GameMap.POSITIONMATRIX[1][pawn.racePosition + this.diceValue]*GameMap.TILESIZE+(GameMap.TILESIZE/2))){
			movePawn(pawn, pawn.racePosition+this.diceValue, -1);
			positionned = true;
		} else if(!positionned) {
			pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.racePosition]*GameMap.TILESIZE);
			positionned = true;
		}
	}
	
	private void triggerVictory() {
		System.out.println("VICTORY : Player "+ this.playerTurn);
		this.screen.parent.changeScreen(JeuDesPetitsChevaux.ENDGAME);
	}

}
