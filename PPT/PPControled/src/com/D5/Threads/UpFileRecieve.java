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
		// �õ��ͻ��˷����ĵ�һ��Э�����ݣ�Content-Length=143253434;filename=xxx.3gp;

		DataInputStream dataInputStream = null;
		try {
			dataInputStream = new DataInputStream(new BufferedInputStream(
					inputStream));
		} catch (Exception e) {
			System.out.print("������Ϣ�������\n");
			return;
		}

		try {
			// ���ر���·�����ļ������Զ��ӷ������˼̳ж�����
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

			System.out.println("�ļ��ĳ���Ϊ:" + len + "\n");
			System.out.println("��ʼ�����ļ�!" + "\n");

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
				// �����������Ϊͼ�ν����prograssBar���ģ���������Ǵ��ļ������ܻ��ظ���ӡ��һЩ��ͬ�İٷֱ�
				int temp = (int) (passedlen * 100 / len);
				System.out.println("�ļ�������" + (passedlen * 100 / len) + "%\n");
				fileOut.write(buf, 0, read);
				if (temp == 100) {
					flag = false;
					break;
				}
			}
			System.out.println("������ɣ��ļ���Ϊ" + savePath + "\n");
			fileOut.close();
		} catch (Exception e) {
			System.out.println("������Ϣ����" + "\n");
			System.out.println(e);
			return;
		}
	}

}
