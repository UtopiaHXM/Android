package com.example.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
/**
 * 
 * 
 * 数据库工具类 继承SQLiteOpenHelper类  用于创建数据库及数据库表
 * @author Administrator
 *
 */
class  DBOpenHelper extends SQLiteOpenHelper{

	
	

	
	private static final String SCENE_CREATE = " create table scene ( name text not null, gateway text not null, node text not null)";
	private static final String LAWN_CREATE = " create table lawn ( name text not null, gateway text not null, node text not null, id integer )";
	private static final String STREET_CREATE = " create table street ( name text not null, gateway text not null, node text not null )";
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {//直到获取获取数据库中可读/可写对象时，该数据库才被建立或者打开
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//数据库首次被创建时候执行创建数据库表的操作
		// TODO Auto-generated method stub
		Log.d("aa","dsada");
		db.execSQL(SCENE_CREATE);
		db.execSQL("insert into scene ( name,gateway,node ) values ( ?,?,? )",new Object[]{"子鼠","00000000-00000000-00409DFF-FF55A0F1","test_wjx"});
		db.execSQL(LAWN_CREATE);
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"子鼠","00000000-00000000-00409DFF-FF55A0F1","test_c1",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"丑牛","00000000-00000000-00409DFF-FF55A0F1","test_c2",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"寅虎","00000000-00000000-00409DFF-FF55A0F1","test_c3",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"卯兔","00000000-00000000-00409DFF-FF55A0F1","test_c4",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"辰龙","00000000-00000000-00409DFF-FF55A0F1","test_c5",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"巳蛇","00000000-00000000-00409DFF-FF55A0F1","test_c6",0});
		//db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"艮灯","00000000-00000000-00409DFF-FF5C2659","test_c7",0});
		//db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"兑灯","00000000-00000000-00409DFF-FF5C2659","test_c8",0});
		db.execSQL(STREET_CREATE);
		db.execSQL("insert into street ( name,gateway,node ) values ( ?,?,? )",new Object[]{"五角星","00000000-00000000-00409DFF-FF55A0F1","test_c1"});
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//对数据库执行升级时删除数据库中已存在的数据表
		// TODO Auto-generated method stub
		db.execSQL("drop table [if exits] "+"scene");
		db.execSQL("drop table [if exits] "+"lawn");
		db.execSQL("drop table [if exits] "+"street");
	}
}