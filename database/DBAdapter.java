package com.example.rule.database;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import android.R.integer;
import android.animation.TimeAnimator.TimeListener;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


public class DBAdapter {

	private static final String DB="myDB";
	private static final int version=2;
	private static final String p_plant="p_plant";
	private static final String LAB="lab_info";

	private final Context context;
	private static SQLiteDatabase mydb;
	private DBOpenHelper dbOpenHelper;
	
	
	
	public DBAdapter(Context context){
		this.context = context;
		dbOpenHelper = new DBOpenHelper(context, DB, null, version);
		
	}
	/**
	 * 
	 * 打开或创建数据库
	 * @return
	 */
	public SQLiteDatabase open(){//创建并打开数据库;
		dbOpenHelper = new DBOpenHelper(context, DB, null, version);
		try {
			mydb = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			// TODO: handle exception
			e.printStackTrace();
			mydb = dbOpenHelper.getReadableDatabase();
		}
        return mydb;
	}
	
	/**
	 * 
	 * 关闭数据库
	 * 
	 */
	public void close(){//关闭数据库
		
		if (mydb.isOpen())
			mydb.close();	
		System.out.println("数据库已成功关闭");
		
	}
	/**
	 * 完成对数据库的增删改查
	 * 1.插入数据
	 * @param obj  插入对象
	 * 
	 */
	//插入数据
	public boolean insert (Object obj){
		long result =0;
		open();
		if(obj instanceof Plant){
			Plant plant = (Plant)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
				
				ContentValues values = new ContentValues();
				
				values.put("plantType", plant.getPlantType());
				values.put("tempuraturemin", plant.getTempuratureMin());
				values.put("humiditymin", plant.getHumidityMin());
				values.put("atmospheremin",plant.getAtmosphereMin());
				values.put("lightmin", plant.getLightMin());
				values.put("yoututempuraturemin", plant.getYoututempuraturemin());
				values.put("youtuhumiditymin", plant.getYoutuhumidityemin());
				values.put("tempuraturemax", plant.getTempuratureMax());
				values.put("humiditymax", plant.getHumidityMax());
				values.put("atmospheremax",plant.getAtmosphereMax());
				values.put("lightmax", plant.getLightMax());
				values.put("yoututempuraturemax", plant.getYoututempuraturemax());
				values.put("youtuhumiditymax", plant.getYoutuhumidityemax());
				values.put("co2min", plant.getco2min());
				values.put("co2max", plant.getco2max());
				
				result=mydb.insert(p_plant, null, values);
			
				
			} catch (SQLiteException e) {
				e.printStackTrace();
				
			}finally{
				mydb.endTransaction();
				System.out.println("成功插入植物规则"+plant.getPlantName());
			}
		}
		
		
		if(obj instanceof Atmosphere){
			Atmosphere atmosphere = (Atmosphere)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Atmosphere",atmosphere.getAtmosphere());
			values.put("Time", atmosphere.getTime());
			
			result=mydb.insert("atmosphere", null, values);
			
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
		}
		
		
		
		if(obj instanceof CO2){
			CO2 co2 = (CO2)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("CO2", co2.getCo2());
			values.put("Time", co2.getTime());
			result=mydb.insert("co2", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
	}
		
		if(obj instanceof Illumination){
			Illumination illumination = (Illumination)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Illumination", illumination.getIllumination());
			values.put("Time", illumination.getTime());
			
			result=mydb.insert("illumination", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
	}
		
		if(obj instanceof WuTuHumidity){
			WuTuHumidity wutuhumidity = (WuTuHumidity)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Wutuhumidity", wutuhumidity.getHumidity());
			values.put("Time", wutuhumidity.getTime());
			
			result=mydb.insert("wutuhumidity", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
	}
		
		if(obj instanceof YouTuTempurature){
			YouTuTempurature yoututempurature = (YouTuTempurature)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Yoututempurature", yoututempurature.getTempurature());
			values.put("Time", yoututempurature.getTime());
			
			result=mydb.insert("yoututempurature", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
	}
		if(obj instanceof WuTuTempurature){
			WuTuTempurature wututempurature = (WuTuTempurature)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Wututempurature", wututempurature.getTempurature());
			values.put("Time", wututempurature.getTime());
			
			result=mydb.insert("wututempurature", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
		}
		
		if(obj instanceof YouTuRoomTempurature){
			YouTuRoomTempurature youturoomtempurature = (YouTuRoomTempurature)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Youturoomtempurature", youturoomtempurature.getTempurature());
			values.put("Time", youturoomtempurature.getTime());
			
			result=mydb.insert("youturoomtempurature", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
		}
		
		if(obj instanceof YouTuRoomHumidity){
			YouTuRoomHumidity youturoomhumidity = (YouTuRoomHumidity)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Youturoomhumidity", youturoomhumidity.getHumidity());
			values.put("Time", youturoomhumidity.getTime());
			
			result=mydb.insert("youturoomhumidity", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
		}
		
		if(obj instanceof YouTuHumidity){
			YouTuHumidity youtuhumidity = (YouTuHumidity)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Youtuhumidity", youtuhumidity.getHumidity());
			values.put("Time", youtuhumidity.getTime());
			
			result=mydb.insert("youtuhumidity", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
		}
		
		
		
		if(obj instanceof Music){
			Music music = (Music)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("volumn", music.getvolunm());
			values.put("lujing", music.getlujing());
			
			result=mydb.insert("musictable", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
		}
		
 		if(obj instanceof DayTable){
 			DayTable daytable =(DayTable)obj;
 			open();
 			mydb.beginTransaction();
 			try {
 				mydb.setTransactionSuccessful();
 			ContentValues values = new ContentValues();
 			values.put("Year", daytable.getYear());
 			values.put("Month",daytable.getMonth());
 			values.put("Day", daytable.getDay());
 			values.put("Hour", daytable.getHour());
 			values.put("Atmosphere", daytable.getAtmosphere());
 			values.put("CO2", daytable.getCO2());
 			values.put("Light", daytable.getLight());
 			values.put("Illumination", daytable.getIlluminaton());
 			values.put("Wutuhumidity", daytable.getWutuhumidity());
 			values.put("Wututempurature", daytable.getWututempurature());
 			values.put("Youtuhumidity", daytable.getYoutuhumidity());
 			values.put("Youturoomhumidity", daytable.getYouturoomhumidity());
 			values.put("Youturoomtempurature", daytable.getYouturoomtempurature());
 			values.put("Yoututempurature", daytable.getYoututempurature());
 			
 			mydb.insert("daytable", null, values);
 		} catch (SQLiteException e) {
 			e.printStackTrace();
 			
 		}finally{
 			mydb.endTransaction();
 		}
 			close();
		}
		
		if(obj instanceof MonthTable){
			MonthTable monthtable = (MonthTable)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Year", monthtable.getYear());
			values.put("Month", monthtable.getMonth());
			values.put("Day", monthtable.getDay());
			values.put("Atmosphere", monthtable.getAtmosphere());
			values.put("CO2", monthtable.getCO2());
			values.put("Light", monthtable.getLight());
			values.put("Illumination", monthtable.getIlluminaton());
			values.put("Wutuhumidity", monthtable.getWutuhumidity());
			values.put("Wututempurature", monthtable.getWututempurature());
			values.put("Youtuhumidity", monthtable.getYoutuhumidity());
			values.put("Youturoomhumidity", monthtable.getYouturoomhumidity());
			values.put("Youturoomtempurature", monthtable.getYouturoomtempurature());
			values.put("Yoututempurature", monthtable.getYoututempurature());			
			result=mydb.insert("monthtable", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
		}
		
		if(obj instanceof YearTable){
			YearTable yeartable = (YearTable)obj;
			mydb.beginTransaction();
			try {
				mydb.setTransactionSuccessful();
			ContentValues values = new ContentValues();
			values.put("Year", yeartable.getYear());
			values.put("Month", yeartable.getMonth());
			values.put("Atmosphere", yeartable.getAtmosphere());
			values.put("CO2", yeartable.getCO2());
			values.put("Light", yeartable.getLight());
			values.put("Illumination", yeartable.getIlluminaton());
			values.put("Wutuhumidity", yeartable.getWutuhumidity());
			values.put("Wututempurature", yeartable.getWututempurature());
			values.put("Youtuhumidity", yeartable.getYoutuhumidity());
			values.put("Youturoomhumidity", yeartable.getYouturoomhumidity());
			values.put("Youturoomtempurature", yeartable.getYouturoomtempurature());
			values.put("Yoututempurature", yeartable.getYoututempurature());
			
			result=mydb.insert("yeartable", null, values);
		} catch (SQLiteException e) {
			e.printStackTrace();
			
		}finally{
			mydb.endTransaction();
		}
		}
			close();
			if(result<0)
				return false;
			else 
				return true;
	}
	
	public List<String> quaryYearMonthDay(String table,String column,int year){
		open();
		List<String> list = new ArrayList<String>();
		Cursor cursor = mydb.query(table, new String[]{column}, "Year="+year, null, null, null, null);
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		return list;		
	}
	/**
	 * 
	 * @param table 要查找的表名
	 * @param column 要查找的列名
	 * @param year 查找的列所在的年份
	 * @param month 查找的列所在月份
	 * @return 查找到的信息list
	 */
	public List<String> quaryYearMonthDay(String table,String column,int year,int month){
		open();
		List<String> list = new ArrayList<String>();
		Cursor cursor = mydb.query(table, new String[]{column}, "Year="+year+" AND "+"Month="+month, null, null, null, null);
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		return list;		
	}
	/**
	 * 
	 * @param table 
	 * @param column 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public List<String> quaryYearMonthDay(String table,String column,int year,int month,int day){
		open();
		List<String> list = new ArrayList<String>();
		Cursor cursor = mydb.query(table, new String[]{column}, "Year="+year+" AND "+"Month="+month+" AND "+"Day="+day, null, null, null, null);
		while(cursor.moveToNext()){
			list.add(cursor.getString(0));
		}
		return list;		
	}
	public HashMap<String,String> getMusic(){
		
		open();
		HashMap<String,String> hashmap = new HashMap<String,String>(); 
		Cursor lujingcursor=mydb.query("musictable", new String[]{"lujing"}, null, null, null, null, null);
		while(lujingcursor.moveToNext()){
			hashmap.put("lujing", lujingcursor.getString(0));			
		}
		Cursor volumncursor=mydb.query("musictable", new String[]{"volumn"}, null, null, null, null, null);
		while(lujingcursor.moveToNext()){
			hashmap.put("volumn", volumncursor.getString(0));			
		}
		return hashmap;		
	}
	
	/**
	 * 
	 * 按用户id删除数据
	 * @param id
	 * @param obj
	 * @return 
	 * 
	 */
	public int delete (String  _plantName){//按植物名删除
		 int result=0;
		 open();
		 result=mydb.delete(p_plant, "plantName=?", new String[]{_plantName});
		 close();
		 return result;
	}

	
	/**
	 * 
	 * @param table表
	 * @param column列
	 * @return table表中column列time值从min到max的数据list
	 */
	public List<Float> columnMeanValue(String table,String column,long max,long min){
		int atmosphereminnum=0;
		List<Float> list = new ArrayList<Float>();
		open();
		Cursor cursor = mydb.query(table, new String[]{column}, "Time<"+max+" AND "+"Time>"+min+" OR "+"Time="+max+" OR "+"Time="+min, null, null, null, null);
		while(cursor.moveToNext()){
			list.add(Float.parseFloat(cursor.getString(0)));
		}
		close();
		return list;
	}
	/**
	 * 查找出time列的最小值
	 * @param table表名
	 * @return表中Time列的最小值
	 */
	public long quaryColumnMinOne(){
		
		List<Long> timeList = new ArrayList();
		Long minnum = 0L;
		long atmosphereminnum = 0 ;
		long co2minnum = 0 ;
		long illuminationminnum = 0 ;
		long wutuhumidityminnum = 0 ;
		long wututempuratureminnum = 0 ;
		long youtuhumidityminnum = 0 ;
		long youturoomhumidityminnum = 0 ;
		long youturoomtempuratureminnum = 0 ;
		long yoututempuratureminnum = 0 ;
		long lightminnum = 0;
		open();
		Cursor cursor0,cursor1,cursor2,cursor3,cursor4,cursor5,cursor6,cursor7,cursor8,cursor9;
		Cursor atmospherecursor=mydb.query("atmosphere", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor co2cursor=mydb.query("co2", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor lightcursor = mydb.query("light", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor illuminationcursor=mydb.query("illumination", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor wutuhumiditycursor=mydb.query("wutuhumidity", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor wututempuraturecursor=mydb.query("wututempurature", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor youtuhumiditycursor=mydb.query("youtuhumidity", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor youturoomhumiditycursor=mydb.query("youturoomhumidity", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor youturoomtempuraturecursor=mydb.query("youturoomtempurature", new String[]{"MIN(Time)"}, null, null, null, null, null);
		Cursor yoututempuraturecursor=mydb.query("yoututempurature", new String[]{"MIN(Time)"}, null, null, null, null, null);
		
		cursor0=mydb.query("atmosphere", new String[]{"Time"}, null, null, null, null, null);
		if(cursor0.getCount()>0){
		while(atmospherecursor.moveToNext())
		{Log.d("number", atmospherecursor.getCount()+"");
			atmosphereminnum=Long.parseLong(atmospherecursor.getString(0));
			timeList.add(atmosphereminnum);
		}
		}
		cursor1=mydb.query("co2", new String[]{"Time"}, null, null, null, null, null);
		if(cursor1.getCount()>0){
		while(co2cursor.moveToNext())
		{
			co2minnum=Long.parseLong(co2cursor.getString(0));
			timeList.add(co2minnum);
		}
		}
		cursor2=mydb.query("light", new String[]{"Time"}, null, null, null, null, null);
		if(cursor2.getCount()>0){
		while(co2cursor.moveToNext())
		{
			lightminnum=Long.parseLong(lightcursor.getString(0));
			timeList.add(lightminnum);
		}
		}
		cursor3=mydb.query("illumination", new String[]{"Time"}, null, null, null, null, null);
		if(cursor3.getCount()>0){
		while(illuminationcursor.moveToNext())
		{
			illuminationminnum=Long.parseLong(illuminationcursor.getString(0));
			timeList.add(illuminationminnum);
		}
		}
		cursor4=mydb.query("wutuhumidity", new String[]{"Time"}, null, null, null, null, null);
		if(cursor4.getCount()>0){
		while(wutuhumiditycursor.moveToNext())
		{
			wutuhumidityminnum=Long.parseLong(wutuhumiditycursor.getString(0));
			timeList.add(wutuhumidityminnum);
		}
		}
		cursor5=mydb.query("wututempurature", new String[]{"Time"}, null, null, null, null, null);
		if(cursor5.getCount()>0){
		while(wututempuraturecursor.moveToNext())
		{
			wututempuratureminnum=Long.parseLong(wututempuraturecursor.getString(0));
			timeList.add(wututempuratureminnum);
		}
		}
		cursor6=mydb.query("youtuhumidity", new String[]{"Time"}, null, null, null, null, null);
		if(cursor6.getCount()>0){
		while(youtuhumiditycursor.moveToNext())
		{
			youtuhumidityminnum=Long.parseLong(youtuhumiditycursor.getString(0));
			timeList.add(youtuhumidityminnum);
		}
		}
		cursor7=mydb.query("youturoomhumidity", new String[]{"Time"}, null, null, null, null, null);
		if(cursor7.getCount()>0){
		while(youturoomhumiditycursor.moveToNext())
		{
			youturoomhumidityminnum=Long.parseLong(youturoomhumiditycursor.getString(0));
			timeList.add(youturoomhumidityminnum);
		}
		}
		cursor8=mydb.query("youturoomtempurature", new String[]{"Time"}, null, null, null, null, null);
		if(cursor8.getCount()>0){
		while(youturoomtempuraturecursor.moveToNext())
		{
			youturoomtempuratureminnum=Long.parseLong(youturoomtempuraturecursor.getString(0));
			timeList.add(youturoomtempuratureminnum);
		}
		}
		cursor9=mydb.query("yoututempurature", new String[]{"Time"}, null, null, null, null, null);
		if(cursor9.getCount()>0){
		while(yoututempuraturecursor.moveToNext())
		{
			yoututempuratureminnum=Long.parseLong(yoututempuraturecursor.getString(0));
			timeList.add(yoututempuratureminnum);
		}
		}

		minnum = CalculationMin(timeList);
		return minnum;
		
	}
	public void deleteAll(){
		open();
		mydb.delete(p_plant, null, null);
		close();
	}
	/**
	 * 查找出time列的最大值
	 * @param table表名
	 * @return表中Time列的最大值
	 */
	public long quaryColumnMaxOne(){
		
		List<Long> timeList = new ArrayList();
		long minnum;
		long atmospheremaxnum = 0 ;
		long co2maxnum = 0 ;
		long illuminationmaxnum = 0 ;
		long wutuhumiditymaxnum = 0 ;
		long wututempuraturemaxnum = 0 ;
		long youtuhumiditymaxnum = 0 ;
		long youturoomhumiditymaxnum = 0 ;
		long youturoomtempuraturemaxnum = 0 ;
		long yoututempuraturemaxnum = 0 ;
		long lightmaxnum = 0;
		open();
		Cursor cursor0,cursor1,cursor2,cursor3,cursor4,cursor5,cursor6,cursor7,cursor8,cursor9; 
		Cursor atmospherecursor=mydb.query("atmosphere", new String[]{"MAX(Time)"}, null, null, null, null, null);
		Cursor co2cursor=mydb.query("co2", new String[]{"MAX(Time)"}, null, null, null, null, null);
		Cursor lightcursor=mydb.query("light", new String[]{"MAX(Time)"}, null, null, null, null, null);
		Cursor illuminationcursor=mydb.query("illumination", new String[]{"MAX(Time)"}, null, null, null, null, null);
		Cursor wutuhumiditycursor=mydb.query("wutuhumidity", new String[]{"MAX(Time)"}, null, null, null, null, null);
		Cursor wututempuraturecursor=mydb.query("wututempurature", new String[]{"MAX(Time)"}, null, null, null, null, null);
		Cursor youtuhumiditycursor=mydb.query("youtuhumidity", new String[]{"MAX(Time)"}, null, null, null, null, null);
		Cursor youturoomhumiditycursor=mydb.query("youturoomhumidity", new String[]{"MAX(Time)"}, null, null, null, null, null);		
		Cursor youturoomtempuraturecursor=mydb.query("youturoomtempurature", new String[]{"MAX(Time)"}, null, null, null, null, null);
		Cursor yoututempuraturecursor=mydb.query("yoututempurature", new String[]{"MAX(Time)"}, null, null, null, null, null);
		
		cursor0=mydb.query("atmosphere", new String[]{"Time"}, null, null, null, null, null);
		if(cursor0.getCount()>0){
			while(atmospherecursor.moveToNext())
			{Log.d("number", atmospherecursor.getCount()+"");
				atmospheremaxnum=Long.parseLong(atmospherecursor.getString(0));
				timeList.add(atmospheremaxnum);
			}
			}
			cursor1=mydb.query("co2", new String[]{"Time"}, null, null, null, null, null);
			if(cursor1.getCount()>0){
			while(co2cursor.moveToNext())
			{
				co2maxnum=Long.parseLong(co2cursor.getString(0));
				timeList.add(co2maxnum);
			}
			}
			cursor2=mydb.query("light", new String[]{"Time"}, null, null, null, null, null);
			if(cursor2.getCount()>0){
			while(co2cursor.moveToNext())
			{
				lightmaxnum=Long.parseLong(lightcursor.getString(0));
				timeList.add(lightmaxnum);
			}
			}
			cursor3=mydb.query("illumination", new String[]{"Time"}, null, null, null, null, null);
			if(cursor3.getCount()>0){
			while(illuminationcursor.moveToNext())
			{
				illuminationmaxnum=Long.parseLong(illuminationcursor.getString(0));
				timeList.add(illuminationmaxnum);
			}
			}
			cursor4=mydb.query("wutuhumidity", new String[]{"Time"}, null, null, null, null, null);
			if(cursor4.getCount()>0){
			while(wutuhumiditycursor.moveToNext())
			{
				wutuhumiditymaxnum=Long.parseLong(wutuhumiditycursor.getString(0));
				timeList.add(wutuhumiditymaxnum);
			}
			}
			cursor5=mydb.query("wututempurature", new String[]{"Time"}, null, null, null, null, null);
			if(cursor5.getCount()>0){
			while(wututempuraturecursor.moveToNext())
			{
				wututempuraturemaxnum=Long.parseLong(wututempuraturecursor.getString(0));
				timeList.add(wututempuraturemaxnum);
			}
			}
			cursor6=mydb.query("youtuhumidity", new String[]{"Time"}, null, null, null, null, null);
			if(cursor6.getCount()>0){
			while(youtuhumiditycursor.moveToNext())
			{
				youtuhumiditymaxnum=Long.parseLong(youtuhumiditycursor.getString(0));
				timeList.add(youtuhumiditymaxnum);
			}
			}
			cursor7=mydb.query("youturoomhumidity", new String[]{"Time"}, null, null, null, null, null);
			if(cursor7.getCount()>0){
			while(youturoomhumiditycursor.moveToNext())
			{
				youturoomhumiditymaxnum=Long.parseLong(youturoomhumiditycursor.getString(0));
				timeList.add(youturoomhumiditymaxnum);
			}
			}
			cursor8=mydb.query("youturoomtempurature", new String[]{"Time"}, null, null, null, null, null);
			if(cursor8.getCount()>0){
			while(youturoomtempuraturecursor.moveToNext())
			{
				youturoomtempuraturemaxnum=Long.parseLong(youturoomtempuraturecursor.getString(0));
				timeList.add(youturoomtempuraturemaxnum);
			}
			}
			cursor9=mydb.query("yoututempurature", new String[]{"Time"}, null, null, null, null, null);
			if(cursor9.getCount()>0){
			while(yoututempuraturecursor.moveToNext())
			{
				yoututempuraturemaxnum=Long.parseLong(yoututempuraturecursor.getString(0));
				timeList.add(yoututempuraturemaxnum);
			}
			}
			minnum =  CalculationMax(timeList);
			close();
			return minnum;		
	}
	
	
	/**
	 * @param _userName  用户名
	 * @param condition  更新的数据头名
	 * @param data 数据
	 * @return  更新结果
	 */
	public int updateOne(String planttype, String plantname, String tempuraturemin,String tempuraturemax,String  humiditymin,String humiditymax,String yoututempuraturemin,String yoututempuraturemax,String youtuhumiditymin,String youtuhumiditymax, String atmosphermin,String atmosphermax,String lightmin,String lightmax,String co2min,String co2max)
	{
		int resultUpdate=0;
		open();
		try {
			
				ContentValues values = new ContentValues();
				values.put("plantType", planttype);
				values.put("plantName", plantname);
				values.put("tempuraturemin", tempuraturemin);
				values.put("tempuraturemax",tempuraturemax);
				values.put("humiditymin",humiditymin);
				values.put("humiditymax", humiditymax);
				values.put("yoututempuraturemin", yoututempuraturemin);
				values.put("yoututempuraturemax", yoututempuraturemax);
				values.put("youtuhumiditymin",youtuhumiditymin);
				values.put("youtuhumiditymax",youtuhumiditymax);
				values.put("atmosphermin", atmosphermin);
				values.put("atmosphermax", atmosphermax);
				values.put("lightmin",lightmin);
				values.put("lightmax",lightmax);
				values.put("co2min",co2min);
				values.put("co2max",co2max);
				resultUpdate=mydb.update(p_plant, values, "plantName=?",new String[]{plantname});
				
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		close();
		return resultUpdate;
	}
	/**
	 * @return  全部的植物生长状况
	 */
	public List<String> showPlantType()
	{
		List<String> plantTypeList=new ArrayList<String>();
		open();
		Cursor cursor=mydb.query(p_plant, new String[]{"plantType"}, null, null, null, null, null);
		while(cursor.moveToNext())
		{
			String userNameOne=cursor.getString(0);
			plantTypeList.add(userNameOne);
		}
		close();
		return plantTypeList;
	}
	/**
	 * 
	 * @param _plantName 输入规则中一种植物的名字
	 *
	 * @return 这种植物索要生长的信息
	 */
	public List<HashMap<String,String>> showOne(String _plantType)
	{
		Plant plant=new Plant();
		List<HashMap<String,String>> plant_list = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> plant_map = new HashMap<String,String>();
		open();
		Cursor cursor=mydb.query(p_plant, new String[]{"plantType","tempuraturemin","tempuraturemax","humiditymin","humiditymax","yoututempuraturemin","yoututempuraturemax","youtuhumiditymin","youtuhumiditymax","atmospheremin","atmospheremax","lightmin","lightmax","co2min","co2max"}, "plantName=?", new String[]{_plantType}, null, null, null);
		while(cursor.moveToNext())
		{	
			plant_map.put("tempuraturemin", cursor.getString(2));
			plant_map.put("tempuraturemax", cursor.getString(3));
			plant_map.put("humiditymin", cursor.getString(4));
			plant_map.put("humiditymax", cursor.getString(5));
			plant_map.put("yoututempuraturemin", cursor.getString(6));
			plant_map.put("yoututempuraturemax", cursor.getString(7));
			plant_map.put("youtuhumiditymin", cursor.getString(8));
			plant_map.put("youtuhumiditymax", cursor.getString(9));
			plant_map.put("atmospheremin", cursor.getString(10));
			plant_map.put("atmospheremax", cursor.getString(11));
			plant_map.put("lightmin", cursor.getString(12));
			plant_map.put("lightmax", cursor.getString(13));
			plant_map.put("co2min", cursor.getString(14));
			plant_map.put("co2max", cursor.getString(15));
			plant_list.add(plant_map);
		}
		close();
		Log.d("list123", plant_list.get(0).get("tempuraturemin"));
		return plant_list;
	}
	public long CalculationMin(List sampleList) 
	 {
	            try
	            {
	                long mixDevation = 0 ;
	                int totalCount = sampleList.size();
	                if (totalCount >= 1)
	                {
	                        long min = Long.parseLong(sampleList.get(0).toString());
	                        for (int i = 0; i < totalCount; i++)
	                        {
	                            long temp = Long.parseLong(sampleList.get(i).toString());
	                            if (min > temp)
	                            {
	                                min = temp;
	                            }
	                        } mixDevation = min;
	                        return mixDevation;
	                }else {
	                	return 0;
					}                
	            }
	            catch (Exception ex)
	            {	               	              						
					
	            }
				return 0;					            
	 }
	public long CalculationMax(List sampleList)
	 {
	            try
	            {
	                long maxDevation = 0L;
	                int totalCount = sampleList.size();
	                if (totalCount >= 1)
	                {
	                    long max = Long.parseLong(sampleList.get(0).toString());
	                    for (int i = 0; i < totalCount; i++)
	                    {
	                        long temp = Long.parseLong(sampleList.get(i).toString());
	                        if (temp > max)
	                        {
	                            max = temp;
	                        }
	                    } maxDevation = max;
	                }
	                return maxDevation;
	            }
	            catch (Exception ex)
	            {
	                
	            }
				return 0;
	  }
	
}



