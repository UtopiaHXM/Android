package com.D5.MainMethod;

import javax.swing.JFrame;
import javax.swing.UIManager;

import com.D5.OfJFrams.ShowJframe;


public class MainFunction {

	public static void main(String[] args) {

		Beautify();
		

	}

	public static void Beautify() {

		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			// UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");//��ͺ�
			// UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");//
			// ��Բ��ť+��ɫ��ť����
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");// ��ɫ���
			new ShowJframe("����״̬����").initMain();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
