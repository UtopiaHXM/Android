package com.example.cleverlampcontrol;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Thread.State;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Enumeration;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

@SuppressLint({ "HandlerLeak", "DefaultLocale" })
public class wififilenowsend extends Activity {


	RadioGroup sendRadioGroup;
	private final byte[] LINK_CHONGZHI={(byte)0xff,(byte)0xff,(byte)0x05};
	private final byte[] LINK_CHONGZHI1={(byte)0xff,(byte)0xff,(byte)0x05,(byte)0x01,(byte)0x02,(byte)0x03};
	Button connectButton;
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
	Boolean connectResult = true;
	Boolean creatSocket = false;
	
	Handler h_one = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			String result = msg.getData().getString("result");
			if(result.equals("CONNECT OK")){
				
				Toast.makeText(wififilenowsend.this, "连接成功", Toast.LENGTH_SHORT).show();
			}
			else if(result.equals("CONNECT FALSE")) {
				Toast.makeText(wififilenowsend.this, "连接失败", Toast.LENGTH_SHORT).show();
			}
		}

	};
private RadioGroup.OnCheckedChangeListener sendListener1 = new RadioGroup.OnCheckedChangeListener() {
		

		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.radiobutton_wifinowchongzhi:
				if(!connectResult){
				ZIDINGYIBOOL = false;
				new Thread(new Runnable() {
             
					public void run() {
			
						if (zidingyi_down) {

									wifi_send(LINK_CHONGZHI1);
							
							
						}
						
					
              
						if (!zidingyi_down) {
						
								wifi_send(LINK_CHONGZHI);												
					
						}
					}			
				}).start();
				}
				break;

			case R.id.radiobutton_wifinowzidingyi:
				if(!connectResult){
				zidingyi_down = true;
				send();
				}
				break;
				
			default:
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wififilenowsend);
		sendRadioGroup = (RadioGroup)findViewById(R.id.radioGroup_wifinowsend);
        sendRadioGroup.setOnCheckedChangeListener(sendListener1);
		connectButton = (Button)findViewById(R.id.button_wifinowipconnect);
		portnumberedittext = (EditText)findViewById(R.id.edittext_wifinowduankouinput);
		ipinput_edittext = (EditText)findViewById(R.id.edittext_wifinowipinput);
		redBar = (SeekBar)findViewById(R.id.seek_wifinowsendred);
		redBar.setOnSeekBarChangeListener(colorBarChangeListener);
		greenBar = (SeekBar)findViewById(R.id.seek_wifinowsendgreen);
		greenBar.setOnSeekBarChangeListener(colorBarChangeListener);
		blueBar = (SeekBar)findViewById(R.id.seek_wifinowsendblue);
		blueBar.setOnSeekBarChangeListener(colorBarChangeListener);
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
				    	 Toast.makeText(wififilenowsend.this, "输入内容错误", Toast.LENGTH_SHORT).show();//提示为空 
				    	 
				     }
					}
					else{
						Toast.makeText(wififilenowsend.this, "已连接", Toast.LENGTH_SHORT).show();
					}
			     }

		});
		
	}
	
@Override
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
			case R.id.seek_wifinowsendred:
				
					Red_color = seekBar.getProgress();
									

				break;

			case R.id.seek_wifinowsendgreen:
				

					Green_color = seekBar.getProgress();
					
					
				break;
				
			case R.id.seek_wifinowsendblue:
				
					Blue_color = seekBar.getProgress();
					
				
				break;
				
			default:
				break;
			}
			
			
		}
	};
	
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



	

	public String getLocalIpAddress() {
		String ipaddress = "";

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						ipaddress = ipaddress + ";"
								+ inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return ipaddress;
	}


	
	public void send(){
		ZIDINGYIBOOL = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				byte[] LINK_ZIDINGYI2 = {(byte)0xff,(byte)0xff,(byte)0x06};
				
					
						wifi_send(LINK_ZIDINGYI2);
						
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				while(ZIDINGYIBOOL){
					byte[] LINK_ZIDINGYI = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)Red_color,(byte)Green_color,(byte)Blue_color};
					
						
							
							wifi_send(LINK_ZIDINGYI);
							Log.d("wwwwwwwwwwwwwwwwwwwww","wwwwwwwwwwwwwwwwwww");
						
						
						
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
	public synchronized void wifi_send(byte[] send_byte) {
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

