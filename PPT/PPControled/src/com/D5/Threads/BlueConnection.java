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

	// ��ȡ����
	public StreamConnection getConnection(BlueConnPC PC) {

		/*
		 * if(WifiConnection.getCode()){
		 * MyLittleTray.getTrayIcon().displayMessage("�����", "��ǰ����Wifi����",
		 * TrayIcon.MessageType.INFO); try {
		 * recieveThreadOfWifi.getOutputStream().write("����wifi����".getBytes()); }
		 * catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * }
		 *//* else { */
		setMyPC(PC);

		try {
			// ��ȡ�����豸ʵ��
			LocalDevice local = PC.getPClocalDevice();

			if (local != null) {
				System.out.println("�������������豸");
				myPC.setIsExitDevice(true);
				// System.out.println(myPc.getIsExitDevice());
				String bluetoothName = local.getFriendlyName();
				System.out.println(bluetoothName);
				String bluetoothAddress = local.getBluetoothAddress();
				System.out.println(bluetoothAddress);
				// �豸�ɷ���ģʽΪ��ʱ������ģʽ
				myPC.setPCBluetoothAddress(bluetoothAddress);
				myPC.setPCBluetoothName(bluetoothName);
				System.out.println("PC�����ĵ�ַ:" + bluetoothAddress + "PC����������:"
						+ bluetoothName);
				UUID uuid = new UUID(80087355);// "04c6093b-0000-1000-8000-00805f9b34fb"
				myPC.setUuid(uuid);
				System.out.println("uuid=" + uuid.toString());
				// ����UUID����URL
				String url = "btspp://localhost:" + uuid.toString()
						+ ";name=RemoteBluetooth";
				myPC.setUrl(url);
				System.out.println("url=" + url);
				// ����StreamConnectioinNotifier����
				notifier = (StreamConnectionNotifier) Connector.open(url);

				System.out.println("notifier=" + notifier);
				System.out.println("nitifier==null �ȴ�����");

				if (notifier != null) {
					// ��ȡ����
					System.out.println("nitifier !=null �ȴ�����");
					Streamconnection = notifier.acceptAndOpen();


					System.out.println("����״̬" + code);

					if (Streamconnection.equals(null)) {
						code = false;
					} else {
						code = true;
						myPC.setConnection(Streamconnection);

					}
					setCode(code);

					if (getCode() == true) {
						MyLittleTray.getTrayIcon().displayMessage("�����",
								"�������ӳɹ�", TrayIcon.MessageType.INFO);
						;
						MyLittleTray.getTrayIcon().setToolTip("��������״̬");

						Thread recieveThread = new Thread(
								new recieveThreadOfBlue(Streamconnection));
						recieveThread.start();

						while (recieveThreadOfBlue.getOutputStream() == null) {
							System.out.println("null");
						}
						recieveThreadOfBlue.getOutputStream().write(
								"����BlueTooth����".getBytes());

					} else if (getCode() == false) {
						MyLittleTray.getTrayIcon().displayMessage("�����",
								"��������ʧ��", TrayIcon.MessageType.INFO);
						;
						MyLittleTray.getTrayIcon().setToolTip("�����ȴ�״̬");
						/*
						 * 
						 * byte[] buffer = "pc���������ʧ��".getBytes() ;
						 * outputStream.write(buffer); outputStream.flush();
						 */
					}

					System.out.println("����״̬" + code);

					myPC.setNotifier(notifier);

					System.out.println("current connection:"
							+ myPC.getConnection());
				} else {
					return null;
				}
			} else {
				// ������
				myPC.setIsExitDevice(false);
				System.out.println("PC��������");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (notifier == null) {
				System.out.println("notofier is null");
				MyLittleTray.getTrayIcon().displayMessage("�����",
						"�����豸ע��ʧ�ܣ��������ܲ�������ʹ�ã�����������", TrayIcon.MessageType.INFO);
				return null;
			}
		}
		return Streamconnection;

	}

	// �ͷ�����

}
