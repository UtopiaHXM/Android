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
				// 设置数据源
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
				Toast.makeText(file_search.this, "该文件不存在", Toast.LENGTH_SHORT)
						.show();

			}
			if (msg.what == 0x04) {
				imageButton.performClick();
			}

			if (msg.what == 0x05) {
				Toast.makeText(file_search.this, "连接已断开", Toast.LENGTH_LONG)
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
				Toast.makeText(file_search.this, "当前路径下无ppt文件",
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
		// 自动完成的列表
		listView = (listview_style) findViewById(R.id.lv);
		imageButton = (Button) findViewById(R.id.imageButton);
		progressBar = (ProgressBar) findViewById(R.id.progressBarTransport);
		listView.setVerticalScrollBarEnabled(true);
		// 生成SimpleAdapter适配器对象
		mySimpleAdapter = new SimpleAdapter(this, listName,// 数据源
				R.layout.list_item,// ListView内部数据展示形式的布局文件listitem.xml
				new String[] { "fileName", "status" },// HashMap中的key值pptPC
				new int[] { R.id.itemTitle, R.id.itemTitle });/*
															 * 布局文件listitem.
															 * xml中组件的id
															 * 布局文件的各组件分别映射到HashMap的各元素上
															 * ，完成适配
															 */
		mySimpleAdapter.setViewBinder(this);
		listView.setAdapter(mySimpleAdapter);
		Log.d("tag", "mySimpleAdaper" + getMySimpleAdapter());
		Log.d("socket", "search socket == " + Data.SocketType);
		isConnValid();
		imageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 布局文件的各组件分别映射到HashMap的各元素上，完成适配*/
				sendGetOrder();
			}
		});

		OnItemClickListener listener1 = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// //获得选中项的HashMap对象
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) listView
						.getItemAtPosition(arg2);
				String title = (String) map.get("fileName");
				FileStatus status = (FileStatus) map.get("status");
				if (!status.equals(FileStatus.ON_SD))
					sendPPTFileName(title);
				else {
					// Toast.makeText(file_search.this,
					// "PC端不存在该文件，无法打开，请在PC端导入该文件", Toast.LENGTH_LONG).show();
					Toast.makeText(file_search.this,
							"PC端不存在该文件，无法打开，暂时不支持上传文件", Toast.LENGTH_LONG)
							.show();
				}

			}

		};
		// listview1 item短监听 负责弹出android端下载电脑端文件选择的dialog
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
					Toast.makeText(file_search.this, "该文件属于PC端，暂时无法进行下载",
							Toast.LENGTH_LONG).show();
				}
				if (status.equals("ON_SD")) {

					upFile(fileName);
					// Toast.makeText(file_search.this, "该文件共有，无法进行上传下载",
					// Toast.LENGTH_LONG).show();
					// Toast.makeText(file_search.this,
					// "PC端不存在该文件，无法打开，暂时不支持上传文件"+" "+fileName,
					// Toast.LENGTH_LONG).show();
				}
				if (status.equals("ON_BOTH")) {
					// progressBar.setVisibility(View.VISIBLE);
					Toast.makeText(file_search.this, "该文件共有，无法进行上传下载",
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

		final String fileOrder = "上传" + file;
		AlertDialog alertDialog = new AlertDialog.Builder(file_search.this)
				.setTitle("上传文件")
				.setMessage("是否上传该文件")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
						} // 发送下载文件handler
						progressBar.setVisibility(View.INVISIBLE);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create(); // 创建对话框
		alertDialog.show();

	}

	private void downFile(String file) {
		Log.d("tag", "in file_search path::" + file);
		Data.DownFile = Data.SDPath + "/" + file;
		final String fileOrder = "下载 " + file;
		AlertDialog alertDialog = new AlertDialog.Builder(file_search.this)
				.setTitle("下载文件").setMessage("是否下载该文件")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// 向PC发送命令+要下载的文件名 e.g. "下载 "+"文件名";
						try {
							ClientOutputStream.write(fileOrder.getBytes());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).create(); // 创建对话框
		alertDialog.show();

	}

	private void sendPPTFileName(String title) {
		Toast.makeText(getApplicationContext(), "你选择的PPT是：" + title,
				Toast.LENGTH_SHORT).show();

		// 将获取的ppt名发送到pc端，并打开
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
		String Sent_code = "获取文件夹";
		byte[] code_byte = Sent_code.getBytes();
		try {
			Log.d("search", "outputStream==" + ClientOutputStream);
			ClientOutputStream.write(code_byte);
			Log.d("button", "文件列表button 已点击,已写出获取文件夹");
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
			Log.d("tag", "未知类型socket");
			return false;
		}

	}

	// 将名字数组存到list中 所有接收到的电脑端指定路径下的ppt文件
	private ArrayList<HashMap<String, Object>> getPCFilesName(
			String[] NameString) {
		ArrayList<HashMap<String, Object>> tempPC = new ArrayList<HashMap<String, Object>>();
		if (NameString.length == 0) {
			Log.d("b...", " NameString  空 ");
		} else {
			for (int j = 0; j < NameString.length; j++) {

				if (NameString[j].equals("等待导入PPT")) {
					Toast.makeText(file_search.this, "等待PC端导入PPT",
							Toast.LENGTH_SHORT).show();
				} else if (NameString[j].equals("请打开文件夹")) {
					Toast.makeText(file_search.this, "导入完毕，请打开",
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
			Log.d("go", "List_tempPC的长度==" + tempPC.size());

		}
		return tempPC;

	}

	// 本地SD卡内所有文件
	private ArrayList<HashMap<String, Object>> getSDFilesName(String filePath) {

		File url = new File(filePath);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		if (url.listFiles().length <= 0) {
			Log.d("tag", "当前路径下无ppt文件");
		} else {
			File[] files = url.listFiles();
			if (files == null) {
				Log.d("tag", "当前路径下无ppt文件");
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

	// 获取SD卡和电脑共有的ppt文件

	public static class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals("InfoBundle")) {
				Log.d("receiver", "infoBundle 广播已接收");
				Bundle bundle = intent.getBundleExtra("1");
				Message msg = new Message();
				msg.what = 0x01;
				msg.setData(bundle);
				revHandler.sendMessage(msg);
			}
			if (intent.getAction().equals("NameStringBundle")) {
				Log.d("receiver", "NameStringBundle 广播已接收");
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
