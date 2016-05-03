package com.D5.Threads;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.D5.MyBean.MyChooser;

public class UpFileRecieve {
	private OutputStream outputStream;
	private InputStream inputStream;
	private boolean flag = true;
	private String fileName;

	public UpFileRecieve(String fileName, InputStream inputStream,
			OutputStream outputStream) throws IOException {
		this.fileName = fileName;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		DoUpFile(this.fileName, this.inputStream, this.outputStream);
	}

	public void DoUpFile(String fileName, InputStream inputStream,
			OutputStream outputStream) throws IOException {
		// 得到客户端发来的第一行协议数据：Content-Length=143253434;filename=xxx.3gp;

		DataInputStream dataInputStream = null;
		try {
			dataInputStream = new DataInputStream(new BufferedInputStream(
					inputStream));
		} catch (Exception e) {
			System.out.print("接收消息缓存错误\n");
			return;
		}

		try {
			// 本地保存路径，文件名会自动从服务器端继承而来。
			String savePath = MyChooser.getChoosePath();
			System.out.println("savePath==" + savePath);
			int bufferSize = 8192;
			byte[] buf = new byte[bufferSize];
			int passedlen = 0;
			long len = 0;

			savePath += "/" + fileName;
			System.out.println("fileName:" + fileName);
			System.out.println("filePath==" + savePath);

			DataOutputStream fileOut = new DataOutputStream(
					new FileOutputStream(savePath));
			len = dataInputStream.readLong();

			System.out.println("文件的长度为:" + len + "\n");
			System.out.println("开始接收文件!" + "\n");

			while (flag) {
				int read = 0;
				if (dataInputStream != null) {
					read = dataInputStream.read(buf);
					System.out.println(read);
				}
				passedlen += read;
				/*
				 * if (read == -1) { System.out.println(read); break; }
				 */
				// 下面进度条本为图形界面的prograssBar做的，这里如果是打文件，可能会重复打印出一些相同的百分比
				int temp = (int) (passedlen * 100 / len);
				System.out.println("文件接收了" + (passedlen * 100 / len) + "%\n");
				fileOut.write(buf, 0, read);
				if (temp == 100) {
					flag = false;
					break;
				}
			}
			System.out.println("接收完成，文件存为" + savePath + "\n");
			fileOut.close();
		} catch (Exception e) {
			System.out.println("接收消息错误" + "\n");
			System.out.println(e);
			return;
		}
	}

}
