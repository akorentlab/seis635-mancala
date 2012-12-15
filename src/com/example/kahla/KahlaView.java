package com.example.kahla;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class KahlaView extends View{
	private KahlaGame game;
	private int pitSelected=-1;
	private boolean eventOccured;
	private int startingStones=4;
	private Point[] pitLocation= new Point [14];
	private int[]stoneTracker = new int[14];
	private Random generator = new Random();
	private boolean buttonClicked;
	private int playerIndex=0;
	private Bitmap[] imageMap= new Bitmap[8];
    private long mMoveDelay = 70;
    private long mLastMove;
    
    private RefreshHandler mRedrawHandler = new RefreshHandler();
    
    //This is a class that is tied to the thread update.  It handles refreshing the screen.
    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            KahlaView.this.update();
            KahlaView.this.invalidate();
        }

        public void sleep(long delayMillis) {
        	this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    };


	/*	KahlaView constructor is called when the view is switched in KahlaActivity.
	 	It creates a reference to a KahlaGame object that will run the logic in the game.
	*/
	public KahlaView(Context context, AttributeSet attrs) {
		super( context, attrs );
		game = new KahlaGame("player1", "player2", 4, false);
		init();
		}
	
	/*	init() organizes the variables that are initialized and defines the touchListener
		used for gathering user clicks on the screen.
	*/
	public void init()
	{	
		setFocusable(true);
		loadPitLocations();
		loadImages();
		this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            	
            	if(buttonListener(event.getX(), event.getY()))
            		{ 	
            			game.doTurn(pitSelected);
            			playerIndex=game.getCurrentPlayerIndex();
            			eventOccured=true;
            			update();
            		}   
                    return true;
            }
        });
    }
	
	/*	buttonListener is called in the TouchListener.  It validates that the user clicked an acceptable
	  	pit.  This depends on who's turn it is and pit locations.  It also retains which pit was selected
	  	with the pitSelected variable.
	 */
	public boolean buttonListener(float x, float y)
	{
		buttonClicked=false;
		if(playerIndex==0)
			{for(int i=0; i<=5;i++)
			{
				if(x>=pitLocation[i].x && x<=pitLocation[i].x+100 && y>=pitLocation[i].y && y<=pitLocation[i].y+100 && stoneTracker[i]!=0)
					{buttonClicked=true;
					pitSelected=i;
					break;}}}	
		if(playerIndex==1)
			{for(int i=6; i<=11;i++)
				{if( x>=pitLocation[i].x && x<=pitLocation[i].x+100 && y>=pitLocation[i].y && y<=pitLocation[i].y+100 && stoneTracker[i]!=0)
					{buttonClicked=true;
					pitSelected=(i-6);
						break;}}}
		return buttonClicked;
	}

	//Loads all images that will be drawn with a key, image, width and height
	public void loadImages()
	{
		Resources r = this.getContext().getResources();
		loadImage(0, r.getDrawable(R.drawable.stone), 20, 20);
		loadImage(1, r.getDrawable(R.drawable.player1pit),110,110);
		loadImage(2, r.getDrawable(R.drawable.player2pit),110,110);
		loadImage(3,r.getDrawable(R.drawable.player1store),120,240);
		loadImage(4,r.getDrawable(R.drawable.player2store),120,240);
		loadImage(5,r.getDrawable(R.drawable.playerone),325,75);
		loadImage(6,r.getDrawable(R.drawable.playertwo),325,75);
		loadImage(7,r.getDrawable(R.drawable.gameover),325,75);
	}
	
	/*	loadPitLocations loads arrays with starting values for the number of stones and 
	 * 	locations of the pits and stores.  The pits are loaded into the array in a way that
	 * 	is consistent with the logic.
	 */
	public void loadPitLocations()
	{		
		for (int i=0; i<=11; i++)
			stoneTracker[i]=startingStones;
		for (int i=0; i<=5; i++)
			pitLocation[i]=createPoint((620-(i*100)), 70);
		
		for (int i=6; i<=11; i++)
			pitLocation[i]=createPoint((120+((i-6)*100)), 210);

		pitLocation[12]=createPoint(0,75);
		pitLocation[13]=createPoint(725,75);
	}
	
	//Creates point that is used for pit locations
	public Point createPoint(int x, int y)
	{
		Point location = new Point();
		location.set(x, y);
		return location;
	}
	
	//loadImage will store an image with a key, width and height into imageMap
	public void loadImage(int key, Drawable tile, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0, 0, width, height);
        tile.draw(canvas);
        imageMap[key]=bitmap;
    }
	
	//onDraw draws the pits, stones, stores, and player state.
	  public void onDraw(Canvas canvas) {
	       super.onDraw(canvas);   
	       Paint paint = new Paint();
	       paint.setTextSize(15);
	       paint.setARGB(255, 255, 255, 0);
	       //For loop to draw pits and their stones
	       for (int i=0; i<=11; i++)
	       { 	//draw player1 pits
	    	   if (i<6)
	    		   canvas.drawBitmap(imageMap[1], pitLocation[i].x, pitLocation[i].y, null);
	    	   //draw player2 pits
	    	   else
	    		   canvas.drawBitmap(imageMap[2], pitLocation[i].x, pitLocation[i].y, null);
	    	   //draw stones for all pits, random number on location so stones are not drawn on top of each other.
	    	   for (int x=0; x<stoneTracker[i]; x++)
	    		   canvas.drawBitmap(imageMap[0], (pitLocation[i].x + 20 + generator.nextInt(50)), (pitLocation[i].y+ 20 + generator.nextInt(50)), null);
	    	  //draw number below each pit stating number of stones
	    	   canvas.drawText(""+stoneTracker[i], pitLocation[i].x+50, pitLocation[i].y+120, paint);
	       }

	       	paint.setARGB(255, 0, 0, 255);
	       	paint.setTextSize(40);
	       	//draw store for player 2
	       	canvas.drawBitmap(imageMap[4], pitLocation[13].x, pitLocation[13].y, null);
	       	//draw score for player 2
	       	canvas.drawText(""+stoneTracker[13], pitLocation[13].x+48, pitLocation[13].y+280, paint);
	       	//draw stones for player 2 store
	        for (int x=0; x<stoneTracker[13]; x++)
	    		   canvas.drawBitmap(imageMap[0], (pitLocation[13].x + 20 + generator.nextInt(60)), (pitLocation[13].y+ 20 + generator.nextInt(150)), null);
	   
	       	paint.setARGB(255, 255, 0, 0);
	       	//draw store for player 1
	       	canvas.drawBitmap(imageMap[3], pitLocation[12].x, pitLocation[12].y, null);
	       	//draw score for player 1
	       	canvas.drawText(""+stoneTracker[12], pitLocation[12].x+48, pitLocation[12].y+280, paint);
	       	//draw stones for player 1 store
	        for (int x=0; x<stoneTracker[12]; x++)
	    		   canvas.drawBitmap(imageMap[0], (pitLocation[12].x + 20 + generator.nextInt(60)), (pitLocation[12].y+ 20 + generator.nextInt(150)), null);

	       	//draw player state depending on whose turn it is
	        if (game.isDone())
	        	canvas.drawBitmap(imageMap[7], 300, 0, null);
	        else
	        	{if(playerIndex==0)
	        		canvas.drawBitmap(imageMap[5], 300, 0, null); 
	        	else if (playerIndex==1)
	       			canvas.drawBitmap(imageMap[6], 300, 0, null);}	
	    }
	
	  //Supposed to set the number of stones that start in each pit.  Not working.
	  public void setStartingStoneCount(int x)
		{
			startingStones=x;
		}
		
	  
	  

	/*	Called in update thread.  updateStoneCount calls the game object to
		get updated values for each pit and store.
	*/
	  public void updateStoneCounts()
	  {		
		  int [] temp = new int [6];
		  for(int i =0; i<=1;i++)
		  { 
			  if(i==0)
			  {for(int x=0;x<=5;x++)	
			  	{ 
				  temp=game.getPitsCounts(i);
				  stoneTracker[x]=temp[x];
			  	}}
			  if(i==1)
			  {
				  for(int x=0;x<=5;x++)	
				  	{ 
					  temp=game.getPitsCounts(i);
					  stoneTracker[x+6]=temp[x];
				  	} 
			  }
		  }
		  stoneTracker[12]=game.getScore(0);
		  stoneTracker[13]=game.getScore(1);	  
	  }
	  
	  //The main thread.  It updates the board by calling updateStoneCounts.
	   public void update() {
	        if (eventOccured) 
	        {
	            long now = System.currentTimeMillis();
	            if (now - mLastMove > mMoveDelay) {     
	            	updateStoneCounts();
	                mLastMove = now;
	                eventOccured=false;
	            }
	            mRedrawHandler.sleep(mMoveDelay);
	       }

	    }
	  
	  
}
