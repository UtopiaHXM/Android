package com.example.rule.database;

public class YouTuHumidity {
	private float humidity;
	private long time;
	public void setHumidity(float humidity,long time){
		this.humidity = humidity;
		this.time = time;
	}
	 public float getHumidity(){
		 return humidity;
	 }
	 public long getTime(){
		 return time;
	 }
}
