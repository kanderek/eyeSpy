package com.example.eyespy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

  //private static final String TAG = "MySQLiteHelper";
  
  public static final String TABLE_GAMES = "games";
  public static final String COLUMN_ID = "_id";
  public static final String COLUMN_DATE_CREATED = "dateCreated";
  public static final String COLUMN_TYPE = "type";
  public static final String COLUMN_CROP_FILE = "cropFile";
  public static final String COLUMN_PIC_FILE = "picFile";
  public static final String COLUMN_HINT = "hint";
  public static final String COLUMN_ANSWER = "answer";

  private static final String DATABASE_NAME = "eyeSpy.db";
  private static final int DATABASE_VERSION = 3;

  // Database creation sql statement
  private static final String DATABASE_CREATE = "create table "
      + TABLE_GAMES + " ( " + 
		  COLUMN_ID + " integer primary key autoincrement, " + 
          COLUMN_DATE_CREATED + " text not null, " + 
		  COLUMN_TYPE + " text not null, " + 
		  COLUMN_CROP_FILE + " text not null, " + 
		  COLUMN_PIC_FILE + " text not null, " + 
		  COLUMN_HINT + " text not null, " + 
		  COLUMN_ANSWER + " text not null );";
 

  public MySQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    //Log.d(TAG, "in MySQLiteHelper " + DATABASE_CREATE);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
	//Log.d(TAG, "in MySQLiteHelper onCreate " + DATABASE_CREATE);
    database.execSQL(DATABASE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(MySQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
    onCreate(db);
  }

} 
