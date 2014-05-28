package com.example.eyespy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class playGame extends Activity {
	//private static final String TAG = "playGame";
	private GameDataSource datasource;
	
	private ImageView picturePlay;
	private TextView hintTextPlay;
	private TextView gameTypeTextPlay;
	private TextView answerFormatPlay;
	private EditText answerTextPlay;
	private Button submitAnswer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_game);
		
		picturePlay = (ImageView) findViewById(R.id.picturePlay);
		hintTextPlay = (TextView) findViewById(R.id.hintTextPlay);
		gameTypeTextPlay = (TextView) findViewById(R.id.gameTypeTextPlay);
		answerFormatPlay = (TextView) findViewById(R.id.answerFormatPlay);
		answerTextPlay = (EditText) findViewById(R.id.answerTextPlay);
		
		datasource = new GameDataSource(this);

	    final Long gameId = getIntent().getLongExtra("gameId", -1);
	    
	    datasource.open();
		final Game game = datasource.getGameWithId(gameId);
		datasource.close();
		
		hintTextPlay.append("    " + game.getHint());
		gameTypeTextPlay.setText("Game Type: " + game.getGameType());
		
		String maskedAnswer = maskAnswer(game.getAnswer());
		answerFormatPlay.append("    " + maskedAnswer);
		
		//Log.d(TAG, "gameType is " + game.getGameType());
		if(game.getGameType().equals("I SPY")) {

			picturePlay.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(game.getPicFile(), 300, 300));
		}
		else if(game.getGameType().equals("ASS OR ELBOW")){

			picturePlay.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(game.getCropFile(), 300, 300));
		}
		
		
		submitAnswer = (Button) findViewById(R.id.submitAnswer);
		submitAnswer.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if(isCorrect(game)){
					Intent winIntent = new Intent(playGame.this, gameWon.class);
					winIntent.putExtra("gameId", gameId); 
					playGame.this.startActivity(winIntent);
					finish();
				}
				else {
					Intent lossIntent = new Intent(playGame.this, gameLoss.class);
					lossIntent.putExtra("gameId", gameId);
					lossIntent.putExtra("guess", answerTextPlay.getText().toString());
					playGame.this.startActivity(lossIntent);
					finish();
				}
			}
		});
	}

	public String maskAnswer(String answer){
		String blanks = "";
		for(int i=0; i< answer.length(); i++){
			if(Character.toString(answer.charAt(i)) == " "){
				blanks += "   ";
			}
			else{
				blanks += " _ ";
			}
		}
		return blanks;
	}
	
	public boolean isCorrect(Game game){
		String answer = game.getAnswer();
		String guess = answerTextPlay.getText().toString();
		answer = answer.replaceAll("\\s+","");
		guess = guess.replaceAll("\\s+", "");
		
		return answer.equalsIgnoreCase(guess);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
