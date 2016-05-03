package com.D5.OfJFrams;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.D5.MyBean.MyChooser;

@SuppressWarnings("serial")
public class importPPT extends JFrame {

	private File file;
	private int count = 0;
	private Boolean isExit = false;
	private JButton ok = new JButton("ȷ��");
	private JButton url = new JButton("���");
	private JFileChooser chooser = new JFileChooser("");
	public JTextField textArea = new JTextField("����������ָ�����ļ���·��");
	

	private Box baseBox, box1,box2;
	

	public JTextField getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextField textArea) {
		this.textArea = textArea;
	}

	public File getFile() {
		return file;
	}

	public Boolean getIsExit() {
		return isExit;
	}

	public void setIsExit(Boolean isExit) {
		this.isExit = isExit;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public importPPT() {
		

	}

	public void SetShow() {
		setSize(240, 110);
		setVisible(true);
		setTitle("����ppt");
		setLocation(500, 400);
		setResizable(false);
		Image image = Toolkit.getDefaultToolkit().getImage("Image/pp.png");
		setIconImage(image);
		
		box1 = Box.createHorizontalBox();
		box1.add(textArea);
		box1.add(Box.createHorizontalStrut(0));
		
		box2 = Box.createHorizontalBox();
		box2.add(url);
		box2.add(Box.createHorizontalStrut(8));
		box2.add(ok);
		box2.add(Box.createHorizontalStrut(8));
		
		baseBox = Box.createVerticalBox();
		baseBox.add(Box.createVerticalStrut(8));
		baseBox.add(box1);
		baseBox.add(Box.createVerticalStrut(8));
		baseBox.add(box2);
		
		setLayout(new FlowLayout());
		add(baseBox);
	}

	public void setImport() {
		// �����ť
		url.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setCurrentDirectory(tabShow.createFileCollection());
				chooser.showOpenDialog(MyChooser.getImportppt());
				MyChooser.getImportppt().getTextArea()
						.setText(tabShow.createFileCollection().toString());
				MyChooser.getImportppt().file = tabShow.createFileCollection();
				// ѡ����ļ����£�����ȡ���ļ����µ�ppt�ļ�
				if(chooser.getSelectedFile()!=null) {
					String choosePath = chooser.getSelectedFile().getPath();
					MyChooser.getImportppt().getTextArea().setText(choosePath);
					File file = new File(choosePath);
					MyChooser.setChoosePath(choosePath);
					MyChooser.getImportppt().setFile(file);
					MyChooser.setSetPath(true);
				}
			}
		});
		// ȷ�ϰ�ť
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<byte[]> list = tabShow.getFilesNameByte(MyChooser
						.getImportppt().getFile());
				tabShow.setList(list);
				dispose();
				MyChooser.getImportppt().setIsExit(false);
				MyChooser.getImportppt().getTextArea()
						.setText("����������ָ�����ļ���·��");
				MyLittleTray.getTrayIcon().displayMessage("�����",
						"·�����óɹ�,��ѡ�����ӷ�ʽ", TrayIcon.MessageType.INFO);
			}
		});
		MyChooser.getImportppt().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (e.getID() == WindowEvent.WINDOW_CLOSING) {
					MyChooser.getImportppt().setIsExit(false);
				}
			}
		});
	}

	public void showChooseFrame() {
		setIsExit(true);
		SetShow();
		setImport();
	}
	
	
	
}
