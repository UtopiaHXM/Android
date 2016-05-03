package com.example.rule.database;

public class DayTable {
	private int year;
	private int month;
	private int day;
	private int hour;
	private float atmosphere;
	private float co2;
	private float illumination;
	private float wutuhumidity;
	private float wututempurature;
	private float youtuhumidity;
	private float youturoomhumidity;
	private float youturoomtempurature;
	private float yoututempurature;
	private float light;
	public void setYear(int year){
		this.year = year;
	}
	public void setMonth(int month){
		this.month = month;
	}
	public void setDay(int day){
		this.day = day;
	}
	public void setHour(int hour){
		this.hour = hour;
	}
	public void setLight(int light){
		this.light = light;
	}
	public int getYear(){
		return year;
	}
	public int getMonth(){
		return month;
	}
	public int getDay(){
		return day;
	}
	public int getHour(){
		return hour;
	}
	public void setAtmosphere(float armosphere){
		this.atmosphere = armosphere;
	}
	public void setCO2(float co2){
		this.co2 = co2;		
	}
	public void setIlluminaton(float illumination){
		this.illumination = illumination;
	}
	public void setWutuhumidity(float wutuhumidity){
		this.wutuhumidity = wutuhumidity;
	}
	public void setWututempurature(float wututempurature){
		this.wututempurature = wututempurature;
	}
	public void setYoututempurature(float yoututempurature){
		this.yoututempurature = yoututempurature;
	}
	public void setYouturoomhumidity(float youturoomhumidity){
		this.youturoomhumidity = youturoomhumidity;
	}
	public void setYoutuhumidity(float youtuhumidity){
		this.youtuhumidity = youtuhumidity;
	}
	public void setYouturoomtempurature(float youturoomtempurature){
		this.youturoomtempurature = youturoomtempurature;
	}
	public float getAtmosphere(){
		return atmosphere;
	}
	public float getCO2(){
		return co2;		
	}
	public float getLight(){
		return light;
	}
	public float getIlluminaton(){
		return illumination;
	}
	public float getWutuhumidity(){
		return  wutuhumidity;
	}
	public float getWututempurature(){
		return wututempurature;
	}
	public float getYoututempurature(){
		return yoututempurature;
	}
	public float getYouturoomhumidity(){
		return youturoomhumidity;
	}
	public float getYoutuhumidity(){
		return  youtuhumidity;
	}
	public float getYouturoomtempurature(){
		return youturoomtempurature;
	}
}
