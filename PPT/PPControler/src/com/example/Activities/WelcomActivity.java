package com.example.Activities;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.Data.Data;
import com.example.meimei.R;

public class WelcomActivity extends Activity {

	private SharedPreferences preferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcomlayout);
		//应用初始化时，在SD卡建立文件夹;
		 File sd = Environment.getExternalStorageDirectory();
		 String path = sd.getPath()+"/PPControler";
		 File file=new File(path);
		  if(!file.exists()){
			  //创建文件夹
			  if(file.mkdirs()){
				  Data.SDPath = file.getAbsolutePath();
				  Log.d("tag", "ppcontroler path=="+Data.SDPath);
				  Toast.makeText(WelcomActivity.this, "SD路径为"+Data.SDPath, Toast.LENGTH_SHORT).show();

			  }else{
				  Log.d("tag", "DATAPATH::"+Data.SDPath);
				  Log.d("tag", "path::"+path);
				  Toast.makeText(WelcomActivity.this, "SD卡无该文件夹并创建失败", Toast.LENGTH_SHORT).show();
			  }
			  
		  }
		  Data.SDPath = file.getAbsolutePath();


		// 读取SharedPreferences中需要的数据
		 preferences = getSharedPreferences("count",MODE_WORLD_READABLE);

		 final int[] count = new int[1];
		 count[0] = preferences.getInt("count", 0);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导页面

		new Thread() {
			@Override
			public void run() {
				try {
					sleep(3000);
					if(count[0]!=0){
						Intent intent = new Intent(WelcomActivity.this, chooseConnWay.class);
						startActivity(intent);
					}else{
						startActivity(new Intent().setClass(WelcomActivity.this,
								View_flow_activity.class));
						Editor editor = preferences.edit();
						editor.putInt("count", ++count[0]);
						editor.commit();
					}
					
					WelcomActivity.this.finish();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		// Editor editor = preferences.edit();

		// 存入数据

		// editor.putInt("count", ++count);

		// 提交修改

		// editor.commit();

	}
}
