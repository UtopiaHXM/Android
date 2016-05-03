package com.example.rule.database;

public class SpecialDayTable {
	private int year;
	private int month;
	private int day;
	private int hour;
	private float atmospheremin;
	private float co2min;
	private float illuminationmin;
	private float wutuhumiditymin;
	private float wututempuraturemin;
	private float youtuhumiditymin;
	private float youturoomhumiditymin;
	private float youturoomtempuraturemin;
	private float yoututempuraturemin;
	private float lightmin;
	private float atmospheremax;
	private float co2max;
	private float illuminationmax;
	private float wutuhumiditymax;
	private float wututempuraturemax;
	private float youtuhumiditymax;
	private float youturoomhumiditymax;
	private float youturoomtempuraturemax;
	private float yoututempuraturemax;
	private float lightmax;
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
	public void setLightMin(int lightmin){
		this.lightmin = lightmin;
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
	public void setAtmosphereMin(float armospheremin){
		this.atmospheremin = armospheremin;
	}
	public void setCO2Min(float co2min){
		this.co2min = co2min;		
	}
	public void setIlluminatonMin(float illuminationmin){
		this.illuminationmin = illuminationmin;
	}
	public void setWutuhumidityMin(float wutuhumiditymin){
		this.wutuhumiditymin = wutuhumiditymin;
	}
	public void setWututempuratureMin(float wututempuraturemin){
		this.wututempuraturemin = wututempuraturemin;
	}
	public void setYoututempuratureMin(float yoututempuraturemin){
		this.yoututempuraturemin = yoututempuraturemin;
	}
	public void setYouturoomhumidityMin(float youturoomhumiditymin){
		this.youturoomhumiditymin = youturoomhumiditymin;
	}
	public void setYoutuhumidityMin(float youtuhumiditymin){
		this.youtuhumiditymin = youtuhumiditymin;
	}
	public void setYouturoomtempuratureMin(float youturoomtempuraturemin){
		this.youturoomtempuraturemin = youturoomtempuraturemin;
	}
	public float getAtmosphereMax(){
		return atmospheremax;
	}
	public float getCO2Max(){
		return co2max;		
	}
	public float getLightMax(){
		return lightmax;
	}
	public float getIlluminatonMax(){
		return illuminationmax;
	}
	public float getWutuhumidityMax(){
		return  wutuhumiditymax;
	}
	public float getWututempuratureMax(){
		return wututempuraturemax;
	}
	public float getYoututempuratureMax(){
		return yoututempuraturemax;
	}
	public float getYouturoomhumidityMax(){
		return youturoomhumiditymax;
	}
	public float getYoutuhumidityMax(){
		return  youtuhumiditymax;
	}
	public float getYouturoomtempuratureMax(){
		return youturoomtempuraturemax;
	}
}
