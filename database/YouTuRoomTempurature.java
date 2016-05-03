package com.example.rule.database;

public class YouTuRoomTempurature {
	private float tempurature;
	private long time;
	public void setTempurature(float tempurature,long time){
		this.tempurature = tempurature;
		this.time = time;
	}
	 public float getTempurature(){
		 return tempurature;
	 }
	 public long getTime(){
		 return time;
	 }
}
