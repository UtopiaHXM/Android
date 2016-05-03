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
			System.out.println("�ļ�����  ��ʼ����");

			DataInputStream fis = new DataInputStream(new FileInputStream(
					savePath));

			DataOutputStream ps = new DataOutputStream(outputStream);
			// ���ļ��������ȴ����ͻ��ˡ�����Ҫ������������ƽ̨�������������Ĵ�������Ҫ�ӹ���������Բμ�Think In Java
			// 4th�����ֳɵĴ��롣
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
					// �����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
					int temp = (int) (passedlen * 100 / len);
					System.out.println("download�ļ�������" + (passedlen * 100 / len)
							+ "%\n");
					System.out.println(temp);

				}
				outputStream.write("������ɣ�".getBytes());
				System.out.println("������ɣ�");
			}
			ps.flush();
			// ע��ر�socket����Ŷ����Ȼ�ͻ��˻�ȴ�server�����ݹ�����
			// ֱ��socket��ʱ���������ݲ�������
			fis.close();
			System.out.println("�ļ��������");
		} else {
			System.out.println("�ļ������ڣ�");
			outputStream.write("���ļ�������".getBytes());
		}

	}

}
