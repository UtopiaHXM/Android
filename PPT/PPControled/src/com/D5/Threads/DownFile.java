package com.D5.Threads;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import com.D5.MyBean.MyChooser;

public class DownFile implements Runnable {

	private String file;
	private OutputStream outputStream;

	public DownFile(String file, OutputStream outputStream) {
		this.file = file;
		this.outputStream = outputStream;
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("========DownFileThread is run=======");

		try {
			sendMessage(file, outputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendMessage(String fileName, OutputStream outputStream)
			throws Exception {
		String savePath = MyChooser.getChoosePath() + "\\" + fileName;
		System.out.println("savePath::" + savePath);
		File file = new File(savePath);
		if (file.exists()) {
			System.out.println("文件存在  开始下载");

			DataInputStream fis = new DataInputStream(new FileInputStream(
					savePath));

			DataOutputStream ps = new DataOutputStream(outputStream);
			// 将文件名及长度传给客户端。这里要真正适用所有平台，例如中文名的处理，还需要加工，具体可以参见Think In Java
			// 4th里有现成的代码。
			ps.writeUTF(file.getName());
			System.out.println(file.getName());
			ps.flush();
			ps.writeLong((long) file.length());
			ps.flush();

			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int read = -1;
			int passedlen = 0;
			long len = 0;
			len = ((DataInput) fis).readLong();
			if (fis != null) {
				while ((read = fis.read(buf)) != -1) {
					try {
					} catch (Exception e) {
						ps.write(buf, 0, read);
					}

					passedlen += read;
					/*
					 * if (read == -1) { System.out.println(read); break; }
					 */
					// 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
					int temp = (int) (passedlen * 100 / len);
					System.out.println("download文件接收了" + (passedlen * 100 / len)
							+ "%\n");
					System.out.println(temp);

				}
				outputStream.write("下载完成！".getBytes());
				System.out.println("发送完成！");
			}
			ps.flush();
			// 注意关闭socket链接哦，不然客户端会等待server的数据过来，
			// 直到socket超时，导致数据不完整。
			fis.close();
			System.out.println("文件传输完成");
		} else {
			System.out.println("文件不存在！");
			outputStream.write("该文件不存在".getBytes());
		}

	}

}
