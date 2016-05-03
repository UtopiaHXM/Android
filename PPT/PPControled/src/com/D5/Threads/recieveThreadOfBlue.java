package com.D5.Threads;

import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.io.StreamConnection;

import com.D5.Function.IsFunction;
import com.D5.MyBean.MyChooser;
import com.D5.OfJFrams.MyLittleTray;


public class recieveThreadOfBlue implements Runnable {

	private File file = null;
	private StreamConnection streamconnection;
	private InputStream inputStream;
	private static OutputStream outputStream;
	private int UPDOWN;
	private int MAX = 5;
	private int UNMAX = 6;
	private int CLOSE = 7;
	private String bstring;
	private IsFunction isFunction = new IsFunction();

	public static OutputStream getOutputStream() {
		return outputStream;
	}

	public static void setOutputStream(OutputStream outputStream) {
		recieveThreadOfBlue.outputStream = outputStream;
	}

	public recieveThreadOfBlue(StreamConnection streamconnection) {
		this.streamconnection = streamconnection;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void run() {

		ReadBlue();

	}

	public void ReadBlue() {

		if (streamconnection != null) {

			try {
				inputStream = streamconnection.openInputStream();
				outputStream = streamconnection.openOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (true) {
				try {
					while (inputStream.available() > 0) {
						if (inputStream.available() > 0) {
							byte[] b_byte = new byte[inputStream.available()];
							inputStream.read(b_byte);
							bstring = new String(b_byte,
									Charset.forName("utf-8"));
							if (bstring.equals("1") || bstring.equals("2")) {
								UPDOWN = Integer.valueOf(bstring);
								isFunction.TurnPage(UPDOWN);
							}
							else if (bstring.equals("��ȡ�ļ���")) {
								isFunction.OpenFolder(bstring, outputStream);
							} else if ((bstring.indexOf(".ppt") != -1
									|| bstring.indexOf(".dps") != -1)&&(!bstring.startsWith("�ϴ�"))&&(!bstring.startsWith("����"))) {
								String path = MyChooser.getChoosePath();
								String filename = bstring;
								String FileAdd = path + "\\" + filename;
								file = new File(FileAdd);
								if (file.exists()) {
									setFile(file);
									isFunction.OpenFile(FileAdd, outputStream);
								} else {
									outputStream.write("�ļ�������".getBytes());
								}
							}  else if (bstring.equals("ɾ���ļ�")) {
								File currentFile = getFile();
								isFunction.DeleteFile(bstring, currentFile,
										outputStream);
							} else if (bstring.equals("����ļ�")) {
								if (isFunction.MaxSlide(MAX)) {
									outputStream.write("Max".getBytes());
								}
							} else if (bstring.equals("�˳����")) {
								isFunction.unMaxSlide(UNMAX);
							} else if (bstring.equals("�ر��ļ�")) {
								isFunction.CloseFile(CLOSE);
							}
							
							else if (bstring.startsWith("�ϴ�")) {
								System.out.println("========�ϴ�");
																String fileName = bstring.substring(3);
								System.out.println("�ļ���::"+fileName);
																new Thread(new AcceptUpFile(fileName,inputStream, outputStream)).start();								
															}
															else if(bstring.startsWith("����")){
								System.out.println("========����");
																String fileName = bstring.substring(3);
								System.out.println("�ļ���::"+fileName);
								outputStream.write("download".getBytes());
																new Thread(new DownFile(fileName,outputStream)).start();
															}
							else if (bstring.equals("�Ͽ�����")) {
								MyLittleTray.getTrayIcon().displayMessage(
										"�����", "���ӶϿ����˳�����",
										TrayIcon.MessageType.INFO);
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
								timer.schedule(task, 2000, 2000); // ����������2����֮��ִ��TimerTask��ߵ����ݣ���ߵ�ִ��ʱ����Ϊ2���ӡ�
							}
							else {

							}
						}

					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {

		}

	}

}
