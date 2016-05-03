package com.example.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
/**
 * 
 * 
 * ���ݿ⹤���� �̳�SQLiteOpenHelper��  ���ڴ������ݿ⼰���ݿ��
 * @author Administrator
 *
 */
class  DBOpenHelper extends SQLiteOpenHelper{

	
	

	
	private static final String SCENE_CREATE = " create table scene ( name text not null, gateway text not null, node text not null)";
	private static final String LAWN_CREATE = " create table lawn ( name text not null, gateway text not null, node text not null, id integer )";
	private static final String STREET_CREATE = " create table street ( name text not null, gateway text not null, node text not null )";
	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {//ֱ����ȡ��ȡ���ݿ��пɶ�/��д����ʱ�������ݿ�ű��������ߴ�
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {//���ݿ��״α�����ʱ��ִ�д������ݿ��Ĳ���
		// TODO Auto-generated method stub
		Log.d("aa","dsada");
		db.execSQL(SCENE_CREATE);
		db.execSQL("insert into scene ( name,gateway,node ) values ( ?,?,? )",new Object[]{"����","00000000-00000000-00409DFF-FF55A0F1","test_wjx"});
		db.execSQL(LAWN_CREATE);
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"����","00000000-00000000-00409DFF-FF55A0F1","test_c1",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"��ţ","00000000-00000000-00409DFF-FF55A0F1","test_c2",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"����","00000000-00000000-00409DFF-FF55A0F1","test_c3",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"î��","00000000-00000000-00409DFF-FF55A0F1","test_c4",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"����","00000000-00000000-00409DFF-FF55A0F1","test_c5",0});
		db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"����","00000000-00000000-00409DFF-FF55A0F1","test_c6",0});
		//db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"�޵�","00000000-00000000-00409DFF-FF5C2659","test_c7",0});
		//db.execSQL("insert into lawn ( name,gateway,node,id ) values ( ?,?,?,? )",new Object[]{"�ҵ�","00000000-00000000-00409DFF-FF5C2659","test_c8",0});
		db.execSQL(STREET_CREATE);
		db.execSQL("insert into street ( name,gateway,node ) values ( ?,?,? )",new Object[]{"�����","00000000-00000000-00409DFF-FF55A0F1","test_c1"});
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//�����ݿ�ִ������ʱɾ�����ݿ����Ѵ��ڵ����ݱ�
		// TODO Auto-generated method stub
		db.execSQL("drop table [if exits] "+"scene");
		db.execSQL("drop table [if exits] "+"lawn");
		db.execSQL("drop table [if exits] "+"street");
	}
}