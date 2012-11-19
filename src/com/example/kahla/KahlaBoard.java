/**
 * @(#) MancalaBoard.java
 */

package com.example.kahla;

public class KahlaBoard
{
	private Player[] players;
	
	public KahlaBoard(String player1Name, String player2Name, int stoneCount){
		players = new Player[2];
		// create players object
		players[0] = new Player(player1Name, stoneCount);
		players[1] = new Player(player2Name, stoneCount);
	}
	
	public int getNextPlayerIndex(int currentPlayerIndex ){
		if(currentPlayerIndex == 0){
			return 1;
		}
		else
			return 0;
	}
	
	// this method return true if player gets another turn . in another words, last stone went to store
	public boolean makeMove(int playerIndex, int pitIndex){
		//boolean anotherTurn = false;
		Player player = players[playerIndex];
		int stonesLeft = player.pickPit(pitIndex);
		if(stonesLeft ==  0){
			return true;
		}
		else if(stonesLeft > 0){
			// keep distributing the stones among players until we're done
			while(stonesLeft > 0){
				playerIndex = getNextPlayerIndex(playerIndex);
				player = players[playerIndex];
				int stonesToGive = stonesLeft;
				stonesLeft = player.takeStones(stonesToGive);
			}
		}
		else if(stonesLeft < 0 && stonesLeft > -6){
			int opponentPitIndex= 5 + stonesLeft;
			playerIndex = getNextPlayerIndex(playerIndex);
			Player opponentPlayer = players[playerIndex];
			// check opposite pit actually have stones
			Pit oppositePit = opponentPlayer.getPit(opponentPitIndex);
			Pit playerPit = player.getPit(-(stonesLeft));
			int oppositeStones = oppositePit.getStoneCount();
			if(oppositeStones > 0){
				int stonesToGive = oppositePit.getStones() + playerPit.getStones();
				player.storeDeposite(stonesToGive);
				
			}

			
		}
	return false;
	}
	
	
	
}
