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
		MyLittleTray.getTrayIcon().displayMessage("�����",
				"��ѡ��һ�����ӷ�ʽ����ǰPPTĬ������·��Ϊ����", TrayIcon.MessageType.INFO);
	}
}
