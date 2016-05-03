package com.example.Activities;

import java.io.IOException;
import java.io.OutputStream;
import com.example.Data.Data;
import com.example.Thread.readpptThread;
import com.example.Tools.Setup_WifiSocket;
import com.example.meimei.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint({ "NewApi", "HandlerLeak" })
public class wifi extends AbstractActivity {
	private EditText enterIP;
	private Button connect;
	public static boolean isOver = false;

	private ProgressBar progressBar;
	private OutputStream ClientOutputStream;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			if (msg.what == 0x1111) {
				String infoProcess = msg.getData().getString("process");
				Toast.makeText(wifi.this, infoProcess,
						Toast.LENGTH_SHORT).show();
				 Log.d("msg.what = 0x111:", "infoProcess"+infoProcess);
			}
			if (msg.what == 0x4000) {
				progressBar.setVisibility(View.INVISIBLE);
				Toast.makeText(wifi.this, "socket����ʧ�ܣ�����������IP",
						Toast.LENGTH_SHORT).show();
			}
			if (msg.what == 0x3000) {
				Toast.makeText(wifi.this, "���ӳɹ�", Toast.LENGTH_SHORT).show();
				try {
					progressBar.setVisibility(View.INVISIBLE);
					Log.d("out", "output.write(���ӳɹ�)");
					OutputStream outputStream = Data.ClientwifiSocket
							.getOutputStream();
					outputStream.write("���ӳɹ�".getBytes());
					Log.d("out", "wifi ���ӳɹ� д��");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (msg.what == 0x01) {// ��ȡ��Ӧ��Ϣ
				Log.d("tag", "0x01 in wifi");// ���͹㲥
				Bundle infoBundle = msg.getData();
				Intent intent = new Intent();
				intent.putExtra("1", infoBundle);
				intent.setAction("InfoBundle");
				sendBroadcast(intent);
				Log.d("tag", "infoBundle" + infoBundle);

			}
			if (msg.what == 0x02) {// ��ȡppt��Ϣ
				Log.d("tag", "0x02 in wifi");// ���͹㲥
				Bundle NameStringBundle = msg.getData();
				Intent intent = new Intent();
				intent.putExtra("2", NameStringBundle);
				intent.setAction("NameStringBundle");
				sendBroadcast(intent);
				Log.d("tag", "NameString" + NameStringBundle);
			}
			if (msg.what == 0x03) {
				Intent intent = new Intent("FileNotFound");
				sendBroadcast(intent);
			}
			if (msg.what == 0x04) {
				wifi.isOver = true;
				Intent intent = new Intent("ConnectionBreakOut");
				sendBroadcast(intent);
			}

			if (msg.what == 0x110) {
				Log.d("tag", "no ppt");
				Intent intent = new Intent("NoPPT");
				sendBroadcast(intent);

			}

		}

	};

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi);
		progressBar = (ProgressBar) findViewById(R.id.progressBarWifi);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());

		enterIP = (EditText) findViewById(R.id.enterIP);
		connect = (Button) findViewById(R.id.connect);
		connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("tag", "IP==" + enterIP.getText().toString());
				Log.d("tag", "Data.IP==" + Data.IP);
				Log.d("socket", "wifi data.socketType" + Data.SocketType);
				if (Data.IP.equals(enterIP.getText().toString())) {
					Toast.makeText(wifi.this, "�������Ѵ���", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent();
					intent.setClass(wifi.this, file_search.class);
					wifi.this.startActivity(intent);
				} else {

					st: while (Data.SocketType != null) {

						progressBar.setVisibility(View.VISIBLE);

						Log.d("socket", "socket!=null");
						Data.SocketType = null;
						Data.IP = "none";
						readpptThread.setIsAlive(false);
						Log.d("isAlive",
								"thread is Alive" + readpptThread.getIsAlive());
						while (!readpptThread.getIsAlive()) {// �߳�����
							try {
								Data.ClientwifiSocket.getOutputStream().write(
										"�Ͽ�����".getBytes());
								Log.d("tag", "out");
								Data.ClientwifiSocket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (Data.ClientwifiSocket.isClosed()) {
								Data.ClientwifiSocket = null;
								sendBroadcast(new Intent("ConnectionBreakOut"));

								Toast.makeText(wifi.this, "��ǰ�����ѶϿ�",
										Toast.LENGTH_SHORT).show();

							}
							Log.d("isAlive", "thread has dead");
							break st;
						}

					}
					Log.d("tag", "enterIP.getText()==" + enterIP.getText());
					if (enterIP.getText().toString().equals("")) {
						Log.d("tag", "edittext");
						progressBar.setVisibility(View.INVISIBLE);

						Toast.makeText(wifi.this, "��������ȷ��IP,����192.168.1.100!",
								Toast.LENGTH_SHORT).show();

					} else {
						if (Data.ClientwifiSocket == null) {
							progressBar.setVisibility(View.VISIBLE);

							Setup_WifiSocket wifiSocket = new Setup_WifiSocket(
									handler);
							wifiSocket.Connection(enterIP.getText().toString());

							Log.d("tag", "Data.ClientWifiSocket"
									+ Data.ClientwifiSocket);

							if (Data.ClientwifiSocket != null
									&& Data.ClientwifiSocket.isConnected()) {
								try {
									ClientOutputStream = Data.ClientwifiSocket
											.getOutputStream();
									ClientOutputStream.write("���ļ���".getBytes());
									Log.d("output", "output");

								} catch (IOException e) {
									// TODO Auto-generated catch block
									try {
										Data.ClientwifiSocket.close();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									Data.ClientwifiSocket = null;
									Data.SocketType = null;
									e.printStackTrace();
								} finally {

									Log.d("finally", "finally");
									if (Data.ClientwifiSocket != null
											&& Data.ClientwifiSocket
													.isConnected()) {
										// ���̣߳���ȡ�ļ���
										readpptThread.setIsAlive(true);
										readpptThread rpt = new readpptThread(
												handler);
										rpt.start();

										Data.IP = enterIP.getText().toString();
										Log.d("tag", "setIP");
										Intent intent = new Intent();
										intent.setClass(wifi.this,
												file_search.class);
										wifi.this.startActivity(intent);
									} else {
										Toast.makeText(wifi.this,
												"����ʧ�ܣ���������ȷIP���������Ƿ��������ӷ�ʽ",
												Toast.LENGTH_SHORT).show();
									}

								}
							} else {
								Toast.makeText(wifi.this, "��������ȷ��Ŀ��IP���������",
										Toast.LENGTH_SHORT).show();
							}
						}

					}
				}

			}

		});
	}

}
