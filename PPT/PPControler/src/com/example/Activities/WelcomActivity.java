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
		// �����ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����ȫ��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcomlayout);
		//Ӧ�ó�ʼ��ʱ����SD�������ļ���;
		 File sd = Environment.getExternalStorageDirectory();
		 String path = sd.getPath()+"/PPControler";
		 File file=new File(path);
		  if(!file.exists()){
			  //�����ļ���
			  if(file.mkdirs()){
				  Data.SDPath = file.getAbsolutePath();
				  Log.d("tag", "ppcontroler path=="+Data.SDPath);
				  Toast.makeText(WelcomActivity.this, "SD·��Ϊ"+Data.SDPath, Toast.LENGTH_SHORT).show();

			  }else{
				  Log.d("tag", "DATAPATH::"+Data.SDPath);
				  Log.d("tag", "path::"+path);
				  Toast.makeText(WelcomActivity.this, "SD���޸��ļ��в�����ʧ��", Toast.LENGTH_SHORT).show();
			  }
			  
		  }
		  Data.SDPath = file.getAbsolutePath();


		// ��ȡSharedPreferences����Ҫ������
		 preferences = getSharedPreferences("count",MODE_WORLD_READABLE);

		 final int[] count = new int[1];
		 count[0] = preferences.getInt("count", 0);
		// �жϳ�����ڼ������У�����ǵ�һ����������ת������ҳ��

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

		// ��������

		// editor.putInt("count", ++count);

		// �ύ�޸�

		// editor.commit();

	}
}
