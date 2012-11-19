/**
 * @(#) Store.java
 */

package com.example.kahla;

public class Store extends StoneContainer
{
	public Store(){
		super();
	}
	// this is a unique operation that makes sense only with store object, but not with pit object
	public void addStones(int count){
		stonesCount+=count;
	}
	
}
