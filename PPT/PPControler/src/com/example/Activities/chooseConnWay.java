package com.example.Activities;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.Data.Data;
import com.example.Tools.ExitApplication;
import com.example.meimei.R;

public class chooseConnWay extends AbstractActivity {
	private Button bluetooth = null;
	private long mExitTime;
	Button wifi = null;
	Boolean wifiIsRun = false, blueIsRun = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose);
		bluetooth = (Button) findViewById(R.id.bluetooth);
		wifi = (Button) findViewById(R.id.wifi);
		bluetooth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("tag", "isExit" + Data.isExit);

				if (Data.isExit.equals("wifi")) {

					Toast.makeText(chooseConnWay.this, "电脑设置wifi连接",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(chooseConnWay.this, wifi.class);
					startActivity(intent);
				} else {
					/*Intent intent = new Intent();
					intent.setClass(chooseConnWay.this, bluetooth.class);
					chooseConnWay.this.startActivity(intent);*/
					if (blueIsRun) {
						Intent intent = new Intent();
						intent.setClass(chooseConnWay.this, bluetooth.class);
						chooseConnWay.this.startActivity(intent);
					} else {
						Intent intent = new Intent(
								android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
						startActivity(intent);
						blueIsRun = true;
					}
				}

			}

		});
		wifi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("tag", "isExit" + Data.isExit);
				if (Data.isExit.equals("bluetooth")) {
					Toast.makeText(chooseConnWay.this, "电脑设置蓝牙连接",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(chooseConnWay.this,
							bluetooth.class);
					startActivity(intent);
				} else {
					if (wifiIsRun) {
						Intent intent = new Intent();
						intent.setClass(chooseConnWay.this, wifi.class);
						chooseConnWay.this.startActivity(intent);

					} else {
						Toast.makeText(chooseConnWay.this,
								"当前通信为局域网通信，请与服务端处于同于wifi环境内",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(
								android.provider.Settings.ACTION_WIFI_SETTINGS);
						startActivity(intent);
						wifiIsRun = true;
					}
				}

			}

		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - mExitTime) > 2000) {

				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

				mExitTime = System.currentTimeMillis();

			} else {
				if(Data.SocketType!=null){
					if(Data.SocketType.equals("BluetoothSocket")){
						try {
							Data.ClientblueSocket.getOutputStream().write("断开连接".getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if(Data.SocketType.equals("WifiSocket")){
						try {
							Data.ClientwifiSocket.getOutputStream().write("断开连接".getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					Log.d("tag", "无连接");
				}
				
				ExitApplication.getInstance().exit(this);
			}

			return true;

		}

		return super.onKeyDown(keyCode, event);

	}
}
