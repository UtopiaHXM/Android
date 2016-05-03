package com.D5.Threads;

import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;
import com.D5.Function.IsFunction;
import com.D5.MyBean.MyChooser;
import com.D5.OfJFrams.MyLittleTray;

public class recieveThreadOfWifi implements Runnable {

	private InputStream inputStream;
	private static OutputStream outputStream;
	private Socket socket;
	private File file;
	private int b;
	private String bstring;
	private int UPDOWN;
	private final int MAX = 5;

	public static OutputStream getOutputStream() {
		return outputStream;
	}

	private IsFunction isFunction = new IsFunction();

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	recieveThreadOfWifi(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		Readwifi();
	}

	public void Readwifi() {
		if (socket != null) {
			try {
				inputStream = socket.getInputStream();
				outputStream = socket.getOutputStream();

				while (true) {
					while (inputStream.available() > 0) {

						if (inputStream.available() > 0) {
							byte[] b_byte = new byte[inputStream.available()];
							b = inputStream.read(b_byte);
							bstring = new String(b_byte,
									Charset.forName("utf-8"));

							if (bstring.equals("1") || bstring.equals("2")) {
								UPDOWN = Integer.valueOf(bstring);
								isFunction.TurnPage(UPDOWN);
							} else if (bstring.equals("获取文件夹")) {
								isFunction.OpenFolder(bstring, outputStream);
							} else if ((bstring.indexOf(".ppt") != -1 || bstring
									.indexOf(".dps") != -1)
									&& (!bstring.startsWith("上传"))
									&& (!bstring.startsWith("下载"))) {
								String path = MyChooser.getChoosePath();
								String filename = bstring;
								String FileAdd = path + "\\" + filename;
								file = new File(FileAdd);
								if (file.exists()) {
									setFile(file);
									isFunction.OpenFile(FileAdd, outputStream);
								} else {
									outputStream.write("文件不存在".getBytes());
								}
							} else if (bstring.equals("删除文件")) {
								File currentFile = getFile();
								isFunction.DeleteFile(bstring, currentFile,
										outputStream);
							} else if (bstring.equals("最大化文件")) {
								if (isFunction.MaxSlide(MAX)) {
									outputStream.write("Max".getBytes());
								}
							} else if (bstring.equals("退出最大化")) {
								isFunction.unMaxSlide(6);
							} else if (bstring.equals("关闭文件")) {
								isFunction.CloseFile(7);
							} else if (bstring.equals("连接成功")) {

							} else if (bstring.contains("跳到具体某一页")) {
								System.out.println("bstring：" + bstring);
								String num = bstring.substring(0,
										bstring.indexOf("跳到具体某一页"));
								System.out.println("页数：" + num);
								int[] pages = new int[num.length()];
								for (int i = 0; i < num.length(); i++) {
									// System.out.println(i + ":" +
									// num.charAt(i));
									pages[i] = Integer.parseInt(num.charAt(i)
											+ "");
								}
								isFunction.JumpToPage(8, pages);

							} else if (bstring.equals("断开连接")) {

								MyLittleTray.getTrayIcon().displayMessage(
										"服务端", "连接断开，退出程序！",
										TrayIcon.MessageType.INFO);
								final Timer timer = new Timer();
								TimerTask task = new TimerTask() {
									public void run() {
										System.exit(0);

									}
								};
								timer.schedule(task, 3000, 2000); // 这个命令就是3秒钟之后执行TimerTask里边的内容，后边的执行时间间隔为2秒钟。
							} else if (bstring.startsWith("上传")) {
								System.out.println("========上传");
								String fileName = bstring.substring(2);
								System.out.println("文件名::" + fileName);
								// new Thread(new AcceptUpFile(fileName,
								// inputStream, outputStream)).start();
								new UpFileRecieve(fileName, inputStream, outputStream);
							} else if (bstring.startsWith("下载")) {
								System.out.println("========下载");
								String fileName = bstring.substring(3);
								System.out.println("文件名::" + fileName);
								outputStream.write("download".getBytes());
								new Thread(new DownFile(fileName, outputStream))
										.start();
							} else {

							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

		}
	}

	private String getTransFileName(String string) {
		String[] infos = string.split(" ");
		return infos[infos.length - 1];
	}

}
