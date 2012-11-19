/**
 * @(#) Player.java
 */

package com.example.kahla;

public class Player
{
	private String name;
	private Store store;
	private Pit[] pits;
	
	public Player(String name, int stonesCount){
		this.name=name;
		pits = new Pit[6];
		// intialize palyers pits to initial stones count
		for(int i = 0; i < 6 ; i++){
			Pit pit = new Pit(stonesCount);
			pits[i] = pit;
		}
		// create store for the player
		store = new Store();
		
	}
	// this method make the player take out stones from specified pit and move them along up to player's store
	// if anything left, it is responsiblity of board object to pass left stones to another player to distribute
	// if returned 0, means no stones left and player gets to player again
	// if return -1 , no stones left and player doesn't have another turn
	// if return -lastpitIndex (0 - 5), player is done but last store was empty (it is a weird wasy to do this but it works)
	// if > 0 , we have stones overflow
	public int pickPit(int pitIndex){
		Pit pickedPit = getPit(pitIndex);
		int pickedStones = pickedPit.getStones();
		while(pitIndex < 5 && pickedStones > 0){
			pitIndex++; // go to next Pit
			Pit pit = getPit(pitIndex);
			pit.addStone();
			pickedStones--;
			// check if we're done before getting to the store (no other turn)
			if(pickedStones == 0 && pitIndex <=5  ){
				// check if last pit was empty before we added the stone then return negative of pitIndex :) so odd
				if(pit.getStoneCount() == 1){
					return -(pitIndex);
					
				}
				else{
					return -9; // this is just an arbitrary negative number less thatn -5 to allow my logic
				}
			}
		}
		// check if we have left stones , one should go to store
		if(pickedStones > 0){
			store.addStone();
			pickedStones--;
			if(pickedStones == 0){
				return 0;
			}
		}
		
		return pickedStones;
		
	}
	
	public int takeStones(int count){
		int pitIndex = 0;
		while(pitIndex < 5){
			Pit pit = getPit(pitIndex);
			pit.addStone();
			count--;
			pitIndex++;
		}
		return count;
	}
	
	public Pit getPit(int index){
		Pit pit = null;
		if(index >= 0 && index <= 5){
			pit = pits[index];
		}
		return pit;
	}
	// this method add number of stones to player's store
	public void storeDeposite(int count){
		store.addStones(count);
		
	}
	// check if we have any pits that still have stones.
	
	public boolean hasMoreStones(){
		Pit currentPit;
		for(int i = 0 ; i < 6; i++){
			currentPit = pits[i];
			if(! currentPit.isEmpty()){
				return true;
			}
		}
		return false;
	}
	
	// if we're at the end of game where player doesnt have any stones left, move all stones left to store
	public void depositeAllMyStones(){
		int allStones = 0;
		Pit currentPit;
		for(int i = 0; i < 6; i++){
			currentPit = getPit(i);
			allStones += currentPit.getStones();
		}
		store.addStones(allStones);
	}
	
	// return player's score
	public int getScore(){
		return store.getStoneCount();
	}
	
	// return array of stone counts for each pit. first pit would be at index 0 etc
	// this would be useful api for communication with controller
	public int[] getPitsAsArray(){
		int[] pitsArray = new int[6];
		for(int i = 0 ; i < 6; i++){
			pitsArray[i] = pits[i].getStoneCount();
		}
		return pitsArray;
	}
	
	
}
