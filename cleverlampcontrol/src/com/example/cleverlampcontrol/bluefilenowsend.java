package com.example.cleverlampcontrol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class bluefilenowsend extends Activity{
	
	private final byte[] LINK_CHONGZHI={(byte)0xff,(byte)0xff,(byte)0x05};
	private final byte[] LINK_CHONGZHI1={(byte)0xff,(byte)0xff,(byte)0x05,(byte)0x01,(byte)0x02,(byte)0x03};
	OutputStream outputStream;
	InputStream inputStream;
	DataInputStream dis;
	DataOutputStream dos = new DataOutputStream(outputStream);
	
	RadioGroup deviceRadioGroup;
	RadioGroup sendRadioGroup;
	ListView deviceListView;
	BluetoothAdapter bAdapter;
	ArrayList<String> deviceList=new ArrayList<String>();
	ArrayAdapter<String> deviceAdapter;
	String address;
	BluetoothSocket clienSocket = null ;
	TextView fileTextView;
	ArrayAdapter<String> fileAdapter ;
	String folderPath;
	String firstPath,secondPath;
	Message m_one;
	Bundle b_one;
	Boolean isSendBoolean = false;
	Boolean isWaitBoolean = true;
	Boolean isGetBoolean = true;
	Boolean isReadBoolean = true;
	SeekBar redBar,greenBar,blueBar;
	int Red_color,Green_color,Blue_color;
	boolean zidingyi_down = false;
	boolean ZIDINGYIBOOL = true;
	boolean blueconnect_result=false;
	Handler h_one = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String result = msg.getData().getString("result");
			if(result.equals("CONNECT OK")){
				
				Toast.makeText(bluefilenowsend.this, "连接成功", Toast.LENGTH_SHORT).show();
				blueconnect_result = true;
			}
			else if(result.equals("CONNECT FALSE")) {
				Toast.makeText(bluefilenowsend.this, "连接失败", Toast.LENGTH_SHORT).show();
			}
			else if(result.equals("SENDING")){
				
				Toast.makeText(bluefilenowsend.this, "开始传输数据", Toast.LENGTH_SHORT).show();
			}
			else if(result.equals("SENDED")) {
				Toast.makeText(bluefilenowsend.this, "数据传输完毕", Toast.LENGTH_SHORT).show();
			}
		}
		
	};
	 @SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bluefilenowsend);  
	        bAdapter = BluetoothAdapter.getDefaultAdapter();
	        if (!bAdapter.isEnabled()) {
	            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	            startActivityForResult(enableIntent, 0);
	        }
	        sendRadioGroup = (RadioGroup)findViewById(R.id.radioGroup_bluenowsend);
	        sendRadioGroup.setOnCheckedChangeListener(sendListener1);
	        deviceRadioGroup = (RadioGroup)findViewById(R.id.radioGroup_bluenowpeidui);
	        deviceRadioGroup.setOnCheckedChangeListener(deviceListener);
	        deviceListView = (ListView)findViewById(R.id.blueleft_bluenowlist);
	        deviceListView.setOnItemClickListener(deviceConnectListener); 
	        deviceAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deviceList);
	        deviceListView.setAdapter(deviceAdapter);  
	        IntentFilter intentFilter=new IntentFilter(BluetoothDevice.ACTION_FOUND);
			registerReceiver(mReceiver, intentFilter);
			intentFilter=new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
			registerReceiver(mReceiver, intentFilter);
			deviceRadioGroup.clearCheck();
			redBar = (SeekBar)findViewById(R.id.seek_bluenowsendred);
			redBar.setOnSeekBarChangeListener(colorBarChangeListener);
			greenBar = (SeekBar)findViewById(R.id.seek_bluenowsendgreen);
			greenBar.setOnSeekBarChangeListener(colorBarChangeListener);
			blueBar = (SeekBar)findViewById(R.id.seek_bluenowsendblue);
			blueBar.setOnSeekBarChangeListener(colorBarChangeListener);

			//x
			//ceshiListView = (ListView)findViewById(R.id.blueright_list);
			//ceshiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, ceshiArrayList);
			//ceshiListView.setAdapter(ceshiAdapter);
			//x
			
	 }
	 private RadioGroup.OnCheckedChangeListener deviceListener = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radiobutton_bluenowpeidui:

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

			case R.id.radiobutton_bluenownopeidui:
				deviceList.clear();
				deviceAdapter.notifyDataSetChanged();
				bAdapter.startDiscovery();
				break;
				
			default:
				break;
			}
		}
	};
private RadioGroup.OnCheckedChangeListener sendListener1 = new RadioGroup.OnCheckedChangeListener() {
		

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radiobutton_bluenowchongzhi:
				if(blueconnect_result){
				ZIDINGYIBOOL = false;
				new Thread(new Runnable() {
             
					public void run() {
			
						if (zidingyi_down) {

									dos = new DataOutputStream(outputStream);
									blue_send(LINK_CHONGZHI1);							
							
						}
						
					
              
						if (!zidingyi_down) {
						
								dos = new DataOutputStream(outputStream);
								blue_send(LINK_CHONGZHI);												
					
						}
					}			
				}).start();
				}
				break;

			case R.id.radiobutton_bluenowzidingyi:
				if(blueconnect_result){
				zidingyi_down = true;
				send();
				}
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
				Toast.makeText(bluefilenowsend.this, "地址无效", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(bluefilenowsend.this, "正在连接蓝牙", Toast.LENGTH_SHORT).show();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						BluetoothDevice device=bAdapter.getRemoteDevice(address);
						try {
							clienSocket=device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
							clienSocket.connect();
							Log.d("aa","连接成功");
							b_one = new Bundle();
							b_one.putString("result", "CONNECT OK");
							m_one = new Message();
							m_one.setData(b_one);
							h_one.sendMessage(m_one);
							//Toast.makeText(bluesendActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try {
			clienSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
private OnSeekBarChangeListener colorBarChangeListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			switch (seekBar.getId()) {
			case R.id.seek_bluenowsendred:
				
					Red_color = seekBar.getProgress();
					
					
					

				break;

			case R.id.seek_bluenowsendgreen:
				

					Green_color = seekBar.getProgress();
					
					
				break;
				
			case R.id.seek_bluenowsendblue:
				
					Blue_color = seekBar.getProgress();
					
				
				break;
				
			default:
				break;
			}
			
			
		}
	};
	public void send(){
		ZIDINGYIBOOL = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				byte[] LINK_ZIDINGYI2 = {(byte)0xff,(byte)0xff,(byte)0x06};
				
					dos = new DataOutputStream(outputStream);
						blue_send(LINK_ZIDINGYI2);
						
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				while(ZIDINGYIBOOL){
					byte[] LINK_ZIDINGYI = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)Red_color,(byte)Green_color,(byte)Blue_color};
					
						
							dos = new DataOutputStream(outputStream);
							blue_send(LINK_ZIDINGYI);
						
						
						
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					
				}
				
			}			
		}).start();
	}
	
	public synchronized void blue_send(byte[] send_byte) {
	    // TODO 线程输出方法
		try {
			for(int i=0;i<send_byte.length;i++){
				dos.write(send_byte[i]);
				Log.d("send_byte",send_byte[i]+"");
			}			
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
