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
							else if (bstring.equals("获取文件夹")) {
								isFunction.OpenFolder(bstring, outputStream);
							} else if ((bstring.indexOf(".ppt") != -1
									|| bstring.indexOf(".dps") != -1)&&(!bstring.startsWith("上传"))&&(!bstring.startsWith("下载"))) {
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
							}  else if (bstring.equals("删除文件")) {
								File currentFile = getFile();
								isFunction.DeleteFile(bstring, currentFile,
										outputStream);
							} else if (bstring.equals("最大化文件")) {
								if (isFunction.MaxSlide(MAX)) {
									outputStream.write("Max".getBytes());
								}
							} else if (bstring.equals("退出最大化")) {
								isFunction.unMaxSlide(UNMAX);
							} else if (bstring.equals("关闭文件")) {
								isFunction.CloseFile(CLOSE);
							}
							
							else if (bstring.startsWith("上传")) {
								System.out.println("========上传");
																String fileName = bstring.substring(3);
								System.out.println("文件名::"+fileName);
																new Thread(new AcceptUpFile(fileName,inputStream, outputStream)).start();								
															}
															else if(bstring.startsWith("下载")){
								System.out.println("========下载");
																String fileName = bstring.substring(3);
								System.out.println("文件名::"+fileName);
								outputStream.write("download".getBytes());
																new Thread(new DownFile(fileName,outputStream)).start();
															}
							else if (bstring.equals("断开连接")) {
								MyLittleTray.getTrayIcon().displayMessage(
										"服务端", "连接断开，退出程序！",
										TrayIcon.MessageType.INFO);
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
								timer.schedule(task, 2000, 2000); // 这个命令就是2秒钟之后执行TimerTask里边的内容，后边的执行时间间隔为2秒钟。
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
