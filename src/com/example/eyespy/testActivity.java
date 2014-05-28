package com.example.eyespy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class testActivity extends Activity {
	private static final String TAG = "testActivity";
	private GameDataSource datasource;
	public int newIndex;
	public Button getIndex;
	public Button addRecord;
	public Button deleteRecord;
	public TextView indexText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_activity);
		
		datasource = new GameDataSource(this);
	    datasource.open();
		
	    addRecord = (Button) findViewById(R.id.addRecord);
	    
	    deleteRecord = (Button) findViewById(R.id.deleteRecord);
	    
		getIndex = (Button) findViewById(R.id.getIndex);
		indexText = (TextView) findViewById(R.id.indexText);
	
		getIndex.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				newIndex = datasource.getLastId();
				indexText.setText(Integer.toString(newIndex));
			}
		});
		
		addRecord.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Game game = null;
				String gameType = "I SPY";
				String dateCreated = "2013-12-16 5:13:13";
				String picFile = "game_X_pic.jpg";
				String cropFile = "game_X_crop.jpg";
				String hint = "This is a test hint";
				String answer = "This is a test answer";
				game = datasource.createGame(
						  dateCreated, 
						  gameType, 
						  cropFile, 
						  picFile,
						  hint,
						  answer); 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
  @Override
  protected void onResume() {
    datasource.open();
    super.onResume();
  }


  @Override 
  public void onPause(){
	  super.onPause();
	  datasource.close();
  }
	
}
