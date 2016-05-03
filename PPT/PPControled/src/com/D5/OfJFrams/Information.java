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
	private String StringPath = "������·����";
	private String StringWifi = "��wifi����  ��";
	private String StringBlue = "���������ã�";
	private String StringMatter = "��ע�����";
	private String StringLocalName = "�ﱾ��������";
	private String StringLocalIP = "��  ���� IP   :";
			
	

	private String StringPathS = "ȷ��ppt�ļ���������·���£���û������·������Ĭ��·��Ϊ���档";
	private String StringBlueS = "ȷ���ֻ��˺͵��Զ˵��������Ա���������ȷ��������Գɹ���";
	private String StringWifiS = "ȷ����ͬһ�����������ӡ�";
	private String StringMatterS = "���Զ�Ϊ����ˣ��ֻ���Ϊ�ͻ��ˡ���ֻ֧��һ�����ӷ�ʽ��";
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
			LocalBlueName = "����û������";
		}
		LocalWIFIIP = getIPOFPC.getLocalIp();
	}

	public void SetShow() {
		setSize(550, 190);
		setTitle("������˵��");
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
