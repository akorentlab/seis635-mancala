/**
 * @(#) MancalaGame.java
 */

package com.example.kahla;

public class KahlaGame
{
	private Strategy strategy;
	private KahlaBoard board;
	private int currentPlayerIndex = 0;
	private boolean soundOn = false;
	private boolean isDone = false;
	
	// constructor will start the game
	public KahlaGame(String player1Name, String player2Name, int stoneCount, boolean soundOn){
		board = new KahlaBoard(player1Name, player2Name, stoneCount);
		this.soundOn = soundOn;
	}
	
	// check if game is still goin on
	public boolean isDone(){
		return isDone;
	}
	
	// question for professor: what does it mean we methods repeat from lower level classes to upper classes ? 
	public int[] getPitsCounts(int playerIndex){
		return board.getPitsCounts(playerIndex);
	}
	
	
	public int getScore(int playerIndex){
		return board.getScore(playerIndex);
	}
	
	public void doTurn(int pitIndex){
		// if current player doesn't get second, go to next one
		boolean playAgain = board.makeMove(currentPlayerIndex, pitIndex);
		// check if need to keep going or stop the game
		if(board.hasMoreStones(currentPlayerIndex)){
			if(!playAgain){
				currentPlayerIndex = board.getNextPlayerIndex(currentPlayerIndex);
			}
		}
		// stop the game
		else{
			// change player index to next player with stones left
			currentPlayerIndex = board.getNextPlayerIndex(currentPlayerIndex);
			// grab anything left on other side
			board.emptyPits(currentPlayerIndex);
			isDone=true;
		}
		
	}
	
	
	

	
	// get whose turn it is
	public int getCurrentPlayerIndex(){
		return currentPlayerIndex;
	}
	
}
