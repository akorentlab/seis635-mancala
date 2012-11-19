/**
 * @(#) StoneContainer.java
 */

package com.example.kahla;

public class StoneContainer
{
	protected int stonesCount;
	
	public StoneContainer(int intialCount){
		stonesCount=intialCount;
	}
	
	public StoneContainer(){
		stonesCount=0;
	}
	
	public void addStone( )
	{
		stonesCount++;
		
	}
	
	public int getStoneCount( )
	{
		return stonesCount;
	}
	
	
}
