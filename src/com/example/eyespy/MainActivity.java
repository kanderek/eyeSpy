package com.example.eyespy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	public Button chooseGame, createGame, test;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createGame = (Button) findViewById(R.id.gotoCreateGame);
		chooseGame = (Button) findViewById(R.id.gotoListGames);
		//test = (Button) findViewById(R.id.test);
		createListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void createListeners() {
		/*
		test.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// load activity with list of games
				Intent myIntent = new Intent(MainActivity.this, testActivity.class);
				//myIntent.putExtra("key", value); //Optional parameters
				MainActivity.this.startActivity(myIntent);
				finish();
			}
		});*/
		
		createGame.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				Intent myIntent = new Intent(MainActivity.this, createGame.class);
				//myIntent.putExtra("key", value); //Optional parameters
				MainActivity.this.startActivity(myIntent);
				//finish();
				
				// TODO Auto-generated method stub
				//check if phone has camera or update manifest
				//checkCameraHardware(R)
				//launch Camera -> 
				//Crop Image -> 
				//text input answer and hint -> 
				//store game info
			}
		});

		chooseGame.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// load activity with list of games
				Intent myIntent = new Intent(MainActivity.this, listGames.class);
				MainActivity.this.startActivity(myIntent);
				//finish();
			}
		});
		
		
	}
	

}
