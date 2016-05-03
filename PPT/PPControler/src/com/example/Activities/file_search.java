package com.example.Activities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.Add_listview_style.listview_style;
import com.example.Data.Data;
import com.example.Thread.UpFileSend;
import com.example.Thread.readpptThread;
import com.example.meimei.R;

@SuppressLint("HandlerLeak")
public class file_search extends AbstractActivity implements ViewBinder {

	public static enum FileStatus {
		ON_SD, ON_PC, ON_BOTH, ON_NULL;
	}

	private SimpleAdapter mySimpleAdapter;

	public SimpleAdapter getMySimpleAdapter() {
		return mySimpleAdapter;
	}

	private BluetoothSocket ClientblueSocket;
	private Socket ClientWifiSocket;
	private OutputStream ClientOutputStream;
	private InputStream ClientInputStream;

	private ProgressBar progressBar;

	private byte b_byte[];

	private Button imageButton;
	private listview_style listView;

	private ArrayList<HashMap<String, Object>> listName = new ArrayList<HashMap<String, Object>>();

	public Handler getHandler() {
		return handler;
	}

	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler() {
		@Override
		@SuppressLint("NewApi")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

		

			if (msg.what == 0x02) {
				String[] NameString = msg.getData().getStringArray("2");
				// String_to_List(NameString);
				// ��������Դ
				ArrayList<HashMap<String, Object>> tempPC = null;
				ArrayList<HashMap<String, Object>> tempSD = null;
				if (getPCFilesName(NameString) != null)
					tempPC = getPCFilesName(NameString);
				Log.d("tag", "Data.Path::" + Data.SDPath);
				tempSD = getSDFilesName(Data.SDPath);

				setDataSource(tempPC, tempSD);
				for (int i = 0; i < listName.size(); i++) {
					Log.d("tag",
							i + "::fileName=="
									+ listName.get(i).get("fileName"));
					Log.d("tag",
							i + "::status==" + listName.get(i).get("status"));
				}
				mySimpleAdapter.notifyDataSetChanged();
			}
			if (msg.what == 0x01) {
				String info = msg.getData().getString("1");
				Log.d("tag", "searchHandler" + info);

				Toast.makeText(file_search.this, info, Toast.LENGTH_SHORT)
						.show();

				imageButton.performClick();
			}
			if (msg.what == 0x03) {
				imageButton.performClick();
				Toast.makeText(file_search.this, "���ļ�������", Toast.LENGTH_SHORT)
						.show();

			}
			if (msg.what == 0x04) {
				imageButton.performClick();
			}

			if (msg.what == 0x05) {
				Toast.makeText(file_search.this, "�����ѶϿ�", Toast.LENGTH_LONG)
						.show();
				Log.d("tag", "0x05");
				do {
					readpptThread.setIsAlive(false);
				} while (readpptThread.getIsAlive());
				if (!readpptThread.getIsAlive()) {
					if (bluetooth.isOver) {
						try {
							Data.ClientblueSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!Data.ClientblueSocket.isConnected()) {
							Data.ClientblueSocket = null;
							Data.deviceName = "none";
						}
					}
					if (wifi.isOver) {
						try {
							Data.ClientwifiSocket.shutdownInput();
							Data.ClientwifiSocket.shutdownOutput();
							Data.ClientwifiSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (Data.ClientwifiSocket.isClosed()) {
							Data.ClientwifiSocket = null;
							Data.IP = "none";
						}

					}

					Data.isExit = "none";
					Data.isExtand = false;
					Data.SocketType = null;
					Intent intent = new Intent(file_search.this,
							chooseConnWay.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}

			}
			if (msg.what == 0x110) {
				Log.d("tag", "no ppt");
				Toast.makeText(file_search.this, "��ǰ·������ppt�ļ�",
						Toast.LENGTH_SHORT).show();
			}

		}

	};
	private static Handler revHandler;

	public static Handler getRevHandler() {
		return revHandler;
	}

	public static void setRevHandler(Handler revHandler) {
		file_search.revHandler = revHandler;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_search);
		revHandler = handler;
		Log.d("socket", "oncreate file_search");
		// �Զ���ɵ��б�
		listView = (listview_style) findViewById(R.id.lv);
		imageButton = (Button) findViewById(R.id.imageButton);
		progressBar = (ProgressBar) findViewById(R.id.progressBarTransport);
		listView.setVerticalScrollBarEnabled(true);
		// ����SimpleAdapter����������
		mySimpleAdapter = new SimpleAdapter(this, listName,// ����Դ
				R.layout.list_item,// ListView�ڲ�����չʾ��ʽ�Ĳ����ļ�listitem.xml
				new String[] { "fileName", "status" },// HashMap�е�keyֵpptPC
				new int[] { R.id.itemTitle, R.id.itemTitle });/*
															 * �����ļ�listitem.
															 * xml�������id
															 * �����ļ��ĸ�����ֱ�ӳ�䵽HashMap�ĸ�Ԫ����
															 * ���������
															 */
		mySimpleAdapter.setViewBinder(this);
		listView.setAdapter(mySimpleAdapter);
		Log.d("tag", "mySimpleAdaper" + getMySimpleAdapter());
		Log.d("socket", "search socket == " + Data.SocketType);
		isConnValid();
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �����ļ��ĸ�����ֱ�ӳ�䵽HashMap�ĸ�Ԫ���ϣ��������*/
				sendGetOrder();
			}
		});

		OnItemClickListener listener1 = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// //���ѡ�����HashMap����
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) listView
						.getItemAtPosition(arg2);
				String title = (String) map.get("fileName");
				FileStatus status = (FileStatus) map.get("status");
				if (!status.equals(FileStatus.ON_SD))
					sendPPTFileName(title);
				else {
					// Toast.makeText(file_search.this,
					// "PC�˲����ڸ��ļ����޷��򿪣�����PC�˵�����ļ�", Toast.LENGTH_LONG).show();
					Toast.makeText(file_search.this,
							"PC�˲����ڸ��ļ����޷��򿪣���ʱ��֧���ϴ��ļ�", Toast.LENGTH_LONG)
							.show();
				}

			}

		};
		// listview1 item�̼��� ���𵯳�android�����ص��Զ��ļ�ѡ���dialog
		OnItemLongClickListener listener2 = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String status = listName.get(arg2).get("status").toString();
				Log.d("status", "status==" + status);
				String fileName = (String) listName.get(arg2).get("fileName");
				if (status.equals("ON_PC")) {
					// progressBar.setVisibility(View.VISIBLE);
					//downFile(fileName);
					Toast.makeText(file_search.this, "���ļ�����PC�ˣ���ʱ�޷���������",
							Toast.LENGTH_LONG).show();
				}
				if (status.equals("ON_SD")) {

					upFile(fileName);
					// Toast.makeText(file_search.this, "���ļ����У��޷������ϴ�����",
					// Toast.LENGTH_LONG).show();
					// Toast.makeText(file_search.this,
					// "PC�˲����ڸ��ļ����޷��򿪣���ʱ��֧���ϴ��ļ�"+" "+fileName,
					// Toast.LENGTH_LONG).show();
				}
				if (status.equals("ON_BOTH")) {
					// progressBar.setVisibility(View.VISIBLE);
					Toast.makeText(file_search.this, "���ļ����У��޷������ϴ�����",
							Toast.LENGTH_LONG).show();
				}
				return false;
			}
		};
		listView.setOnItemClickListener(listener1);
		listView.setOnItemLongClickListener(listener2);
		imageButton.performClick();

	}

	private void upFile(String file) {
		final String filePath = Data.SDPath + "/" + file;
		Log.d("tag", "in file_search path::" + filePath);

		final String fileOrder = "�ϴ�" + file;
		AlertDialog alertDialog = new AlertDialog.Builder(file_search.this)
				.setTitle("�ϴ��ļ�")
				.setMessage("�Ƿ��ϴ����ļ�")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						progressBar.setVisibility(View.VISIBLE);
						try {
							ClientOutputStream.write(fileOrder.getBytes());
							Data.UpFile = filePath;
							Log.d("tag", "Data.UpFile::" + Data.UpFile);
							new Thread(new UpFileSend(file_search.this,
									handler, ClientInputStream,
									ClientOutputStream)).start();
						} catch (IOException e) {
							e.printStackTrace();
						} // ���������ļ�handler
						progressBar.setVisibility(View.INVISIBLE);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create(); // �����Ի���
		alertDialog.show();

	}

	private void downFile(String file) {
		Log.d("tag", "in file_search path::" + file);
		Data.DownFile = Data.SDPath + "/" + file;
		final String fileOrder = "���� " + file;
		AlertDialog alertDialog = new AlertDialog.Builder(file_search.this)
				.setTitle("�����ļ�").setMessage("�Ƿ����ظ��ļ�")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// ��PC��������+Ҫ���ص��ļ��� e.g. "���� "+"�ļ���";
						try {
							ClientOutputStream.write(fileOrder.getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).create(); // �����Ի���
		alertDialog.show();

	}

	private void sendPPTFileName(String title) {
		Toast.makeText(getApplicationContext(), "��ѡ���PPT�ǣ�" + title,
				Toast.LENGTH_SHORT).show();

		// ����ȡ��ppt�����͵�pc�ˣ�����
		b_byte = title.getBytes();
		try {
			ClientOutputStream.write(b_byte);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Intent intent = new Intent();
		intent.setClass(file_search.this, file_operate.class);
		file_search.this.startActivity(intent);
	}

	private void sendGetOrder() {
		listName.clear();
		mySimpleAdapter.notifyDataSetChanged();
		Log.d("go", "listName:" + listName.size());
		String Sent_code = "��ȡ�ļ���";
		byte[] code_byte = Sent_code.getBytes();
		try {
			Log.d("search", "outputStream==" + ClientOutputStream);
			ClientOutputStream.write(code_byte);
			Log.d("button", "�ļ��б�button �ѵ��,��д����ȡ�ļ���");
			Log.d("tag",
					"search in readThread is Alive == "
							+ readpptThread.getIsAlive());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Boolean isConnValid() {
		if (Data.SocketType.equals("WifiSocket")) {
			ClientWifiSocket = Data.ClientwifiSocket;
			try {
				ClientOutputStream = ClientWifiSocket.getOutputStream();
				ClientInputStream = ClientWifiSocket.getInputStream();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} else if (Data.SocketType.equals("BluetoothSocket")) {
			ClientblueSocket = Data.ClientblueSocket;
			Log.d("tag", "fileSearch Type " + ClientblueSocket);
			try {
				ClientOutputStream = ClientblueSocket.getOutputStream();
				ClientInputStream = ClientblueSocket.getInputStream();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} else {
			Log.d("tag", "δ֪����socket");
			return false;
		}

	}

	// ����������浽list�� ���н��յ��ĵ��Զ�ָ��·���µ�ppt�ļ�
	private ArrayList<HashMap<String, Object>> getPCFilesName(
			String[] NameString) {
		ArrayList<HashMap<String, Object>> tempPC = new ArrayList<HashMap<String, Object>>();
		if (NameString.length == 0) {
			Log.d("b...", " NameString  �� ");
		} else {
			for (int j = 0; j < NameString.length; j++) {

				if (NameString[j].equals("�ȴ�����PPT")) {
					Toast.makeText(file_search.this, "�ȴ�PC�˵���PPT",
							Toast.LENGTH_SHORT).show();
				} else if (NameString[j].equals("����ļ���")) {
					Toast.makeText(file_search.this, "������ϣ����",
							Toast.LENGTH_SHORT).show();
				} else {
					Log.d("name1", "name:" + NameString[j].toString());
					HashMap<String, Object> NameMap = new HashMap<String, Object>(
							2);
					NameMap.put("fileName", NameString[j]);
					Log.d("name1", "name:" + NameString[j].toString());
					NameMap.put("status", FileStatus.ON_PC);
					tempPC.add(NameMap);

				}

			}
			Log.d("go", "List_tempPC�ĳ���==" + tempPC.size());

		}
		return tempPC;

	}

	// ����SD���������ļ�
	private ArrayList<HashMap<String, Object>> getSDFilesName(String filePath) {

		File url = new File(filePath);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (url.listFiles().length <= 0) {
			Log.d("tag", "��ǰ·������ppt�ļ�");
		} else {
			File[] files = url.listFiles();
			if (files == null) {
				Log.d("tag", "��ǰ·������ppt�ļ�");
			}
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (fileName.endsWith(".ppt") || fileName.endsWith("dps")) {
					HashMap<String, Object> map = new HashMap<String, Object>(2);
					// map.put("size", getFileSize(files[i]));
					map.put("fileName", fileName);
					map.put("status", FileStatus.ON_SD);
					list.add(map);
				}
			}
		}
		return list;
	}

	private ArrayList<HashMap<String, Object>> setDataSource(
			ArrayList<HashMap<String, Object>> tempPC,
			ArrayList<HashMap<String, Object>> tempSD) {
		for (int i = 0; i < tempPC.size(); i++) {
			String fileNamePC = (String) tempPC.get(i).get("fileName");
			for (int j = 0; j < tempSD.size(); j++) {
				String fileNameSD = (String) tempSD.get(j).get("fileName");
				if (fileNamePC.equals(fileNameSD)) {
					tempPC.get(i).put("status", FileStatus.ON_NULL);
					tempSD.get(j).put("status", FileStatus.ON_NULL);
					HashMap<String, Object> map = new HashMap<String, Object>(2);
					map.put("fileName", fileNamePC);
					map.put("status", FileStatus.ON_BOTH);
					listName.add(map);
				}
			}
		}
		for (int m = 0; m < tempPC.size(); m++) {
			HashMap<String, Object> map = tempPC.get(m);
			FileStatus status = (FileStatus) map.get("status");
			if (status.equals(FileStatus.ON_PC)) {
				listName.add(map);
			}
		}
		for (int n = 0; n < tempSD.size(); n++) {
			HashMap<String, Object> map = tempSD.get(n);
			FileStatus status = (FileStatus) map.get("status");
			if (status.equals(FileStatus.ON_SD)) {
				listName.add(map);
			}
		}
		return listName;
	}

	// ��ȡSD���͵��Թ��е�ppt�ļ�

	public static class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals("InfoBundle")) {
				Log.d("receiver", "infoBundle �㲥�ѽ���");
				Bundle bundle = intent.getBundleExtra("1");
				Message msg = new Message();
				msg.what = 0x01;
				msg.setData(bundle);
				revHandler.sendMessage(msg);
			}
			if (intent.getAction().equals("NameStringBundle")) {
				Log.d("receiver", "NameStringBundle �㲥�ѽ���");
				Bundle bundle = intent.getBundleExtra("2");
				Message msg = new Message();
				msg.what = 0x02;
				msg.setData(bundle);
				revHandler.sendMessage(msg);

			}
			if (intent.getAction().equals("NoPPT")) {

				revHandler.sendEmptyMessage(0x110);
			}
			if (intent.getAction().equals("FileNotFound")) {
				revHandler.sendEmptyMessage(0x03);
				Log.d("tag", "file not found in search");
			}
			if (intent.getAction().equals("ConnectionBreakOut")) {
				revHandler.sendEmptyMessage(0x05);
				Log.d("tag", "connection break out");
			}
		}

	}

	@Override
	public boolean setViewValue(View view, Object data,
			String textRepresentation) {
		if (data instanceof String) {
			((TextView) view).setText(textRepresentation);
		} else if (data instanceof FileStatus) {
			switch ((FileStatus) data) {
			case ON_SD:
				((TextView) view).setTextColor(Color.GREEN);
				break;
			case ON_PC:
				((TextView) view).setTextColor(Color.RED);
				break;
			case ON_BOTH:
				((TextView) view).setTextColor(Color.WHITE);
				break;
			}
		}
		return true;
	}
}
