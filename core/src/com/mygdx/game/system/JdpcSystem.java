package com.mygdx.game.system;

import java.util.ArrayList;
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
			checkUnder(pawn, false);
		} else {
			pawn.setPosition(pawn.ladderMatrix[0][ladderPosition]*GameMap.TILESIZE, pawn.ladderMatrix[1][ladderPosition]*GameMap.TILESIZE);
			checkUnder(pawn, true);
		}
		this.moveDone = true;
		unShowPossibleMove();
	}


	public void movePawnToStart(Pawn pawn) {
		pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.raceStartPosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.raceStartPosition]*GameMap.TILESIZE);
		pawn.isInStable = false;
		pawn.racePosition = pawn.raceStartPosition;
		this.moveDone = true;
		unShowPossibleMove();
	}

	private void checkUnder(Pawn pawnAbove, Boolean moveInLadder) {
		boolean found = false;
		
		ArrayList<Pawn> otherPawnList = new ArrayList<Pawn>(screen.pawnList);
		for(int i =0; i<otherPawnList.size(); i++) {
			if(otherPawnList.get(i).equals(pawnAbove))
			otherPawnList.remove(i);
		}
		
		for(Pawn p : otherPawnList) {
			
			if(found) {
				break;
			} else if(p.ladderPosition == pawnAbove.ladderPosition && moveInLadder) {
				found = true;
				p.setToStablePosition();
			} else if(p.racePosition == pawnAbove.racePosition && !moveInLadder) {
				found = true;
				p.setToStablePosition();
			}
		}
		
	}
	
	public boolean findPossibleMove(Pawn pawn, boolean animate, boolean isSettingPosition) {
		if(animate) {
			screen.selectedPawn.setPosition(pawn.spritePion.getX(),pawn.spritePion.getY());
			screen.selectedPawn.setVisible(true);
		}

		boolean movePossible = false;
		
		ArrayList<Pawn> otherPawnList = new ArrayList<Pawn>(screen.pawnList);
		for(int i =0; i<otherPawnList.size(); i++) {
			if(otherPawnList.get(i).equals(pawn))
			otherPawnList.remove(i);
		}
		
		int futurePosition = pawn.racePosition;
		boolean moveInLadder = false;
		
		if(pawn.racePosition == pawn.raceEndPosition) {
			if(this.diceValue == 1) {
				moveInLadder = true;
				futurePosition = 0;
				movePossible = true;
			}
		} else if(pawn.ladderPosition >= 1 && pawn.ladderPosition <= 5) {
			if(this.diceValue == pawn.ladderPosition+1) {
				moveInLadder = true;
				futurePosition = this.diceValue-1;
				movePossible = true;
			}
	    } else if(pawn.ladderPosition == 6 && this.diceValue == 6) {
			moveInLadder = true;
			futurePosition = 6;
			movePossible = true;
		} else if(pawn.isInStable) {
			if(this.diceValue == 6 && this.diceThrown) {
				futurePosition = pawn.raceStartPosition;
				movePossible = true;
			}
		} else {
			int direction = 1;
			int distanceLeft = this.diceValue;
			
			while(distanceLeft > 0) {
				distanceLeft -=1;
				futurePosition += direction;
				if(futurePosition == 56) {
					futurePosition = 0;
					pawn.passed55 = true;
				} else if(futurePosition == -1) {
					futurePosition = 55;
					pawn.passed55 = false;
				}
				
				boolean found = false;
				for(Pawn p : otherPawnList) {
					if(found) {
						break;
					} else if( p.racePosition == futurePosition || (pawn.raceEndPosition == futurePosition && pawn.passed55)) {
						found = true;
						direction *= -1;
					}
				}
			}
			movePossible = true;
		}
		
		if(animate) {
			if(movePossible) {
				if(moveInLadder) {
					screen.possibleMove.setVisible(true);
					screen.possibleMove.setPosition(pawn.ladderMatrix[0][futurePosition]*GameMap.TILESIZE,pawn.ladderMatrix[1][futurePosition]*GameMap.TILESIZE);
				} else {
					screen.possibleMove.setVisible(true);
					screen.possibleMove.setPosition(GameMap.POSITIONMATRIX[0][futurePosition]*GameMap.TILESIZE,GameMap.POSITIONMATRIX[1][futurePosition]*GameMap.TILESIZE);
				}
			} else {
				screen.possibleMove.setVisible(false);
			}

		} else if(isSettingPosition) {
			if(moveInLadder) {
				if(movePossible && pawn.spritePion.getBoundingRectangle().contains(pawn.ladderMatrix[0][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2), pawn.ladderMatrix[1][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2))) {
					movePawn(pawn, -1, futurePosition);
					if(futurePosition == 6) {
						triggerVictory();
					}
				} else {
					pawn.setPosition(pawn.ladderMatrix[0][pawn.ladderPosition]*GameMap.TILESIZE, pawn.ladderMatrix[1][pawn.ladderPosition]*GameMap.TILESIZE);
				}
			} else {
				if(movePossible && pawn.spritePion.getBoundingRectangle().contains(GameMap.POSITIONMATRIX[0][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2), GameMap.POSITIONMATRIX[1][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2))) {
					if(pawn.isInStable && futurePosition == pawn.raceStartPosition) {
						movePawnToStart(pawn);
					} else {
						movePawn(pawn, futurePosition, -1);
					}
				} else if(pawn.isInStable) {
					pawn.setPosition(pawn.stablePosition[0]*GameMap.TILESIZE,pawn.stablePosition[1]*GameMap.TILESIZE);
				} else {
					pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.racePosition]*GameMap.TILESIZE);
				}
			}	
		}
		
		return movePossible;
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
		for(Pawn p : screen.pawnList) {
			if(p.team == this.playerTurn && findPossibleMove(p,false, false)) {
				return true;
			}
		}
		return false;
	}

	private void triggerVictory() {
		System.out.println("VICTORY : Player "+ this.playerTurn);
		this.screen.parent.changeScreen(JeuDesPetitsChevaux.ENDGAME);
	}

}
