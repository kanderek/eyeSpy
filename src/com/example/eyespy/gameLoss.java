package com.example.eyespy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class gameLoss extends Activity{
	private GameDataSource datasource;
	private Long gameId;
	
	private ImageView pictureLoss;
	private Button goHomeLoss;
	private Button goBackGameLoss;
	private TextView gameLossGuessText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_loss);
		
		pictureLoss = (ImageView) findViewById(R.id.pictureLoss);
		goHomeLoss = (Button) findViewById(R.id.goHomeLoss);
		goBackGameLoss = (Button) findViewById(R.id.goBackGameLoss);
		gameLossGuessText = (TextView) findViewById(R.id.gameLossGuessText);
		
		datasource = new GameDataSource(this);
	    
		Intent lossIntent = getIntent();
	    gameId = lossIntent.getLongExtra("gameId", -1);
	    String guess = lossIntent.getStringExtra("guess");

	    datasource.open();
		Game game = datasource.getGameWithId(gameId);
		datasource.close();
		
		gameLossGuessText.setText(guess);
		
		if(game.getGameType().equals("I SPY")) {

			pictureLoss.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(game.getPicFile(), 300, 300));
		}
		else if(game.getGameType().equals("ASS OR ELBOW")){

			pictureLoss.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(game.getCropFile(), 300, 300));
		}
		
		createListeners();	
	}
	
	private void createListeners() {

		goHomeLoss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent myIntent = new Intent(gameLoss.this, MainActivity.class);
				gameLoss.this.startActivity(myIntent);
				finish();
			}
		});
		
		goBackGameLoss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent myIntent = new Intent(gameLoss.this, playGame.class);
				myIntent.putExtra("gameId", gameId);
				gameLoss.this.startActivity(myIntent);
				finish();
			
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
