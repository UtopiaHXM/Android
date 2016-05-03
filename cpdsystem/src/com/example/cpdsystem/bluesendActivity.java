package com.example.cpdsystem;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class bluesendActivity extends Activity{
	private final byte[] LINK_SEND= {(byte)0xff,(byte)0xff,(byte)0x01};
	private final byte[] LINK_RESET = {(byte)0xff,(byte)0xff,(byte)0x05};
	
	OutputStream outputStream;
	InputStream inputStream;
	DataInputStream dis;
	DataOutputStream dos;
	
	RadioGroup deviceRadioGroup;
	ListView deviceListView;
	BluetoothAdapter bAdapter;
	ArrayList<String> deviceList=new ArrayList<String>();
	ArrayAdapter<String> deviceAdapter;
	String address;
	BluetoothSocket clienSocket = null ;
	//x
	ArrayList<String> ceshiArrayList = new ArrayList<String>();
	ListView ceshiListView;
	ArrayAdapter<String> ceshiAdapter ;
	//x
	Spinner folderSpinner ;
	ListView fileListView;
	TextView fileTextView;
	Button sendOneButton;
	Button sendAllButton;
	ArrayList<String> folderArrayList = new ArrayList<String>();
	ArrayList<String> txtList = new ArrayList<String>();
	ArrayAdapter<String> fileAdapter ;
	File[] folderAimFiler;
	String folderPath;
	String firstPath,secondPath;
	FileDeal fd;
	Message m_one;
	Bundle b_one;
	Boolean isSendBoolean = false;
	Boolean isWaitBoolean = true;
	Boolean isGetBoolean = true;
	Boolean isReadBoolean = true;
	Boolean isFileBoolean =false;
	
	Handler h_one = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String result = msg.getData().getString("result");
			if(result.equals("CONNECT OK")){
				sendOneButton.setVisibility(0x00000000);
				sendAllButton.setVisibility(0x00000000);
				Toast.makeText(bluesendActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
			}
			else if(result.equals("CONNECT FALSE")) {
				Toast.makeText(bluesendActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
			}
			else if(result.equals("SENDING")){
				
				Toast.makeText(bluesendActivity.this, "开始传输数据", Toast.LENGTH_SHORT).show();
			}
			else if(result.equals("SENDED")) {
				Toast.makeText(bluesendActivity.this, "传输数据完毕", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	 @SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bluesend);  
	        bAdapter = BluetoothAdapter.getDefaultAdapter();
	        if (!bAdapter.isEnabled()) {
	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableIntent, 0);
	        }
	        deviceRadioGroup = (RadioGroup)findViewById(R.id.radioGroup_peidui);
	        deviceRadioGroup.setOnCheckedChangeListener(deviceListener);
	        deviceListView = (ListView)findViewById(R.id.blueleft_list);
	        deviceListView.setOnItemClickListener(deviceConnectListener); 
	        deviceAdapter=new ArrayAdapter<String>(this, R.layout.simple_list_item, deviceList);
	        deviceListView.setAdapter(deviceAdapter);  
	        IntentFilter intentFilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
			registerReceiver(mReceiver, intentFilter);
			intentFilter=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
			registerReceiver(mReceiver, intentFilter);
			deviceRadioGroup.clearCheck();
			
			fd= new FileDeal();
			folderSpinner = (Spinner)findViewById(R.id.spinner_filename);
			folderSpinner.getScrollY();
			folderPath=new Environment().getExternalStorageDirectory()
					.toString() + "/" + "command";
			File folderFile = new File(folderPath);
			folderAimFiler = folderFile.listFiles();
			for(int i=0;i<folderAimFiler.length;i++)
			{
				folderArrayList.add(folderAimFiler[i].getName());
			}
			SpinnerAdapter folderAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item,folderArrayList);
			folderSpinner.setAdapter(folderAdapter);
			folderSpinner.setOnItemSelectedListener(folderListener);
			fileListView = (ListView)findViewById(R.id.blueright_list);
			fileListView.setOnItemClickListener(fileListener);
			fileAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, txtList);
			fileListView.setAdapter(fileAdapter);
			//x
			//ceshiListView = (ListView)findViewById(R.id.blueright_list);
			//ceshiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, ceshiArrayList);
			//ceshiListView.setAdapter(ceshiAdapter);
			//x
			fileTextView = (TextView)findViewById(R.id.textView_selectfile1);
			sendOneButton = (Button)findViewById(R.id.button_sendsome);
			sendOneButton.setOnClickListener(sendListener);
			sendAllButton = (Button)findViewById(R.id.button_sendall);
			sendAllButton.setOnClickListener(resetListener);
			sendOneButton.setVisibility(0x00000004);
			sendAllButton.setVisibility(0x00000004);
			
	 }
	 private RadioGroup.OnCheckedChangeListener deviceListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radiobutton_peidui:
				Log.d("aa","sdad");
				deviceList.clear();
				Set<BluetoothDevice> pariedDevices = bAdapter.getBondedDevices();
				if(pariedDevices.size()>0)
				{
					for(BluetoothDevice device : pariedDevices)
					{
						String aString=device.getName() + "\n" + device.getAddress();
						deviceList.add(aString);
					}
				}
				else {
					deviceList.add("NO DEVICE"+ "\n" +"*****************");
				}
				deviceAdapter.notifyDataSetChanged();
				break;

			case R.id.radiobutton_nopeidui:
				deviceList.clear();
				deviceAdapter.notifyDataSetChanged();
				bAdapter.startDiscovery();
				break;
				
			default:
				break;
			}
		}
	};
	private BroadcastReceiver mReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action=intent.getAction();
			
			if(BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice device=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if(device.getBondState()!=BluetoothDevice.BOND_BONDED);
				{
					deviceList.add(device.getName()+"\n"+device.getAddress());
				}
				deviceAdapter.notifyDataSetChanged();
			}
			else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
			{
				if(deviceList.size()==0)
				{
					deviceList.add("NO FOUND"+ "\n" +"*****************");
				}
				deviceAdapter.notifyDataSetChanged();
			}
			
		}
 		
 	};
 	private OnItemClickListener deviceConnectListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String info=((TextView)arg1).getText().toString();
			address = info.substring(info.length()-17);
			if(address.equals("*****************"))
			{
				Toast.makeText(bluesendActivity.this, "地址无效", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(bluesendActivity.this, "正在连接数据", Toast.LENGTH_SHORT).show();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						BluetoothDevice device=bAdapter.getRemoteDevice(address);
						try {
							clienSocket=device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
							Log.d("aa","cccc");
							clienSocket.connect();
							Log.d("aa","bbbbbb");
							Log.d("aa","连接成功");
							b_one = new Bundle();
							b_one.putString("result", "CONNECT OK");
							m_one = new Message();
							m_one.setData(b_one);
							h_one.sendMessage(m_one);
							//Toast.makeText(bluesendActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
							outputStream = clienSocket.getOutputStream();
							inputStream = clienSocket.getInputStream();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Log.d("aa","aaaa");
							b_one = new Bundle();
							b_one.putString("result", "CONNECT FALSE");
							m_one = new Message();
							m_one.setData(b_one);
							h_one.sendMessage(m_one);
							//Toast.makeText(bluesendActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
						}
					}
				}).start();
			}
			
		}
	};
	private OnItemSelectedListener folderListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			txtList.clear();
			String folderAddress=((TextView)arg1).getText().toString();
			String txtFilePath = folderPath +"/" +folderAddress;
			Log.d("file","aa"+txtFilePath );
			firstPath = txtFilePath;
			File txtFile = new File(txtFilePath);
			ArrayList<String> thirdList = fd.getFileNameListTxt(txtFile, ".txt");
			for(int i=0;i<thirdList.size();i++){
				txtList.add(thirdList.get(i).toString());
			};
            //txtList = ceshiArrayList; 
			//Log.d("file","file.."+txtList.get(0).toString());
			fileAdapter.notifyDataSetChanged();	
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	private OnItemClickListener fileListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String txtChoosed=((TextView)arg1).getText().toString();
			fileTextView.setText(txtChoosed);
			isFileBoolean = true;
			secondPath = firstPath + "/"+txtChoosed;
		}
	};
	private OnClickListener sendListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isFileBoolean)
			{
				sendOneButton.setClickable(false);
				dos = new DataOutputStream(outputStream);
				try {
					dos.write(LINK_SEND);
					dos.flush();
					Log.d("aa","指令发送中");
					isGetBoolean = true;
					isReadBoolean = true;
					
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							while(isGetBoolean){
								try {
									getIn();
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
								
						}
					}).start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isWaitBoolean = true;
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						long time = System.currentTimeMillis();
						while(isWaitBoolean)
						{
							if(isSendBoolean){
								Log.d("aa","数据发送中");
								ArrayList<byte[]> sendDataArrayList = new ArrayList<byte[]>();
								sendDataArrayList = fd.readtxtFile(secondPath);
								Log.d("aa","ss.."+sendDataArrayList.size());
								for(int i =0;i<sendDataArrayList.size();i++)
								{
									try {
										String aaaaa = new String(sendDataArrayList.get(i));
										Log.d("aa","data"+i+":"+aaaaa);
										dos.write(sendDataArrayList.get(i));
										dos.flush();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								isSendBoolean = false;
								isReadBoolean = true;
								isWaitBoolean = false;
								sendOneButton.setClickable(true);
								Log.d("aa","数据传输完毕");
							}
							else {
								if(System.currentTimeMillis()-time>100000){
									isWaitBoolean = false;
									sendOneButton.setClickable(true);
									Log.d("aa","超时");
								}
							}
						}
					}
				}).start();
				//isReadBoolean = true;
			}else{
				Toast.makeText(bluesendActivity.this, "请选择文件", Toast.LENGTH_SHORT).show();
			}
			
			
		}
	};
	private void getIn(){
		byte[] match={(byte)0xff,(byte)0x01,(byte)0x02,(byte)0x03,(byte)0x04};
		byte[] read_s = new byte[3];
		dis = new DataInputStream(inputStream);
		
		while(isReadBoolean)
		{			
			Log.d("aa","寮�鎺ュ彈" );
			read_s[0]=read_s[1];
			read_s[1]=read_s[2];
			try {
				read_s[2]=dis.readByte();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(read_s[0]==match[0]&&read_s[1]==match[0]&&read_s[2]==match[2])
			{
				Log.d("aa","开始传输");
				isReadBoolean=false;
				isSendBoolean = true;
				b_one = new Bundle();
				b_one.putString("result", "SENDING");
				m_one = new Message();
				m_one.setData(b_one);
				h_one.sendMessage(m_one);
			}
			else if(read_s[0]==match[0]&&read_s[1]==match[0]&&read_s[2]==match[3]){
				isReadBoolean = false;
				isGetBoolean = false;
				b_one = new Bundle();
				b_one.putString("result", "SENDED");
				m_one = new Message();
				m_one.setData(b_one);
				h_one.sendMessage(m_one);
				Log.d("aa","数据接收成功");
			}
		}
	}
	private OnClickListener resetListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dos = new DataOutputStream(outputStream);
			try {
				dos.write(LINK_RESET);
				dos.flush();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	};
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			if(clienSocket!=null)
			{
				clienSocket.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
