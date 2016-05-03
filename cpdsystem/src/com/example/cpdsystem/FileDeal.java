package com.example.cpdsystem;


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

	public ArrayList<String> getFileNameListTxt(File path, String filetype) {

		ArrayList<String> FileNameList = new ArrayList<String>();

		if (path.isDirectory()) {

			if (path.exists()) {
				path.mkdir();
			}
			MyFilter myfilter = new MyFilter(filetype);
			File[] fileNum = path.listFiles(myfilter);

			for (int i = 0; i < fileNum.length; i++) {

				String fileName = fileNum[i].getName();
				FileNameList.add(fileName);
				Log.d("file",".."+fileName);
			}
		}
		return FileNameList;
	}

	public class MyFilter implements FilenameFilter {
		
		private String type;

		public MyFilter(String type) {
			this.type = type;
		}

		public boolean accept(File dir, String name) {
			return name.endsWith(type);
		}
	}
}
