package com.D5.Threads;

import java.awt.TrayIcon;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import com.D5.MyBean.BlueConnPC;
import com.D5.OfJFrams.MyLittleTray;


public class BlueConnection implements Runnable {

	private StreamConnection Streamconnection = null;
	public static StreamConnectionNotifier notifier;
	private static BlueConnPC myPC = new BlueConnPC();
	private static boolean code = false;

	public static BlueConnPC getMyPC() {
		return myPC;
	}

	public void setMyPC(BlueConnPC myPC) {
		BlueConnection.myPC = myPC;
	}

	public static boolean getCode() {
		System.out.println("getCode");
		return code;
	}

	public static void setCode(boolean c) {
		code = c;
		System.out.println("setCode");
	}

	public StreamConnection getStreamConnection() {
		return Streamconnection;
	}

	public void setStreamConnection(StreamConnection s) {
		this.Streamconnection = s;
	}

	public void run() {
		getConnection(myPC);
	}

	// 获取连接
	public StreamConnection getConnection(BlueConnPC PC) {

		/*
		 * if(WifiConnection.getCode()){
		 * MyLittleTray.getTrayIcon().displayMessage("服务端", "当前存在Wifi连接",
		 * TrayIcon.MessageType.INFO); try {
		 * recieveThreadOfWifi.getOutputStream().write("存在wifi连接".getBytes()); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * }
		 *//* else { */
		setMyPC(PC);

		try {
			// 获取本地设备实例
			LocalDevice local = PC.getPClocalDevice();

			if (local != null) {
				System.out.println("本机存在蓝牙设备");
				myPC.setIsExitDevice(true);
				// System.out.println(myPc.getIsExitDevice());
				String bluetoothName = local.getFriendlyName();
				System.out.println(bluetoothName);
				String bluetoothAddress = local.getBluetoothAddress();
				System.out.println(bluetoothAddress);
				// 设备可发现模式为无时间限制模式
				myPC.setPCBluetoothAddress(bluetoothAddress);
				myPC.setPCBluetoothName(bluetoothName);
				System.out.println("PC蓝牙的地址:" + bluetoothAddress + "PC蓝牙的名字:"
						+ bluetoothName);
				UUID uuid = new UUID(80087355);// "04c6093b-0000-1000-8000-00805f9b34fb"
				myPC.setUuid(uuid);
				System.out.println("uuid=" + uuid.toString());
				// 利用UUID计算URL
				String url = "btspp://localhost:" + uuid.toString()
						+ ";name=RemoteBluetooth";
				myPC.setUrl(url);
				System.out.println("url=" + url);
				// 创建StreamConnectioinNotifier连接
				notifier = (StreamConnectionNotifier) Connector.open(url);

				System.out.println("notifier=" + notifier);
				System.out.println("nitifier==null 等待连接");

				if (notifier != null) {
					// 获取连接
					System.out.println("nitifier !=null 等待连接");
					Streamconnection = notifier.acceptAndOpen();


					System.out.println("连接状态" + code);

					if (Streamconnection.equals(null)) {
						code = false;
					} else {
						code = true;
						myPC.setConnection(Streamconnection);

					}
					setCode(code);

					if (getCode() == true) {
						MyLittleTray.getTrayIcon().displayMessage("服务端",
								"蓝牙连接成功", TrayIcon.MessageType.INFO);
						;
						MyLittleTray.getTrayIcon().setToolTip("蓝牙连接状态");

						Thread recieveThread = new Thread(
								new recieveThreadOfBlue(Streamconnection));
						recieveThread.start();

						while (recieveThreadOfBlue.getOutputStream() == null) {
							System.out.println("null");
						}
						recieveThreadOfBlue.getOutputStream().write(
								"存在BlueTooth连接".getBytes());

					} else if (getCode() == false) {
						MyLittleTray.getTrayIcon().displayMessage("服务端",
								"蓝牙连接失败", TrayIcon.MessageType.INFO);
						;
						MyLittleTray.getTrayIcon().setToolTip("蓝牙等待状态");
						/*
						 * 
						 * byte[] buffer = "pc服务端连接失败".getBytes() ;
						 * outputStream.write(buffer); outputStream.flush();
						 */
					}

					System.out.println("连接状态" + code);

					myPC.setNotifier(notifier);

					System.out.println("current connection:"
							+ myPC.getConnection());
				} else {
					return null;
				}
			} else {
				// 无蓝牙
				myPC.setIsExitDevice(false);
				System.out.println("PC端无蓝牙");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (notifier == null) {
				System.out.println("notofier is null");
				MyLittleTray.getTrayIcon().displayMessage("服务端",
						"蓝牙设备注册失败，蓝牙功能不能正常使用，请检查驱动！", TrayIcon.MessageType.INFO);
				return null;
			}
		}
		return Streamconnection;

	}

	// 释放连接

}
