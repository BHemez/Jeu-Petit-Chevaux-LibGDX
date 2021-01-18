package com.mygdx.game.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mygdx.game.JeuDesPetitsChevaux;
import com.mygdx.game.view.MainScreen;

/**
 * JdpcSystem class, keep the game's logic for each interactions.
 */
public class JdpcSystem {
	//Screen where the game is displayed.
	private MainScreen screen;
	//Current value of the dice.
	private int diceValue;
	//List of the game's players.
	public List<Integer> playerList;
	//Int to keep track of the player's turn.
	public int playerTurn = 1;
	//Boolean to keep track if the dice has been thrown during the turn or not
	public Boolean diceThrown = false;
	//Boolean to keep track if the player have moved a pawn during the turn or not
	public Boolean moveDone = false;
		
	/**
	 * Class' constructor
	 * Set it's parent screen and create an array of player.
	 * The array is filled depending of the number of player.
	 * 1=Red 2=Blue 3=Purple 4=Green
	 */
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
	
	/**
	 * changeTurn is called when a player want to change turn
	 * Check if the player is allowed to do so by calling checkForEndTurnCondition()
	 * Also check if the player will have to replay (then call replay())
	 * if the player need to change, the playerTurn change, dice and move are reset
	 * and the player icon is changed to tthe right color.
	 */
	public boolean changeTurn() {
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
			return true;
		}
		return false;
	}
	
	/**
	 * Replay is called when a player change turn but have to replay
	 * just reset the dice and move.
	 */
	private void replay() {
		this.diceThrown = false;
		this.moveDone = false;
		screen.diceLabel.setText("ReThrow");
	}
	
	/**
	 * throwDice called when the player try to throw the dice
	 * check if it's allowed then allocate a random value to the dice
	 * and set the dice's sprite acordingly
	 */
	public boolean throwDice() {
		if(!diceThrown || (diceThrown && diceValue == 6 && moveDone)) {
			this.diceValue = new Random().nextInt(6)+1;
			this.screen.spriteDice.setRegion(this.screen.diceAtlas.findRegion("Dice"+Integer.toString(diceValue)));
			this.diceThrown = true;
			this.moveDone = false;
			screen.diceLabel.setText(""+diceValue);
			return true;
		}
		return false;
	}
	
	/**
	 * movePawn is called when a Pawn need to change position 
	 * on the race track or the ladder.
	 */
	private void movePawn(Pawn pawn, int position, int ladPosition) {
		pawn.racePosition = position;
		pawn.ladderPosition = ladPosition;
		if(position != -2) {
			pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition]*GameMap.TILESIZE, 
					GameMap.POSITIONMATRIX[1][pawn.racePosition]*GameMap.TILESIZE);
			checkUnder(pawn);
		} else {
			pawn.setPosition(pawn.ladderMatrix[0][ladPosition]*GameMap.TILESIZE, 
					pawn.ladderMatrix[1][ladPosition]*GameMap.TILESIZE);
		}
		this.moveDone = true;
		unShowPossibleMove();
	}

	/**
	 * movePawnToStart is called when a Pawn need to be moved to 
	 * it's starting position. Also set it out of the stable.
	 */
	private void movePawnToStart(Pawn pawn) {
		pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.raceStartPosition]*GameMap.TILESIZE, 
				GameMap.POSITIONMATRIX[1][pawn.raceStartPosition]*GameMap.TILESIZE);
		pawn.isInStable = false;
		pawn.racePosition = pawn.raceStartPosition;
		this.moveDone = true;
		checkUnder(pawn);
		unShowPossibleMove();
	}

	/**
	 * checkUnder is called when a Pawn move, check if an ennemy pawn
	 * is under and if so send him to it's stable.
	 * If it's an ally, the current pawn just go behind.
	 */
	private void checkUnder(Pawn pawnAbove) {
		boolean found = false;
		//List of pawn excluding the current one
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
	
	/**
	 * findPossibleMove is the system most important method
	 * Calculate the future position of a selected pawn accordingly
	 * to the rules. 
	 * If animate is set to true it will do an animation
	 * around the selected pawn and it's possible move.
	 * If isSettingPosition is set to true the method will set the pawn
	 * to the position where it belong according to the rules. If the 
	 * player drop the pawn on a possible position the method will move
	 * the pawn to that position.
	 */
	public boolean findPossibleMove(Pawn pawn, boolean animate, boolean isSettingPosition) {
		boolean movePossible = false;
		
		ArrayList<Pawn> otherPawnList = getOtherPawnList(pawn, screen.pawnList);
		int futurePosition = pawn.racePosition;
		boolean moveInLadder = false; //Check if the pawn will move in the ladder
		
		//when the pawn is at the bottom of the ladder
		if(pawn.racePosition == pawn.raceEndPosition && pawn.passed55) {
			if(this.diceValue == 1) {
				moveInLadder = true;
				futurePosition = 0;
				movePossible = true;
			}
			//when the pawn is in the ladder but not the last position
		} else if(pawn.ladderPosition >= 0 && pawn.ladderPosition < 5) {
			if(this.diceValue == pawn.ladderPosition+2) {
				moveInLadder = true;
				futurePosition = pawn.ladderPosition+1;
				movePossible = true;
			}
			//when the pawn is in the last step of the ladder
	    } else if(pawn.ladderPosition == 5) {
	    	if(this.diceValue == 6) {
				moveInLadder = true;
				futurePosition = 6;
				movePossible = true;
	    	}
	    	//when the pawn is in it's stable
		} else if(pawn.isInStable) {
			if(this.diceValue == 6 && this.diceThrown) {
				futurePosition = pawn.raceStartPosition;
				movePossible = true;
			}
			//when the pawn is in the race track
		} else {
			int direction = 1;//keep track if the pawn is movingg forward(1) or backward(-1) after a coollision
			int distanceLeft = this.diceValue;//Keep track of the number of tile the pawn need to cross
			
			while(distanceLeft > 0) {
				distanceLeft -=1;	
				futurePosition = positionRectifier(pawn, futurePosition+direction);
				
				boolean found = false;
				for(Pawn p : otherPawnList) {
					//Check of a pawn is in the way and change the current pawn direction
					if(found) {
						break;
					} else if(p.racePosition == futurePosition || (pawn.raceEndPosition == futurePosition && pawn.passed55)) {
						found = true;
						direction *= -1;
					}
				}
			}
			if(pawn.racePosition != futurePosition) {//if the pawn don't land in the same position
				movePossible = true;
			}
		}
		
		if(moveInLadder == true) {//check if the pawn is free to move inside the ladder
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
					screen.possibleMove.setPosition(pawn.ladderMatrix[0][futurePosition]*GameMap.TILESIZE,
							pawn.ladderMatrix[1][futurePosition]*GameMap.TILESIZE);
				} else {
					screen.possibleMove.setVisible(true);
					screen.possibleMove.setPosition(GameMap.POSITIONMATRIX[0][futurePosition]*GameMap.TILESIZE,
							GameMap.POSITIONMATRIX[1][futurePosition]*GameMap.TILESIZE);
				}
			} else {
				screen.possibleMove.setVisible(false);
			}

		} else if(isSettingPosition) {
			if(moveInLadder) {
				if(movePossible && pawn.spritePion.getBoundingRectangle().contains(
						pawn.ladderMatrix[0][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2), 
						pawn.ladderMatrix[1][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2))) {
					movePawn(pawn, -2, futurePosition);
					if(futurePosition == 6) {
						triggerVictory();
					}
				}
			} else {
				if(movePossible && pawn.spritePion.getBoundingRectangle().contains(
						GameMap.POSITIONMATRIX[0][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2), 
						GameMap.POSITIONMATRIX[1][futurePosition]*GameMap.TILESIZE+(GameMap.TILESIZE/2))) {
					if(pawn.isInStable && futurePosition == pawn.raceStartPosition) {
						movePawnToStart(pawn);
					} else {
						movePawn(pawn, futurePosition, -2);
					}
				} else if(pawn.isInStable) {
					pawn.setPosition(pawn.stablePosition[0]*GameMap.TILESIZE,
							pawn.stablePosition[1]*GameMap.TILESIZE);
				} else if(pawn.racePosition != -2){
					pawn.setPosition(GameMap.POSITIONMATRIX[0][pawn.racePosition]*GameMap.TILESIZE,
							GameMap.POSITIONMATRIX[1][pawn.racePosition]*GameMap.TILESIZE);
				} else if(pawn.ladderPosition != -2){
					pawn.setPosition(pawn.ladderMatrix[0][pawn.ladderPosition]*GameMap.TILESIZE,
							pawn.ladderMatrix[1][pawn.ladderPosition]*GameMap.TILESIZE);
				}
			}	
		}
		
		return movePossible;
	}
	
	/**
	 * unShowPossibleMove is called when we want to hide
	 * the animation showing the selected pawn and the move
	 * possible for that pawn.
	 */
	public void unShowPossibleMove() {
		screen.possibleMove.setVisible(false);
		screen.selectedPawn.setVisible(false);
	}
	
	/**
	 * checkForEndTurnCondition is called to check if the turn can change.
	 * Check if the dice is thrown and the player did a move (or can't move)
	 */
	private boolean checkForEndTurnCondition() {
		if(diceThrown && (moveDone || !checkForPossibleMove())) {
			return true;
		}
		return false;
	}
	
	/**
	 * checkForPossibleMove return if the player have available move or not
	 */
	private boolean checkForPossibleMove() {
		for(Pawn p : screen.pawnList) {
			if(p.team == playerList.get(playerTurn-1) && findPossibleMove(p,false, false)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * triggerVictory called when a player win
	 * Set the screen to the victory one.
	 */
	private void triggerVictory() {
		this.screen.parent.changeScreen(JeuDesPetitsChevaux.ENDGAME);
	}

	/**
	 * getOtherPawnList return a list of pawn excluding the selected one
	 */
	private ArrayList<Pawn> getOtherPawnList(Pawn pawn, ArrayList<Pawn> list) {
		ArrayList<Pawn> otherPawnList = new ArrayList<Pawn>(list);
		for(int i =0; i<otherPawnList.size(); i++) {
			if(otherPawnList.get(i).equals(pawn))
			otherPawnList.remove(i);
		}
		return otherPawnList;
	}
	
	/**
	 * positionRectifier is used when a pawn cross the boord's end
	 * which loop back to the beginning.
	 */
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
