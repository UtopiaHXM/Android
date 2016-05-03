package com.example.cleverlampcontrol;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;





public class FileDeal {

	public ArrayList<byte[]> readtxtFile(String path) {
		ArrayList<byte[]> dataArrayList = new ArrayList<byte[]>();
		byte[] databyte = new byte[10];
		Log.d("aa","path:"+path);
		File txtFile = new File(path);
		try {
			FileInputStream inputStream = new FileInputStream(txtFile);
			try {
				while(inputStream.read(databyte)!=-1)
				{
					dataArrayList.add(databyte);
					String aaString= new String(databyte);
					Log.d("aa","file.."+aaString);
					databyte=new byte[10];
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataArrayList;
	}
	/**
	 * 获取文件夹中的所有txt或xls格式文件的名�?
	 * @param path File�?
	 * @param filetype  文件类型
	 * @return  包含文件类型的list�?
	 */
	public ArrayList<String> getFileNameListTxt(File path, String filetype) {

		ArrayList<String> FileNameList = new ArrayList<String>();

		if (path.isDirectory()) {

			if (path.exists()) {
				path.mkdir(); // path定义为目�?
			}
			MyFilter myfilter = new MyFilter(filetype);// 更改txt，换成所�?��文件格式
			File[] fileNum = path.listFiles(myfilter);// 获取目录下的�?��txt格式文件

			for (int i = 0; i < fileNum.length; i++) {

				String fileName = fileNum[i].getName();
				// 添加
				FileNameList.add(fileName);
				Log.d("file",".."+fileName);
			}
		}
		return FileNameList;
	}

	/**
	 * 文件过滤�?
	 * @author Administrator
	 *
	 */
	public class MyFilter implements FilenameFilter {
		// type为需要过滤的条件，比如如果type=".jpg"，则只能返回后缀为jpg的文�?
		private String type;

		public MyFilter(String type) {
			this.type = type;
		}

		public boolean accept(File dir, String name) {
			return name.endsWith(type); // 返回true的文件则合格
		}
	}
}
