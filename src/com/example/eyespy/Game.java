package com.example.eyespy;

public class Game {
	  private long id;
	  private String dateCreated;
	  private String gameType;
	  private String cropFile;
	  private String picFile;
	  private String hint;
	  private String answer;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getDateCreated() {
	    return dateCreated;
	  }

	  public void setDateCreated(String dateCreated) {
	    this.dateCreated = dateCreated;
	  }
	  
	  public String getGameType() {
	    return gameType;
	  }

	  public void setGameType(String gameType) {
	    this.gameType = gameType;
	  }
		  
	  public String getCropFile() {
	    return cropFile;
	  }

	  public void setCropFile(String cropFile) {
	    this.cropFile = cropFile;
	  }
	  
	  public String getPicFile() {
	    return picFile;
	  }

	  public void setPicFile(String picFile) {
	    this.picFile = picFile;
	  }
	  
	  public String getHint() {
	    return hint;
	  }

	  public void setHint(String hint) {
	    this.hint = hint;
	  }
	  
	  public String getAnswer() {
	    return answer;
	  }

	  public void setAnswer(String answer) {
	    this.answer = answer;
	  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return hint;
	  }
	} 
