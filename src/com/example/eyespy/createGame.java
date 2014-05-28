package com.example.eyespy;


import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
* Code modified from...
* 	ShootAndCropActivity demonstrates capturing and cropping camera images
* 	- user presses button to capture an image using the device camera
*	- when they return with the captured image Uri, the app launches the crop action intent
* 	- on returning from the crop action, the app displays the cropped image
* 
* 	Sue Smith
* 		Mobiletuts+ Tutorial: Capturing and Cropping an Image with the Android Camera
* 		July 2012
**/

public class createGame extends Activity {
	
	private static final String TAG = "createGame";
	private GameDataSource datasource;
	
	//keep track of camera capture intent 
	final int CAMERA_CAPTURE = 1;
	//keep track of cropping intent
	final int PIC_CROP = 2;

	private Uri picUri;
	private Uri cropUri;
	private String picFile;
	private String cropFile;
	private String inputAnswer;
	private String inputHint;
	private String gameTypeSelected;
	
	//Reference to activity View Widgets Used
	private ImageView picView, cropView;
	private Button captureAndCrop, createGame;
	private RadioGroup gameType;
	private EditText hint, answer;
	
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      setContentView(R.layout.create_game);
      datasource = new GameDataSource(this);
      
      picView = (ImageView)findViewById(R.id.picture);
      cropView = (ImageView)findViewById(R.id.crop);
      
      hint = (EditText)findViewById(R.id.hintText);
      answer = (EditText)findViewById(R.id.answerText);
      gameType = (RadioGroup)findViewById(R.id.gameType);
      gameType.requestFocusFromTouch();
      
      int checkedRadioButtonID = gameType.getCheckedRadioButtonId();
      setGameTypeDetails(checkedRadioButtonID);
      
      gameType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
       
	       @Override
	       public void onCheckedChanged(RadioGroup gameType, int CheckedId) {
	         // TODO Auto-generated method stub
		     // Check which radio button was clicked
	    	   gameType.requestFocusFromTouch();
	    	   setGameTypeDetails(CheckedId);
	       }
      });
      
      // BUTTONS
      captureAndCrop = (Button)findViewById(R.id.captureAndCrop); 
      captureAndCrop.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handleCaptureAndCrop();
			}
      });
      
      createGame = (Button)findViewById(R.id.createGame);
      createGame.setOnClickListener(new OnClickListener(){
    	   	public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isCompleteGame()){
					storeGameInDB();
					Toast toast = Toast.makeText(createGame.this, "Game Created!", Toast.LENGTH_SHORT);
					toast.show();
					Intent goHome = new Intent(createGame.this, MainActivity.class);
					createGame.this.startActivity(goHome);
					//finish();
				}
				else {
					Toast toast = Toast.makeText(createGame.this, "Please Complete All Fields to Create a Game.", Toast.LENGTH_SHORT);
				    toast.show();
					//Log.d(TAG, "please complete all fields to create a game");
				}
			}
      });

      if (savedInstanceState != null) {
    	  picUri = savedInstanceState.getParcelable("picUri");
    	  cropFile = savedInstanceState.getString("cropFile");
    	  picFile = savedInstanceState.getString("picFile");
    	  answer.setText(savedInstanceState.getString("inputAnswer"));
    	  hint.setText(savedInstanceState.getString("inputHint"));
    	  gameType.check(savedInstanceState.getInt("checkedRadioId"));
      } 
  }
  
  private void setGameTypeDetails(int checkedId){
	  switch(checkedId) {
        case R.id.iSpy:
            gameTypeSelected = "I SPY";
            if(picFile != null)
            	displayImages(picFile, cropFile);
            	Log.d(TAG, gameTypeSelected + " was selected");
            break;
        case R.id.assOrElbow:
            gameTypeSelected = "ASS OR ELBOW";
            if(picFile != null)
            	displayImages(cropFile, picFile);
            	Log.d(TAG, gameTypeSelected + " was selected");
            break;
    }
  }
  
  protected void onSaveInstanceState(Bundle outState)
  {
      outState.putParcelable("picUri", picUri);
      outState.putString("cropFile", cropFile);
      outState.putString("picFile", picFile);
      outState.putString("inputAnswer", answer.getText().toString());
      outState.putString("inputHint", hint.getText().toString());
      outState.putInt("checkedRadioId", gameType.getCheckedRadioButtonId());
      
  }
  
  
  private boolean isCompleteGame() {
	  int errorCount = 0;
	  
	  if(answer.getText().toString().trim().length() == 0) {
		  Log.d(TAG, "The answer edit text field is empty");
		  errorCount++;
	  }
	  else {
		  inputAnswer = answer.getText().toString();
	  }
	  
	  if(hint.getText().toString().trim().length() == 0) {
		  Log.d(TAG, "The hint edit text field is empty");
		  errorCount++;
	  }
	  else {
		  inputHint = hint.getText().toString();
	  }
	  
	  if(cropFile == null) {
		  Log.d(TAG, "The crop was not completed");
		  errorCount++;
	  }
	  
	  if(picFile == null) {
		  Log.d(TAG, "The picture capture was not completed");
		  errorCount++;
	  }
	  
	  if(gameTypeSelected == null) {
		  Log.d(TAG, "The gameType was not selected");
		  errorCount++;
	  }
	  
	  if(errorCount > 0){
		  return false;
	  }
	  else {
		  return true;
	  }
}
  
  private void storeGameInDB() {
	 
	  Time now = new Time();
	  now.setToNow();
	  String dateCreated = now.format("%Y-%m-%d %H:%M:%S");
	  
	  /*
	  Log.d(TAG, "You are going to store this game data in the database eyeSpy.db");
	  Log.d(TAG, dateCreated );
	  Log.d(TAG, gameTypeSelected );
	  Log.d(TAG, cropFile );
	  Log.d(TAG, picFile );
	  Log.d(TAG, inputHint );
	  Log.d(TAG, inputAnswer );
	  */
     datasource.open();
	 datasource.createGame(dateCreated, gameTypeSelected, cropFile, picFile, inputHint, inputAnswer);
	 datasource.close();
  }
  
  private String getImagesFolderPath() {
	  	File returnImagesFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "eyeSpy");
        if (!returnImagesFolder.exists())
        {
            //displayToast("makingDirectory");
        	returnImagesFolder.mkdirs();
        }
        return returnImagesFolder.getPath(); 
  }
  
  private void handleCaptureAndCrop(){
		try {
		  	//use standard intent to capture an image
		  	Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		  
		  	datasource.open();
		  	int imageNumber = datasource.getLastId() + 1;
		  	datasource.close();
		  	String DirPath = getImagesFolderPath();
            picFile = DirPath + "/game_" + String.valueOf(imageNumber) + "_pic.jpg";
            cropFile = DirPath + "/game_" + String.valueOf(imageNumber) + "_crop.jpg";
            
            Log.d(TAG, "handleCapture and Crop : " + picFile + " : " + cropFile);
            
            File createPic = new File(picFile);//imagesFolder, picFile);
            picUri = Uri.fromFile(createPic);

            //we will handle the returned data in onActivityResult
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
		    startActivityForResult(captureIntent, CAMERA_CAPTURE);
		}
	    catch(ActivityNotFoundException anfe) {
			//display an error message
			String errorMessage = "Whoops - your device doesn't support capturing images!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
  }
  
  /**
   * Handle user returning from both capturing and cropping the image
   */
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  	if (resultCode == RESULT_OK) {
  		//user is returning from capturing an image using the camera
  		if(requestCode == CAMERA_CAPTURE){
  			
  			//Log.d(TAG, data.toString());
  			performCrop();
  		}
  		//user is returning from cropping the image
  		else if(requestCode == PIC_CROP){
  			
  			Log.d(TAG, "Data returned from cropImage : " + data.toString());
  			int checkedId = gameType.getCheckedRadioButtonId();
  			setGameTypeDetails(checkedId);
  		}
  	}
  }
  
  private void displayImages(String pic1File, String pic2File){

	  picView.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(pic1File, 300, 300));

	  cropView.setImageBitmap(BitmapUtility.decodeSampledBitmapFromFile(pic2File, 100, 100));
  }
  
  /**
   * Helper method to carry out crop operation
   */
  private void performCrop(){
  	//take care of exceptions
	  Log.d(TAG, "picUri in performCrop(): " + picUri);
  	try {
  		//call the standard crop action intent (the user device may not support it)
	  		File createCrop = new File(cropFile);
	        cropUri = Uri.fromFile(createCrop);
  			Intent cropIntent = new Intent("com.android.camera.action.CROP"); 
	    	//indicate image type and Uri
	    	cropIntent.setDataAndType(picUri, "image/*");
	    	//set crop properties
	    	cropIntent.putExtra("output", cropUri);
	    	cropIntent.putExtra("crop", "true");
	    	//indicate aspect of desired crop
	    	cropIntent.putExtra("aspectX", 1);
	    	cropIntent.putExtra("aspectY", 1);
	    	//indicate output X and Y
	    	cropIntent.putExtra("outputX", 256);
	    	cropIntent.putExtra("outputY", 256);
	    	//retrieve data on return
	    	cropIntent.putExtra("return-data", true);
	    	//start the activity - we handle returning in onActivityResult
	        startActivityForResult(cropIntent, PIC_CROP);  
  	}
  	//respond to users whose devices do not support the crop action
  	catch(ActivityNotFoundException anfe){
  		//display an error message
  		String errorMessage = "Whoops - your device doesn't support the crop action!";
  		Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
  		toast.show();
  	}
  }
 
}

