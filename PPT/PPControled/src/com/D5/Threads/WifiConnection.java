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
			serverSocket = new ServerSocket(30000);// ϵͳ����˿�
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
					MyLittleTray.getTrayIcon().displayMessage("�����",
							"wifi���ӳɹ�", TrayIcon.MessageType.INFO);
					;
					MyLittleTray.getTrayIcon().setToolTip("wifi����״̬");
					Thread wifireadThread = new Thread(new recieveThreadOfWifi(
							socket));
					wifireadThread.start();
					outputStream.write("����wifi����".getBytes());
				} else if (getCode() == false) {
					MyLittleTray.getTrayIcon().displayMessage("�����",
							"wifi�B��ʧ��", TrayIcon.MessageType.INFO);
					;
					MyLittleTray.getTrayIcon().setToolTip("wifi�ȴ�״̬");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

			}
		}

	}

}
