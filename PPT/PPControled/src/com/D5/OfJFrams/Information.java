package com.D5.OfJFrams;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.D5.Function.getIPOFPC;
import com.D5.MyBean.Inform;

@SuppressWarnings("serial")
public class Information extends JFrame {
	private String StringPath = "★设置路径：";
	private String StringWifi = "★wifi设置  ：";
	private String StringBlue = "★蓝牙设置：";
	private String StringMatter = "★注意事项：";
	private String StringLocalName = "★本地蓝牙：";
	private String StringLocalIP = "★  本地 IP   :";
			
	

	private String StringPathS = "确保ppt文件在所设置路径下，若没有设置路径，其默认路径为桌面。";
	private String StringBlueS = "确定手机端和电脑端的蓝牙可以被搜索，以确保蓝牙配对成功。";
	private String StringWifiS = "确定在同一局域网下连接。";
	private String StringMatterS = "电脑端为服务端，手机端为客户端。且只支持一种连接方式。";
	private String LocalBlueName = "";
	private String LocalWIFIIP = "";

	private Box baseBox, box1, box2;

	public Information() {
		LocalDevice device = null;
		try {
			device = LocalDevice.getLocalDevice();
		} catch (BluetoothStateException e1) {
		}
		if(device!=null) {
			LocalBlueName = device.getFriendlyName();
		}else{
			LocalBlueName = "本地没有蓝牙";
		}
		LocalWIFIIP = getIPOFPC.getLocalIp();
	}

	public void SetShow() {
		setSize(550, 190);
		setTitle("步骤与说明");
		setLocation(450, 350);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		
		
		
		box1 = Box.createVerticalBox();
		box1.add(new JLabel(StringPath));
		box1.add(Box.createVerticalStrut(8));
		box1.add(new JLabel(StringWifi));
		box1.add(Box.createVerticalStrut(8));
		box1.add(new JLabel(StringBlue));
		box1.add(Box.createVerticalStrut(8));
		box1.add(new JLabel(StringMatter));
		box1.add(Box.createVerticalStrut(8));
		box1.add(new JLabel(StringLocalIP));
		box1.add(Box.createVerticalStrut(8));
		box1.add(new JLabel(StringLocalName));
		box1.add(Box.createVerticalStrut(8));

		box2 = Box.createVerticalBox();
		box2.add(new JLabel(StringPathS));
		box2.add(Box.createVerticalStrut(8));
		box2.add(new JLabel(StringWifiS));
		box2.add(Box.createVerticalStrut(8));
		box2.add(new JLabel(StringBlueS));
		box2.add(Box.createVerticalStrut(8));
		box2.add(new JLabel(StringMatterS));
		box2.add(Box.createVerticalStrut(8));
		box2.add(new JLabel(LocalWIFIIP));
		box2.add(Box.createVerticalStrut(8));
		box2.add(new JLabel(LocalBlueName));
		box2.add(Box.createVerticalStrut(8));

		baseBox = Box.createHorizontalBox();
		baseBox.add(box1);
		baseBox.add(Box.createHorizontalStrut(10));
		baseBox.add(box2);
		setLayout(new FlowLayout());
		add(baseBox);

		//setResizable(false);
		Image image = Toolkit.getDefaultToolkit().getImage("Image/pp.png");
		setIconImage(image);

		validate();
		Inform.getInfor().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (e.getID() == WindowEvent.WINDOW_CLOSING) {
					Inform.setIn(false);
				}
			}
		});
		Inform.setIn(true);
	}
}
