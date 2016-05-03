package com.D5.OfJFrams;

import java.awt.TrayIcon;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ShowJframe extends JFrame {

	public ShowJframe(String name) {
		super(name);
	}

	public void initMain() {
		MyLittleTray mytray = new MyLittleTray();
		MyLittleTray.getTrayIcon().displayMessage("服务端",
				"请选择一种连接方式，当前PPT默认搜索路径为桌面", TrayIcon.MessageType.INFO);
	}
}
