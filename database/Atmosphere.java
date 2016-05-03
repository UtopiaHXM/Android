package com.example.rule.database;

public class Atmosphere {
	
 private float atmosphere;
 private long time;
 
 public void setAtmosphere(float atmosphere,long time){
	 this.atmosphere = atmosphere;
	 this.time = time;
 }
 public float getAtmosphere(){
	 return atmosphere;
 }
 public long getTime(){
	 return time;
 }

}
