package com.example.rule.database;

public class Plant {

	private String planttype;//ֲ�����ࣺ����������
	
	private String plantname;//ֲ������
	
	private String tempuraturemin;//�¶�
	
	private String yoututempuraturemin;//�����¶�
	
	private String humiditymin;//ʪ��                                     
	
	private String youtuhumiditymin;//����ʪ��
	
	private String atmospheremin;//����ѹ
	
	private String lightmin;
	
	private String tempuraturemax;
	
	private String yoututempuraturemax;
	
	private String humiditymax;
	
	private String youtuhumiditymax;
	
	private String atmospheremax;
	
	private String lightmax;
	
	private String co2min;
	
	private String co2max;

	//ֲ�����ࣺ����������
	public void setPlantType(String planttype)
	{
		this.planttype=planttype;
	}
	public String  getPlantType()
	{
		return planttype;
	}

	//ֲ������
	public void setPlantName(String plantname) {
		this.plantname = plantname;
	}
	public String getPlantName() {
		return plantname;
	}

	//�¶�
	public void setTempuratureMin(String tempurature) {
		this.tempuraturemin = tempurature;
	}
	
	
	public String getTempuratureMin() {
		return tempuraturemin;
	}

	//ʪ��
	public void setHumidityMin(String humidity) {
		this.humiditymin = humidity;
	}
	public String getHumidityMin() {
		return humiditymin;
	}
	//����ѹ
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

	//ʪ��
	public void setHumidityMax(String humidity) {
		this.humiditymax = humidity;
	}
	public String getHumidityMax() {
		return humiditymax;
	}
	//����ѹ
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
