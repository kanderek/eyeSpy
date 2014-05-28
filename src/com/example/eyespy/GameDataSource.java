package com.example.eyespy;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameDataSource {

  private static final String TAG = "GameDataSource";
  
  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { 
			  MySQLiteHelper.COLUMN_ID,
			  MySQLiteHelper.COLUMN_DATE_CREATED,
			  MySQLiteHelper.COLUMN_TYPE,
			  MySQLiteHelper.COLUMN_CROP_FILE,
			  MySQLiteHelper.COLUMN_PIC_FILE,
			  MySQLiteHelper.COLUMN_HINT,
			  MySQLiteHelper.COLUMN_ANSWER
		  };

  public GameDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
    Log.d(TAG, "dbHelper created");
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
    Log.d(TAG, "database opened");
    
  }

  public void close() {
    dbHelper.close();
  }

  public Game createGame(
		  String dateCreated, 
		  String gameType, 
		  String cropFile, 
		  String picFile,
		  String hint,
		  String answer) {
	  
	//Log.d(TAG, database.getPath());
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_DATE_CREATED, dateCreated);
    values.put(MySQLiteHelper.COLUMN_TYPE, gameType);
    values.put(MySQLiteHelper.COLUMN_CROP_FILE, cropFile);
    values.put(MySQLiteHelper.COLUMN_PIC_FILE, picFile);
    values.put(MySQLiteHelper.COLUMN_HINT, hint);
    values.put(MySQLiteHelper.COLUMN_ANSWER, answer);
    
    long insertId = database.insertOrThrow(MySQLiteHelper.TABLE_GAMES, null,
        values);
    //Cursor findEntry = db.query("sku_table", columns, "owner=?", new String[] { owner }, null, null, null);
    
    Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
    cursor.moveToFirst();
    Game newGame = cursorToGame(cursor);
    cursor.close();
    return newGame;
  }

  public void deleteGame(Game game) {
    long id = game.getId();
    System.out.println("Game deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_GAMES, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public int getLastId() {
	Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES,  new String[] {MySQLiteHelper.COLUMN_ID}, null, null, null, null, null);  
	long lastIndex = 0;
	if (cursor!=null && cursor.getCount()>0) {
		if(cursor.moveToLast()) { 
			lastIndex = cursor.getLong(0);
		}
	}
	cursor.close();
	return (int)lastIndex;
  }
  
  public List<Game> getAllGames() {
    List<Game> games = new ArrayList<Game>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Game game = cursorToGame(cursor);
      games.add(game);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return games;
  }
  
  public Game getGameWithId(long id){
	  
	  Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES, allColumns, MySQLiteHelper.COLUMN_ID +" = "+id, null, null, null, null);
	  
	  cursor.moveToFirst();
	  Game game = cursorToGame(cursor);
	  cursor.close();
	  
	  return game;
  }

  private Game cursorToGame(Cursor cursor) {
    Game game = new Game();
    game.setId(cursor.getLong(0));
    game.setDateCreated(cursor.getString(1));
    game.setGameType(cursor.getString(2));
    game.setCropFile(cursor.getString(3));
    game.setPicFile(cursor.getString(4));
    game.setHint(cursor.getString(5));
    game.setAnswer(cursor.getString(6));
    
    return game;
  }
} 
