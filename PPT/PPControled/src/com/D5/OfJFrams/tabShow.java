package com.D5.OfJFrams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

@SuppressWarnings("serial")
public class tabShow extends JFrame {
	public static List<byte[]> list = null;// �����������ļ����ļ���

	public static List<byte[]> getList() {
		return list;
	}

	public static void setList(List<byte[]> l) {
		list = l;
	}

	// ��ȡ���PPT���ļ���
	public static File createFileCollection() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File file = fsv.getHomeDirectory();
		if (file.exists()) {

		} else {
			file.mkdir();
		}
		return file;
	}

	// ��ȡָ���ļ����е��ļ���
	public static List<String> getFilesNameString(File url) {
		List<String> list = new ArrayList<String>();
		if (url.listFiles().length <= 0) {

		} else {
			int length = url.listFiles().length;
			File[] files = new File[length];
			files = url.listFiles();
			if (files == null) {

			}
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (fileName.endsWith(".ppt") || fileName.endsWith("dps")) {
					list.add(fileName);
				}
			}
		}
		return list;
	}

	// ��ȡ���������������ļ���;
	public static List<byte[]> getFilesNameByte(File url) {
		List<byte[]> list = new ArrayList<byte[]>();
		List<String> l;
		if (url.listFiles().length <= 0) {

		} else {
			l = getFilesNameString(url);
			for (int i = 0; i < l.size(); i++) {
				String str = l.get(i);
				byte[] fileName = strToByte(str);
				list.add(fileName);
			}
		}

		return list;
	}

	// �������ļ���ת��Ϊbyte����
	public static byte[] strToByte(String str) {
		byte[] fileNames = new byte[1024];
		fileNames = str.getBytes();
		return fileNames;
	}

	public static String strArrayToString(String[] str) {

		String info = "";
		for (int i = 0; i < str.length; i++) {
			if (i == 0) {
				info = str[0] + "\r\n";
			} else {
				info = info + str[i] + "\r\n";
			}
		}
		return info;
	}
}
