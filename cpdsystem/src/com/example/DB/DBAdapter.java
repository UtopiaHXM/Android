package com.example.DB;


import java.util.ArrayList;
import java.util.List;
import android.R.integer;
import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


public class DBAdapter {

	private static final String DB="LED";
	private static final int version=1;
	

	private final Context context;
	private static SQLiteDatabase mydb;
	private DBOpenHelper dbOpenHelper;
	
	public DBAdapter(Context context){
		this.context = context;
		dbOpenHelper = new DBOpenHelper(context, DB, null, version);
	}
	/**
	 * 
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
	public ArrayList<String> quanryName(String table)
	{
		ArrayList<String> ledNameList = new ArrayList<String>();
		open();
		Cursor cursor = mydb.query(table, new String[]{"name"}, null, null, null, null, null);
		while(cursor.moveToNext())
		{
			ledNameList.add(cursor.getString(0));
		}
		close();
		return ledNameList;
	}
	public String[] quaryOne(String table,String name) {
		String[] info = new String[2];
		open();
		Cursor cursor = mydb.query(table, new String[]{"gateway,node"}, "name=?", new String[]{name}, null, null, null);
		while(cursor.moveToNext())
		{
			info[0] = cursor.getString(0);//网关
			info[1] = cursor.getString(1);//节点
		}
		close();
		return info;
	}
	public boolean insetIntoLawn(String name,String gateway,String node,int id){
		boolean result = false;
		try {
			open();
			ContentValues values = new ContentValues();
			
			values.put("name", name);
			values.put("gateway",gateway);
			values.put("node", node);
			values.put("id", id);
			
			long r=mydb.insert("lawn", null, values);
			close();
			if(r>0)
			{
				result=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	public boolean deleteLawn(int id){
		boolean result = false;
		try {
			String idString= "" + id;
			long r = mydb.delete("lawn", "id = ?", new String[]{idString});
			close();
			if(r>0)
			{
				result = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	public boolean updataLawn(int id,String name,String gateway,String node){
		boolean result = false;
		try {
			open();
			ContentValues values = new ContentValues();
			
			values.put("name", name);
			values.put("gateway",gateway);
			values.put("node", node);
			values.put("id", id);
			
			long r = mydb.update("lawn", values, "id = ?", new String[]{id+""});
			close();
			if(r>0)
			{
				result = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
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
	
}

