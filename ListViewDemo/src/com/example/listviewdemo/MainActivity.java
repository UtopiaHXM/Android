//Editor : HXM
package com.example.listviewdemo;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class MainActivity extends ListActivity implements ViewBinder
{
	private static final String DATA = "[{\"filename\":\"01第一章 数据库理论基础.ppt\",\"status\":2},{\"filename\":\"02第二章 关系数据库设计和建模工具.ppt\",\"status\":2},{\"filename\":\"03第三章  SQL Server 2008简介.ppt\",\"status\":3},{\"filename\":\"04第四章  SQL Server 2008数据库管理.ppt\",\"status\":3},{\"filename\":\"05第五章  SQL Server 2008数据表管理.ppt\",\"status\":1},{\"filename\":\"06第六章  T-SQL编程基础.ppt\",\"status\":3},{\"filename\":\"07第七章  数据查询.ppt\",\"status\":2},{\"filename\":\"08第8章  数据库高级编程.ppt\",\"status\":1},{\"filename\":\"08第八章  数据库高级对象操作和管理.ppt\",\"status\":3},{\"filename\":\"08第八章 数据库系统的安全性管理.ppt\",\"status\":1},{\"filename\":\"09第9章  数据库系统的安全.ppt\",\"status\":2},{\"filename\":\"10第10章  网上玩具商店范例.ppt\",\"status\":1}]";
	
	private enum FileStatus
	{
		ON_SD, ON_PC, ON_BOTH;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayList<HashMap<String, Object>> list = null;
		try
		{
			list = convertData();
		}
		catch (JSONException e) {e.printStackTrace();}
		
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item_main_list, new String[]{"filename", "status"}, new int[]{R.id.txtFileName, R.id.txtFileName});
		adapter.setViewBinder(this);
		setListAdapter(adapter);
	}
	
	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation)
	{
		if (data instanceof String)
		{
			((TextView)view).setText(textRepresentation);
		}
		else if (data instanceof FileStatus)
		{
			switch ((FileStatus)data)
			{
				case ON_SD:		((TextView)view).setTextColor(Color.GREEN);	break;
				case ON_PC:		((TextView)view).setTextColor(Color.RED);	break;
				case ON_BOTH:	((TextView)view).setTextColor(Color.WHITE);	break;
			}
		}
		return true;
	}

	/** 用于初始化数据，可以无视 */
	private ArrayList<HashMap<String, Object>> convertData() throws JSONException
	{
		// 加载与解析数据
		JSONArray array = null;
		array = new JSONArray(DATA);
		
		// 转换格式
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>(12);
		HashMap<String, Object> map = null;
		JSONObject item = null;
		
		int nLength = array.length();
		for (int i = 0; i < nLength; i++)
		{
			item = array.getJSONObject(i);
			
			map = new HashMap<String, Object>(2);
			map.put("filename", item.getString("filename"));
			switch (item.getInt("status"))
			{
				case 1:		map.put("status", FileStatus.ON_SD);	break;
				case 2:		map.put("status", FileStatus.ON_PC);	break;
				case 3:		map.put("status", FileStatus.ON_BOTH);	break;
			}
			
			list.add(map);
		}
		
		return list;
	}
}
