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
			// UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");//柔和黑
			// UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");//
			// 椭圆按钮+黄色按钮背景
			UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");// 黑色风格
			new ShowJframe("连接状态管理").initMain();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
