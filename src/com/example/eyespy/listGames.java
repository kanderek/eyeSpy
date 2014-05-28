package com.example.eyespy;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class listGames extends Activity {
	private GameDataSource datasource;
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_games);
		
		datasource = new GameDataSource(this);
	    
		
		datasource.open();
	    final List<Game> values = datasource.getAllGames();
		datasource.close();
	    
		CustomList adapter = new CustomList(listGames.this, values);
	    list=(ListView)findViewById(R.id.list);
	    list.setAdapter(adapter);
	    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	//Toast.makeText(listGames.this, "You Clicked at " + values.get(position).getId(), Toast.LENGTH_SHORT).show();
	        	Intent myIntent = new Intent(listGames.this, playGame.class);
				myIntent.putExtra("gameId", values.get(position).getId());
	        	listGames.this.startActivity(myIntent);
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
	    //datasource.open();
	    super.onResume();
	  }


	  @Override 
	  public void onPause(){
		  super.onPause();
		  //datasource.close();
	  }
	        
}
