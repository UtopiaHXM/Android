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

	private String text = "ppt控制";
	private static TrayIcon trayIcon;

	public static void setTrayIcon(TrayIcon t) {
		trayIcon = t;
	}

	public static TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public MyLittleTray() {
		setTray();
		MyLittleTray.getTrayIcon().setToolTip("路径未设置");
	}

	        // 添加托盘显示：先判断当前平台是否支持托盘显示
	public void setTray() {
		if (SystemTray.isSupported()) {// 判断当前平台是否支持托盘功能
			// 创建托盘实例
			SystemTray tray = SystemTray.getSystemTray();
			// 创建托盘图标：1.显示图标Image 2.停留提示text 3.弹出菜单popupMenu 4.创建托盘图标实例
			Image image = Toolkit.getDefaultToolkit().getImage("Image/up2.jpg");
			// 弹出菜单popupMenu
			PopupMenu popMenu = new PopupMenu();

			Menu itmWifi = new Menu("Wifi连接");
			itmWifi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});

			MenuItem addItem = new MenuItem(getIPOFPC.getLocalIp());

			addItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (BlueConnection.getCode()) {
						MyLittleTray.getTrayIcon().displayMessage("服务端",
								"当前存在蓝牙连接", TrayIcon.MessageType.INFO);
						try {
							recieveThreadOfBlue.getOutputStream().write(
									"存在BlueTooth连接".getBytes());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						if (MyChooser.getChoosePath().equals("")) {
							MyLittleTray.getTrayIcon().displayMessage("服务端",
									"当前路径默认系统桌面，如有需要请设置路径",
									TrayIcon.MessageType.INFO);
							MyChooser.setChoosePath(tabShow
									.createFileCollection().toString());
						}
						MyLittleTray.getTrayIcon().displayMessage("服务端",
								"IP:" + getIPOFPC.getLocalIp(),
								TrayIcon.MessageType.INFO);

						if (WetherWifiRun) {

						} else {
							wificonThread.start();
							WetherWifiRun = true;
							WetherConnect = WifiConnection.getCode();
							if (WetherConnect) {
								MyLittleTray.getTrayIcon().setToolTip(
										"wifi连接状态");
							} else {
								MyLittleTray.getTrayIcon().setToolTip(
										"wifi等待状态");
							}
						}
					}
				}
			});
			itmWifi.add(addItem);

			MenuItem itmBlue = new MenuItem("蓝牙连接");
			itmBlue.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (WifiConnection.getCode()) {
						MyLittleTray.getTrayIcon().displayMessage("服务端",
								"当前存在Wifi连接", TrayIcon.MessageType.INFO);
						try {
							recieveThreadOfWifi.getOutputStream().write(
									"存在wifi连接".getBytes());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						if (MyChooser.getChoosePath().equals("")) {
							MyLittleTray.getTrayIcon().displayMessage("服务端",
									"当前路径默认系统桌面，如有需要请设置路径",
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
							MyLittleTray.getTrayIcon().displayMessage("服务端",
									"本机蓝牙是" + device.getFriendlyName(),
									TrayIcon.MessageType.INFO);

							if (WetherBlueRun) {

							} else {
								blueconThread.start();
								WetherBlueRun = true;
								WetherConnect = BlueConnection.getCode();

								if (WetherConnect) {
									MyLittleTray.getTrayIcon().setToolTip(
											"蓝牙连接状态");
								} else {
									MyLittleTray.getTrayIcon().setToolTip(
											"蓝牙等待状态");
								}

							}
						} else {
							MyLittleTray.getTrayIcon().displayMessage("服务端",
									"本机无蓝牙设备", TrayIcon.MessageType.INFO);
						}
					}
				}
			});

			MenuItem information = new MenuItem("说明");
			information.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					/*
					 * String informate = "本地IP為：" + getIPOFPC.getLocalIp();
					 * JOptionPane.showMessageDialog(null, informate, "说明", 3);
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

			MenuItem itmExit = new MenuItem("退出");
			itmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if (BlueConnection.getCode()) {
						try {
							recieveThreadOfBlue.getOutputStream().write(
									"连接已断开".getBytes());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					if (WifiConnection.getCode()) {
						try {
							recieveThreadOfWifi.getOutputStream().write(
									"连接已断开".getBytes());

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

					MyLittleTray.getTrayIcon().displayMessage("服务端",
							"连接断开，退出程序！", TrayIcon.MessageType.INFO);

					final Timer timer = new Timer();
					TimerTask task = new TimerTask() {
						public void run() {
							System.exit(0);
							// timer.cancel();
							// 终止此计时器，丢弃所有当前已安排的任务。这不会干扰当前正在执行的任务（如果存在）。一旦终止了计时器，那么它的执行线程也会终止，并且无法根据它安排更多的任务。注意，在此计时器调用的计时器任务的
							// run
							// 方法内调用此方法，就可以绝对确保正在执行的任务是此计时器所执行的最后一个任务。
						}
					};
					timer.schedule(task, 1000, 2000); // 这个命令就是4秒钟之后执行TimerTask里边的内容，后边的执行时间间隔为2秒钟。
				}
			});

			MenuItem itmPath = new MenuItem("设置路径");
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

			// 创建托盘图标
			trayIcon = new TrayIcon(image, text, popMenu);
			setTrayIcon(trayIcon);
			WetherConnect = BlueConnection.getCode();
			if (WetherConnect) {
				trayIcon.setToolTip("wifi已连接");
			} else {
			}
			// 添加指定的鼠标侦听器，以接收发自此 TrayIcon 的鼠标事件。
			trayIcon.addMouseListener(new MyDoubleClick());
			// 将托盘图标加到托盘上
			try {
				tray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}
	}
}