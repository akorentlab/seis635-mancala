package com.example.kahla;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class KahlaActivity extends Activity {
	public SoundPlayer soundManager;
	public MediaPlayer menuSong;
	public KahlaView kView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_kahla);
		
		menuSong = MediaPlayer.create(KahlaActivity.this, R.raw.planescape2);
        menuSong.start();
        menuSong.setLooping(true);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_kahla, menu);
		return true;
	}
	
	
	
	/** Called when the user clicks the Start button */
	public void startGame(View view){
		setContentView(R.layout.kahlaboard);
		kView = (KahlaView) findViewById(R.id.kahlaboard);		
		//kView.setMode(kView.RUNNING);
	}
	
	/** Called when the user clicks the Settings button */
	public void gameSettings(View view){
		 System.exit(0);
	}
	
	/** Called when the user clicks the Quit button */
	public void quitGame(View view){
		 System.exit(0);
	}
	
}
