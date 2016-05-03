package com.example.rule.database;

public class Illumination {
	private float illumination;
	private long time;
	public void setIllumination(float illumination,long time){
		this.illumination = illumination;
		this.time = time;
	}
	 public float getIllumination(){
		 return illumination;
	 }
	 public long getTime(){
		 return time;
	 }
}
