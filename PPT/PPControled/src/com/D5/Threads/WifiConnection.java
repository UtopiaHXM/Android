package com.D5.Threads;

import java.awt.TrayIcon;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.D5.MyBean.WifiConnPC;
import com.D5.OfJFrams.MyLittleTray;


public class WifiConnection implements Runnable {
	private ServerSocket serverSocket = null;
	private Socket socket;
	private OutputStream outputStream;
	private static boolean code = false;
	private static WifiConnPC myPC = new WifiConnPC();

	public static WifiConnPC getMyPC() {
		return myPC;
	}

	public static void setMyPC(WifiConnPC myPC) {
		WifiConnection.myPC = myPC;
	}

	public static boolean getCode() {
		return code;
	}

	public void setCode(boolean c) {
		code = c;
	}

	public void run() {
		Connetion();
	}

	public void Connetion() {
		try {
			serverSocket = new ServerSocket(30000);// 系统分配端口
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (true) {
			try {
				socket = serverSocket.accept();
				outputStream = socket.getOutputStream();
				socket.getInputStream();
				if (socket.equals(null) || (!socket.isConnected())) {
					code = false;
				} else {
					code = true;
				}
				setCode(code);
				if (getCode() == true) {
					myPC.setSocket(socket);
					MyLittleTray.getTrayIcon().displayMessage("服务端",
							"wifi连接成功", TrayIcon.MessageType.INFO);
					;
					MyLittleTray.getTrayIcon().setToolTip("wifi连接状态");
					Thread wifireadThread = new Thread(new recieveThreadOfWifi(
							socket));
					wifireadThread.start();
					outputStream.write("存在wifi连接".getBytes());
				} else if (getCode() == false) {
					MyLittleTray.getTrayIcon().displayMessage("服务端",
							"wifi連接失敗", TrayIcon.MessageType.INFO);
					;
					MyLittleTray.getTrayIcon().setToolTip("wifi等待状态");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
		}

	}

}
