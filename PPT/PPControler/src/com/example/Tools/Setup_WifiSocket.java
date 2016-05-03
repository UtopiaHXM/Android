package com.example.Tools;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import com.example.Data.Data;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;

public class Setup_WifiSocket {

	private Handler handler;
	private Socket ClientwifiSocket;

	public Setup_WifiSocket(Handler handler){
		this.handler = handler;
	}

	public Socket getClientwifiSocket() {
		return ClientwifiSocket;
	}

	public void setClientwifiSocket(Socket ClientwifiSocket) {
		this.ClientwifiSocket = ClientwifiSocket;
	}

	@SuppressLint("NewApi")
	public void Connection(String destIP) {

		while(ClientwifiSocket==null){
			try {
				String IP = destIP;
				Log.d("IP", IP);
				ClientwifiSocket = new Socket(destIP, 30000);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("infoError", "UnknownHost--Ox4000");
				if(Data.ClientwifiSocket!=null){
					try {
						Data.ClientwifiSocket.getOutputStream().write("断开连接".getBytes());
						Data.ClientwifiSocket.close();
						Data.ClientwifiSocket = null;
						Data.SocketType = null;
						Data.isExit = "none";
						Data.isExtand = false;
						Data.IP = "none";
						Log.d("IP","IP=="+Data.IP);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				ClientwifiSocket = null;
				handler.sendEmptyMessage(0x4000);
				Log.d("tag", "ClientwifiSocket in Setup == "+ClientwifiSocket);
				return;
				
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("infoError", "Connection--Ox4000");
				if(Data.ClientwifiSocket!=null){
					try {
						Data.IP = "none";
						Data.ClientwifiSocket.getOutputStream().write("断开连接".getBytes());
						Data.ClientwifiSocket.close();
						Data.ClientwifiSocket = null;
						Data.SocketType = null;
						Data.isExit = "none";
						Data.isExtand = false;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				handler.sendEmptyMessage(0x4000);
				ClientwifiSocket = null; 
				return;
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
				Log.d("infoError", "IO--Ox4000");
				handler.sendEmptyMessage(0x4000);
				if(Data.ClientwifiSocket!=null){
					try {
						Data.IP = "none";
						Data.SocketType = null;
						Data.ClientwifiSocket.getOutputStream().write("断开连接".getBytes());

						Data.ClientwifiSocket.close();
						Data.ClientwifiSocket = null;
						Data.isExit = "none";
						Data.isExtand = false;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				ClientwifiSocket = null;
				return;
			}
			if(ClientwifiSocket!=null){
				Data.ClientwifiSocket = ClientwifiSocket;
				Data.SocketType = "WifiSocket";
				Log.d("socket", " socket建立成功并连接");
				handler.sendEmptyMessage(0x3000);
			}
			
		}
		
	}
}