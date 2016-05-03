package com.D5.OfJFrams;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.swing.JFrame;

import com.D5.Function.getIPOFPC;
import com.D5.MouseClick.MyDoubleClick;
import com.D5.MyBean.Inform;
import com.D5.MyBean.MyChooser;
import com.D5.Threads.BlueConnection;
import com.D5.Threads.WifiConnection;
import com.D5.Threads.recieveThreadOfBlue;
import com.D5.Threads.recieveThreadOfWifi;


public class MyLittleTray extends JFrame {

	private static final long serialVersionUID = -2748466485251174355L;
	private boolean WetherConnect = false;
	private boolean WetherWifiRun = false;
	private boolean WetherBlueRun = false;
	private BlueConnection blueconnection = new BlueConnection();
	private Thread blueconThread = new Thread(blueconnection);

	private WifiConnection wificonnection = new WifiConnection();
	private Thread wificonThread = new Thread(wificonnection);

	private String text = "ppt����";
	private static TrayIcon trayIcon;

	public static void setTrayIcon(TrayIcon t) {
		trayIcon = t;
	}

	public static TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public MyLittleTray() {
		setTray();
		MyLittleTray.getTrayIcon().setToolTip("·��δ����");
	}

	        // ���������ʾ�����жϵ�ǰƽ̨�Ƿ�֧��������ʾ
	public void setTray() {
		if (SystemTray.isSupported()) {// �жϵ�ǰƽ̨�Ƿ�֧�����̹���
			// ��������ʵ��
			SystemTray tray = SystemTray.getSystemTray();
			// ��������ͼ�꣺1.��ʾͼ��Image 2.ͣ����ʾtext 3.�����˵�popupMenu 4.��������ͼ��ʵ��
			Image image = Toolkit.getDefaultToolkit().getImage("Image/up2.jpg");
			// �����˵�popupMenu
			PopupMenu popMenu = new PopupMenu();

			Menu itmWifi = new Menu("Wifi����");
			itmWifi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});

			MenuItem addItem = new MenuItem(getIPOFPC.getLocalIp());

			addItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (BlueConnection.getCode()) {
						MyLittleTray.getTrayIcon().displayMessage("�����",
								"��ǰ������������", TrayIcon.MessageType.INFO);
						try {
							recieveThreadOfBlue.getOutputStream().write(
									"����BlueTooth����".getBytes());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						if (MyChooser.getChoosePath().equals("")) {
							MyLittleTray.getTrayIcon().displayMessage("�����",
									"��ǰ·��Ĭ��ϵͳ���棬������Ҫ������·��",
									TrayIcon.MessageType.INFO);
							MyChooser.setChoosePath(tabShow
									.createFileCollection().toString());
						}
						MyLittleTray.getTrayIcon().displayMessage("�����",
								"IP:" + getIPOFPC.getLocalIp(),
								TrayIcon.MessageType.INFO);

						if (WetherWifiRun) {

						} else {
							wificonThread.start();
							WetherWifiRun = true;
							WetherConnect = WifiConnection.getCode();
							if (WetherConnect) {
								MyLittleTray.getTrayIcon().setToolTip(
										"wifi����״̬");
							} else {
								MyLittleTray.getTrayIcon().setToolTip(
										"wifi�ȴ�״̬");
							}
						}
					}
				}
			});
			itmWifi.add(addItem);

			MenuItem itmBlue = new MenuItem("��������");
			itmBlue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (WifiConnection.getCode()) {
						MyLittleTray.getTrayIcon().displayMessage("�����",
								"��ǰ����Wifi����", TrayIcon.MessageType.INFO);
						try {
							recieveThreadOfWifi.getOutputStream().write(
									"����wifi����".getBytes());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						if (MyChooser.getChoosePath().equals("")) {
							MyLittleTray.getTrayIcon().displayMessage("�����",
									"��ǰ·��Ĭ��ϵͳ���棬������Ҫ������·��",
									TrayIcon.MessageType.INFO);
							MyChooser.setChoosePath(tabShow
									.createFileCollection().toString());
						}
						LocalDevice device = null;
						try {
							device = LocalDevice.getLocalDevice();

						} catch (BluetoothStateException e1) {
						}
						if (device != null) {
							MyLittleTray.getTrayIcon().displayMessage("�����",
									"����������" + device.getFriendlyName(),
									TrayIcon.MessageType.INFO);

							if (WetherBlueRun) {

							} else {
								blueconThread.start();
								WetherBlueRun = true;
								WetherConnect = BlueConnection.getCode();

								if (WetherConnect) {
									MyLittleTray.getTrayIcon().setToolTip(
											"��������״̬");
								} else {
									MyLittleTray.getTrayIcon().setToolTip(
											"�����ȴ�״̬");
								}

							}
						} else {
							MyLittleTray.getTrayIcon().displayMessage("�����",
									"�����������豸", TrayIcon.MessageType.INFO);
						}
					}
				}
			});

			MenuItem information = new MenuItem("˵��");
			information.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					/*
					 * String informate = "����IP�飺" + getIPOFPC.getLocalIp();
					 * JOptionPane.showMessageDialog(null, informate, "˵��", 3);
					 */
					if (Inform.getInfor() == null) {
						Information in = new Information();
						Inform.setInfor(in);
						Inform.getInfor().SetShow();
					} else {
						if (Inform.isIn()) {
							
						} else {
							Inform.setInfor(null);
							Information in = new Information();
							Inform.setInfor(in);
							Inform.getInfor().SetShow();
						}
					}
				}
			});

			MenuItem itmExit = new MenuItem("�˳�");
			itmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if (BlueConnection.getCode()) {
						try {
							recieveThreadOfBlue.getOutputStream().write(
									"�����ѶϿ�".getBytes());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if (WifiConnection.getCode()) {
						try {
							recieveThreadOfWifi.getOutputStream().write(
									"�����ѶϿ�".getBytes());

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

					MyLittleTray.getTrayIcon().displayMessage("�����",
							"���ӶϿ����˳�����", TrayIcon.MessageType.INFO);

					final Timer timer = new Timer();
					TimerTask task = new TimerTask() {
						public void run() {
							System.exit(0);
							// timer.cancel();
							// ��ֹ�˼�ʱ�����������е�ǰ�Ѱ��ŵ������ⲻ����ŵ�ǰ����ִ�е�����������ڣ���һ����ֹ�˼�ʱ������ô����ִ���߳�Ҳ����ֹ�������޷����������Ÿ��������ע�⣬�ڴ˼�ʱ�����õļ�ʱ�������
							// run
							// �����ڵ��ô˷������Ϳ��Ծ���ȷ������ִ�е������Ǵ˼�ʱ����ִ�е����һ������
						}
					};
					timer.schedule(task, 1000, 2000); // ����������4����֮��ִ��TimerTask��ߵ����ݣ���ߵ�ִ��ʱ����Ϊ2���ӡ�
				}
			});

			MenuItem itmPath = new MenuItem("����·��");
			itmPath.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (MyChooser.getImportppt() == null) {
						importPPT importppt = new importPPT();
						MyChooser.setImportppt(importppt);
						MyChooser.getImportppt().showChooseFrame();
					} else {
						if (MyChooser.getImportppt().getIsExit()) {
						} else {
							MyChooser.setImportppt(null);
							importPPT importppt = new importPPT();
							MyChooser.setImportppt(importppt);
							MyChooser.getImportppt().showChooseFrame();
						}
					}

				}
			});

			
			
			popMenu.add(itmPath);
			popMenu.add(itmWifi);
			popMenu.add(itmBlue);
			popMenu.add(information);
			popMenu.add(itmExit);

			// ��������ͼ��
			trayIcon = new TrayIcon(image, text, popMenu);
			setTrayIcon(trayIcon);
			WetherConnect = BlueConnection.getCode();
			if (WetherConnect) {
				trayIcon.setToolTip("wifi������");
			} else {
			}
			// ���ָ����������������Խ��շ��Դ� TrayIcon ������¼���
			trayIcon.addMouseListener(new MyDoubleClick());
			// ������ͼ��ӵ�������
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}
}