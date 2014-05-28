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

public class gameWon extends Activity{
	private GameDataSource datasource;
	
	public ImageView pictureWin;
	public TextView gameWinAnswerText;
	public Button goHomeWin;
	public Button goGameListWin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_won);
		
		pictureWin = (ImageView) findViewById(R.id.pictureWin);
		gameWinAnswerText = (TextView) findViewById(R.id.gameWinAnswerText);
		goHomeWin = (Button) findViewById(R.id.goHomeWin);
		goGameListWin = (Button) findViewById(R.id.goGameListWin);
		
		datasource = new GameDataSource(this);
	    
	    final Long gameId = getIntent().getLongExtra("gameId", -1);

	    datasource.open();
		Game game = datasource.getGameWithId(gameId);
		datasource.close();
		
		if(game.getGameType().equals("I SPY")) {

			pictureWin.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(game.getCropFile(), 300, 300));
		}
		else if(game.getGameType().equals("ASS OR ELBOW")){

		    pictureWin.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(game.getPicFile(), 300, 300));
		}
		
		gameWinAnswerText.setText(game.getAnswer());
		createListeners();
		
	}
	
	private void createListeners() {

		goHomeWin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent myIntent = new Intent(gameWon.this, MainActivity.class);
				gameWon.this.startActivity(myIntent);
				finish();
			}
		});
		
		goGameListWin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent myIntent = new Intent(gameWon.this, listGames.class);
				gameWon.this.startActivity(myIntent);
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
