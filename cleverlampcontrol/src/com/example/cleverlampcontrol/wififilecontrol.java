package com.example.cleverlampcontrol;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.State;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;



import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

@SuppressLint({ "HandlerLeak", "DefaultLocale" })
public class wififilecontrol extends Activity {


	RadioGroup sendRadioGroup;
	private final byte[] LINK_SEND= {(byte)0xff,(byte)0xff,(byte)0x01};
	private final byte[] LINK_RESET = {(byte)0xff,(byte)0xff,(byte)0x05};
	
	RadioGroup deviceRadioGroup;
	ListView deviceListView;
	ArrayList<String> deviceList=new ArrayList<String>();
	ArrayAdapter<String> deviceAdapter;
	String address;
	//x
	ArrayList<String> ceshiArrayList = new ArrayList<String>();
	ListView ceshiListView;
	ArrayAdapter<String> ceshiAdapter ;
	//x
	
	Button connectButton;
	Button sendOneButton;
	Button sendAllButton;
	EditText ipinput_edittext,portnumberedittext;
	static int linkmode = 1;// 1tcp客户端2tcp服务器
	static int zhishi = 1;
	static int thstate = 0;
	Socket tcpcso = null;
	Socket tcpscso = null;
	ServerSocket tcpsso = null;
	private String ServerIP ;
	private int tcpSPORT ;
	// private static int udpSPORT = 6666;
	private DataInputStream reader;
	static BufferedInputStream buff_in;
	static InputStream input_one = null;
	static OutputStream output_one = null;
	DataOutputStream dos;
	String strR;
	Message m_one;
	ClientThread clientThread;
	SeekBar redBar,greenBar,blueBar;
	int Red_color,Green_color,Blue_color;
	boolean zidingyi_down = false;
	boolean ZIDINGYIBOOL = true;
	Bundle b_one;
	File[] folderAimFiler;
	String folderPath;
	String firstPath,secondPath;
	ArrayList<String> folderArrayList = new ArrayList<String>();
	ArrayList<String> txtList = new ArrayList<String>();
	FileDeal fd;
	Spinner folderSpinner ;
	ListView fileListView;
	TextView fileTextView;
	ArrayAdapter<String> fileAdapter ;
	Boolean connectResult = true;
	Boolean creatSocket = false;
	Boolean isSendBoolean = false;
	Boolean isWaitBoolean = true;
	Boolean isGetBoolean = true;
	Boolean isReadBoolean = true;
	Boolean isFileBoolean =false;
	
	Handler h_one = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String result = msg.getData().getString("result");
			if(result.equals("CONNECT OK")){
				
				Toast.makeText(wififilecontrol.this, "连接成功", Toast.LENGTH_SHORT).show();
				isSendBoolean = true;
			}
			else if(result.equals("CONNECT FALSE")) {
				Toast.makeText(wififilecontrol.this, "连接失败", Toast.LENGTH_SHORT).show();
				connectResult = true;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wififilecontrol);
		connectButton = (Button)findViewById(R.id.button_wififileipconnect);
		portnumberedittext = (EditText)findViewById(R.id.edittext_wififileduankouinput);
		ipinput_edittext = (EditText)findViewById(R.id.edittext_wififileipinput);
		sendOneButton = (Button)findViewById(R.id.button_wififilesendsome);	
			fd= new FileDeal();
			folderSpinner = (Spinner)findViewById(R.id.spinner_wififilename);
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
			fileListView = (ListView)findViewById(R.id.blueright_wififilelist);
			fileListView.setOnItemClickListener(fileListener);
			fileAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, txtList);
			fileListView.setAdapter(fileAdapter);
			fileTextView = (TextView)findViewById(R.id.textView_wififileselectfile1);
		sendOneButton.setOnClickListener(sendListener);
		sendAllButton = (Button)findViewById(R.id.button_wififilesendall);
		sendAllButton.setOnClickListener(resetListener);
		clientThread = new ClientThread();
		clientThread.start();
		connectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					if(connectResult)
					{
				     if (!ipinput_edittext.getText().toString().equals("")&&!portnumberedittext.getText().toString().equals("")) {
					    ServerIP = ipinput_edittext.getText().toString().trim();
					    tcpSPORT = Integer.parseInt(portnumberedittext.getText().toString().trim());
					    creatSocket = true;
					    connectResult = false;
				     }
				     else
				     {
				    	 Toast.makeText(wififilecontrol.this, "输入内容错误", Toast.LENGTH_SHORT).show();//提示为空 
				    	 
				     }
					}
					else{
						Toast.makeText(wififilecontrol.this, "已连接", Toast.LENGTH_SHORT).show();
					}
			     }

		});
		
	}

	
	private class ClientThread extends Thread {
		public void run() {
			while(true){
			initClient();
			}
		}

		public void initClient() {
			if(creatSocket){		
			try {
				tcpcso = new Socket(ServerIP, tcpSPORT);
				input_one = tcpcso.getInputStream();
				output_one = tcpcso.getOutputStream();
				dos = new DataOutputStream(output_one);
				Log.d("test", "客户端tcpcso创建");
				b_one = new Bundle();
				b_one.putString("result", "CONNECT OK");
				m_one = new Message();
				m_one.setData(b_one);
				h_one.sendMessage(m_one);
				creatSocket=false;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.d("test", "" + e.getMessage());
				tcpcso = null;
				e.printStackTrace();
				b_one = new Bundle();
				b_one.putString("result", "CONNECT FALSE");
				m_one = new Message();
				m_one.setData(b_one);
				h_one.sendMessage(m_one);
				connectResult = true;
				creatSocket=false;
			}
		}
	}
}


	
	
	private OnItemSelectedListener folderListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//txtList.clear();
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
	
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			tcpcso.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private OnClickListener sendListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isSendBoolean){
			if(isFileBoolean)
			{
				sendOneButton.setClickable(false);
				try {
					dos.write(LINK_SEND);
					dos.flush();
					Log.d("aa","指令发送");
					isGetBoolean = true;
					isReadBoolean = true;
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				isWaitBoolean = true;
				Log.d("aa",isWaitBoolean+"");
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						long time = System.currentTimeMillis();
						while(isWaitBoolean)
						{
							if(isSendBoolean){
								Log.d("aa","数据传送中。。。");
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
								isReadBoolean = true;
								isWaitBoolean = false;
								sendOneButton.setClickable(true);
								Log.d("aa","数据传送完毕");
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
				Toast.makeText(wififilecontrol.this, "请选择文件", Toast.LENGTH_SHORT).show();
			}
		}		
	}
};
	private OnClickListener resetListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				dos.write(LINK_RESET);
				dos.flush();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

}

