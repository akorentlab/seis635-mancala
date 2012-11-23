package com.example.kahla;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KhalaTestActivity extends Activity {
	
	private KahlaGame game;
	private final String TAG = KhalaTestActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_khala_test);
		// starting the game right away
		game = new KahlaGame("player1", "player2", 3, false);
		drawBoard();
		// add listerner to all button pits
		registerPitsListener();
	}
	
	
	// draw the state of the game 
	public void drawBoard(){
		Button[] buttons;
		Button button;
		int[] counts;
		int playerIndex = game.getCurrentPlayerIndex();
		for(int i = 0; i < 2; i++){
			buttons = getPitButtons(i);
			// this array of counts of pits for the user
			counts = game.getPitsCounts(i);
			// iterate through buttons to update their values
			for(int j = 0; j < 6; j++){
				button = buttons[j];
				// diable buttons for player whoe doens't have the current turn
				if(playerIndex == i){
					button.setEnabled(true);
				}
				else{
					// diable buttons for player whoe doens't have the current turn
					button.setEnabled(false);
				}
				// a hack to convert int to string
				button.setText( new Integer(counts[j]).toString());
			}
			// now update the store score
			String playerScoreId = "player";
			if(i == 0)
				playerScoreId = playerScoreId + "1_store";
			else if(i == 1)
				playerScoreId = playerScoreId + "2_store";
			int textViewId = getResources().getIdentifier(playerScoreId, "id", "com.example.kahla");
			TextView score = (TextView) findViewById(textViewId);
			score.setText(new Integer(game.getScore(i)).toString());
		}
	}
	
	// listener to add to all buttons (pits)
	private OnClickListener getPitsListener() {
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int pitIndex=-1;
				// determine which pit index this means based on button
				if(v instanceof Button){
					int buttonId = v.getId();
					if(buttonId == R.id.player1_pit0 || buttonId == R.id.player2_pit0)
						pitIndex = 0;
					else if(buttonId == R.id.player1_pit1 || buttonId == R.id.player2_pit1)
						pitIndex = 1;
					else if(buttonId == R.id.player1_pit2 || buttonId == R.id.player2_pit2)
						pitIndex = 2;
					else if(buttonId == R.id.player1_pit3 || buttonId == R.id.player2_pit3)
						pitIndex = 3;
					else if(buttonId == R.id.player1_pit4 || buttonId == R.id.player2_pit4)
						pitIndex = 4;
					else if(buttonId == R.id.player1_pit5 || buttonId == R.id.player2_pit5)
						pitIndex = 5;
					//Log.e("index: ", new Integer(pitIndex).toString());	
				}
				if(pitIndex != -1){
					game.doTurn(pitIndex);
					// check if the game is done or not
					if(game.isDone()){
						drawBoard();
						Toast.makeText(getApplicationContext(), "Game is done", Toast.LENGTH_SHORT).show();
						
					}
					else{
						drawBoard();
					}
				}
			}
		};
		return onClickListener;
	}
	
	// register generic Pitts Listern  to all buttons:
	private void registerPitsListener(){
		OnClickListener  pitsListener = getPitsListener();
		Button[] buttons;
		Button button;
		// parent first loop is to get list of buttons each time for specific user
		for(int i = 0; i < 2; i++){
			buttons = getPitButtons(i);
			// inner loop is to iterate throught list of buttons
			for(int j = 0; j < 6; j++){
				button = buttons[j];
				// add the listern to button
				button.setOnClickListener(pitsListener);
			}
		}
		
	}
	
	
	// helper method to get list of Buttons for specific player
	public Button[] getPitButtons(int playerIndex){
		Button[] buttons = new Button[6];
		String playerName = "";
		String buttonId = "";
		if(playerIndex == 0){
			playerName="player1";
		}
		else if(playerIndex == 1){
			playerName="player2";
		}
		for(int i = 0; i < 6 ; i++){
			buttonId = playerName + "_pit" + i;
			try{
				int resID = getResources().getIdentifier(buttonId, "id", "com.example.kahla");
				Log.e(TAG, buttonId);
				buttons[i]=(Button)findViewById(resID);
			}
			catch (Exception e){
				Log.e(TAG, e.getLocalizedMessage());
			}
		}
		return buttons;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_khala_test, menu);
		return true;
	}

}
