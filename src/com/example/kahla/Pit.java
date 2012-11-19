/**
 * @(#) Pit.java
 */

package com.example.kahla;

public class Pit extends StoneContainer
{
	public Pit(int intialCount){
		super(intialCount);
	}
	// remove stones from the pit and return the number of stones
	public int getStones()
	{
		int count = getStoneCount();
		stonesCount = 0;
		return count;
		
	}
	// check if pit is empty
	public boolean isEmpty(){
		if (getStoneCount() > 0){
			return false;
		}
		else
			return true;
	}
	
	
}
