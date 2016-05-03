package com.example.rule.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.text.format.Time;
/**
 * 
 * 
 * 数据库工具类 继承SQLiteOpenHelper类  用于创建数据库及数据库表
 * @author Administrator
 *
 */
class  DBOpenHelper extends SQLiteOpenHelper{

	
	//规则属性信息
	private static final String p_plant="p_plant";
	
	private static final String plantType = "plantType";
	private static final String plantName = "plantName";
	private static final String tempuraturemin="tempuraturemin";
	private static final String tempuraturemax="tempuraturemax";
	private static final String humiditymin = "humiditymin";
	private static final String humiditymax = "humiditymax";
	private static final String atmospheremin = "atmospheremin";
	private static final String atmospheremax = "atmospheremax";
	private static final String lightmin = "lightmin";
	private static final String lightmax = "lightmax";
	private static final String youtu_tempuraturemin = "yoututempuraturemin";
	private static final String youtu_tempuraturemax = "yoututempuraturemax";
	private static final String youtu_humiditymin = "youtuhumiditymin";
	private static final String youtu_humiditymax = "youtuhumiditymax";
	private static final String co2min = "co2min";
	private static final String co2max = "co2max";
	private static final String p_plant_create =" create table "+ p_plant +" ("+plantType +" text not null,"+tempuraturemin+" text,"+tempuraturemax+" text,"+humiditymin+" text,"+humiditymax+" text,"+youtu_tempuraturemin+" text,"+youtu_tempuraturemax+" text,"+youtu_humiditymin+" text,"+youtu_humiditymax+" text,"+atmospheremin+" text,"+atmospheremax+" text,"+lightmin+" text,"+lightmax+" text,"+co2min+" text,"+co2max+" text,primary key("+plantType+" ))";
	private static final String atmosphere_create =" create table "+ "atmosphere" +" ("+"Atmosphere" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
	private static final String co2_create =" create table "+ "co2" +" ("+"CO2" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
	private static final String light_create =" create table "+ "light" +" ("+"Light" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
	private static final String illumination_create =" create table "+ "illumination" +" ("+"Illumination" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
	private static final String wutuhumidity_create =" create table "+ "wutuhumidity" +" ("+"Wutuhumidity" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
	private static final String wututempurature_create =" create table "+ "wututempurature" +" ("+"Wututempurature" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
	private static final String yoututempurature_create =" create table "+ "yoututempurature" +" ("+"Yoututempurature" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
	private static final String youtuhumidity_create =" create table "+ "youtuhumidity" +" ("+"Youtuhumidity" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
    private static final String youturoomtempurature_create =" create table "+ "youturoomtempurature" +" ("+"Youturoomtempurature" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))";
	private static final String youturoomhumidity_create =" create table "+ "youturoomhumidity" +" ("+"Youturoomhumidity" +" float not null,"+"Time"+" long not null,primary key("+"Time"+" ))"; 
	private static final String daytable_create =" create table "+ "daytable" +" ("+"Year" +" integer not null,"+"Month" +" integer not null,"+"Day" +" integer not null,"+"Hour" +" integer not null,"+"Atmosphere"+" float not null,"+"CO2"+" float not null,"+"Light"+" float not null,"+"Illumination"+" float not null,"+"Wutuhumidity"+" float not null,"+"Wututempurature"+" float not null,"+"Youtuhumidity"+" float not null,"+"Youturoomhumidity"+" float not null,"+"Youturoomtempurature"+" float not null,"+"Yoututempurature"+" float not null,primary key("+"Day"+" ))"; 
	private static final String monthtable_create =" create table "+ "monthtable" +" ("+"Year" +" long not null,"+"Month" +" integer not null,"+"Day" +" integer not null,"+"Atmosphere"+" float not null,"+"CO2"+" float not null,"+"Light"+" float not null,"+"Illumination"+" float not null,"+"Wutuhumidity"+" float not null,"+"Wututempurature"+" float not null,"+"Youtuhumidity"+" float not null,"+"Youturoomhumidity"+" float not null,"+"Youturoomtempurature"+" float not null,"+"Yoututempurature"+" float not null,primary key("+"Month"+" ))"; 
	private static final String yeartable_create =" create table "+ "yeartable" +" ("+"Year" +" long not null,"+"Month" +" integer not null,"+"Atmosphere"+" float not null,"+"CO2"+" float not null,"+"Light"+" float not null,"+"Illumination"+" float not null,"+"Wutuhumidity"+" float not null,"+"Wututempurature"+" float not null,"+"Youtuhumidity"+" float not null,"+"Youturoomhumidity"+" float not null,"+"Youturoomtempurature"+" float not null,"+"Yoututempurature"+" float not null,primary key("+"Year"+" ))"; 
	private static final String musictable_create = " create table "+ "musictable" +" ("+"volumn" +" integer not null,"+"lujing"+" long not null,primary key("+"lujing"+" ))"; 
	private static final String specialdaytable_create = " create table "+ "specialdaytable" +" ("+"Year" +" integer not null,"+"Month"+" integer not null,"+"Day"+" integer not null,"+"Hour"+" integer not null,"+"Atmospheremin"+" integer not null,"+"Atmospheremax"+" integer not null,"+"CO2min"+" integer not null,"+"CO2max"+" integer not null,"+"Illuminationmin"+" integer not null,"+"Illuminationmax"+" integer not null,"+"Wutuhumiditymin"+" integer not null,"+"Wututempuraturemin"+" integer not null,"+"Wututempuraturemax"+" integer not null,"+"Yoututempuraturemin"+" integer not null,"+"Yoututempuraturemax"+" integer not null,"+"Youturoomhumiditymin"+" integer not null,"+"Youturoomhumiditymax"+" integer not null,"+"Youtuhumiditymin"+" integer not null,"+"Youtuhumiditymax"+" integer not null,"+"Youturoomtempuraturemin"+" integer not null,"+"Youturoomtempuraturemax"+" integer not null,primary key("+"Day"+" ))"; 
	private static final String specialmonthtable_create = " create table "+ "specialmonthtable" +" ("+"Year" +" integer not null,"+"Month"+" integer not null,"+"Day"+" integer not null,"+"Hour"+" integer not null,"+"Atmospheremin"+" integer not null,"+"Atmospheremax"+" integer not null,"+"CO2min"+" integer not null,"+"CO2max"+" integer not null,"+"Illuminationmin"+" integer not null,"+"Illuminationmax"+" integer not null,"+"Wutuhumiditymin"+" integer not null,"+"Wututempuraturemin"+" integer not null,"+"Wututempuraturemax"+" integer not null,"+"Yoututempuraturemin"+" integer not null,"+"Yoututempuraturemax"+" integer not null,"+"Youturoomhumiditymin"+" integer not null,"+"Youturoomhumiditymax"+" integer not null,"+"Youtuhumiditymin"+" integer not null,"+"Youtuhumiditymax"+" integer not null,"+"Youturoomtempuraturemin"+" integer not null,"+"Youturoomtempuraturemax"+" integer not null,primary key("+"Month"+" ))";
	private static final String specialyeartable_create = " create table "+ "specialyeartable" +" ("+"Year" +" integer not null,"+"Month"+" integer not null,"+"Day"+" integer not null,"+"Hour"+" integer not null,"+"Atmospheremin"+" integer not null,"+"Atmospheremax"+" integer not null,"+"CO2min"+" integer not null,"+"CO2max"+" integer not null,"+"Illuminationmin"+" integer not null,"+"Illuminationmax"+" integer not null,"+"Wutuhumiditymin"+" integer not null,"+"Wututempuraturemin"+" integer not null,"+"Wututempuraturemax"+" integer not null,"+"Yoututempuraturemin"+" integer not null,"+"Yoututempuraturemax"+" integer not null,"+"Youturoomhumiditymin"+" integer not null,"+"Youturoomhumiditymax"+" integer not null,"+"Youtuhumiditymin"+" integer not null,"+"Youtuhumiditymax"+" integer not null,"+"Youturoomtempuraturemin"+" integer not null,"+"Youturoomtempuraturemax"+" integer not null,primary key("+"Year"+" ))";
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {//直到获取获取数据库中可读/可写对象时，该数据库才被建立或者打开
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//数据库首次被创建时候执行创建数据库表的操作
		// TODO Auto-generated method stub
		db.execSQL(p_plant_create);
		db.execSQL(atmosphere_create);
		db.execSQL(co2_create);
		db.execSQL(light_create);
		db.execSQL(illumination_create);
		db.execSQL(wutuhumidity_create);
		db.execSQL(wututempurature_create);
		db.execSQL(yoututempurature_create);
		db.execSQL(youtuhumidity_create);
		db.execSQL(youturoomtempurature_create);
		db.execSQL(youturoomhumidity_create);
		db.execSQL(daytable_create);
		db.execSQL(monthtable_create);
		db.execSQL(yeartable_create);
		db.execSQL(musictable_create);
		db.execSQL(specialdaytable_create);
		db.execSQL(specialmonthtable_create);
		db.execSQL(specialyeartable_create);				
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS tabella");
        onCreate(db);
	}
	
}