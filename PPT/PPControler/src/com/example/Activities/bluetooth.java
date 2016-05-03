package com.example.Activities;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.Add_listview_style.listview_style;
import com.example.Data.Data;
import com.example.Thread.readpptThread;
import com.example.Tools.Setup_bluetoothSocket;
import com.example.meimei.R;

@SuppressLint("HandlerLeak")
public class bluetooth extends AbstractActivity {
	private listview_style bluetooth_list;
	private Button imageButton;
	private ProgressBar progressBar;
	private ArrayList<Map<String, String>> listName = new ArrayList<Map<String, String>>();
	public static boolean isOver = false;
	private HashMap<String, String> map = new HashMap<String, String>();
	private BluetoothAdapter bluetoothadapter = BluetoothAdapter
			.getDefaultAdapter();
	private Set<BluetoothDevice> devices = bluetoothadapter.getBondedDevices();
	private ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	private BluetoothDevice device;
	private String[] deviceString = new String[devices.size()];
	private OutputStream ClientOutputStream;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 0x4000) {
				Toast.makeText(bluetooth.this, "��������������������",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(bluetooth.this, chooseConnWay.class);
				startActivity(intent);
			}
			if (msg.what == 0x3000) {
				Toast.makeText(bluetooth.this, "���ӳɹ�", Toast.LENGTH_SHORT)
						.show();
				try {
					Log.d("out", "output.write(���ӳɹ�)");
					progressBar.setVisibility(View.INVISIBLE);
					OutputStream outputStream = Data.ClientblueSocket
							.getOutputStream();
					outputStream.write("���ӳɹ�".getBytes());
					Log.d("out", "bluetooth ���ӳɹ� д��");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (msg.what == 0x00) {// ��ȡ��Ӧ��Ϣ
				progressBar.setVisibility(View.INVISIBLE);
				Toast.makeText(bluetooth.this, "����socket����ʧ�ܣ���ѡ�������������������",
						Toast.LENGTH_SHORT).show();
			}
			if (msg.what == 0x01) {// ��ȡ��Ӧ��Ϣ
				Log.d("tag", "0x01 in bluetooth");// ���͹㲥
				Bundle infoBundle = msg.getData();
				Intent intent = new Intent();
				intent.putExtra("1", infoBundle);
				intent.setAction("InfoBundle");
				sendBroadcast(intent);
				Log.d("tag", "infoBundle" + infoBundle);

			}
			if (msg.what == 0x02) {// ��ȡppt��Ϣ
				Log.d("tag", "0x02 in bluetooth");// ���͹㲥
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
				isOver = true;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth);
		final SimpleAdapter mySimpleAdapter = new SimpleAdapter(this, listName,// ����Դ
				R.layout.list_bluetooth,// ListView�ڲ�����չʾ��ʽ�Ĳ����ļ�listitem.xml
				new String[] { "bluetoothDevice" },// HashMap�е�keyֵbluetoothDevice
				new int[] { R.id.itembluetooth });// �����ļ�listitem.xml�������id;
		imageButton = (Button) findViewById(R.id.imageView1);
		bluetooth_list = (listview_style) findViewById(R.id.bluetooth_list);
		progressBar = (ProgressBar) findViewById(R.id.progressBarBlue);
		bluetooth_list.setAdapter(mySimpleAdapter);
		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				devices = bluetoothadapter.getBondedDevices();
				deviceString = new String[devices.size()];
				if (devices.isEmpty()) {
					Log.d("devices", "devices is empty");
					Toast.makeText(bluetooth.this, "û��������豸", Toast.LENGTH_LONG)
							.show();
				} else {
					// /����ArrayList���� ���������
					if (devices.size() > 0) {

						int i = 0;
						for (Iterator<BluetoothDevice> it = devices.iterator(); it
								.hasNext();) {
							device = it.next();

							deviceString[i] = device.getName();
							Log.d("tag", "device name " + deviceString[i]);

							map.put("bluetoothDevice", device.getName());
							list.add(map);

							i++;

						}

					}
				}

				String_to_List(deviceString);

				mySimpleAdapter.notifyDataSetChanged();
			}
		});

		imageButton.performClick();
		bluetooth_list.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.d("tag", "choosen");
				// ����Ҫ���ӵ��������֣������ӡ�
				Log.d("tag",
						"Data.deviceName " + Data.deviceName + ",choosenName "
								+ bluetooth_list.getItemAtPosition(arg2));
				@SuppressWarnings("unchecked")
				HashMap<String, String> temp = (HashMap<String, String>) bluetooth_list
						.getItemAtPosition(arg2);
				if (Data.deviceName.equals(temp.get("bluetoothDevice"))) {
					Toast.makeText(bluetooth.this, "�������Ѵ���", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent();
					intent.setClass(bluetooth.this, file_search.class);
					bluetooth.this.startActivity(intent);
				} else {
					Log.d("tag", "collect");
					st: while (Data.SocketType != null) {
						progressBar.setVisibility(View.VISIBLE);

						Log.d("socket", "socket!=null");
						Data.SocketType = null;
						Data.deviceName = "none";
						readpptThread.setIsAlive(false);
						Log.d("isAlive",
								"thread is Alive" + readpptThread.getIsAlive());
						while (!readpptThread.getIsAlive()) {// �߳�����
							try {
								Data.ClientblueSocket.getOutputStream().write("�Ͽ�����".getBytes());
								Log.d("info", "out");
								Data.ClientblueSocket.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Log.d("info","isClose"+Data.ClientblueSocket.isConnected());
							if (!Data.ClientblueSocket.isConnected()) {
								Data.ClientblueSocket = null;
								Toast.makeText(bluetooth.this, "��ǰ�����ѶϿ�",
										Toast.LENGTH_SHORT).show();
sendBroadcast(new Intent("ConnectionBreakOut"));
							}
							Log.d("isAlive", "thread has dead");
							break st;
						}
					}
					// ��������
					if (Data.ClientblueSocket == null) { // //���ѡ�����HashMap����
						progressBar.setVisibility(View.VISIBLE);

						@SuppressWarnings("unchecked")
						HashMap<String, String> map = (HashMap<String, String>) bluetooth_list
								.getItemAtPosition(arg2);
						String title = map.get("bluetoothDevice");
						Log.d("tag", "title=" + title);
						Setup_bluetoothSocket stb = new Setup_bluetoothSocket(
								handler);
						Log.d("socket", "bluetooth data.socketType"
								+ Data.SocketType);
						stb.Connection(title, devices);

						Log.d("tag", "Data.ClientBluetoothSocket"
								+ Data.ClientblueSocket);

						if (Data.ClientblueSocket != null
								&& Data.ClientblueSocket.isConnected()) {
							Log.d("tag", "bluetooth ������");
							try {
								ClientOutputStream = Data.ClientblueSocket
										.getOutputStream();
								ClientOutputStream.write("���ļ���".getBytes());
								Log.d("output", "output");
							} catch (IOException e) { // TODO Auto-generated

								Log.d("tag", "io blue�����쳣");
								e.printStackTrace();
								if (Data.ClientblueSocket != null) {
									try {
										Data.ClientblueSocket.close();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									Data.ClientblueSocket = null;
									Data.SocketType = null;
									e.printStackTrace();
								}
							} finally {
								Log.d("finally", "finally");

								if (Data.ClientblueSocket.isConnected()) { // ���̣߳���ȡ�ļ���
									readpptThread.setIsAlive(true);
									readpptThread rpt = new readpptThread(
											handler);
									rpt.start();

									Log.d("tag", "rpt is begin");

									Intent intent = new Intent();
									intent.setClass(bluetooth.this,
											file_search.class);
									bluetooth.this.startActivity(intent);

								} else {
									Log.d("tag", "outputStream faith");
									ClientOutputStream = null;

									Toast.makeText(bluetooth.this, "��ǰ����ʧ��",
											Toast.LENGTH_SHORT).show();
								}

							}

						} else {
							Toast.makeText(bluetooth.this, "��������ȷ������",
									Toast.LENGTH_SHORT).show();
						}
					}

				}
			}

		});
	}

	private void String_to_List(String[] deviceString) {
		int j;
		listName.clear();

		if (deviceString.length == 0) {
			Log.d("b...", " NameString in bluetooth is null ");
		} else {
			for (j = 0; j < deviceString.length; j++) {
				HashMap<String, String> NameMap = new HashMap<String, String>();
				NameMap.put("bluetoothDevice", deviceString[j]);
				Log.d("tag", "bluetoothDevice in String_to_list bluetooth "
						+ deviceString[j]);
				listName.add(NameMap);
			}
		}
	}
}
