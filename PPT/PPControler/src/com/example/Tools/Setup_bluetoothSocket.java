package com.example.Tools;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import com.example.Data.Data;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class Setup_bluetoothSocket {

	private BluetoothSocket ClientblueSocket;

	private Handler handler;	
	private Boolean isSuccess = false;
	private BluetoothDevice device;
	private String connected_deviceName;

	public Setup_bluetoothSocket(Handler handler){
		this.handler = handler;
	}
	public String getConnected_deviceName() {
		return connected_deviceName;
	}

	public void setConnected_deviceName(String connected_deviceName) {
		this.connected_deviceName = connected_deviceName;
	}

	public BluetoothSocket getClientblueSocket() {
		return ClientblueSocket;
	}

	@SuppressWarnings("finally")
	@SuppressLint("NewApi")
	public void Connection(String bluetoothName, Set<BluetoothDevice> devices) {

		if (devices.size() > 0) {

			//String[] deviceString = new String[devices.size()];

			for (Iterator<BluetoothDevice> it = devices.iterator(); it
					.hasNext();) {
				device = it.next();

				if (device.getName().equals(bluetoothName)) {// 蓝牙连接请求
					connected_deviceName = device.getName();
					Log.d("tag", "请求连接蓝牙:"+connected_deviceName);
					
					try {
						ClientblueSocket = device
								.createRfcommSocketToServiceRecord(UUID
										.fromString("04c6093b-0000-1000-8000-00805f9b34fb"));// 00001101-0000-1000-8000-00805F9B34FB
						Log.d("socket", "Setup_bluetooth ClientblueSocket == "
								+ ClientblueSocket);

						ClientblueSocket.connect();
						if(ClientblueSocket.isConnected()){
							isSuccess = true;
						}
						Log.d("tag", "setup isConnect "+ClientblueSocket.isConnected());
					} catch (IOException e) {
						e.printStackTrace();
						Log.d("tag", "socket 建立失败");
						handler.sendEmptyMessage(0x00);
						if(Data.ClientblueSocket!=null){
							try {
								Data.deviceName = "none";
								Data.SocketType = null;
								Data.ClientblueSocket.getOutputStream().write("断开连接".getBytes());

								Data.ClientblueSocket.close();
								Data.ClientblueSocket = null;
								Data.isExit = "none";
								Data.isExtand = false;
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						return;
					}finally{
						if(isSuccess&&ClientblueSocket!=null){
							if(ClientblueSocket.isConnected()){
								Data.ClientblueSocket = ClientblueSocket;
								Data.SocketType = "BluetoothSocket";
								Data.deviceName = connected_deviceName;
								handler.sendEmptyMessage(0x3000);
						        Log.d("tag", "配对成功，socket成功建立并连接");
						        break;
							}else {
								Log.d("tag", "连接失败");
								handler.sendEmptyMessage(0x00);
							}
							
					}

				}
				

			}
			
			}
		}else {
			Log.d("tag", "无配对蓝牙，请重新配对");
			handler.sendEmptyMessage(0x4000);
		}


	}

}
