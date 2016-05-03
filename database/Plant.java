package com.example.rule.database;

public class Plant {

	private String planttype;//植物中类：有土，无土
	
	private String plantname;//植物名称
	
	private String tempuraturemin;//温度
	
	private String yoututempuraturemin;//有土温度
	
	private String humiditymin;//湿度                                     
	
	private String youtuhumiditymin;//有土湿度
	
	private String atmospheremin;//大气压
	
	private String lightmin;
	
	private String tempuraturemax;
	
	private String yoututempuraturemax;
	
	private String humiditymax;
	
	private String youtuhumiditymax;
	
	private String atmospheremax;
	
	private String lightmax;
	
	private String co2min;
	
	private String co2max;

	//植物中类：有土，无土
	public void setPlantType(String planttype)
	{
		this.planttype=planttype;
	}
	public String  getPlantType()
	{
		return planttype;
	}

	//植物名称
	public void setPlantName(String plantname) {
		this.plantname = plantname;
	}
	public String getPlantName() {
		return plantname;
	}

	//温度
	public void setTempuratureMin(String tempurature) {
		this.tempuraturemin = tempurature;
	}
	
	
	public String getTempuratureMin() {
		return tempuraturemin;
	}

	//湿度
	public void setHumidityMin(String humidity) {
		this.humiditymin = humidity;
	}
	public String getHumidityMin() {
		return humiditymin;
	}
	//大气压
	public void setAtmosphereMin(String atmosphere) {
		this.atmospheremin = atmosphere;
	}
	public String getAtmosphereMin() {
		return atmospheremin;
	}
	public void setLightMin(String light){
		this.lightmin = light;
	}
	public String getLightMin(){
		return lightmin;
	}
	public void setYoututempuraturemin(String yoututempuraturemin){
		this.yoututempuraturemin = yoututempuraturemin;
	}
	public String getYoututempuraturemin(){
		return yoututempuraturemin;
	}
	public void setYoutuhumiditymin(String youtuhumiditymin){
		this.youtuhumiditymin = youtuhumiditymin;
	}
	public String getYoutuhumidityemin(){
		return youtuhumiditymin;
	}
	public void setco2min(String co2min){
		this.co2min = co2min;
	}
	public String getco2min(){
		return co2min;
	}
	
	
	
	
	
	
	
	
	public void setTempuratureMax(String tempurature) {
		this.tempuraturemax = tempurature;
	}
	
	
	public String getTempuratureMax() {
		return tempuraturemax;
	}

	//湿度
	public void setHumidityMax(String humidity) {
		this.humiditymax = humidity;
	}
	public String getHumidityMax() {
		return humiditymax;
	}
	//大气压
	public void setAtmosphereMax(String atmosphere) {
		this.atmospheremax = atmosphere;
	}
	public String getAtmosphereMax() {
		return atmospheremax;
	}
	public void setLightMax(String light){
		this.lightmax = light;
	}
	public String getLightMax(){
		return lightmax;
	}
	public void setYoututempuraturemax(String yoututempuraturemax){
		this.yoututempuraturemax = yoututempuraturemax;
	}
	public String getYoututempuraturemax(){
		return yoututempuraturemax;
	}
	public void setYoutuhumiditymax(String youtuhumiditymax){
		this.youtuhumiditymax = youtuhumiditymax;
	}
	public String getYoutuhumidityemax(){
		return youtuhumiditymax;
	}
	public void setco2max(String co2max){
		this.co2max = co2max;
	}
	public String getco2max(){
		return co2max;
	}
	
	
	
	
}
