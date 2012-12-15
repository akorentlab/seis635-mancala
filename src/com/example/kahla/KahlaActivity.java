package com.example.kahla;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

public class KahlaActivity extends Activity{
	private MediaPlayer menuSong;
	private RadioGroup numberStartingStones;
	private int startingStoneCount;
	private boolean singlePlayer=false;
	private KahlaView kView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Sets up the built-in MediaPlayer class with the menu music
		menuSong = MediaPlayer.create(KahlaActivity.this, R.raw.planescape2);
		
		//Sets the current view to the starting Menu
		setContentView(R.layout.activity_kahla);
		startingStoneCount=4;
        numberStartingStones=(RadioGroup) findViewById(R.id.radioGroup1);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.  Not used in this iteration.
		getMenuInflater().inflate(R.menu.activity_kahla, menu);
		return true;
	}	
	
	
	// Called when the user clicks the Start button.  This will set the board view.
	public void startGame(View view){
		setContentView(R.layout.kahlaboard);
		kView = (KahlaView) findViewById(R.id.kahlaboard);	
		kView.setStartingStoneCount(startingStoneCount);
		}	

	// Called when the user clicks the Quit button on the Start Menu or during the game.  Quits the game.
	public void quitGame(View view){
		menuSong.stop();
		finish();
	}
	
	//Toggles the starting stones with radio buttons.  Unused in current iteration, but flag is set correctly.
	public void setStartingStoneCount(View view)
	{
		boolean checked=((RadioButton)view).isChecked();
		switch(view.getId()){
		case R.id.four:
			if(checked)
				startingStoneCount=4;
			break;
		case R.id.five:
			if(checked)
				startingStoneCount=5;
			break;
		case R.id.six:
			if(checked)
				startingStoneCount=6;
			break;
		}
	}
	
	//Toggles the singlePlayer flag On/Off.  Unused in current iteration.
	public void playerToggle(View view)
	{
		boolean on=((Switch)view).isChecked();
		if(on)
			{singlePlayer=true;}
		else
			{singlePlayer=false;}
	}
	
	//Toggles Music On/Off.
	public void musicToggle(View view)
	{
		boolean on=((Switch)view).isChecked();
		if(on)
		{	
			menuSong.start();
			menuSong.setLooping(true);}
		else
			{menuSong.stop();
			menuSong = MediaPlayer.create(KahlaActivity.this, R.raw.planescape2);}
	}
	
	
	


}
