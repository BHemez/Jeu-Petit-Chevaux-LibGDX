package com.mygdx.game.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mygdx.game.JeuDesPetitsChevaux;
import com.mygdx.game.view.MainScreen;

public class JdpcSystem {
	
	private MainScreen screen;
	private int diceValue;
	public List<Integer> playerList;
	public int playerTurn = 1;
	public Boolean diceThrown = false;
	public Boolean moveDone = false;
		
	public JdpcSystem(MainScreen scrn, int numberOfPlayer) {
		this.screen = scrn;
		this.playerList = new ArrayList<Integer>();
		switch(numberOfPlayer) {
			case 2:
				this.playerList.add(1);
				this.playerList.add(3);
				break;
			case 3:
				this.playerList.add(1);
				this.playerList.add(2);
				this.playerList.add(3);
				break;
			case 4:
				this.playerList.add(1);
				this.playerList.add(2);
				this.playerList.add(3);
				this.playerList.add(4);
				break;
		}
	}
	
	public void changeTurn() {
		if (checkForEndTurnCondition()){
			if(diceValue == 6) {
				replay();
			} else {
				if(this.playerTurn >= this.playerList.size()) {
					this.playerTurn = 1;
				} else {
					this.playerTurn += 1;
				}
				this.diceThrown = false;
				this.moveDone = false;
				screen.diceLabel.setText("Throw");
				
		        switch(playerList.get(playerTurn-1)) {
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
			}
		}
	}
	
	private void replay() {
		this.diceThrown = false;
		this.moveDone = false;
		screen.diceLabel.setText("ReThrow");
	}
	
	public int throwDice() {
		if(!diceThrown || (diceThrown && diceValue == 6 && moveDone)) {
			this.diceValue = new Random().nextInt(6)+1;
			//AJOUTER ANIMATION / FEEDBACK SONORE
			this.screen.spriteDice.setRegion(this.screen.diceAtlas.findRegion("Dice"+Integer.toString(diceValue)));
			this.diceThrown = true;
			this.moveDone = false;
			screen.diceLabel.setText(""+diceValue);
		}
		return diceValue;
	}
	
	private void movePawn(Pawn pawn, int position, int ladPosition) {
		pawn.racePosition = position;
		pawn.ladderPosition = ladPosition;
		if(position != -2) {
			pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.racePosition]*GameMap.TILESIZE);
			checkUnder(pawn);
		} else {
			pawn.setPosition(pawn.ladderMatrix[0][ladPosition]*GameMap.TILESIZE, pawn.ladderMatrix[1][ladPosition]*GameMap.TILESIZE);
		}
		this.moveDone = true;
		unShowPossibleMove();
	}

	private void movePawnToStart(Pawn pawn) {
		pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.raceStartPosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.raceStartPosition]*GameMap.TILESIZE);
		pawn.isInStable = false;
		pawn.racePosition = pawn.raceStartPosition;
		this.moveDone = true;
		checkUnder(pawn);
		unShowPossibleMove();
	}

	private void checkUnder(Pawn pawnAbove) {
		boolean found = false;
		
		ArrayList<Pawn> otherPawnList = getOtherPawnList(pawnAbove, screen.pawnList);
		
		for(Pawn p : otherPawnList) {
			
			if(found) {
				break;
			} else if(p.racePosition == pawnAbove.racePosition) {
				found = true;
				if(p.team == pawnAbove.team) {
					int futurePosition = positionRectifier(pawnAbove, pawnAbove.racePosition-1);
					movePawn(pawnAbove, futurePosition, pawnAbove.ladderPosition);
				} else {
					p.setToStablePosition();
				}
			}
		}
	}
	
	public boolean findPossibleMove(Pawn pawn, boolean animate, boolean isSettingPosition) {
		boolean movePossible = false;
		
		ArrayList<Pawn> otherPawnList = getOtherPawnList(pawn, screen.pawnList);
		
		int futurePosition = pawn.racePosition;
		boolean moveInLadder = false;
		
		if(pawn.racePosition == pawn.raceEndPosition && pawn.passed55) {
			if(this.diceValue == 1) {
				moveInLadder = true;
				futurePosition = 0;
				movePossible = true;
			}
		} else if(pawn.ladderPosition >= 0 && pawn.ladderPosition < 5) {
			if(this.diceValue == pawn.ladderPosition+2) {
				moveInLadder = true;
				futurePosition = pawn.ladderPosition+1;
				movePossible = true;
			}
	    } else if(pawn.ladderPosition == 5) {
	    	if(this.diceValue == 6) {
				moveInLadder = true;
				futurePosition = 6;
				movePossible = true;
	    	}
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
				futurePosition = positionRectifier(pawn, futurePosition+direction);
				
				boolean found = false;
				for(Pawn p : otherPawnList) {
					if(found) {
						break;
					} else if(p.racePosition == futurePosition || (pawn.raceEndPosition == futurePosition && pawn.passed55)) {
						found = true;
						direction *= -1;
					}
				}
			}
			if(pawn.racePosition != futurePosition) {
				movePossible = true;
			}
		}
		
		if(moveInLadder == true) {
			for(Pawn p : otherPawnList) {
				if(p.team == pawn.team && p.ladderPosition == futurePosition) {
					movePossible = false;
				}
			}
		}
		
		if(animate) {
			screen.selectedPawn.setPosition(pawn.spritePion.getX(),pawn.spritePion.getY());
			screen.selectedPawn.setVisible(true);
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
					movePawn(pawn, -2, futurePosition);
					if(futurePosition == 6) {
						triggerVictory();
					}
				}
			} else {
				if(movePossible && pawn.spritePion.getBoundingRectangle().contains(GameMap.POSITIONMATRIX[0][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2), GameMap.POSITIONMATRIX[1][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2))) {
					if(pawn.isInStable && futurePosition == pawn.raceStartPosition) {
						movePawnToStart(pawn);
					} else {
						movePawn(pawn, futurePosition, -2);
					}
				} else if(pawn.isInStable) {
					pawn.setPosition(pawn.stablePosition[0]*GameMap.TILESIZE,pawn.stablePosition[1]*GameMap.TILESIZE);
				} else if(pawn.racePosition != -2){
					pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition]*GameMap.TILESIZE, GameMap.POSITIONMATRIX[1][pawn.racePosition]*GameMap.TILESIZE);
				} else if(pawn.ladderPosition != -2){
					pawn.setPosition(pawn.ladderMatrix[0][pawn.ladderPosition]*GameMap.TILESIZE, pawn.ladderMatrix[1][pawn.ladderPosition]*GameMap.TILESIZE);//TRIGGER TROP TOT
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
			return true;
		}
		return false;
	}
	
	private boolean checkForPossibleMove() {
		for(Pawn p : screen.pawnList) {
			if(p.team == playerList.get(playerTurn-1) && findPossibleMove(p,false, false)) {
				return true;
			}
		}
		return false;
	}

	private void triggerVictory() {
		this.screen.parent.changeScreen(JeuDesPetitsChevaux.ENDGAME);
	}

	private ArrayList<Pawn> getOtherPawnList(Pawn pawn, ArrayList<Pawn> list) {
		ArrayList<Pawn> otherPawnList = new ArrayList<Pawn>(list);
		for(int i =0; i<otherPawnList.size(); i++) {
			if(otherPawnList.get(i).equals(pawn))
			otherPawnList.remove(i);
		}
		return otherPawnList;
	}
	
	private int positionRectifier(Pawn pawn, int position) {
		if(position == 56) {
			position = 0;
			pawn.passed55 = true;
		} else if(position == -1) {
			position = 55;
			pawn.passed55 = false;
		}
		return position;
	}
	
}
